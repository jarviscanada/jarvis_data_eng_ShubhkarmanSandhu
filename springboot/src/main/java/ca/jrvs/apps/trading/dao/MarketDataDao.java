package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String>{
    private static final String IEX_BATCH_PATH = "stock/market/batch?types=quote&token=";
    private String IEX_BATCH_URL = "";



    private HttpClientConnectionManager httpClientConnectionManager;

    @Autowired
    public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager,
                         MarketDataConfig marketDataConfig) {
        this.httpClientConnectionManager = httpClientConnectionManager;
        IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + marketDataConfig.getToken();

    }

    @Override
    public Optional<IexQuote> findById(String ticker) {
        Optional<IexQuote> iexQuote;
        List<IexQuote> quotes = (List<IexQuote>) findAllById(Collections.singletonList(ticker));

        if (quotes.size() == 0) {
            return Optional.empty();
        } else if (quotes.size() == 1) {
            iexQuote = Optional.of(quotes.get(0));
        } else {
            throw new DataRetrievalFailureException("Unexpected number of quotes");
        }
        return iexQuote;
    }

    @Override
    public Iterable<IexQuote> findAllById(Iterable<String> tickers) {
        StringBuilder url = new StringBuilder(IEX_BATCH_URL + "&symbols=");
        for (String ticker : tickers) {
            url.append(ticker).append(",");
        }
        String response = executeHttpGet(url.toString()).orElseThrow(
                () -> new IllegalArgumentException("Invalid Ticker" + url.toString()));


        JSONObject jsonObject = new JSONObject(response);

        if (jsonObject.length() == 0) {
            throw new IllegalArgumentException("Invalid Ticker");
        }

        int size = jsonObject.length();

        ObjectMapper mapper = new ObjectMapper();
        JSONArray names = jsonObject.names();
        JSONArray array = jsonObject.toJSONArray(names);
        List<IexQuote> quotes = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            JSONObject quoteJson = (JSONObject) array.get(i);
            String stringValue = quoteJson.opt("quote").toString();
            try {
                quotes.add(mapper.readValue(stringValue, IexQuote.class));
            } catch (IOException e) {
                throw new RuntimeException("Unable to deserialize quote json to java object: " + e);
            }
        }
        return quotes;

    }

    private Optional<String> executeHttpGet(String url) {
        HttpClient httpClient = getHttpClient();
        HttpGet get = new HttpGet(url);

        try {
            HttpResponse response = httpClient.execute(get);
            if (response.getStatusLine().getStatusCode() == 404) {
                return Optional.empty();
            }

            return Optional.ofNullable(EntityUtils.toString(response.getEntity()));

        } catch (IOException ex) {
            throw new DataRetrievalFailureException("Http request failed");
        }
    }

    private CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager)
                //prevent connectionManager shutdown when calling httpClient.close()
                .setConnectionManagerShared(true)
                .build();
    }

    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Iterable<IexQuote> findAll() {
        throw new UnsupportedOperationException("Not implemented");
    }
    @Override
    public long count() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(IexQuote entity) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends IexQuote> entities) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends IexQuote> S save(S entity) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("Not implemented");
    }


}
