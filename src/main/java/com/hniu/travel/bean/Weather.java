package com.hniu.travel.bean;

import lombok.Data;

import java.util.List;

@Data
public class Weather {
    private String count;
    private String info;
    private String infocode;
    private List<WheatherDetails> lives;
}
