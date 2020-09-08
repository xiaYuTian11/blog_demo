package com.sunnyday.google.impl;

import com.google.auto.service.AutoService;
import com.sunnyday.google.Weather;

/**
 * @author TMW
 * @date 2020/9/8 15:58
 */
@AutoService(Weather.class)
public class RainWeather implements Weather {
    @Override
    public void report() {
        System.out.println("明天将要下雨");
    }
}
