package koreatech.cse.controller.rest;

import koreatech.cse.domain.rest.*;
import koreatech.cse.domain.weather.Weather;
import koreatech.cse.repository.TemperatureMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

@RestController
public class WeatherController {
    @Value("${weather.service.key}")
    String weather_service_key;

    @RequestMapping("/weather")
    public void getWeather() throws IOException {
        System.out.println("Testing GET METHOD -----/weather ");

//        String str = URLEncoder.encode()

        String strUrl = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastGrib";
        strUrl += "?serviceKey=" + weather_service_key;
        strUrl += "&base_date=20181210";
        strUrl += "&base_time=2330";
        strUrl += "&nx=55";
        strUrl += "&ny=127";
        strUrl += "&pageNo=1";
        strUrl += "&numOfRows=3";
        strUrl += "&_type=json";


        URL url = new URL(strUrl);
        HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();

        urlconnection.setRequestMethod("GET");

        BufferedReader br = null;

        br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));

        String result="";
        String line;

        while((line = br.readLine()) != null){
            result = result + line + "\n";
        }

        System.out.println(result);

//        System.out.println(strUrl);
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        try{
//            System.out.println("try");
//
//            ResponseEntity<Weather> WeatherResponseEntity
//                    = restTemplate.getForEntity(strUrl, Weather.class);
//
//            System.out.println(WeatherResponseEntity.toString());
//
//            Weather weather = WeatherResponseEntity.getBody();
//            System.out.println(weather.getResponse());
//            System.out.println("/try");
////
////            System.out.println(weather.toString());
//
//        }catch (HttpClientErrorException e){
//            System.out.println("catch");
//            System.out.println(e.getStatusCode() + ": " + e.getStatusText());
//            System.out.println("/catch");
//        }

//
//        try{
//            System.out.println("try");
//            ResponseEntity<Item> ItemResponseEntity
//                    = restTemplate.getForEntity(strUrl, Item.class);
//            Item item = ItemResponseEntity.getBody();
//            System.out.println(item);
//            System.out.println("/try");
//        }catch (HttpClientErrorException e){
//            System.out.println("catch");
//            System.out.println(e.getStatusCode() + ": " + e.getStatusText());
//            System.out.println("/catch");
//        }
    }
}