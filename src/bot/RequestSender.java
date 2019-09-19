package bot;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;


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
        return rawResponse.getStatusLine().getStatusCode() == 200;
        //String responseRead = IOUtils.toString(rawResponse.getEntity().getContent(), "utf8");
    }

}
