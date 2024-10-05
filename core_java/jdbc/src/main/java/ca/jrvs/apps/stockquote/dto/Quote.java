package ca.jrvs.apps.stockquote.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Quote {

    @JsonProperty("01. symbol")
    private String ticker;
    @JsonProperty("02. open")
    private double open;
    @JsonProperty("03. high")
    private double high;
    @JsonProperty("04. low")
    private double low;
    @JsonProperty("05. price")
    private double price;
    @JsonProperty("06. volume")
    private int volume;
    @JsonProperty("07. latest trading day")
    private Date latestTradingDay;
    @JsonProperty("08. previous close")
    private double previousClose;
    @JsonProperty("09. change")
    private double change;
    @JsonProperty("10. change percent")
    private String changePercent;
    private Timestamp timestamp;


    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public Date getLatestTradingDay() {
        return latestTradingDay;
    }

    public void setLatestTradingDay(Date latestTradingDay) {
        this.latestTradingDay = latestTradingDay;
    }

    public double getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(double previousClose) {
        this.previousClose = previousClose;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(String changePercent) {
        this.changePercent = changePercent;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Quote{");
        sb.append("ticker='").append(ticker).append('\'');
        sb.append(", open=").append(open);
        sb.append(", high=").append(high);
        sb.append(", low=").append(low);
        sb.append(", price=").append(price);
        sb.append(", volume=").append(volume);
        sb.append(", latestTradingDay=").append(latestTradingDay);
        sb.append(", previousClose=").append(previousClose);
        sb.append(", change=").append(change);
        sb.append(", changePercent='").append(changePercent).append('\'');
        sb.append(", timestamp=").append(timestamp);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Quote quote = (Quote) object;
        return Double.compare(open, quote.open) == 0 && Double.compare(high, quote.high) == 0 &&
                Double.compare(low, quote.low) == 0 && Double.compare(price, quote.price) == 0 &&
                volume == quote.volume && Double.compare(previousClose, quote.previousClose) == 0 &&
                Double.compare(change, quote.change) == 0 && Objects.equals(ticker, quote.ticker) &&
                Objects.equals(latestTradingDay, quote.latestTradingDay) && Objects.equals(changePercent, quote.changePercent) &&
                Objects.equals(timestamp, quote.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker, open, high, low, price, volume, latestTradingDay, previousClose, change, changePercent, timestamp);
    }
}
