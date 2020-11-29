package com.sunnyday.utils;

import com.sunnyday.model.excel.rule.ExcelRule;
import com.sunnyday.model.excel.rule.SheetRule;
import com.sunnyday.model.excel.rule.checkrule.CheckResult;
import com.sunnyday.model.excel.rule.checkrule.Rule;
import com.sunnyday.utils.excel.User;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author TMW
 * @since 2020/11/29 13:47
 */
class ExcelUtilTest {
    @BeforeAll
    static void printVersion() {
        System.out.println(System.getProperty("user.home"));
        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("java.vendor.url"));
    }

    @org.junit.jupiter.api.Test
    void readExcel() throws Exception {
        final String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("./excel/test01.xlsx")).getPath();
        File file = new File(path);
        final List<User> userList = ExcelUtil.readExcel(file, User.class, 1, 0);
        System.out.println(userList);
    }

    @org.junit.jupiter.api.Test
    void checkExcelWithComment() throws Exception {
        final String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("./excel/test01.xlsx")).getPath();
        File file = new File(path);
        List<ExcelRule> excelRules = new ArrayList<>();
        List<SheetRule> sheetRules = new ArrayList<>();
        SheetRule sheetRule1 = new SheetRule();
        sheetRule1.setCellIndex(0);
        sheetRule1.setRuleList(new ArrayList<Rule>() {{
            add(Rule.NOT_BLANK);
        }});
        sheetRules.add(sheetRule1);
        SheetRule sheetRule2 = new SheetRule();
        sheetRule2.setCellIndex(1);
        sheetRule2.setRuleList(new ArrayList<Rule>() {{
            add(Rule.ID_CARD);
        }});
        sheetRules.add(sheetRule2);
        ExcelRule excelRule = new ExcelRule(0, 1, sheetRules);
        excelRules.add(excelRule);
        final List<CheckResult> checkResultList = ExcelUtil.checkExcelWithComment(file, excelRules);
        System.out.println(checkResultList);
    }
}