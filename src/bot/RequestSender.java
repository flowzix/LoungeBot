package bot;

import javafx.util.Pair;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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

        String responseRead = IOUtils.toString(rawResponse.getEntity().getContent(), "utf8");
        System.out.println("Login response: " + responseRead);


        //getJSON("https://www.zalando-lounge.pl/api/campaigns/ZZO0TKL/articles?filter=%7B%7D&sort=attractivity_male&gender=MALE&page=1");

        return rawResponse.getStatusLine().getStatusCode() == 200;

    }

    public static HttpResponse get(String url, List<Pair<String, String>> headers) throws Exception {
        HttpGet request = new HttpGet(url);
        if (headers != null) {
            headers.stream().forEach(header -> request.addHeader(header.getKey(), header.getValue()));
        }
        //request.addHeader("Referer", "https://www.zalando-lounge.pl/campaigns/ZZO0TKL/all");
        HttpResponse response = sharedClient.execute(request);
        //String responseRead = IOUtils.toString(response.getEntity().getContent(), "utf8");
        //System.out.println(responseRead);
        return response;
    }
}
