package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dto.Position;

public class PositionService {

    private PositionDao dao;

    public PositionService(PositionDao dao) {
        this.dao = dao;
    }

    /**
     * Processes a buy order and updates the database accordingly
     * @param ticker String ticker
     * @param numberOfShares int numberOfShares
     * @param price double price
     * @return The position in our database after processing the buy
     */
    public Position buy(String ticker, int numberOfShares, double price) {

        if (numberOfShares == 0 || price == 0) {
            throw new IllegalArgumentException("Can not buy at 0 price or shares");
        }

        Position position = new Position();

        try {
            position.setTicker(ticker);
            position.setNumOfShares(numberOfShares);
            position.setValuePaid(numberOfShares * price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return dao.save(position);
    }

    /**
     * Sells all shares of the given ticker symbol
     * @param ticker String ticker
     */
    public void sell(String ticker) {

        dao.deleteById(ticker);
    }

}
