package bot.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.ContentEncoder;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.util.List;

public class RequestSender {
    private static HttpClient sharedClient;
    private static CloseableHttpAsyncClient asyncSharedClient;
    private static CookieStore sharedCookies = new BasicCookieStore();
    private static ConnectingIOReactor ioReactor;
    private static PoolingNHttpClientConnectionManager cm;

    static {
        try {
            ioReactor = new DefaultConnectingIOReactor();
            cm = new PoolingNHttpClientConnectionManager(ioReactor);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        sharedClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD)
                        .build())
                .setDefaultCookieStore(sharedCookies)
                .build();

        asyncSharedClient = HttpAsyncClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD)
                        .build())
                .setDefaultCookieStore(sharedCookies)
                .setConnectionManager(cm)
                .build();
        asyncSharedClient.start();
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

        asyncSharedClient.execute(putRequest, null);

        //HttpResponse resp = sharedClient.execute(putRequest);
        //putRequest.releaseConnection();
        return true;
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

    static class PutRequestProducer implements org.apache.http.nio.protocol.HttpAsyncRequestProducer {
        @Override
        public HttpHost getTarget() {
            return null;
        }

        @Override
        public HttpRequest generateRequest() throws IOException, HttpException {
            return null;
        }

        @Override
        public void produceContent(ContentEncoder contentEncoder, IOControl ioControl) throws IOException {

        }

        @Override
        public void requestCompleted(HttpContext httpContext) {

        }

        @Override
        public void failed(Exception e) {

        }

        @Override
        public boolean isRepeatable() {
            return false;
        }

        @Override
        public void resetRequest() throws IOException {

        }

        @Override
        public void close() throws IOException {

        }
    }

}
