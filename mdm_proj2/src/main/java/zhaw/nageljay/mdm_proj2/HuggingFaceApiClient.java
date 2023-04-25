package zhaw.nageljay.mdm_proj2;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileNotFoundException;


import org.springframework.stereotype.Service;

@Service
public class HuggingFaceApiClient {

    private static String getApiToken() {
        Properties properties = new Properties();
        String apiToken = null;
    
        try (InputStream inputStream = HuggingFaceApiClient.class.getClassLoader().getResourceAsStream("api_key.properties")) {
            if (inputStream == null) {
                throw new FileNotFoundException("api_key.properties not found in classpath");
            }
            properties.load(inputStream);
            apiToken = properties.getProperty("huggingface_api_key");
        } catch (IOException e) {
            System.err.println("Error loading API token from api_key.properties:");
            e.printStackTrace();
        }
    
        if (apiToken == null) {
            System.err.println("API token not found or is null in api_key.properties");
        }
    
        return apiToken;
    }
    

    public byte[] generateImage(String prompt) {
        String apiToken = getApiToken();
        OkHttpClient client = new OkHttpClient();
        String apiUrl = "https://api-inference.huggingface.co/models/CompVis/stable-diffusion-v1-4";

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create("{\"inputs\": \"" + prompt + "\"}", JSON);
        System.out.println("APITOKEN: " + apiToken);
        Request request = new Request.Builder()
                .url(apiUrl)
                .header("Authorization", "Bearer " + apiToken )
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
