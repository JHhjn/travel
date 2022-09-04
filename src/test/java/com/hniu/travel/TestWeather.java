package com.hniu.travel;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hniu.travel.bean.Weather;
import com.hniu.travel.bean.WheatherDetails;
import com.hniu.travel.mapper.AreasMapper;
import com.hniu.travel.pojo.Areas;
import com.hniu.travel.service.WeatherService;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.List;

@SpringBootTest
public class TestWeather {
    @Autowired
    private RestTemplate restTemplate;

    @Value(value = "${gaode.key}")
    private String key;

    private String url="https://restapi.amap.com/v3/weather/weatherInfo?";
    @Resource
    private AreasMapper areasMapper;
    @Resource
    private WeatherService weatherService;

    @Test
    public void t1(){
        QueryWrapper<Areas> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cName","长沙市");
        Areas areas = areasMapper.selectOne(queryWrapper);
        url+="key="+key+"&city="+areas.getCid();
        Weather weather = restTemplate.getForObject(url, Weather.class);
        List<WheatherDetails> lives = weather.getLives();
        lives.forEach(System.out::println);

    }
    @Test
    public void t2(){
        QueryWrapper<Areas> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("cName", "北");
        List<Areas> areas = areasMapper.selectList(queryWrapper);
        areas.forEach(System.out::println);
    }
}
