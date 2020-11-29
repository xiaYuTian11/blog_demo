package com.sunnyday.model.excel.rule.checkrule;

import cn.hutool.core.util.StrUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

import java.util.Objects;

/**
 * 不为空校验
 *
 * @author TMW
 * @since 2020/11/29 11:21
 */
public class NotBlankRule implements CheckRule {

    @Override
    public boolean check(Cell cell) {
        if (cell.getCellType().equals(CellType.STRING)) {
            return StrUtil.isNotBlank(cell.getStringCellValue());
        } else if (cell.getCellType().equals(CellType.BLANK)) {
            return false;
        } else if (CellType.NUMERIC.equals(cell.getCellType())) {
            if (DateUtil.isCellDateFormatted(cell)) {
                // 时间格式
                return Objects.nonNull(cell.getDateCellValue());
            } else {
                // 数字
                return true;
            }
        } else if (CellType.BOOLEAN.equals(cell.getCellType())) {
            return true;
        } else {
            return true;
        }
    }
}
