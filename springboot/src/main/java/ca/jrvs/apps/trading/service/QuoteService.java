package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class QuoteService {

    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    private QuoteDao quoteDao;
    private MarketDataDao marketDataDao;

    @Autowired
    public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
        this.quoteDao = quoteDao;
        this.marketDataDao = marketDataDao;
    }

    /**
     * Update quote table against IEX source
     *  - get all quotes from the db
     *  - foreach ticker get iexQuote
     *  - convert iexQuote to quote entity
     *  - persist quote to db
     *
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException                    for invalid input
     */
    public void updateMarketData() {
        List<Quote> quotes = (List<Quote>) quoteDao.findAll();
        List<IexQuote> iexQuotes = quotes.stream().map(quote -> findIexQuoteByTicker(quote.getID()))
                .collect(Collectors.toList());
        List<Quote> updatedQuotes = iexQuotes.stream().map(quote -> buildQuoteFromIexQuote(quote))
                .collect(Collectors.toList());
        quoteDao.saveAll(updatedQuotes);
    }

    /**
     * Helper method.
     * Map an IexQuote to a Quote entity
     * Note: `iexQuote.getLatestPrice() == null if the stock market is close.
     * Make sure set a default value for number fields(s).
     */
    protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
        Quote quote = new Quote();
        quote.setTicker(iexQuote.getSymbol());
        quote.setBidPrice(iexQuote.getIexBidPrice());
        quote.setBidSize(iexQuote.getIexBidSize());
        quote.setAskPrice(iexQuote.getIexAskPrice());
        quote.setAskSize(iexQuote.getIexAskSize());
        quote.setLastPrice(iexQuote.getLatestPrice());
        return quote;
    }

    /**
     * Validate (against IEX) and save given tickers to quote table
     * - Get iexQuote(s)
     * - convert each iexQuote to Quote entity
     * - persist the quote to db
     *
     * @param tickers a list of tickers/symbols
     * @throws IllegalArgumentException if ticker is not found from IEX
     */
    public List<Quote> saveQuotes(List<String> tickers) {
        List<IexQuote> iexQuotes = tickers.stream().map(ticker -> findIexQuoteByTicker(ticker))
                .collect(Collectors.toList());
        List<Quote> quotes = iexQuotes.stream().map(quote -> buildQuoteFromIexQuote(quote))
                .collect(Collectors.toList());

        quotes.stream().forEach(quote -> saveQuote(quote));
        return quotes;
    }

    /**
     * Helper Method
     * @param ticker
     * @return
     */
    public Quote saveQuote(String ticker) {
        IexQuote iexQuote = findIexQuoteByTicker(ticker);
        Quote quote = buildQuoteFromIexQuote(iexQuote);
        return quoteDao.save(quote);
    }

    /**
     * Find an IexQuote
     *
     * @param ticker id
     * @return IexQuote object
     * @throws IllegalArgumentException if ticker is invalid
     */
    public IexQuote findIexQuoteByTicker(String ticker) {
        return marketDataDao.findById(ticker).orElseThrow(() -> new IllegalArgumentException(ticker + "is invalid"));
    }

    /**
     * Update a given quote to quote table without validation
     *
     * @param quote
     */
    public Quote saveQuote(Quote quote) {
        return quoteDao.save(quote);
    }

    /**
     * Find all quotes from the quote table
     *
     * @return a list of quotes
     */
    public List<Quote> findAllQuotes() {
        return (List<Quote>) quoteDao.findAll();
    }
}