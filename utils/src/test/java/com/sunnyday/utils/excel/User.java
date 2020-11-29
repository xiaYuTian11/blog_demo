package com.sunnyday.utils.excel;

import com.sunnyday.annotation.ExcelImport;
import lombok.Data;
import lombok.ToString;

/**
 * @author TMW
 * @since 2020/11/29 14:04
 */
@Data
@ToString
public class User {
    @ExcelImport(description = "姓名", sort = 0)
    private String name;
    @ExcelImport(sort = 1)
    private String idCard;
    @ExcelImport(sort = 2)
    private Integer age;
    @ExcelImport(sort = 3)
    private String department;
    @ExcelImport(sort = 4)
    private boolean isStudent;
    @ExcelImport(sort = 5, isConvert = true, convert = "是:1,否:0")
    private Integer isMarried;
}
