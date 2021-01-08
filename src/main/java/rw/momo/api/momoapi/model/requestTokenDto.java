package rw.momo.api.momoapi.model;

import java.io.Serializable;

public class requestTokenDto implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private String userId;
    private String apiKey;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

}
