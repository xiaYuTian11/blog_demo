package com.sunnyday.google;

import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;

/**
 * 参考博客 https://cloud.tencent.com/developer/article/1415083
 *
 * @author TMW
 * @date 2020/9/8 16:00
 */
class WeatherTest {

    @Test
    void report() {
        final ClassLoader classLoader = this.getClass().getClassLoader();
        final ServiceLoader<Weather> weathers = ServiceLoader.load(Weather.class, classLoader);
        for (Weather weather : weathers) {
            weather.report();
        }
    }
}