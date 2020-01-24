package tech.maslov.sandbox.client.api.response;

import tech.maslov.sandbox.base.api.response.BaseApiResponse;

public class ClientLoginApiResponse {
    private String id;
    private String accessToken;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
