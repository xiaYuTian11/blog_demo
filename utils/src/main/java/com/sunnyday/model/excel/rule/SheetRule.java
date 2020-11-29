package com.sunnyday.model.excel.rule;

import com.sunnyday.model.excel.rule.checkrule.Rule;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel 校验 规则
 *
 * @author TMW
 * @date 2019/10/16 17:10
 */
@Data
public class SheetRule {

    /**
     * 行号
     */
    private Integer rowIndex;
    /**
     * 列号
     */
    private Integer cellIndex;
    /**
     * 规则
     */
    private List<Rule> ruleList;

    public SheetRule() {
    }

    public SheetRule(Integer rowIndex, Integer cellIndex, Rule rule) {
        this.rowIndex = rowIndex;
        this.cellIndex = cellIndex;
        this.ruleList = new ArrayList<>(2);
        this.ruleList.add(rule);
    }

    public SheetRule(Integer rowIndex, Integer cellIndex, List<Rule> ruleList) {
        this.rowIndex = rowIndex;
        this.cellIndex = cellIndex;
        this.ruleList = ruleList;
    }

    public SheetRule(Integer cellIndex, List<Rule> ruleList) {
        this.cellIndex = cellIndex;
        this.ruleList = ruleList;
    }

    public SheetRule(Integer cellIndex, Rule rule) {
        this.cellIndex = cellIndex;
        this.ruleList = new ArrayList<>(2);
        this.ruleList.add(rule);
    }
}
