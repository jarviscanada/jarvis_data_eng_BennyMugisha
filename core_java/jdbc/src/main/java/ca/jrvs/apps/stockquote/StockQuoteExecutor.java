package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dto.Position;
import ca.jrvs.apps.stockquote.dto.Quote;
import okhttp3.OkHttpClient;

import java.sql.Connection;
import java.sql.SQLException;

public class StockQuoteExecutor {

    public static void main(String[] args) {

        DatabaseConnectionManager  databaseCM = new DatabaseConnectionManager("localhost", "stock_quote",
                "postgres", "password");
        try {
            Connection connection = databaseCM.getConnection();

//             Quote Test
            QuoteDao quoteDao = new QuoteDao(connection);
            QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper("b187ea8091mshb572cbc0e0fd417p1150ccjsn2d030f01c90a", new OkHttpClient());
            Quote quote = quoteHttpHelper.fetchQuoteInfo("MSFT");
//             Save Test
            System.out.println(quoteDao.save(quote));
//             findById Test
//            System.out.println(quoteDao.findById("AAPL").toString());
//             findAll Test
//            System.out.println(quoteDao.findAll());
//             deleteById Test
//            quoteDao.deleteById("MSFT");
//             deleteAll Test
//            quoteDao.deleteAll();

//             Position Test
//            PositionDao positionDao = new PositionDao(connection);
//            Position position = new Position();
//            position.setTicker("MSFT");
//            position.setNumOfShares(20);
//            position.setValuePaid(8320);
//             deleteAll Test
//            positionDao.deleteAll();
//             Save Test
//            System.out.println(positionDao.save(position));
//             findById Test
//            System.out.println(positionDao.findById(position.getTicker()).toString());
//             findAll Test
//            System.out.println(positionDao.findAll());
//             deleteById Test
//            positionDao.deleteById(position.getTicker());



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
