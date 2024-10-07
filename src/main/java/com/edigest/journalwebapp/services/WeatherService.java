package com.edigest.journalwebapp.services;

import com.edigest.journalwebapp.ApiKeys;
import com.edigest.journalwebapp.apiresponse.WeatherApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private static final String WEATHER_URL = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
    private static final String API_KEY = ApiKeys.getWeatherApiKey();

    @Autowired
    private RestTemplate restTemplate;

    public WeatherApiResponse getWeather(String city) {
        String finalEndPoint = WEATHER_URL.replace("API_KEY",API_KEY).replace("CITY", city);
        ResponseEntity<WeatherApiResponse> response= restTemplate.exchange(finalEndPoint, HttpMethod.GET,null, WeatherApiResponse.class);
        return response.getBody();
    }
}
















