package com.hniu.travel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hniu.travel.bean.Weather;
import com.hniu.travel.mapper.AreasMapper;
import com.hniu.travel.pojo.Areas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WeatherService {
    @Autowired
    private RestTemplate restTemplate;

    @Value(value = "${gaode.key}")
    private String key;
    @Value(value = "${gaode.url}")
    private  String url;
    @Resource
    private AreasMapper areasMapper;


    public Weather getWeatherInfo(String cityName){
        //通过城市名字查询城市id
        QueryWrapper<Areas> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cName",cityName);
        Areas areas = areasMapper.selectOne(queryWrapper);
        //拼接url参数，
        String weatherUrl=url+"key="+key+"&city="+areas.getCid();
        //调用高德天气接口
        Weather weather = restTemplate.getForObject(weatherUrl, Weather.class);
        //返回该城市的天气信息
        return weather;
    }

    public List<Areas> getAreasesLikeCityName(String cityName){
        QueryWrapper<Areas> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("cName", cityName);
        List<Areas> areas = areasMapper.selectList(queryWrapper);
        return areas;
    }
}
