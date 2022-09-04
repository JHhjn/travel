package com.hniu.travel.bean;

import lombok.Data;

@Data
public class WheatherDetails {
    private String province;
    private String city;
    private String adcode;
    private String weather;
    private String temperature;
    private String winddirection;
    private String windpower;
    private String humidity;
    private String reporttime;
}
