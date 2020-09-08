package com.sunnyday.google.impl;

import com.google.auto.service.AutoService;
import com.sunnyday.google.Weather;

/**
 * @author TMW
 * @date 2020/9/8 15:59
 */
@AutoService(Weather.class)
public class SunnyWeather implements Weather {
    @Override
    public void report() {
        System.out.println("明天艳阳高照");
    }
}
