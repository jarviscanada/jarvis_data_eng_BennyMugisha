package ca.jrvs.apps.stockquote.dto;

import java.util.Objects;

public class Position {

    private String ticker;
    private int numOfShares;
    private double valuePaid;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getNumOfShares() {
        return numOfShares;
    }

    public void setNumOfShares(int numOfShares) {
        this.numOfShares = numOfShares;
    }

    public double getValuePaid() {
        return valuePaid;
    }

    public void setValuePaid(double valuePaid) {
        this.valuePaid = valuePaid;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Position{");
        sb.append("ticker='").append(ticker).append('\'');
        sb.append(", numOfShares=").append(numOfShares);
        sb.append(", valuePaid=").append(valuePaid);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Position position = (Position) object;
        return numOfShares == position.numOfShares && Double.compare(valuePaid, position.valuePaid) == 0 && Objects.equals(ticker, position.ticker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker, numOfShares, valuePaid);
    }
}
