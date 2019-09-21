package bot;

import bot.json.generated.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
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

        String responseRead = IOUtils.toString(rawResponse.getEntity().getContent(), "utf8");
        System.out.println("Login response: " + responseRead);


        getJSON("https://www.zalando-lounge.pl/api/campaigns/ZZO0TKL/articles?filter=%7B%7D&sort=attractivity_male&gender=MALE&page=1");

        return rawResponse.getStatusLine().getStatusCode() == 200;

    }

    public static boolean getJSON(String url) throws Exception {
        HttpGet request = new HttpGet(url);
        request.addHeader("Accept", "application/json, text/plain, */*");
        request.addHeader("Accept-Encoding", "gzip, deflate, br");
        request.addHeader("Host", "www.zalando-lounge.pl");
        request.addHeader("Accept-Language", "pl,en-US;q=0.7,en;q=0.3");
        request.addHeader("Referer", "https://www.zalando-lounge.pl/campaigns/ZZO0TKL/all");
        request.addHeader("X-Requested-With", "XMLHttpRequest");    // TODO MOVE TO SPECIALISED METHOD WRAPPER
        HttpResponse response = sharedClient.execute(request);
        //String responseRead = IOUtils.toString(response.getEntity().getContent(), "utf8");
        //System.out.println(responseRead);


        ObjectMapper om = new ObjectMapper();
        JSON respPojo = om.readValue(response.getEntity().getContent(), JSON.class);
        System.out.println(respPojo);

        return true;
    }
}
