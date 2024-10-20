package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.jdbc.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dto.Position;
import junit.framework.TestCase;
import org.junit.Assert;

import java.sql.Connection;

public class PositionServiceTest extends TestCase {

    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "stock_quote",
            "postgres", "password");

    Connection connection;

    PositionDao positionDao;

    String ticker = "MSFT";

    public void setUp() throws Exception {
        super.setUp();
        connection = dcm.getConnection();
        positionDao = new PositionDao(connection);
    }

    public void testBuy() {
        Position expected = new Position();
        expected.setTicker(ticker);
        expected.setNumOfShares(2400);
        expected.setValuePaid(960000);
        PositionService positionService = new PositionService(positionDao);
        Assert.assertEquals(expected, positionService.buy(ticker, 2400, 400));
    }

    public void testSell() {
        PositionService positionService = new PositionService(positionDao);
        positionService.sell(ticker);
        Position expected = new Position();

        Assert.assertEquals(expected, positionDao.findById(ticker).orElseThrow());
    }
}