package com.sunnyday.jdk.impl;

import com.sunnyday.jdk.Trip;

/**
 * @author TMW
 * @date 2020/9/8 11:45
 */
public class RunTrip implements Trip {
    @Override
    public void mode() {
        System.out.println("跑步前进");
    }
}
