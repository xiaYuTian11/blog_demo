package com.sunnyday.annotation;

import java.lang.annotation.*;

/**
 * 导入文件
 *
 * @author TMW
 * @date 2019/10/15 10:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
@Inherited
public @interface ExcelImport {

    /**
     * 描述
     */
    String description() default "";

    /**
     * 列序号
     */
    int sort();

    /**
     * 是否转换
     */
    boolean isConvert() default false;

    /**
     * 转换，类似与1:男,2:女
     */
    String convert() default "";

}
