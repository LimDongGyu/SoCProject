package koreatech.cse.controller;

import koreatech.cse.domain.MineralSpring.MineralAsSpring;
import koreatech.cse.domain.MineralSpring.MineralSpring;
import koreatech.cse.domain.rest.Temperature;
import koreatech.cse.domain.weather.Weather;
import koreatech.cse.service.WaterService;
import koreatech.cse.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URLEncoder;

//우리는 컨트롤러가 하나만 있으면 됨


@RestController
@RequestMapping("/MineralSpring")
public class MineralSpringController {

    @Inject
    WaterService waterService;

    @Inject
    WeatherService weatherService;


    MineralSpring mineralSpring = new MineralSpring();
    MineralAsSpring mineralAsSpring = new MineralAsSpring();


    //TODO 가장 우선순위 높은 것만 추천
    @RequestMapping(value="/AsLocation", method = RequestMethod.GET, produces = "application/json")
    public MineralSpring getMineralSpring(
            @RequestParam(name="location", required=true, defaultValue = "충청남도 천안시") String location,
            @RequestParam(name="time", required = true ,defaultValue = "1200") String time,
            @RequestParam(name="date", required = true, defaultValue = "20181217") String date) throws IOException
    {
        System.out.println("Testing GET METHOD -----/MineralSpring ");
//
//        System.out.println(time);
//        System.out.println(location);

        waterService.getWater(location, date, time);


//        //걸러낼 부분, 우선순위 내용까지 출력한다.
//        System.out.println(waterService.totalList);
//        System.out.println(waterService.mineralSpringResult);


        //만약, 추천 개수에 따라서 여기서 처리해주면 될 듯

        //waterService에서 출력된 내용 자르기
        String[] waterArray = waterService.totalList.get(0).split(", ");

        waterArray[0] = waterArray[0].substring(1, waterArray[0].length());
        waterArray[waterArray.length-1] = waterArray[waterArray.length-1].substring(0, waterArray[waterArray.length-1].length()-1);

//        for(int i=0; i < waterArray.length; i++){
//            System.out.println(i + " : " + waterArray[i]);
//        }

        double max = -9999;
        int cnt=0;

        //우선순위 비교하는 부분 여기에 들어가야 한다.
        for(int i=0; i < waterService.mineralSpringResult.size()-1; i =i+2){
            if(max < Double.parseDouble(waterService.mineralSpringResult.get(i+1))){
                max = Double.parseDouble(waterService.mineralSpringResult.get(i+1));
                cnt = i;
            }
        }

        //최고 우선순위 1개
        System.out.println("최고 우선순위 [" + cnt + "] : " + waterService.mineralSpringResult.get(cnt) + ", priority : " + waterService.mineralSpringResult.get(cnt+1));


        //weatherService내용 자르기
//        System.out.println(waterService.mineralSpringResult.get(0));
//        System.out.println(waterService.mineralSpringResult.get(1));


        String[] weatherArray = waterService.mineralSpringResult.get(cnt).split(", ");

        weatherArray[0] = weatherArray[0].substring(1, weatherArray[0].length());
        weatherArray[weatherArray.length-1] = weatherArray[weatherArray.length-1].substring(0, weatherArray[weatherArray.length-1].length()-1);

//        for(int i=0; i < weatherArray.length; i++){
//            System.out.println(i + " : " + weatherArray[i]);
//        }


        // mineralSpring 객체 -> JSON 변환

        mineralSpring.setSpringName(waterArray[0]);
        mineralSpring.setSpringAddress(waterArray[1]);
        mineralSpring.setFitness(waterArray[4]);
        mineralSpring.setDepartment_number(waterArray[6]);


        //우선순위 결과
        if(Double.parseDouble(waterService.mineralSpringResult.get(cnt+1)) >= 300){
            mineralSpring.setResult("추천");
        }else if(Double.parseDouble(waterService.mineralSpringResult.get(cnt+1)) >= 0 && Double.parseDouble(waterService.mineralSpringResult.get(cnt+1)) < 300){
            mineralSpring.setResult("보통");
        }else if(Double.parseDouble(waterService.mineralSpringResult.get(cnt+1)) >= 0 && Double.parseDouble(waterService.mineralSpringResult.get(cnt+1)) < 300){
            mineralSpring.setResult("비추천");
        }else{
            mineralSpring.setResult("금지");
        }

//        System.out.println("mineralSpring toString() : ");
//        System.out.println(mineralSpring.toString());


        weatherService.weatherServiceClear();
        waterService.WaterServiceClear();


        return mineralSpring;
    }



    //TODO 약수터 이름으로 부적합, 적합 판정
    @RequestMapping(value="/AsSpring", method = RequestMethod.GET, produces = "application/json")
    public MineralAsSpring getMineralSpringAsSpringName(@RequestParam(name="springNameEx", required=true, defaultValue = "홍샘약수터") String springNameEx,
                                                        @RequestParam(name="time", required = true ,defaultValue = "1200") String time,
                                                        @RequestParam(name="date", required = true, defaultValue = "20181217") String date) throws IOException {

        waterService.getWater2(springNameEx, date, time);

        mineralAsSpring.setSpringName(waterService.mineralList2.get(0));
        mineralAsSpring.setSpringAddress(waterService.mineralList2.get(1));
        mineralAsSpring.setFitness(waterService.mineralList2.get(4));

        if(waterService.mineralList2.get(5).equals("")){
            mineralAsSpring.setFitness_explain("내용 없음");
        }else{
            mineralAsSpring.setFitness_explain(waterService.mineralList2.get(5));
        }

        mineralAsSpring.setDepartment_number(waterService.mineralList2.get(6));

        int size = waterService.mineralSpringResult.size()-1;

        //우선순위 결과
        if(Double.parseDouble(waterService.mineralSpringResult.get(size)) >= 300){
            mineralAsSpring.setResult("추천");
        }else if(Double.parseDouble(waterService.mineralSpringResult.get(size)) >= 0 && Double.parseDouble(waterService.mineralSpringResult.get(size)) < 300){
            mineralAsSpring.setResult("보통");
        }else if(Double.parseDouble(waterService.mineralSpringResult.get(size)) >= 0 && Double.parseDouble(waterService.mineralSpringResult.get(size)) < 300){
            mineralAsSpring.setResult("비추천");
        }else{
            mineralAsSpring.setResult("금지");
        }

        weatherService.weatherServiceClear();
        waterService.WaterServiceClear();

        return mineralAsSpring;
    }
}