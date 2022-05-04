package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class PositionDao {

    private static final Logger logger = LoggerFactory.getLogger(PositionDao.class);

    private final String TABLE_NAME = "position";
    private final String ID_COLUMN = "account_id";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public PositionDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID_COLUMN);
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return this.simpleJdbcInsert;
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public String getIdColumnName() {
        return ID_COLUMN;
    }

    Class<Position> getEntityClass() {
        return Position.class;
    }

    public boolean existById(Integer id) {
        String existSql =
                "SELECT COUNT(*) FROM " + getTableName() + " WHERE " + getIdColumnName() + " =?";
        int count = getJdbcTemplate().queryForObject(existSql, Integer.class, id);
        return count > 0;
    }

    public Optional<Position> findById(Integer id) {
        Optional<Position> position = Optional.empty();
        String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + " =?";

        try {
            position = Optional.ofNullable(getJdbcTemplate().queryForObject(selectSql,
                    BeanPropertyRowMapper.newInstance(getEntityClass()), id));
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.debug("Can't find trader id:" + id, e);
        }
        return position;
    }

    public List<Position> findAll() {
        String selectSql = "SELECT * FROM " + getTableName();
        List<Position> positions = getJdbcTemplate().query(selectSql,
                BeanPropertyRowMapper.newInstance(getEntityClass()));

        return positions;
    }

    public List<Position> findAllById(Iterable<Integer> ids) {
        List<Position> foundPositions = new ArrayList<>();
        ids.forEach(id -> foundPositions.add(findById(id).get()));
        return foundPositions;
    }

    public void deleteById(Integer id) {
        if (!existById(id)) {
            throw new IllegalArgumentException("ID was not found.");
        }
        String deleteSql = "DELETE FROM " + getTableName() + " WHERE " + getIdColumnName() + " =?";
        getJdbcTemplate().update(deleteSql, id);
    }

    public long count() {
        String selectSql = "SELECT COUNT(*) FROM " + getTableName();
        Long count = getJdbcTemplate().queryForObject(selectSql, Long.class);
        if (count == null) {
            throw new NullPointerException("Sql count null returned.");
        }
        return count;
    }
}