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
import sun.nio.cs.ext.MS949;

import javax.inject.Inject;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

@Controller
public class WaterRestController {
    @RequestMapping(value="/water/{instt_name}", method= RequestMethod.GET, produces = "application/json")
    public String springRequest(List list, @PathVariable String instt_name) throws IOException {
        System.out.println(instt_name);

        //textblank.getInstituteName(instt_name);
        String strUrl = "http://api.data.go.kr/openapi/appn-mnrlsp-info-std";
        strUrl += "?serviceKey=4%2FF6EurpnGygV%2F15k3gSfm5k%2FZMwz8Ggz23KKmwfEggJJLEmMDsNPMTewcr1c0vjzBSfXImvYKEzfmxdCrGJmg%3D%3D";
        strUrl += "&type=xml";
        strUrl += "&s_page=0";
        strUrl += "&s_list=100";
        strUrl += "&instt_nm=" + URLEncoder.encode(instt_name, "UTF-8");

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

       BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "MS949"));
        String urlString = "";
        String current;
        while ((current = in.readLine()) != null) {
            urlString += current + "\n";
        }

       //list = waterlistResponseEntity.getBody();
        System.out.println(urlString);
        //System.out.println(list);
        return "findSpring";
    }
}