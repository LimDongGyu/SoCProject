package koreatech.cse.controller.rest;

import koreatech.cse.domain.rest.Water;
\import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.*;import java.net.HttpURLConnection;

import java.net.URL;
import java.net.URLConnection;

@RestController
@RequestMapping("/water")
public class WaterRestController {
    @Inject

    @RequestMapping(value="/find_nearby_spring/{locationId}", method= RequestMethod.GET, produces = "application/json")
    public String springRequest(Model model, @RequestParam(name = "instt_name") String instt_name) {
        //textblank.getInstituteName(instt_name);
        String strUrl = "http://api.data.go.kr/openapi/{{ckanResceId}}";
        strUrl += "?serviceKey=4%2FF6EurpnGygV%2F15k3gSfm5k%2FZMwz8Ggz23KKmwfEggJJLEmMDsNPMTewcr1c0vjzBSfXImvYKEzfmxdCrGJmg%3D%3D";
        strUrl += "&type=xml";
        strUrl += "&s_page=0";
        strUrl += "&s_list=1";
        strUrl += "&instt_nm={instt_name}";

        URL url = new URL(strUrl);
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection connection = null;
        if (urlConnection instanceof HttpURLConnection) {
            connection = (HttpURLConnection) urlConnection;
        } else {
            System.out.println("error");
            return;
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String urlString = "";
        String current;
        while ((current = in.readLine()) != null) {
            urlString += current + "\n";
        }
        return "findSpring";
    }
    }
}