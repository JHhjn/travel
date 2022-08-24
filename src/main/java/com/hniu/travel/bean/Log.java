package com.hniu.travel.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Log {

    private String url;//访问的路径
    private Date visitTime;//访问时间
    private String username;//访问者
    private String ip; //访问ip
    private int executionTime; //访问时长
    private String executionMessage;//异常信息
}
