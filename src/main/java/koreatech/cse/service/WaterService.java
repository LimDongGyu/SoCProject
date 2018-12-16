package koreatech.cse.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
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

    @Inject
    WeatherService weatherService;

    public List<String> mineralSpringResult = new ArrayList<String>();
    public List<String> totalList = new ArrayList<String>();               //제공기관명 + 시간으로 검색한 결과
    public List<String> mineralList2 = new ArrayList<String>();            //약수터 이름으로 검색한 결과


    public void getWater(String location, String date, String time) throws IOException {

        StringBuilder urlBuilder = new StringBuilder("http://api.data.go.kr/openapi/appn-mnrlsp-info-std"); /*URL*/

        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + water_service_key); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("s_page", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8")); /*조회 시작 지점*/
        urlBuilder.append("&" + URLEncoder.encode("s_list", "UTF-8") + "=" + URLEncoder.encode("5", "UTF-8")); /*한 번에 조회될 최대 row 갯수*/
        urlBuilder.append("&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*XML/JSON 여부*/
        urlBuilder.append("&" + URLEncoder.encode("instt_nm", "UTF-8") + "=" + URLEncoder.encode(location, "UTF-8")); /*제공기관명*/



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
          |  - 소재지지번주소     lnmadr
          |  - 수질검사일자       gltwtr_inspct_date
          |  - 수질검사결과구분   gltwtr_inspct_result_type
          |--------------------------------------------------
          |  - 부적합항목        impropt_iem --> 약수터 정보만 넣었을 때, /MineralSpring?location, time, name
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


        //context debug
        int cnt = 0;

        String sbResult = sb.toString();
        String[] sbList = sbResult.split("}");


        for (String item : sbList) {
            cnt++;
        }


        String[] category = new String[]{"약수터명", "소재지지번주소", "위도", "경도", "수질검사결과구분", "부적합항목", "관리기관전화번호"};

        List<String> mineralList = new ArrayList<String>();

        // 3, 33, 69, 77, 134, 161, 187


        String test = "";
        int x = 0;
        int y = 0;

        //수질검사결과구분에 적합 부분 검사
        for(int i = 0; i < sbList.length-1; i++) {
            if((sbList[i].substring(sbList[i].indexOf(category[4]) + 11, sbList[i].indexOf("\",\"부적합항목"))).equals("부적합")){
                continue;
            }
            else {
                mineralList.add(sbList[i].substring(sbList[i].indexOf(category[0]) + 7, sbList[i].indexOf("\",\"소재지도로명주소"))); //약수터명
                mineralList.add(sbList[i].substring(sbList[i].indexOf(category[1]) + 10, sbList[i].indexOf("\",\"위도")));            //소재지지번주소
                mineralList.add(sbList[i].substring(sbList[i].indexOf(category[2]) + 5, sbList[i].indexOf("\",\"경도")));            //위도
                mineralList.add(sbList[i].substring(sbList[i].indexOf(category[3]) + 5, sbList[i].indexOf("\",\"지정일자")));       //경도
                mineralList.add(sbList[i].substring(sbList[i].indexOf(category[4]) + 11, sbList[i].indexOf("\",\"부적합항목")));       //수질검사결과구분
                mineralList.add(sbList[i].substring(sbList[i].indexOf(category[5]) + 8, sbList[i].indexOf("\",\"관리기관전화번호")));     //부적합항목
                mineralList.add(sbList[i].substring(sbList[i].indexOf(category[6]) + 11, sbList[i].indexOf("\",\"관리기관명")));     //관리기관전화번호


                //여기서 적합 판정을 받은 리스트이므로, 위도, 경도 값을 WeatherService로 보내서 다 확인을 해야한다.
                if(mineralList.get(2).equals("") && mineralList.get(3).equals("")){
                }else{
                    totalList.add(mineralList.toString());
//                    System.out.println(i + " : " + mineralList);

                    test = mineralList.get(2);
                    x = (int)Double.parseDouble(test);

                    test = mineralList.get(3);
                    y = (int)Double.parseDouble(test);


                    //TODO  WaterService 결과 약수터 별 기상 정보와 우선순위를 담아오는 부분
                    getWeatherServiceCall(x, y, date, time);
                }

                mineralList.clear();
            }
        }


        //mineralSpringResult에 담긴 내용 정보를 가지고 우선순위 매기고, 최종 결과를 담아야하는 부분이다.
        //이 담긴 순서가 totalList의 순서와 같으므로 최종적으로 결과를 제공하려면 반환을 totalList + mineralSpringResult 값을 제공한다.
        System.out.println("totalList : " + totalList);
        System.out.println("mineralSpringResult : " + mineralSpringResult);
    }


    public void getWater2(String asSpring, String date, String time) throws IOException {

        StringBuilder urlBuilder = new StringBuilder("http://api.data.go.kr/openapi/appn-mnrlsp-info-std"); /*URL*/

        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + water_service_key); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("s_page", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8")); /*조회 시작 지점*/
        urlBuilder.append("&" + URLEncoder.encode("s_list", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 번에 조회될 최대 row 갯수*/
        urlBuilder.append("&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*XML/JSON 여부*/
        urlBuilder.append("&" + URLEncoder.encode("mnrlsp_nm", "UTF-8") + "=" + URLEncoder.encode(asSpring, "UTF-8")); /*약수터 이름*/


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


        //context debug
        int cnt = 0;

        String sbResult = sb.toString();
        String[] sbList = sbResult.split("}");


        for (String item : sbList) {
            System.out.println(cnt + " : " + item);
            cnt++;
        }


        String[] category = new String[]{"약수터명", "소재지지번주소", "위도", "경도", "수질검사결과구분", "부적합항목", "관리기관전화번호"};


        // 3, 33, 69, 77, 134, 161, 187

        //수질검사결과구분에 적합 부분 검사
        for(int i = 0; i < sbList.length-1; i++) {

                mineralList2.add(sbList[i].substring(sbList[i].indexOf(category[0]) + 7, sbList[i].indexOf("\",\"소재지도로명주소"))); //약수터명
                mineralList2.add(sbList[i].substring(sbList[i].indexOf(category[1]) + 10, sbList[i].indexOf("\",\"위도")));            //소재지지번주소
                mineralList2.add(sbList[i].substring(sbList[i].indexOf(category[2]) + 5, sbList[i].indexOf("\",\"경도")));            //위도
                mineralList2.add(sbList[i].substring(sbList[i].indexOf(category[3]) + 5, sbList[i].indexOf("\",\"지정일자")));       //경도
                mineralList2.add(sbList[i].substring(sbList[i].indexOf(category[4]) + 11, sbList[i].indexOf("\",\"부적합항목")));       //수질검사결과구분
                mineralList2.add(sbList[i].substring(sbList[i].indexOf(category[5]) + 8, sbList[i].indexOf("\",\"관리기관전화번호")));     //부적합항목
                mineralList2.add(sbList[i].substring(sbList[i].indexOf(category[6]) + 11, sbList[i].indexOf("\",\"관리기관명")));     //관리기관전화번호
        }

        //위도, 경도 값이 없어서 조회가 안됨. 처리해야함
        if(mineralList2.get(2).equals("") || mineralList2.get(2).equals("")){
            mineralList2.add("위도, 경도 값이 없어 기상 정보 조회가 불가능합니다.");
        }else{
            String test;
            int x, y;

            test = mineralList2.get(2);
            x = (int)Double.parseDouble(test);

            test = mineralList2.get(3);
            y = (int)Double.parseDouble(test);

            getWeatherServiceCall2(x, y, date, time);
        }

        //mineralSpringResult에 담긴 내용 정보를 가지고 우선순위 매기고, 최종 결과를 담아야하는 부분이다.
        //이 담긴 순서가 totalList의 순서와 같으므로 최종적으로 결과를 제공하려면 반환을 totalList + mineralSpringResult 값을 제공한다.
        System.out.println("mineralList2 : " + mineralList2);
        System.out.println("mineralSpringResult : " + mineralSpringResult);
    }


    //WaterService 결과 약수터 별 기상 정보와 우선순위를 담아오는 부분
    public void getWeatherServiceCall(int x, int y, String date, String time) throws IOException {
        weatherService.getWeather(x, y, date, time);

        mineralSpringResult.add(weatherService.list.toString());
        mineralSpringResult.add(Double.toString(weatherService.priority));

        weatherService.weatherServiceClear();

        //System.out.println("getWeatherServiceCall : "+ mineralSpringResult.toString());
    }



    //WaterService 결과 약수터 별 기상 정보와 우선순위를 담아오는 부분
    public void getWeatherServiceCall2(int x, int y, String date, String time) throws IOException {

//        weatherService.getWeather(x, y, date, time);
        weatherService.getWeather(55, 127, date, time);

        mineralSpringResult.add(weatherService.list.toString());
        mineralSpringResult.add(Double.toString(weatherService.priority));

        weatherService.weatherServiceClear();

        //System.out.println("getWeatherServiceCall : "+ mineralSpringResult.toString());
    }



    public void WaterServiceClear(){
        mineralSpringResult.clear();
        totalList.clear();
        mineralList2.clear();
    }
}
