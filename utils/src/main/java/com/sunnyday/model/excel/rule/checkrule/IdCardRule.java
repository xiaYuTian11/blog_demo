package com.sunnyday.model.excel.rule.checkrule;

import cn.hutool.core.util.IdcardUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

/**
 * 身份证
 *
 * @author TMW
 * @since 2020/11/29 13:40
 */
public class IdCardRule implements CheckRule {
    @Override
    public boolean check(Cell cell) {
        cell.setCellType(CellType.STRING);
        String stringCellValue = cell.getStringCellValue();
        return IdcardUtil.isValidCard(stringCellValue);
    }
}
