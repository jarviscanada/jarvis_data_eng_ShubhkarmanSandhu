package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
    @Service
    public class QuoteService {


        private QuoteDao quoteDao;
        private MarketDataDao marketDataDao;

        @Autowired
        public QuoteService(MarketDataDao marketDataDao, QuoteDao quoteDao) {
            this.marketDataDao = marketDataDao;
            this.quoteDao = quoteDao;
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
}
