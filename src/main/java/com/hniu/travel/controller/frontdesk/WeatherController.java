package com.hniu.travel.controller.frontdesk;

import com.hniu.travel.bean.Weather;
import com.hniu.travel.bean.WheatherDetails;
import com.hniu.travel.pojo.Areas;
import com.hniu.travel.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/frontdesk/weather")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @ResponseBody
    @RequestMapping("/getCurrentWeather")
   public List<WheatherDetails>  getCurrentWeather(String  cName){
        Weather weatherInfo = weatherService.getWeatherInfo(cName);
        List<WheatherDetails> lives = weatherInfo.getLives();
        return lives;
    }

    @ResponseBody
    @RequestMapping("/getAreas")
    public List<Areas> getAreasList(String cName){
        List<Areas> areasList = weatherService.getAreasesLikeCityName(cName);
        return areasList;
    }


}
