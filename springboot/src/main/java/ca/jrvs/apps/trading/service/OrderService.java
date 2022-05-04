package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.MarketOrderDto;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private AccountDao accountDao;
    private PositionDao positionDao;
    private SecurityOrderDao securityOrderDao;
    private QuoteDao quoteDao;

    @Autowired
    public OrderService(QuoteDao quoteDao, AccountDao accountDao, PositionDao positionDao,
                        SecurityOrderDao securityOrderDao) {
        this.quoteDao = quoteDao;
        this.accountDao = accountDao;
        this.positionDao = positionDao;
        this.securityOrderDao = securityOrderDao;
    }

    /**
     * Execute a market order
     *
     * - validate the order(e.g.size and ticker)
     * - create a securityOrder(for security_order table)
     * - Handle buy or sell order
     *   - buy order: check account balance (calls helper method)
     *   - sell order: check position for the ticker/symbol (calls helper method)
     *   - (please don't forget to update securityOrder.status)
     * - Save and return securityOrder
     *
     * NOTE: you will need to some helper methods (protected or private)
     *
     * @param orderDto market order
     * @return SecurityOrder from security_order table
     * @throws org.springframework.dao.DataAccessException if unable to get data from DAO
     * @throws IllegalArgumentException for invalid input
     * */
    public SecurityOrder executeMarketOrder(MarketOrderDto orderDto) {
        if (!validateOrder(orderDto)) {
            throw new IllegalArgumentException("Invalid user input.");
        }

        String ticker = orderDto.getTicker();
        Integer size = orderDto.getSize();
        Integer id = orderDto.getID();
        Double price = null;
        SecurityOrder securityOrder = new SecurityOrder();
        try {
            price = quoteDao.findById(ticker).get().getLastPrice();
        } catch (Exception e) {
            throw new InvalidDataAccessResourceUsageException("Failed to access quote information");
        }

        if (orderDto.getType().toUpperCase(Locale.ROOT).equals("BUY")) {
            if (getAccountBalance(id) > size * price) {
                securityOrder.setTicker(ticker);
                securityOrder.setStatus("FILLED");
                securityOrder.setID(id);
                securityOrder.setAccount_id(id);
                securityOrder.setSize(size);
                securityOrder.setPrice(price);
                securityOrder.setNotes("Buy request");
                updateAccountBalance(id, -price * size);

            }
        } else {
            if (getPosition(id, ticker) >= size) {
                securityOrder.setTicker(ticker);
                securityOrder.setStatus("FILLED");
                securityOrder.setID(id);
                securityOrder.setAccount_id(id);
                securityOrder.setSize(-size);
                securityOrder.setPrice(price);
                securityOrder.setNotes("Sell request");
                updateAccountBalance(id, price * size);


            }
        }

        securityOrderDao.save(securityOrder);
        return securityOrder;
    }

    private boolean validateOrder(MarketOrderDto marketOrderDto) {
        Integer size = marketOrderDto.getSize();
        String ticker = marketOrderDto.getTicker();
        return size > 0 && ticker != null && quoteDao.existsById(ticker);
    }

    private Double getAccountBalance(Integer id) {
        return accountDao.findById(id).get().getAmount();
    }

    private Integer getPosition(Integer id, String ticker) {
        return positionDao.findAllById(Arrays.asList(id)).stream()
                .filter(position -> position.getTicker().equals(ticker)).collect(
                        Collectors.toList()).get(0).getPosition();
    }

    private void updateAccountBalance(Integer id, Double change) {
        Account account = accountDao.findById(id).get();
        account.setAmount(change + getAccountBalance(id));
        accountDao.updateOne(account);
    }
}