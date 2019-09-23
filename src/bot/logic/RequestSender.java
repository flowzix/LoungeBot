package bot.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.List;

public class RequestSender {
    private static HttpClient sharedClient;

    static {
        sharedClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD)
                        .build())
                .build();
    }

    public static boolean postJSON(String url, String json) throws Exception {
        StringEntity requestEntity = new StringEntity(
                json,
                ContentType.APPLICATION_JSON);

        HttpPost postMethod = new HttpPost(url);
        postMethod.setEntity(requestEntity);
        HttpResponse rawResponse = sharedClient.execute(postMethod);
        postMethod.releaseConnection();
        return rawResponse.getStatusLine().getStatusCode() == 200;
    }

    public static boolean putJSON(String url, Object jsonObject, List<Pair<String, String>> headers) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(jsonObject);
        StringEntity requestEntity = new StringEntity(
                json,
                ContentType.APPLICATION_JSON);
        HttpPut putRequest = new HttpPut(url);
        putRequest.setEntity(requestEntity);
        putRequest.addHeader("Content-type", "application/json");

        if (headers != null) {
            headers.stream().forEach(header -> putRequest.addHeader(header.getKey(), header.getValue()));
        }

        HttpResponse resp = sharedClient.execute(putRequest);
        putRequest.releaseConnection();

        return resp.getStatusLine().getStatusCode() == 200;
    }

    public static HttpResponse get(String url, List<Pair<String, String>> headers) throws Exception {
        HttpGet request = new HttpGet(url);
        if (headers != null) {
            headers.stream().forEach(header -> request.addHeader(header.getKey(), header.getValue()));
        }
        request.releaseConnection();
        HttpResponse response = sharedClient.execute(request);
        return response;
    }
}
