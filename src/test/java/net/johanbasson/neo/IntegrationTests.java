package net.johanbasson.neo;


import com.fasterxml.jackson.databind.ObjectMapper;
import net.johanbasson.neo.users.JwtResponse;
import net.johanbasson.neo.users.LoginRequest;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTests {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

//    @Test
    public void shouldLoginWithAdminCredentials() throws JSONException, IOException {

        String json = mapper.writeValueAsString(new LoginRequest("admin@local.com", "admin"));

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/authenticate")
                .post(body)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        assertThat(response.code()).isEqualTo(200);
        JwtResponse jwtResponse = mapper.readValue(response.body().string(), JwtResponse.class);
        assertThat(jwtResponse).isNotNull();
        assertThat(jwtResponse.id()).isNotNull();
    }
}
