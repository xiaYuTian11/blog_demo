package com.sunnyday.model.excel.rule;

import java.util.List;

/**
 * Excel 校核规则
 *
 * @author TMW
 * @date 2019/10/17 11:11
 */
public class ExcelRule {
    /**
     * 工作薄序号
     */
    private Integer sheetIndex;
    /**
     * 起始行号
     */
    private Integer firstRowIndex;
    /**
     * 结束行号
     */
    private Integer endRowIndex;
    /**
     * 起始列号
     */
    private Integer firstCellIndex;
    /**
     * 结束列号
     */
    private Integer endCellIndex;
    /**
     * 校核规则
     */
    private List<SheetRule> sheetRuleList;

    public ExcelRule(Integer sheetIndex, List<SheetRule> sheetRuleList) {
        this.sheetIndex = sheetIndex;
        this.sheetRuleList = sheetRuleList;
    }

    public ExcelRule(Integer sheetIndex, Integer firstRowIndex, List<SheetRule> sheetRuleList) {
        this.sheetIndex = sheetIndex;
        this.firstRowIndex = firstRowIndex;
        this.sheetRuleList = sheetRuleList;
    }

    public ExcelRule(Integer sheetIndex, Integer firstRowIndex, Integer firstCellIndex, List<SheetRule> sheetRuleList) {
        this.sheetIndex = sheetIndex;
        this.firstRowIndex = firstRowIndex;
        this.firstCellIndex = firstCellIndex;
        this.sheetRuleList = sheetRuleList;
    }

    public ExcelRule(Integer sheetIndex, Integer firstRowIndex, Integer endRowIndex,
                     Integer firstCellIndex, Integer endCellIndex, List<SheetRule> sheetRuleList) {
        this.sheetIndex = sheetIndex;
        this.firstRowIndex = firstRowIndex;
        this.endRowIndex = endRowIndex;
        this.firstCellIndex = firstCellIndex;
        this.endCellIndex = endCellIndex;
        this.sheetRuleList = sheetRuleList;
    }

    public Integer getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(Integer sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public Integer getFirstRowIndex() {
        return firstRowIndex == null ? 0 : firstRowIndex;
    }

    public void setFirstRowIndex(Integer firstRowIndex) {
        this.firstRowIndex = firstRowIndex;
    }

    public Integer getEndRowIndex() {
        return endRowIndex;
    }

    public void setEndRowIndex(Integer endRowIndex) {
        this.endRowIndex = endRowIndex;
    }

    public Integer getFirstCellIndex() {
        return firstCellIndex == null ? 0 : firstCellIndex;
    }

    public void setFirstCellIndex(Integer firstCellIndex) {
        this.firstCellIndex = firstCellIndex;
    }

    public Integer getEndCellIndex() {
        return endCellIndex;
    }

    public void setEndCellIndex(Integer endCellIndex) {
        this.endCellIndex = endCellIndex;
    }

    public List<SheetRule> getSheetRuleList() {
        return sheetRuleList;
    }

    public void setSheetRuleList(List<SheetRule> sheetRuleList) {
        this.sheetRuleList = sheetRuleList;
    }

    @Override
    public String toString() {
        return "ExcelRule{" +
                "sheetIndex=" + sheetIndex +
                ", firstRowIndex=" + firstRowIndex +
                ", endRowIndex=" + endRowIndex +
                ", firstCellIndex=" + firstCellIndex +
                ", endCellIndex=" + endCellIndex +
                ", sheetRuleList=" + sheetRuleList +
                '}';
    }
}
