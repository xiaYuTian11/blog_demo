package com.sunnyday.jdk;

import com.sunnyday.jdk.Trip;
import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;

/**
 * @author TMW
 * @date 2020/9/8 11:49
 */
public class TripTest {

    @Test
    public void test() {
        final ClassLoader classLoader = this.getClass().getClassLoader();
        final ServiceLoader<Trip> load = ServiceLoader.load(Trip.class, classLoader);
        for (Trip next : load) {
            next.mode();
        }
    }
}