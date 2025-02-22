package com.treasuremount.petshop.Delivery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class DelhiveryConfig {

    @Value("${delhivery.api.key}") // API key from Delhivery
    private String apiKey;

    @Value("${delhivery.base.url}") // Base URL for Delhivery API
    private String baseUrl;

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Token "+getApiKey());
        return headers;
    }

}