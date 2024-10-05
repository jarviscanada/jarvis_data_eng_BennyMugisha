package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.dto.Quote;
import ca.jrvs.apps.stockquote.util.CrudDao;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuoteDao implements CrudDao<Quote, String> {

    private final Connection c;

    private static final String Insert = "INSERT INTO Quote (symbol, open," +
            "high, low, price, volume," +
            "latest_trading_day, previous_close, change, change_percent," +
            "timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String GET_ONE = "SELECT * FROM Quote WHERE symbol=?";

    private static final String GET_ALL = "SELECT * FROM Quote";

    private static final String DELETE_ONE = "DELETE FROM Quote WHERE symbol=?";

    private static final String DELETE_ALL = "DELETE FROM Quote";

    public QuoteDao(Connection connection) {
        this.c = connection;
    }
    @Override
    public Quote save(@NotNull Quote entity) throws IllegalArgumentException {
        try(PreparedStatement statement = this.c.prepareStatement(Insert);) {
            statement.setString(1, entity.getTicker());
            statement.setDouble(2, entity.getOpen());
            statement.setDouble(3, entity.getHigh());
            statement.setDouble(4, entity.getLow());
            statement.setDouble(5, entity.getPrice());
            statement.setInt(6, entity.getVolume());
            statement.setDate(7, entity.getLatestTradingDay());
            statement.setDouble(8, entity.getPreviousClose());
            statement.setDouble(9, entity.getChange());
            statement.setString(10, entity.getChangePercent());
            statement.setTimestamp(11, entity.getTimestamp());
            statement.execute();
            Optional<Quote> quote = findById(entity.getTicker());
            return quote.orElseThrow();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<Quote> findById(String s) throws IllegalArgumentException {
        Quote quote = new Quote();

        try (PreparedStatement statement = this.c.prepareStatement(GET_ONE)) {
            statement.setString(1, s);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                createQuote(quote, rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(quote);
    }

    @Override
    public Iterable<Quote> findAll() {
        List<Quote> quotes = new ArrayList<>();

        try (PreparedStatement statement = this.c.prepareStatement(GET_ALL)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Quote quote = new Quote();
                createQuote(quote, rs);
                quotes.add(quote);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quotes;
    }

    @Override
    public void deleteById(String s) throws IllegalArgumentException {

        try (PreparedStatement statement = this.c.prepareStatement(DELETE_ONE)) {
            statement.setString(1, s);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteAll() {
        try (PreparedStatement statement = this.c.prepareStatement(DELETE_ALL)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void createQuote(@NotNull Quote quote, @NotNull ResultSet rs) throws SQLException {
        quote.setTicker(rs.getString("symbol"));
        quote.setOpen(rs.getDouble("open"));
        quote.setHigh(rs.getDouble("high"));
        quote.setLow(rs.getDouble("low"));
        quote.setPrice(rs.getDouble("price"));
        quote.setVolume(rs.getInt("volume"));
        quote.setLatestTradingDay(rs.getDate("latest_trading_day"));
        quote.setPreviousClose(rs.getDouble("previous_close"));
        quote.setChange(rs.getDouble("change"));
        quote.setChangePercent(rs.getString("change_percent"));
        quote.setTimestamp(rs.getTimestamp("timestamp"));
    }

}
