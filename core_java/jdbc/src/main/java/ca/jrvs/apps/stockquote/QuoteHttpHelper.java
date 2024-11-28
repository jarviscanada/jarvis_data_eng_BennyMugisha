package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dto.Quote;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

public class QuoteHttpHelper {

    private String apiKey;
    private OkHttpClient client;

    public QuoteHttpHelper(String apiKey, OkHttpClient client) {
        this.apiKey = apiKey;
        this.client = client;
    }

    /**
     * Fetch latest quote data from Alpha Vantage endpoint
     * @param symbol String symbol
     * @return Quote with the latest data
     * @throws IllegalArgumentException IllegalArgumentException
     */

    public Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException {

        Quote quote;
        Request request = new Request.Builder()
                .url("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol="+symbol+"&datatype=json")
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .build();

        try {
            Response response = client.newCall(request).execute();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.body().string()).path("Global Quote");
            quote = JsonParser.toObjectFromJson(jsonNode.toString(), Quote.class);
            quote.setTimestamp(Timestamp.from(Instant.now()));

        } catch (IOException e) {
            throw new  IllegalArgumentException(e);
        }

        return quote;
    }

}
