package com.edigest.journalwebapp.services;

import com.edigest.journalwebapp.apiresponse.WeatherApiResponse;
import com.edigest.journalwebapp.cache.AppCache;
import com.edigest.journalwebapp.constants.PlaceHolders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
//    getting api key from application.yml
    @Value("${weather.api.key}")
    private String API_KEY;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherApiResponse getWeather(String city) {
        String finalEndPoint = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(PlaceHolders.API_KEY,API_KEY).replace(PlaceHolders.CITY, city);
        ResponseEntity<WeatherApiResponse> response= restTemplate.exchange(finalEndPoint, HttpMethod.GET,null, WeatherApiResponse.class);
        return response.getBody();
    }

//    how to sent post call to another api via java
/*    public WeatherApiResponse postWeather(String city) {
       String finalEndPoint = WEATHER_URL.replace("API_KEY",API_KEY).replace("CITY", city);

       String reqBody = "{\n" +
                "    \"userName\":\"shreeram\",\n" +
                "    \"email\":\"shreeram@gmail.com\",\n" +
                "    \"password\":\"12345678\"\n" +
                "}";
//        sending data in body from java code
        HttpEntity<String> httpEntity = new HttpEntity<>(reqBody);

//        sending data in header from java code
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("key","value");
        HttpEntity<String> httpEntity1 = new HttpEntity<>(reqBody,httpHeaders);

        ResponseEntity<WeatherApiResponse> response= restTemplate.exchange(finalEndPoint, HttpMethod.GET,null, WeatherApiResponse.class);
        return response.getBody();
    }
    */
}
















