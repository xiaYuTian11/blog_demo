package com.sunnyday.jdk.impl;

import com.sunnyday.jdk.Trip;

/**
 * @author TMW
 * @date 2020/9/8 11:44
 */
public class WalkTrip implements Trip {

    @Override
    public void mode() {
        System.out.println("步行方式");
    }
}
