package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.jdbc.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.dto.Position;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionDaoTest {

    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "stock_quote",
            "postgres", "password");

    Connection connection;

    PositionDao positionDao;

    Position position = new Position();
    Position position2 = new Position();

    @BeforeClass
    public static void setup() throws SQLException {
        QuoteDaoTest quoteDaoTest = new QuoteDaoTest();
        quoteDaoTest.init();
        quoteDaoTest.testDeleteAll();
        quoteDaoTest.quoteDao.save(quoteDaoTest.quote);
        quoteDaoTest.quoteDao.save(quoteDaoTest.quote2);
    }

    @Before
    public void setUp() throws Exception {
        connection = dcm.getConnection();
        positionDao = new PositionDao(connection);
        position.setTicker("MSFT");
        position.setNumOfShares(20);
        position.setValuePaid(8320);

        position2.setTicker("AAPL");
        position2.setNumOfShares(60);
        position2.setValuePaid(12960);
    }

    @Test
    public void testSave() {
        String ticker = position2.getTicker();
        positionDao.deleteById(ticker);

        Assert.assertEquals(position2, positionDao.save(position2));
    }

    @Test
    public void testFindById() {
        String ticker = position.getTicker();
        positionDao.deleteById(ticker);
        positionDao.save(position);

        Assert.assertEquals(position, positionDao.findById(ticker).orElseThrow());
    }

    @Test
    public void testFindAll() {
        positionDao.deleteAll();
        List<Position> expected = new ArrayList<>();
        expected.add(position);
        expected.add(position2);
        positionDao.save(position);
        positionDao.save(position2);

        Assert.assertEquals(expected, positionDao.findAll());
    }

    @Test
    public void testDeleteById() {
        positionDao.deleteAll();
        String ticker = position.getTicker();
        positionDao.save(position);
        positionDao.deleteById(ticker);
        Position expected = new Position();

        Assert.assertEquals(expected, positionDao.findById(ticker).orElseThrow());
    }

    @Test
    public void testDeleteAll() {
        positionDao.deleteById(position.getTicker());
        positionDao.deleteById(position2.getTicker());
        positionDao.save(position);
        positionDao.save(position2);
        positionDao.deleteAll();

        Assert.assertEquals(List.of(), positionDao.findAll());
    }
}