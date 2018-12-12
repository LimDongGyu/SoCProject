package koreatech.cse.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class WaterService {

    @Value("${water.service.key}")
    String water_service_key;

    public void getWater() throws IOException {

        StringBuilder urlBuilder = new StringBuilder("http://api.data.go.kr/openapi/appn-mnrlsp-info-std"); /*URL*/

        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + water_service_key); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("s_page", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8")); /*조회 시작 지점*/
        urlBuilder.append("&" + URLEncoder.encode("s_list", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 번에 조회될 최대 row 갯수*/
        urlBuilder.append("&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*XML/JSON 여부*/
        urlBuilder.append("&" + URLEncoder.encode("instt_nm", "UTF-8") + "=" + URLEncoder.encode("경기도 수원시", "UTF-8")); /*제공기관명*/



        /*---------------------------------------------------------------
          |  * 제공받는 내용
          |  - 위치 정보(좌표? 지역명?) 구체적일 필요가 있다.
          |  - 각 지역에 나오는 약수터가 많다면 다 비교할 필요 있음.
          |    = 적합도를 기준으로 자르고 데이터를 저장한 뒤에 제공(자체배열? DB?)
          |  - nx, ny (위도, 경도) -> weatherController
          ---------------------------------------------------------------*/


        /*--------------------------------------------------
          |  * 제공해야하는 내용
          |  - 약수터명          mnrlsp_nm
          |  - 소재지도로명주소   rdnmadr
          |  - 소재지지번주소     lnmadr
          |  - 수질검사일자       gltwtr_inspct_date
          |  - 수질검사결과구분   gltwtr_inspct_result_type
          |  - 부적합항목        impropt_iem
          --------------------------------------------------*/


        System.out.println(urlBuilder);

        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd;

        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
        }

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());


        List<String> list = new ArrayList<String>();


        //context debug
        int cnt = 0;

        String sbResult = sb.toString();
        String[] sbList = sbResult.split("}");


        for (String item : sbList) {
            System.out.println(cnt + " : " + item);
            cnt++;
        }

    }
}
