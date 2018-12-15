package koreatech.cse.controller;

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

    //TODO 20181213 파라미터 값으로 날짜도 받아와야 함, 또, 약수터?시간,날짜,약수터이름 포맷도 만들어야 함
    @RequestMapping("/AsLocation")
    public void getMineralSpring(@RequestParam(name = "location", required=true, defaultValue = "충청남도 천안시") String location,
                                 @RequestParam(name="time", required = true ,defaultValue = "0000") String time,
                                 @RequestParam(name="date", required = true, defaultValue = "20181215") String date) throws IOException
    {
        System.out.println("Testing GET METHOD -----/MineralSpring ");

        System.out.println(time);
        System.out.println(location);


        waterService.getWater(location, date, time);


        //걸러낼 부분, 우선순위 내용까지 출력한다.
        System.out.println(waterService.totalList);
        System.out.println(waterService.mineralSpringResult);


        //만약, 추천 개수에 따라서 여기서 처리해주면 될 듯

        //waterService에서 출력된 내용 자르기
        String[] waterArray = waterService.totalList.get(0).split(", ");

        waterArray[0] = waterArray[0].substring(1, waterArray[0].length());

        for(int i=0; i < waterArray.length; i++){
            System.out.println(i + " : " + waterArray[i]);
        }


        //weatherService내용 자르기
        System.out.println(waterService.mineralSpringResult.get(0));
        System.out.println(waterService.mineralSpringResult.get(1));

        String[] weatherArray = waterService.mineralSpringResult.get(0).split(", ");

        weatherArray[0] = weatherArray[0].substring(1, weatherArray[0].length());

        for(int i=0; i < weatherArray.length; i++){
            System.out.println(i + " : " + weatherArray[i]);
        }


        //여기서 MineralSpring 도메인에 담아서 클라이언트에게 json 형태로 보여주어야할 듯



        mineralSpring.setSpringName(waterArray[0]);
        mineralSpring.setSpringAddress(waterArray[1]);
        mineralSpring.setFitness(waterArray[4]);
        mineralSpring.setDepartment_number(waterArray[6]);


        System.out.println("mineralSpring toString() : ");
        System.out.println(mineralSpring.toString());


        weatherService.weatherServiceClear();
        waterService.WaterServiceClear();



        //@RequestBody

    }





    @RequestMapping("/AsSpring")
    public void getMineralSpringAsSpringName(@RequestParam(name = "AsSpring", required=true, defaultValue = "흑성산약수터") String AsSpring,
                                             @RequestParam(name="time", required = true ,defaultValue = "0000") String time,
                                             @RequestParam(name="date", required = true, defaultValue = "20181213") String date) throws IOException {

        waterService.getWater2(AsSpring);

    }
}
