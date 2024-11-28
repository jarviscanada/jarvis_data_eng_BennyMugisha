package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.jdbc.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.dto.Quote;
import okhttp3.OkHttpClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class QuoteDaoTest {

    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "stock_quote",
            "postgres", "password");

    Connection connection;

    QuoteDao quoteDao;

    Quote quote = new Quote();
    Quote quote2 = new Quote();

    @BeforeClass
    public static void setup() throws SQLException {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "stock_quote",
                "postgres", "password");

        Connection connection = dcm.getConnection();;
        PositionDao positionDao = new PositionDao(connection);
        positionDao.deleteAll();
    }

    @Before
    public void init () throws SQLException {
        connection = dcm.getConnection();
        quoteDao = new QuoteDao(connection);
        quote.setTicker("AAPL");
        quote.setOpen(227.9);
        quote.setHigh(228.0);
        quote.setLow(224.13);
        quote.setPrice(226.8);
        quote.setVolume(37140165);
        quote.setLatestTradingDay(new Date(2024,10,4));
        quote.setPreviousClose(225.67);
        quote.setChange(1.13);
        quote.setChangePercent("0.5007%");
        quote.setTimestamp(Timestamp.from(Instant.now()));

        quote2.setTicker("MSFT");
        quote2.setOpen(418.24);
        quote2.setHigh(419.75);
        quote2.setLow(414.97);
        quote2.setPrice(416.06);
        quote2.setVolume(19081803);
        quote2.setLatestTradingDay(new Date(2024,10,4));
        quote2.setPreviousClose(416.54);
        quote2.setChange(0.48);
        quote2.setChangePercent("-0.1152%");
        quote2.setTimestamp(Timestamp.from(Instant.now()));
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testSave() {
        String ticker = quote.getTicker();
        quoteDao.deleteById(ticker);

        Assert.assertEquals(quote, quoteDao.save(quote));

    }

    @Test
    public void testFindById() {
        String ticker = quote.getTicker();
        quoteDao.deleteById(ticker);
        quoteDao.save(quote);

        Assert.assertEquals(quote, quoteDao.findById(ticker).orElseThrow());
    }

    @Test
    public void testFindAll() {
        quoteDao.deleteAll();
        List<Quote> expected = new ArrayList<>();
        expected.add(quote);
        expected.add(quote2);
        quoteDao.save(quote);
        quoteDao.save(quote2);

        Assert.assertEquals(expected, quoteDao.findAll());

     }

    @Test
    public void testDeleteById() {
        quoteDao.deleteAll();
        String ticker = quote.getTicker();
        quoteDao.save(quote);
        quoteDao.deleteById(ticker);
        Quote expected = new Quote();

        Assert.assertEquals(expected, quoteDao.findById(ticker).orElseThrow());
    }

    @Test
    public void testDeleteAll() {
        quoteDao.deleteById(quote.getTicker());
        quoteDao.deleteById(quote2.getTicker());
        quoteDao.save(quote);
        quoteDao.save(quote2);
        quoteDao.deleteAll();

        Assert.assertEquals(List.of(), quoteDao.findAll());

    }
}