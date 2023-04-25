package zhaw.nageljay.mdm_proj2;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.Properties;

import org.springframework.stereotype.Service;

@Service
public class HuggingFaceApiClient {

    private static String getApiToken() {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream("api_key.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("huggingface_api_key");
    }

    public byte[] generateImage(String prompt) {
        String apiToken = getApiToken();
        OkHttpClient client = new OkHttpClient();
        String apiUrl = "https://api-inference.huggingface.co/models/CompVis/stable-diffusion-v1-4";

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create("{\"inputs\": \"" + prompt + "\"}", JSON);

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("Authorization", "Bearer" + apiToken )
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            byte[] imageData = response.body().bytes();
            return imageData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
