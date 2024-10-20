package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.jdbc.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import junit.framework.TestCase;
import okhttp3.OkHttpClient;
import org.junit.Assert;

import java.sql.Connection;
import java.sql.SQLException;

public class QuoteServiceTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testFetchQuoteDataFromAPI() throws SQLException {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "stock_quote",
                "postgres", "password");
        Connection connection = dcm.getConnection();
        QuoteDao quoteDao = new QuoteDao(connection);
        QuoteService quoteService = new QuoteService(quoteDao, new QuoteHttpHelper("dummy_api_key_0123", new OkHttpClient()));
        String expected = "MSFT";
        Assert.assertEquals(expected, quoteService.fetchQuoteDataFromAPI("MSFT").orElseThrow().getTicker());
    }
}