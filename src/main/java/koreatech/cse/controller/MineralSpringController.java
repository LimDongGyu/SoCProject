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
public class MineralSpringController {
    @Inject
    WaterService waterService;
    @Inject
    WeatherService weatherService;

    // 요청메소드 예상
    // http://localhost:8080/MineralSpring?location=강원도 수원시&time=0300
    @RequestMapping("/MineralSpring")
    public void getMineralSpring(@RequestParam(name = "location", required=true, defaultValue = "강원도 수원시") String location,
                                 @RequestParam(name="time", required = true ,defaultValue = "0000") String time) throws IOException
    {
        System.out.println("Testing GET METHOD -----/MineralSpring ");

        System.out.println(time);
        System.out.println(location);

        //weatherService.getWeather();
        waterService.getWater();
    }
}
