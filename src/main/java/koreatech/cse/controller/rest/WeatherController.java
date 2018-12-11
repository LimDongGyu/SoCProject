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
import java.util.ArrayList;
import java.util.List;

@RestController
public class WeatherController {
    @Value("${weather.service.key}")
    String weather_service_key;

    @RequestMapping("/weather")
    public void getWeather() throws IOException {
        System.out.println("Testing GET METHOD -----/weather ");

//        String str = URLEncoder.encode()

//        String strUrl = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastGrib";      /* 초단기 실황*/
//        String strUrl = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastTimeData"; /* 초단기예보조회 */
        String strUrl = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData"; /* 동네예보조회 -> numOfRows=11 로 설정하면 됨*/

        strUrl += "?serviceKey=" + weather_service_key;
        strUrl += "&base_date=20181211";                                 /*발표일자*/
        strUrl += "&base_time=0500";                                     /*발표시각*/



        strUrl += "&nx=55";                                              /*예보지점 X 좌표*/
        strUrl += "&ny=127";                                             /*예보지점 Y 좌표*/


        strUrl += "&pageNo=1";
        strUrl += "&numOfRows=11";
        strUrl += "&_type=json";


        /*--------------------------------------------------
          |  * 제공해야하는 내용
          |  - 실황 값          obsrValue
          |  - 자료구분코드      category
          |  - 예측일자         fcstDate
          |  - 예측시간         fcstTime
          |  - 예보 값         fcstValue
          --------------------------------------------------*/

        /*---------------------------------------------------
          |                코드값 정보 (동네예보)
          |--------------------------------------------------
          | 항목값 |         항목 명         | 단위
          |--------------------------------------------------
          |  POP  |         강수확률        | %
          |  PTY  |         강수형태        | 코드값
          |  SKY  |         하늘상태        | 코드값
          |  T3H  |        3시간 기온       | 도
          |  R06  |       6시간 강수량      |  범주(1mm)
          |  REH  |          습도          |  %
          |  WSD  |          풍속          |  1
          --------------------------------------------------*/

        // 이 값들은 fcstValue에 담겨 있다.


        /*---------------------------------------------------
          | 하늘상태 : 맑음(1), 구름조금(2), 구름많음(3), 흐림(4)
          | 강수상태 : 없음(0), 비(1), 비/눈(2)=진눈깨비, 눈(3)
          |--------------------------------------------------
          |                     강수량 범주
          |--------------------------------------------------
          |         범주        |   문자열 표시 | GRIB 저장값
          |--------------------------------------------------
          |      0.1mm 미만     | 0mm 또는 없음 |  0
          | 0.1mm 이상  1mm 미만 |   1mm 미만   |  1
          |  1mm 이상  5mm 미만  |    1~4mm    |  5
          |  5mm 이상  10mm 미만 |    5~9mm    |  10
          | 10mm 이상  20mm 미만 |   10~19mm   |  20
          | 20mm 이상  40mm 미만 |   20~39mm   |  40
          | 40mm 이상  70mm 미만 |   40~69mm   |  70
          |       70mm 이상     |   70mm 이상  |  100
          --------------------------------------------------*/

        /*---------------------------------------------------
          |                      풍속
          |--------------------------------------------------
          |   풍속 구간   |  통보문  |           비고
          |--------------------------------------------------
          |    4미만     |    약    | 연기 흐름에 따라 풍향 감지 가능
          | 4이상 9미만   |  약간강  | 안면에 감촉을 느끼면서 나뭇잎이 조금 흔들림
          | 9이상 14미만  |    강    | 나무가지와 깃발이 가볍게 흔들림
          |   14이상     |   매우강  | 먼지가 일고, 작은 나무 전체가 흔들림
          ---------------------------------------------------*/




        System.out.println(strUrl);

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

        //System.out.println(result);

        br.close();
        urlconnection.disconnect();




        //System.out.println("debug : result = " + result);

        //context debug
//        int cnt = 0;
//
//        String[] sbList = result.split(",");
//
//        for(String item : sbList){
//            System.out.println(cnt + " : " + item);
//            cnt++;
//        }




        List<String> list = new ArrayList<String>();



//        //출력 값 정리
//        String targetResult1 = result.substring(result.indexOf("\"POP"), (result.substring(result.indexOf("\"POP")).indexOf("}") + result.indexOf("\"POP")));
//        targetResult1 = targetResult1.substring(targetResult1.indexOf("fcstValue"), (targetResult1.substring(targetResult1.indexOf("fcstValue")).indexOf(",") + targetResult1.indexOf("fcstValue")));

//
//        String targetResult2 = result.substring(result.indexOf("\"PTY"), (result.substring(result.indexOf("\"PTY")).indexOf("}") + result.indexOf("\"PTY")));
//        String targetResult3 = result.substring(result.indexOf("\"REH"), (result.substring(result.indexOf("\"REH")).indexOf("}") + result.indexOf("\"REH")));
//        String targetResult4 = result.substring(result.indexOf("\"SKY"), (result.substring(result.indexOf("\"SKY")).indexOf("}") + result.indexOf("\"SKY")));
//        String targetResult5 = result.substring(result.indexOf("\"T3H"), (result.substring(result.indexOf("\"T3H")).indexOf("}") + result.indexOf("\"T3H")));
//        String targetResult6 = result.substring(result.indexOf("\"UUU"), (result.substring(result.indexOf("\"UUU")).indexOf("}") + result.indexOf("\"UUU")));
//        String targetResult7 = result.substring(result.indexOf("\"VEC"), (result.substring(result.indexOf("\"VEC")).indexOf("}") + result.indexOf("\"VEC")));
//        String targetResult8 = result.substring(result.indexOf("\"VVV"), (result.substring(result.indexOf("\"VVV")).indexOf("}") + result.indexOf("\"VVV")));
//        String targetResult9 = result.substring(result.indexOf("\"WSD"), (result.substring(result.indexOf("\"WSD")).indexOf("}") + result.indexOf("\"WSD")));
//

        //데이터 확인
//        System.out.println(targetResult1);
//        System.out.println(targetResult2);
//        System.out.println(targetResult3);
//        System.out.println(targetResult4);
//        System.out.println(targetResult5);
//        System.out.println(targetResult6);
//        System.out.println(targetResult7);
//        System.out.println(targetResult8);
//        System.out.println(targetResult9);



        //System.out.println(targetResult1);
//        System.out.println(t1[0]);  //카테고리 값
//        System.out.println(t1[1]);  //수치값



        //차례대로, 강수확률, 강수형태, 습도, 하늘상태, 3시간 기온, 풍속(동서), 풍향, 풍속(남북), 풍속, 6시간 강수량
        String[] category = new String[]{"\"POP","\"PTY", "\"REH", "\"SKY", "\"T3H", "\"UUU", "\"VEC", "\"VVV", "\"WSD", "\"R06"};

        String targetResult = null;

        String[] t;

        for(int i = 0; i <= 8; i++){
            targetResult = result.substring(result.indexOf(category[i]), (result.substring(result.indexOf(category[i])).indexOf("}") + result.indexOf(category[i])));
            targetResult = targetResult.substring(targetResult.indexOf("fcstValue"), (targetResult.substring(targetResult.indexOf("fcstValue")).indexOf(",") + targetResult.indexOf("fcstValue")));
            t = targetResult.split("\":");
            list.add(t[1]);
        }

        System.out.println(list);




//        System.out.println(list.get(1));



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
//
//            System.out.println(weather.toString());
//
//        }catch (HttpClientErrorException e){
//            System.out.println("catch");
//            System.out.println(e.getStatusCode() + ": " + e.getStatusText());
//            System.out.println("/catch");
//        }

    }
}