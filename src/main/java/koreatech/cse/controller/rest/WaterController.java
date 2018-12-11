package koreatech.cse.controller.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

@RestController
public class WaterController {

    @Value("${water.service.key}")
    String water_service_key;

    @RequestMapping("/water")
    public void getWater() throws IOException {

        StringBuilder urlBuilder = new StringBuilder("http://api.data.go.kr/openapi/appn-mnrlsp-info-std"); /*URL*/

        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + water_service_key); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("s_page","UTF-8") + "=" + URLEncoder.encode("0", "UTF-8")); /*조회 시작 지점*/
        urlBuilder.append("&" + URLEncoder.encode("s_list","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 번에 조회될 최대 row 갯수*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*XML/JSON 여부*/
//        urlBuilder.append("&" + URLEncoder.encode("mnrlsp_nm","UTF-8") + "=" + URLEncoder.encode("88약수터", "UTF-8")); /*약수터명*/
//        urlBuilder.append("&" + URLEncoder.encode("rdnmadr","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*소재지도로명주소*/
//        urlBuilder.append("&" + URLEncoder.encode("lnmadr","UTF-8") + "=" + URLEncoder.encode("경기도 동두천시 생연동 산70번지", "UTF-8")); /*소재지지번주소*/
//        urlBuilder.append("&" + URLEncoder.encode("latitude","UTF-8") + "=" + URLEncoder.encode("37.899604", "UTF-8")); /*위도*/
//        urlBuilder.append("&" + URLEncoder.encode("hardness","UTF-8") + "=" + URLEncoder.encode("127.074691", "UTF-8")); /*경도*/
//        urlBuilder.append("&" + URLEncoder.encode("appn_date","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*지정일자*/
//        urlBuilder.append("&" + URLEncoder.encode("day_use_cn","UTF-8") + "=" + URLEncoder.encode("30", "UTF-8")); /*일평균이용인구수*/
//        urlBuilder.append("&" + URLEncoder.encode("qltwtr_inspct_date","UTF-8") + "=" + URLEncoder.encode("2018-06-14", "UTF-8")); /*수질검사일자*/
//        urlBuilder.append("&" + URLEncoder.encode("qltwtr_inspct_result_type","UTF-8") + "=" + URLEncoder.encode("부적합", "UTF-8")); /*수질검사결과구분*/
//        urlBuilder.append("&" + URLEncoder.encode("impropt_iem","UTF-8") + "=" + URLEncoder.encode("미검사(수원고갈)", "UTF-8")); /*부적합항목*/
//        urlBuilder.append("&" + URLEncoder.encode("phone_number","UTF-8") + "=" + URLEncoder.encode("031-860-2471", "UTF-8")); /*관리기관전화번호*/
//        urlBuilder.append("&" + URLEncoder.encode("institution_nm","UTF-8") + "=" + URLEncoder.encode("경기도 동두천시청", "UTF-8")); /*관리기관명*/
//        urlBuilder.append("&" + URLEncoder.encode("reference_date","UTF-8") + "=" + URLEncoder.encode("2018-06-30", "UTF-8")); /*데이터기준일자*/
//        urlBuilder.append("&" + URLEncoder.encode("instt_code","UTF-8") + "=" + URLEncoder.encode("3740000", "UTF-8")); /*제공기관코드*/
        urlBuilder.append("&" + URLEncoder.encode("instt_nm","UTF-8") + "=" + URLEncoder.encode("경기도 수원시", "UTF-8")); /*제공기관명*/


        System.out.println(urlBuilder);

        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd;

        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(),"utf-8"));
        }

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }
}