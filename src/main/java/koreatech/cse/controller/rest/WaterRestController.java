package koreatech.cse.controller.rest;

import koreatech.cse.domain.rest.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.io.*;
import java.net.HttpURLConnection;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
@Controller
public class WaterRestController {
    @RequestMapping(value="/water/{instt_name}", method= RequestMethod.GET, produces = "application/json")
    public String springRequest(List list,/*, @RequestParam(name = "instt_name") String instt_name*/@PathVariable String instt_name) throws IOException {
        //textblank.getInstituteName(instt_name);
        String strUrl = "http://api.data.go.kr/openapi/appn-mnrlsp-info-std";
        strUrl += "?serviceKey=4%2FF6EurpnGygV%2F15k3gSfm5k%2FZMwz8Ggz23KKmwfEggJJLEmMDsNPMTewcr1c0vjzBSfXImvYKEzfmxdCrGJmg%3D%3D";
        strUrl += "&type=xml";
        strUrl += "&s_page=0";
        strUrl += "&s_list=1";
        strUrl += "&instt_nm=${instt_name}";

        /*MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List> waterlistResponseEntity = restTemplate.exchange(
                strUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                List.class
        );*/
        System.out.println(strUrl);

        HttpURLConnection connection = null;
        try {
            URL url = new URL(strUrl);
            URLConnection urlConnection = url.openConnection();
            connection = null;
            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            }
        } catch (HttpClientErrorException e) {
            System.out.println(e.getStatusCode() + e.getStatusText());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String urlString = "";
        String current;
        while ((current = in.readLine()) != null) {
            urlString += current + "\n";
        }
       //list = waterlistResponseEntity.getBody();
       System.out.println(urlString);
        return "findSpring";
    }
}