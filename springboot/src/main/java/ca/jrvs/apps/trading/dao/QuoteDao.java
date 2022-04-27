package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;


@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

    private static final String TABLE_NAME = "quote";
    private static final String ID_COLUMN_NAME = "ticker";

    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public QuoteDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }

    @Override
    public Quote save(Quote quote) {
        if (existsById(quote.getTicker())) {
            int updateRowNo = updateOne(quote);
            if (updateRowNo != 1) {
                throw new DataRetrievalFailureException("Unable to update quote");
            }
        } else {
            addOne(quote);
        }
        return quote;
    }

    /**
     * helper method that saves on quote
     */
    private void addOne(Quote quote) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
        int row = simpleJdbcInsert.execute(parameterSource);
        if (row != 1) {
            throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
        }
    }

    /**
     * helper method that updates one quote
     */
    private int updateOne(Quote quote) {
        String update_sql = "UPDATE quote SET last_price=?, bid_price=?,"
                + " bid_size=?, ask_price=?, ask_size=? WHERE ticker=?";

        return jdbcTemplate.update(update_sql, makeUpdateValues(quote));
    }

    /**
     * helper method that make sql update values objects
     *
     * @param quote to be updated
     * @return UPDATE_SQL values
     */
    private Object[] makeUpdateValues(Quote quote) {
        Object[] update_values = {quote.getLastPrice(), quote.getBidPrice(), quote.getBidSize(),
                quote.getAskPrice(), quote.getAskSize(), quote.getTicker()};
        return update_values;
    }

    @Override
    public <S extends Quote> List<S> saveAll(Iterable<S> quotes) {
        List<Quote> quoteList = new ArrayList<>();

        for (Quote quote : quotes) {
            quoteList.add(save(quote));
        }
        return (List<S>) quoteList;
    }

    /**
     * Find a quote by ticker
     *
     * @param ticker name
     * @return quote or Optional.empty if not found
     */
    @Override
    public Optional<Quote> findById(String ticker) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";
        Optional<Quote> quote = null;
        try {
            quote = Optional.ofNullable(
                    jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Quote.class),
                            ticker));
        } catch (EmptyResultDataAccessException e) {
            logger.debug("Can not find trader id:" + ID_COLUMN_NAME, e);
        }

        if (quote.isPresent()) {
            return quote;
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(String ticker) {
        Optional<Quote> foundQuote;
        try {
            foundQuote = findById(ticker);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }

        if (foundQuote.isPresent()) {
            return true;
        }

        return false;
    }

    /**
     * return all quotes
     *
     * @throws org.springframework.dao.DataAccessException if failed to update
     */
    @Override
    public Iterable<Quote> findAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        try {
            List<Quote> quotes = jdbcTemplate.query(query,
                    BeanPropertyRowMapper.newInstance(Quote.class));
            return quotes;
        } catch (DataAccessException e) {
            throw new DataRetrievalFailureException("Can not get the value from database", e);
        }
    }

    @Override
    public long count() {
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME;
        return jdbcTemplate.queryForObject(query, Long.class);
    }

    @Override
    public void deleteById(String ticker) {
        if (ticker == null) {
            throw new IllegalArgumentException("ID can't be null");
        }
        String query = "DELETE FROM " + TABLE_NAME + "WHERE " + ID_COLUMN_NAME + " =?";
        jdbcTemplate.update(query, ticker);

    }

    @Override
    public void deleteAll() {
        String query = "DELETE FROM " + TABLE_NAME;
        jdbcTemplate.update(query);
    }

    @Override
    public void deleteAll(Iterable<? extends Quote> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(Quote quote) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Iterable<Quote> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }


}