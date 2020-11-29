package com.sunnyday.utils;

import cn.hutool.core.util.StrUtil;
import com.sunnyday.annotation.ExcelImport;
import com.sunnyday.model.excel.rule.ExcelRule;
import com.sunnyday.model.excel.rule.SheetRule;
import com.sunnyday.model.excel.rule.checkrule.CheckResult;
import com.sunnyday.model.excel.rule.checkrule.CheckRuleFactory;
import com.sunnyday.model.excel.rule.checkrule.Rule;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TMW
 * @since 2020/11/26 10:44
 */
public class ExcelUtil {

    /**
     * 文件后缀
     */
    public static final String FILE_TYPE = ".";

    public static final String XLS = "xls";
    public static final String XLSX = "xlsx";

    /**
     * Excel 样式
     */
    private static CellStyle cellStyle;
    private static Workbook workbook;

    /**
     * 根据索引取工作表对象
     *
     * @param workbook 工作表
     * @param index    索引
     * @return 工作表
     */
    public static Sheet getSheetByIndex(Workbook workbook, int index) {
        Sheet sheetAt;
        try {
            sheetAt = workbook.getSheetAt(index);
        } catch (IllegalArgumentException e) {
            sheetAt = null;
        }
        return sheetAt;
    }

    /**
     * 根据名称获取工作表对象，如果没有，则创建
     *
     * @param workbook 工作表
     * @param name     工作表名称
     * @return 工作表
     */
    public static Sheet getSheetByIndex(Workbook workbook, String name) {
        Sheet sheetAt;
        try {
            sheetAt = workbook.getSheet(name);
        } catch (IllegalArgumentException e) {
            sheetAt = workbook.createSheet(name);
        }
        if (sheetAt == null) {
            sheetAt = workbook.createSheet(name);
        }
        return sheetAt;
    }

    /**
     * 获取行
     *
     * @param sheet 工作薄
     * @param index 索引
     * @return 行对象
     */
    public static Row getRow(Sheet sheet, Integer index) {
        Row row = sheet.getRow(index);
        if (row == null) {
            row = sheet.createRow(index);
        }
        return row;
    }

