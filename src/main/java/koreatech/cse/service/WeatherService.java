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
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService{

    @Value("${weather.service.key}")
    String weather_service_key;

    double priority = 0;
    public List<String> list = new ArrayList<String>();


    //인자로 x, y값, time, date을 받는다.
    public void getWeather(int x, int y, String date, String time) throws IOException {
        System.out.println("Testing GET METHOD -----/weather ");

//        String str = URLEncoder.encode()

        String strUrl = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastGrib";      /* 초단기 실황*/
//        String strUrl = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastTimeData";  /* 초단기예보조회 */
//        String strUrl = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData";   /* 동네예보조회 -> numOfRows=11 로 설정하면 됨*/

        strUrl += "?serviceKey=" + weather_service_key;
        strUrl += "&base_date="+ date;                                   /*발표일자*/
        strUrl += "&base_time=" + time;                                     /*발표시각*/
        strUrl += "&nx=" + x;                                              /*예보지점 X 좌표*/
        strUrl += "&ny=" + y;                                             /*예보지점 Y 좌표*/
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

        String result = "";
        String line;

        while ((line = br.readLine()) != null) {
            result = result + line + "\n";
        }


        br.close();
        urlconnection.disconnect();



        /* 동네예보 */
        //차례대로, 강수확률, 강수형태, 습도, 하늘상태, 3시간 기온, 풍속(동서), 풍향, 풍속(남북), 풍속, 6시간 강수량
        /*
        String[] category = new String[]{"\"POP","\"PTY", "\"REH", "\"SKY", "\"T3H", "\"UUU", "\"VEC", "\"VVV", "\"WSD", "\"R06"};

        String targetResult = "";

        String[] t;

        for(int i = 0; i <= 9; i++){

            if(result.indexOf(category[i]) == -1){
                continue;
            }
            else{
                targetResult = result.substring(result.indexOf(category[i]), (result.substring(result.indexOf(category[i])).indexOf("}") + result.indexOf(category[i])));
                System.out.println("targetResult : " + targetResult);

                list.add(targetResult.substring(1,4));

                targetResult = targetResult.substring(targetResult.indexOf("fcstValue"), (targetResult.substring(targetResult.indexOf("fcstValue")).indexOf(",") + targetResult.indexOf("fcstValue")));
                t = targetResult.split("\":");
                list.add(t[1]);
            }

        }

        System.out.println(list);
        */


        //TODO 지우면 디짐
        /* 초단기 예보 */
        String[] category = new String[]{"\"POP", "\"PTY", "\"REH", "\"SKY", "\"T1H", "\"UUU", "\"VEC", "\"VVV", "\"WSD", "\"R1N"};

        String targetResult = "";

        for (int i = 0; i <= 9; i++) {

            if (result.indexOf(category[i]) == -1) {
                continue;
            } else {
                targetResult = result.substring(result.indexOf(category[i]), (result.substring(result.indexOf(category[i])).indexOf("}") + result.indexOf(category[i])));

                list.add(targetResult.substring(1, 4));

                targetResult = targetResult.substring(targetResult.indexOf("obsrValue"), targetResult.length());
                list.add(targetResult.substring(11, targetResult.length()));
            }
        }

//        System.out.println(list);


        //TODO 우선순위 설정하는 부분
        //강수확률, 강수형태, 습도, 하늘상태, 1시간 기온, 풍속(동서), 풍향, 풍속(남북), 풍속, 6시간 강수량
        //String[] category = new String[]{"\"POP", "\"PTY", "\"REH", "\"SKY", "\"T1H", "\"UUU", "\"VEC", "\"VVV", "\"WSD", "\"R1N"};

        double getDouble = 0.0;

        for(int i = 0; i < list.size()/2; i++){
            getDouble = Double.parseDouble(list.get(i*2+1));

            switch (list.get(i*2)){
//                case "POP":
//                    priority += Double.parseDouble(list.get(i*2+1));
//                    System.out.println(priority);
//                    continue;
                case "PTY":
                    if(getDouble == 0){
                        priority += 100;
                    }else if(getDouble == 1){
                        priority -= 300;
                    }else if(getDouble == 2){
                        priority -= 300;
                    }else if(getDouble == 3){
                        priority -= 300;
                    }else{
                        priority += getDouble;
                    }
                    continue;

                case "SKY":
                    if(getDouble >= 9 && getDouble <= 10){
                        priority += 100;
                    }else if(getDouble >= 6 && getDouble < 9){
                        priority += 0;
                    }else if(getDouble >= 3 && getDouble < 6){
                        priority -= 200;
                    }else if(getDouble >= 0 && getDouble < 3){
                        priority -= 300;
                    }else{
                        priority += getDouble;
                    }
                    continue;

                case "T1H":
                    if(getDouble >= 35){
                        priority -= 500;
                    }else if(getDouble >= 30 && getDouble < 35){
                        priority -= 300;
                    }else if(getDouble >= 5 && getDouble < 30){
                        priority += 300;
                    }else if(getDouble >= -5 && getDouble < 5){
                        priority -= 100;
                    }else{
                        priority -= 500;
                    }
                    continue;

                case "WSD":
                    if(getDouble >= 14){
                        priority -= 500;
                    }else if(getDouble >= 9 && getDouble < 14){
                        priority -= 300;
                    }else if(getDouble >= 4 && getDouble < 9){
                        priority += 0;
                    }else{
                        priority += 100;
                    }
                    continue;

                case "RN1":
                    if(getDouble >= 70){
                        priority -= 2000;
                    }else if(getDouble >= 40 && getDouble < 70){
                        priority -= 1400;
                    }else if(getDouble >= 20 && getDouble < 40){
                        priority -= 800;
                    }else if(getDouble >= 10 && getDouble < 20){
                        priority -= 400;
                    }else if(getDouble >= 5 && getDouble < 10){
                        priority -= 200;
                    }else if(getDouble >= 1 && getDouble < 5){
                        priority -= 100;
                    }else{
                        priority += 0;
                    }
                    continue;


//                case "REH":
//                    priority += getDouble;
//                    continue;

//                case "UUU":
//                    priority += Double.parseDouble(list.get(i*2+1));
//                    System.out.println(priority);

//                case "VEC":
//                    priority += Double.parseDouble(list.get(i*2+1));
//                    System.out.println(priority);

//                case "VVV":
//                    priority += Double.parseDouble(list.get(i*2+1));
//                    System.out.println(priority);
            }
        }
    }

    public void weatherServiceClear(){
        priority = 0;
        list.clear();
    }
}
