package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */



    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException{
        // return statement included so that the starter code can compile and run.
        String url = " https://dog.ceo/api/breed/" + breed + "/list";
        Request request = new Request.Builder()
                .url(url)
                .build();
        String jsonString = "";
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        List<String> subBreeds = new ArrayList<>();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                jsonString = response.body().string();
                jsonObject = new JSONObject(jsonString);
                jsonArray = jsonObject.getJSONArray("message");
            }
            else {
                throw new BreedNotFoundException("Unexpected code " + response);
            }
        }
        catch (Exception e) {
            throw new BreedNotFoundException(e.getMessage());
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            subBreeds.add(jsonArray.getString(i));
        }

        return subBreeds;
    }
}