    /**
     * 获取列
     *
     * @param row   行对象
     * @param index 索引
     * @return 单元格
     */
    public static Cell getCell(Row row, Integer index) {
        Cell cell = row.getCell(index);
        if (cell == null) {
            cell = row.getCell(index, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        }
        return cell;
    }

    /**
     * 设置单元格值
     *
     * @param row
     * @param index
     * @param value
     */
    public synchronized static void setCell(Row row, Integer index, String value) {
        Cell cell = row.getCell(index);
        if (cell == null) {
            cell = row.getCell(index, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        }

        if (workbook == null || !workbook.equals(row.getSheet().getWorkbook())) {
            workbook = row.getSheet().getWorkbook();
            cellStyle = null;
        }
        if (cellStyle == null) {
            cellStyle = workbook.createCellStyle();
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            DataFormat dataFormat = workbook.createDataFormat();
            cellStyle.setDataFormat(dataFormat.getFormat("@"));
        }

        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
    }

    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        //判断是否为null或空串
        if (cell == null || "".equals(cell.toString().trim())) {
            return "";
        }
        String cellValue;
        CellType cellType = cell.getCellType();
        if (CellType.STRING.equals(cellType)) {
            return cell.getStringCellValue().trim();
        } else if (CellType.BOOLEAN.equals(cellType)) {
            cellValue = String.valueOf(cell.getBooleanCellValue());
        } else if (CellType.NUMERIC.equals(cellType)) {
            if (DateUtil.isCellDateFormatted(cell)) {
                cellValue = String.valueOf(cell.getDateCellValue());
            } else {  // 否
                cellValue = String.valueOf(cell.getNumericCellValue());
            }
        } else {
            return null;
        }
        return cellValue;
    }

    /**
     * 获取文件后缀名称
     *
     * @param fileName
     * @return
     */
    public static String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf(ExcelUtil.FILE_TYPE) + 1);
    }

    /**
     * 获取文本对象
     *
     * @param fileType
     * @param file
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(String fileType, File file) throws Exception {
        Workbook workbook;
        // 延迟解析比率
        ZipSecureFile.setMinInflateRatio(-1.0d);
        if (ExcelUtil.XLS.equals(fileType)) {
            try {
                workbook = new HSSFWorkbook(new FileInputStream(file));
            } catch (OfficeXmlFileException e) {
                workbook = new XSSFWorkbook(new FileInputStream(file));
            }
        } else {
            try {
                workbook = new XSSFWorkbook(new FileInputStream(file));
            } catch (OfficeXmlFileException e) {
                workbook = new HSSFWorkbook(new FileInputStream(file));
            }
        }
        // workbook = WorkbookFactory.create(file);
        return workbook;
    }

    /**
     * 读取Excel
     *
     * @param file           文件
     * @param tClass         实体类
     * @param startRowIndex  开始行
     * @param startCellIndex 开始列
     * @return 解析实体类集合
     * @throws Exception
     */
    public static <T> List<T> readExcel(File file, Class<T> tClass, int startRowIndex, int startCellIndex) throws Exception {
        String fileType = getFileType(file.getName());
        Workbook workbook = getWorkbook(fileType, file);
        Sheet sheet = ExcelUtil.getSheetByIndex(workbook, 0);
        return readExcel(sheet, tClass, startRowIndex, startCellIndex);
    }

    /**
     * 读取Excel
     *
     * @param file           文件
     * @param sheetIndex     工作表索引
     * @param tClass         实体类
     * @param startRowIndex  开始行
     * @param startCellIndex 开始列
     * @return 解析实体类集合
     * @throws Exception
     */
    public static <T> List<T> readExcel(File file, Integer sheetIndex, Class<T> tClass, int startRowIndex, int startCellIndex) throws Exception {
        String fileType = getFileType(file.getName());
        Workbook workbook = getWorkbook(fileType, file);
        Sheet sheet = ExcelUtil.getSheetByIndex(workbook, sheetIndex);
        return readExcel(sheet, tClass, startRowIndex, startCellIndex);
    }

    /**
     * 读取excel
     *
     * @return
     */
    public static <T> List<T> readExcel(Sheet sheet, Class<T> tClass, int startRowIndex, int startCellIndex) throws Exception {
        List<T> list = new ArrayList<T>();
        if (sheet == null) {
            return list;
        }
        Field[] fields = tClass.getDeclaredFields();
        Map<Integer, Field> fieldMap = Arrays.stream(fields)
                .filter(field -> field.getAnnotation(ExcelImport.class) != null)
                .collect(Collectors.toMap(field -> field.getAnnotation(ExcelImport.class).sort(), field -> field, (k1, k2) -> k2));
        int lastRowNum = sheet.getLastRowNum();
        for (int rowNum = startRowIndex; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow(rowNum);
            // 判断是否是空行
            if (checkIsBlankRow(row)) {
                continue;
            }
            T entity = tClass.newInstance();
            short lastCellNum = row.getLastCellNum();
            for (int cellNum = startCellIndex; cellNum <= lastCellNum; cellNum++) {
                Cell cell = row.getCell(cellNum);
                Field field = fieldMap.get(cellNum);
                if (field != null) {
                    field.setAccessible(true);
                    ExcelImport excelImport = field.getAnnotation(ExcelImport.class);
                    String value = setCellMerge(sheet, rowNum, cellNum);
                    if (StrUtil.isNotEmpty(value)) {
                        field.set(entity, value);
                    } else {
                        String fieldType = field.getType().getSimpleName();
                        String convert = excelImport.convert();
                        boolean isConvert = excelImport.isConvert();
                        field.set(entity, covertAttrType(fieldType, isConvert, convert, cell));
                    }
                }
            }
            list.add(entity);
        }
        return list;
    }

    /**
     * 获取合并单元格的值
     */
    private static String setCellMerge(Sheet sheet, Integer row, Integer column) {
        //处理合并单元格
        String result;
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    result = fCell.getStringCellValue();
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * 类型转换 将cell 单元格格式转为 字段类型
     */
    private static Object covertAttrType(String fieldType, boolean isConvert, String convert, Cell cell) {
        if (cell == null) {
            return null;
        }
        CellType cellType = cell.getCellType();
        if (Objects.equals(cellType, CellType.BLANK)) {
            return null;
        }
        String value = null;
        if (Objects.equals(cellType, CellType.STRING)) {
            value = cell.getStringCellValue();
        } else if (Objects.equals(cellType, CellType.NUMERIC)) {
            value = String.valueOf(cell.getNumericCellValue());
        } else {
            cell.setCellType(CellType.STRING);
            value = cell.getStringCellValue();
        }
        Map<String, String> map = new HashMap<>(5);
        if (isConvert) {
            String[] split = convert.split(",");
            Arrays.stream(split).forEach(str -> {
                String[] strings = str.split(":");
                map.put(strings[0], strings[1]);
            });
        }

        if ("String".equals(fieldType)) {
            if (isConvert) {
                return map.get(value);
            }
            return value;
        } else if ("Date".equals(fieldType)) {
            if (CellType.NUMERIC.equals(cellType) && DateUtil.isCellDateFormatted(cell)) {
                return DateUtil.getJavaDate(Double.parseDouble(value));
            } else {
                if (StrUtil.isBlank(value)) {
                    return null;
                } else if (StrUtil.isNotBlank(value) && (value.length() == 6 || value.length() == 8)) {
                    return value.length() == 6 ? cn.hutool.core.date.DateUtil.parse(value, "yyyyMM") : cn.hutool.core.date.DateUtil.parse(value, "yyyyMMdd");
                } else if (StrUtil.isNotBlank(value) && (value.length() == 7 || value.length() == 9)) {
                    return value.length() == 7 ? cn.hutool.core.date.DateUtil.parse(value, "yyyy.MM") : cn.hutool.core.date.DateUtil.parse(value, "yyyy.MM.dd");
                } else if (StrUtil.isNotBlank(value) && (value.length() == 20)) {
                    return cn.hutool.core.date.DateUtil.parse(value, "yyyy-MM-dd HH:mm:ss.s");
                } else if (StrUtil.isNotBlank(value) && (value.length() == 21)) {
                    return cn.hutool.core.date.DateUtil.parse(value, "yyyy-MM-dd HH:mm:ss.S");
                } else {
                    throw new RuntimeException("日期格式错误:" + value);
                }
            }
        } else if ("int".equals(fieldType) || "Integer".equals(fieldType)) {
            int i = value.lastIndexOf(".");
            if (i > 0) {
                value = value.substring(0, i);
            }
            if (isConvert) {
                return Integer.parseInt(map.get(value));
            }
            return Integer.parseInt(value);
        } else if ("double".equals(fieldType) || "Double".equals(fieldType)) {
            return Double.parseDouble(value);
        } else if ("boolean".equals(fieldType) || "Boolean".equals(fieldType)) {
            return "是".equals(value);
        } else if ("Long".equals(fieldType) || "long".equals(fieldType)) {
            return Long.parseLong(value);
        } else if ("Object[]".equals(fieldType)) {
            if (StrUtil.isNotBlank(value)) {
                String str = value.substring(1, value.length() - 1);
                return str.split(",");
            }
        } else {
            return null;
        }
        return null;
    }

    /**
     * 数据校核并设置备注
     *
     * @param file          文件
     * @param excelRuleList 校验规则
     * @return
     */
    public static List<CheckResult> checkExcelWithComment(File file, List<ExcelRule> excelRuleList) throws Exception {
        int count = 0;
        Workbook workbook = getWorkbook(getFileType(file.getName()), file);
        OutputStream out = new FileOutputStream(file);
        // 校验规则
        List<ExcelRule> excelRules = excelRuleList.stream().sorted(Comparator.comparingInt(ExcelRule::getSheetIndex))
                .collect(Collectors.toList());

        // style
        CellStyle cellStyle = getCellStyle(workbook);

        List<CheckResult> checkResultList = new ArrayList<>(16);

        // check rule
        // forEach sheet
        for (ExcelRule excelRule : excelRules) {
            Integer index = excelRule.getSheetIndex();
            Integer firstRowNum = excelRule.getFirstRowIndex();
            Sheet sheet = workbook.getSheetAt(index);
            List<SheetRule> sheetRuleList = excelRule.getSheetRuleList();

            // 获取工作表最后一行
            Integer lastRowNum = sheet.getLastRowNum();
            if (firstRowNum > lastRowNum) {
                return checkResultList;
            }

            // 设定最后一行
            Integer endRowNum = excelRule.getEndRowIndex();
            if (endRowNum == null) {
                endRowNum = lastRowNum;
            }

            Integer firstCellIndex = excelRule.getFirstCellIndex();
            Integer endCellIndex = excelRule.getEndCellIndex();

            for (int i = firstRowNum; i <= endRowNum; i++) {
                Row row = sheet.getRow(i);
                //  判断着一行是否为空行
                if (checkIsBlankRow(row)) {
                    continue;
                }

                for (SheetRule sheetRule : sheetRuleList) {
                    Integer rowNum = sheetRule.getRowIndex();
                    Integer cellNum = sheetRule.getCellIndex();
                    List<Rule> ruleList = sheetRule.getRuleList();

                    // 设置了行，则匹配行进行校验，如果没有设置行，则是所有行都要进行校验
                    if (rowNum == null || i == rowNum) {
                        // 最后一个单元格
                        int lastCellNum = row.getLastCellNum();
                        if (endCellIndex != null && endCellIndex < lastRowNum) {
                            // 如果校核规则设定的最后一列小于表格最后一列，按照设置设定的最后一行来判定
                            lastCellNum = endCellIndex;
                        }

                        if (cellNum == null) {
                            // 整行进行校验
                            for (int ci = firstCellIndex; ci < lastCellNum; ci++) {
                                Cell cell = getCell(row, cellNum);
                                String errorMsg = getCheckMessage(cell, ruleList);
                                // 设置备注
                                if (StrUtil.isNotBlank(errorMsg)) {
                                    checkResultList.add(new CheckResult(index, i, cellNum, errorMsg));
                                    setComment(sheet, cell, errorMsg, cellStyle);
                                }
                            }
                        } else {
                            // 单个单元格进行校验
                            if (cellNum < lastCellNum) {
                                Cell cell = getCell(row, cellNum);
                                String errorMsg = getCheckMessage(cell, ruleList);
                                // 设置备注
                                if (StrUtil.isNotBlank(errorMsg)) {
                                    checkResultList.add(new CheckResult(index, i, cellNum, errorMsg));
                                    setComment(sheet, cell, errorMsg, cellStyle);
                                }
                            }
                        }
                    }
                }
            }

        }
        // 写入
        workbook.write(out);
        workbook.close();
        out.close();
        return checkResultList;
    }

    /**
     * 设置单元格备注样式
     *
     * @param workbook 工作簿对象
     * @return
     */
    private static CellStyle getCellStyle(Workbook workbook) {
        // 样式
        CellStyle cellStyle = workbook.createCellStyle();
        // 背景色
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 边框线
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        return cellStyle;
    }

    /**
     * 写入错误提示
     *
     * @param cell     单元格对象
     * @param ruleList 校核规则列表
     * @return
     */
    private static String getCheckMessage(Cell cell, List<Rule> ruleList) {
        List<String> errorMsg = new ArrayList<>(4);
        for (Rule rule : ruleList) {
            boolean check = CheckRuleFactory.getCheckRule(rule).check(cell);
            if (!check) {
                // 未校核通过
                errorMsg.add(rule.getMessage());
            }
        }

        return StrUtil.join(",", errorMsg);
    }

    /**
     * 设置批注
     *
     * @param cell
     */
    private static void setComment(Sheet sheet, Cell cell, String message, CellStyle cellStyle) {
        // 可以选择保留之前备注
        // AtomicReference<String> preMsg = new AtomicReference<>();
        // Optional.ofNullable(cell.getCellComment())
        //         .flatMap(comment -> Optional.ofNullable(comment.getString()))
        //         .flatMap(richTextString -> Optional.ofNullable(richTextString.getString()))
        //         .ifPresent(preMsg::set);
        // message = preMsg.get() + "," + message;

        cell.setCellStyle(cellStyle);
        if (sheet instanceof HSSFSheet) {
            HSSFPatriarch hdp = ((HSSFSheet) sheet).createDrawingPatriarch();
            HSSFComment comment = hdp.createCellComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 4, (short) 6, 6));
            comment.setString(new HSSFRichTextString(message));
            cell.setCellComment(comment);
        } else if (sheet instanceof XSSFSheet) {
            XSSFDrawing hdp = ((XSSFSheet) sheet).createDrawingPatriarch();
            XSSFComment comment = hdp.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 4, (short) 6, 6));
            comment.setString(new XSSFRichTextString(message));
            cell.setCellComment(comment);
        }
    }

    /**
     * 判断是否为空行
     *
     * @param row 行对象
     * @return
     */
    public static boolean checkIsBlankRow(Row row) {
        if (row == null) {
            return true;
        }
        int firstCellNum = row.getFirstCellNum();
        int lastCellNum = row.getLastCellNum();
        // 总共单元格数量
        int totalCell = lastCellNum - firstCellNum + 1;
        // 空单元格数量
        int blankCell = 0;

        for (int i = firstCellNum; i <= lastCellNum; i++) {
            Cell cell = row.getCell(i);
            if (cell == null || CellType.BLANK.equals(cell.getCellType())) {
                blankCell++;
            } else if (CellType.STRING.equals(cell.getCellType())) {
                if (StrUtil.isBlank(cell.getStringCellValue().trim())) {
                    blankCell++;
                }
            } else if (CellType.NUMERIC.equals(cell.getCellType())) {
                double numericCellValue = cell.getNumericCellValue();
                if (StrUtil.isBlank(String.valueOf(numericCellValue))) {
                    blankCell++;
                }
            } else if (CellType.BOOLEAN.equals(cell.getCellType())) {
                if (StrUtil.isBlank(String.valueOf(cell.getBooleanCellValue()))) {
                    blankCell++;
                }
            } else if (CellType.FORMULA.equals(cell.getCellType())) {
                // String cellFormula = cell.getCellFormula();
                // if (StrUtil.isBlank(cellFormula)) {
                //     BlankCell++;
                // }
                // 公式单元格直接为空
                blankCell++;
            }
        }
        return totalCell == blankCell;
    }

}
