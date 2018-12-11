package koreatech.cse.controller.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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



        List<String> list = new ArrayList<String>();


        //context debug
        int cnt = 0;

        String sbResult = sb.toString();
        String[] sbList = sbResult.split("}");


        for(String item : sbList){
            System.out.println(cnt + " : " + item);
            cnt++;
        }


        //System.out.println("sbList size : " + sbList.length);
/**/

//        String[] category = new String[]{"약수터명", "소재지도로명주소", "위도", "경도", "지정일자", "일평균이용인구수", "수질검사일자", "수질검사결과구분", "부적합항목", "관리기간전화번호", "관리기관명", " 데이터기준일자","제공기관코드", "제공기관명"};
//
//        //System.out.println("category size : " + category.length);
//
//
//        String targetResult = null;
//        String[] t;
//
//        for(int i = 0; i <= 10; i++){
//            for(int j = 0; j <= 13; j++){
//                if(j != 13){
//                    targetResult = sbList[i].substring(sbList[i].indexOf(category[j]), (sbList[i].substring(sbList[i].indexOf(category[j])).indexOf(j+1) + sbList[i].indexOf(category[j])));
//                }else{
//                    targetResult = sbList[i].substring(sbList[i].indexOf(category[j]), (sbList[i].substring(sbList[i].indexOf(category[j])).indexOf("시\"") + sbList[i].indexOf(category[j])));
//                }
//                list.add(targetResult);
//            }
//        }
//
//        System.out.println(list.get(0));

/**/
//        String[] category = new String[]{"약수터명", "소재지도로명주소", "위도", "경도", "지정일자", "일평균이용인구수", "수질검사일자", "수질검사결과구분", "부적합항목", "관리기간전화번호", "관리기관명", " 데이터기준일자","제공기관코드", "제공기관명"};
//
//        String targetResult = null;
//
//        String[] t;
//
//        for(int i = 0; i <= 13; i++){
//            targetResult = sbList[i].substring(sbList[i].indexOf(category[i]), (sbList[i].substring(sbList[i].indexOf(category[i])).indexOf("}") + sbList[i].indexOf(category[i])));
////            targetResult = targetResult.substring(targetResult.indexOf("fcstValue"), (targetResult.substring(targetResult.indexOf("fcstValue")).indexOf(",") + targetResult.indexOf("fcstValue")));
////            t = targetResult.split("\":");
//            //list.add(t);
//            System.out.println(targetResult);
//        }
//
//        System.out.println(list);



//        System.out.println(sbList);




//        //특정단어(부분)만 자르기
//        System.out.println("특정단어 부분만 자르기");
//        String target = "약수터명\":\"";
//        int target_num = sbResult.indexOf(target);
//        String targetResult = sbResult.substring(target_num, (sbResult.substring(target_num).indexOf("소") + target_num));
//        System.out.println(targetResult);
//
//        String target2 = "소재지지번주소";
//        int target_num2 = sbResult.indexOf(target2);
//        String targetResult2 = sbResult.substring(target_num2, (sbResult.substring(target_num2).indexOf("위") + target_num2));
//        System.out.println(targetResult2);


    }
}