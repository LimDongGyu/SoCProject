package koreatech.cse.controller;

import koreatech.cse.domain.weather.Weather;
import koreatech.cse.service.WaterService;
import koreatech.cse.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
//
//        System.out.println("mineralSpringController : "+ waterService.mineralSpringResult.toString());
//        waterService.WaterServiceClear();

        //재가공
        //나중에 MineralSpringService에서 처리해도 되고 여기서 처리해도 됨

        System.out.println(waterService.totalList);
        System.out.println(weatherService.list);

        weatherService.weatherServiceClear();
        waterService.WaterServiceClear();


    }


    @RequestMapping("/AsSpring")
    public void getMineralSpringAsSpringName(@RequestParam(name = "AsSpring", required=true, defaultValue = "흑성산약수터") String AsSpring,
                                             @RequestParam(name="time", required = true ,defaultValue = "0000") String time,
                                             @RequestParam(name="date", required = true, defaultValue = "20181213") String date) throws IOException {

        waterService.getWater2(AsSpring);

    }
}
