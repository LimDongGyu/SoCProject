package koreatech.cse.controller.rest;

import koreatech.cse.domain.rest.Temperature;
import koreatech.cse.repository.TemperatureMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/thermometer")
public class TemperatureRestController {
    @Inject
    private TemperatureMapper temperatureMapper;

    @Transactional
    @RequestMapping(value="/temperature/{sensorId}", method= RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Temperature> temperature(@PathVariable("sensorId") String sensorId) {
        Temperature temperature = temperatureMapper.findOneBySensorId(sensorId);
        if (temperature == null) {
            System.out.println("Temperature sensor with id (" + sensorId + ") is not found");
            return new ResponseEntity<Temperature>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Temperature>(temperature, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value="/xml/temperature/{sensorId}", method=RequestMethod.GET, produces="application/xml")
    public ResponseEntity<Temperature> temperatureXml(@PathVariable("sensorId") String sensorId) {
        Temperature temperature = temperatureMapper.findOneBySensorId(sensorId);
        if (temperature == null) {
            System.out.println("Temperature sensor with id " + sensorId + " is not found");
            return new ResponseEntity<Temperature>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Temperature>(temperature, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value="/temperature/location/{location}", method=RequestMethod.GET, produces="application/json")
    public ResponseEntity<List<Temperature>> temperatureByLocation(
            @PathVariable("location") String location) {
        List<Temperature> temperatureList = temperatureMapper.findByLocation(location);
        if (temperatureList.size() == 0) {
            System.out.println("Temperature sensors with location of " + location + " are not found");
            return new ResponseEntity<List<Temperature>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Temperature>>(temperatureList, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value="/xml/temperature/location/{location}", method=RequestMethod.GET, produces="application/xml")
    public ResponseEntity<List<Temperature>> temperatureByLocationXml(
            @PathVariable("location") String location) {
        List<Temperature> temperatureList = temperatureMapper.findByLocation(location);
        if (temperatureList.size() == 0) {
            System.out.println("Temperature sensors with location of " + location + " are not found");
            return new ResponseEntity<List<Temperature>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Temperature>>(temperatureList, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/temperature/", method = RequestMethod.POST)
    public ResponseEntity<Void> createTemperature(@RequestBody Temperature temperature, UriComponentsBuilder ucBuilder) {
        if (temperatureMapper.findOneBySensorId(temperature.getSensorId()) != null) {
            System.out.println("A temperature sensor with id (" +
                    temperature.getSensorId() + ") already exists");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        temperatureMapper.insert(temperature);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                ucBuilder.path("/temperature/{sensorId}").buildAndExpand(temperature.getSensorId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(value = "/temperature/{sensorId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateTemperature(@PathVariable("sensorId") String sensorId, @RequestBody Temperature temperature) {
        Temperature storedTemperature = temperatureMapper.findOneBySensorId(sensorId);

        if (storedTemperature == null) {
            System.out.println("No temperature sensor with id (" + sensorId + " not found");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        storedTemperature.setTemperature(temperature.getTemperature());
        storedTemperature.setLocation(temperature.getLocation());
        storedTemperature.setDatetime(temperature.getDatetime());

        temperatureMapper.update(storedTemperature);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/temperature/{sensorId}", method = RequestMethod.DELETE)
    public ResponseEntity<Temperature> deleteTemperature(@PathVariable("sensorId") String sensorId) {
        Temperature storedTemperature = temperatureMapper.findOneBySensorId(sensorId);

        if (storedTemperature == null) {
            System.out.println("No temperature sensor with id (" + sensorId + " not found");
            return new ResponseEntity<Temperature>(HttpStatus.NOT_FOUND);
        }

        temperatureMapper.delete(storedTemperature.getId());

        return new ResponseEntity<Temperature>(HttpStatus.NO_CONTENT);
    }
}
