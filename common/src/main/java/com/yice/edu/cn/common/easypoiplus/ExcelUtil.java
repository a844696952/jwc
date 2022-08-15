package com.yice.edu.cn.common.easypoiplus;

import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dengfengfeng
 * @link https://www.cnblogs.com/elvinle/p/7942185.html
 */
public class ExcelUtil {


    /**
     * 创建excel的 名称管理器
     * @param workbook
     * @param sheetName sheet的名称
     * @param nameCode 要创建的名称的名字
     * @param rowIndex 要取得行得下标
     * @param size 从第一例到第多少列
     * @param cascadeFlag
     */
    private static void createExcelNameList(Workbook workbook,String sheetName,String nameCode,int rowIndex,int size,boolean cascadeFlag){
        if(workbook.getName(nameCode)!=null){
            throw new IllegalArgumentException("The sheet already contains this name '"+nameCode+"'");
        }

        Name name;
        name = workbook.createName();
        try{
            name.setNameName(nameCode);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("EXCEL名称["+nameCode+"]不符合规范，无法导出Excel，请先修改名称。");
        }
        name.setRefersToFormula(sheetName+"!"+createExcelNameList(rowIndex+1,size,cascadeFlag));


    }

    /**
     * 创建一行数据
     * @param currentRow
     * @param textList
     */
    private static void createRowData(Row currentRow, String[] textList){
        if(textList!=null&&textList.length>0){
            int i = 0;
            for(String cellValue : textList){
                Cell cell = currentRow.createCell(i++);
                cell.setCellValue(cellValue);
            }
        }
    }


    /**
     * 创建一行数据
     * @param currentRow
     * @param textList
     */
    private static void createRowData(Row currentRow, Collection<String> textList){
        if(textList!=null&&!textList.isEmpty()){
            int i = 0;
            for(String cellValue : textList){
                Cell cell = currentRow.createCell(i++);
                cell.setCellValue(cellValue);
            }
        }
    }

    /**
     * 创建一行自定义样式得数据
     * @param currentRow
     * @param textList
     * @param cellStyle
     */
    private static void createRowData(Row currentRow, Collection<String> textList,CellStyle cellStyle){
        if(textList!=null&&!textList.isEmpty()){
            int i = 0;
            for(String cellValue : textList){
                Cell cell = currentRow.createCell(i++);
                cell.setCellValue(cellValue);
                cell.setCellStyle(cellStyle);
            }
        }
    }

    /**
     * 创建一个隐藏得sheet
     * @param sheetName
     * @param workbook
     * @return
     */
    public static Sheet createHideSheet(final String sheetName, final Workbook workbook){
        if(workbook.getSheet(sheetName)!=null){
            throw new IllegalArgumentException("The workbook already contains a sheet named '"+sheetName+"'");
        }
        Sheet sheet = workbook.createSheet(sheetName);
        workbook.setSheetHidden(workbook.getSheetIndex(sheetName), true);
        return sheet;
    }

    /**
     * 在指定得行创建一行数据
     * @param sheet
     * @param rownum
     * @param list
     */
    public static void createRowDataAt(Sheet sheet,int rownum,String[] list){
        Row row = sheet.createRow(rownum);
        createRowData(row, list);
    }
    /**
     * 在指定得行创建一行数据
     * @param sheet
     * @param rownum
     * @param list
     */
    public static void createRowDataAt(Sheet sheet, int rownum, Collection<String> list){
        Row row = sheet.createRow(rownum);
        createRowData(row, list);
    }

    /**
     * 在指定得行创建一行数据
     * @param sheet
     * @param rownum
     * @param list
     */
    public static void createRowDataAt(Sheet sheet, int rownum, Collection<String> list,CellStyle cellStyle){
        Row row = sheet.createRow(rownum);
        createRowData(row, list,cellStyle);
    }


    /**
     * 创建一行数据和名称管理器
     * @param workbook
     * @param sheetName
     * @param startRowIndex
     * @param map
     * @param cellStyle
     */
    public static void createRowAndName(Workbook workbook,String sheetName, int startRowIndex, Map<String,Set<String>> map,CellStyle cellStyle){
        new NextRow(startRowIndex,cellStyle,workbook,sheetName).nextMap(map);
    }

    /**
     * 创建一行数据和名称管理器
     * @param workbook
     * @param sheetName
     * @param startRowIndex
     * @param map
     */
    public static void createRowAndName(Workbook workbook,String sheetName, int startRowIndex, Map<String,Set<String>> map){
        new NextRow(startRowIndex,workbook,sheetName).nextMap(map);
    }

    /**
     * 创建一行数据和名称管理器
     * @param workbook
     * @param sheetName
     * @param startRowIndex
     * @return
     */
    public static NextRow createRowAndName(Workbook workbook,String sheetName, int startRowIndex){
        return new NextRow(startRowIndex,workbook,sheetName);
    }

    /**
     * 创建excel得表达式
     * @param order 第几行
     * @param size 跨几列
     * @param cascadeFlag 是否级联
     * @return
     */
    private static String createExcelNameList(int order,int size,boolean cascadeFlag){
        char start = 'A';
        if(cascadeFlag){
            start = 'B';
            if(size<=25){
                char end = (char)(start+size-1);
                return "$"+start+"$"+order+":$"+end+"$"+order;
            }else{
                char endPrefix = 'A';
                char endSuffix = 'A';
                if((size-25)/26==0||size==51){//26-51之间，包括边界（仅两次字母表计算）
                    if((size-25)%26==0){//边界值
                        endSuffix = (char)('A'+25);
                    }else{
                        endSuffix = (char)('A'+(size-25)%26-1);
                    }
                }else{//51以上
                    if((size-25)%26==0){
                        endSuffix = (char)('A'+25);
                        endPrefix = (char)(endPrefix + (size-25)/26 - 1);
                    }else{
                        endSuffix = (char)('A'+(size-25)%26-1);
                        endPrefix = (char)(endPrefix + (size-25)/26);
                    }
                }
                return "$"+start+"$"+order+":$"+endPrefix+endSuffix+"$"+order;
            }
        }else{
            if(size<=26){
                char end = (char)(start+size-1);
                return "$"+start+"$"+order+":$"+end+"$"+order;
            }else{
                char endPrefix = 'A';
                char endSuffix = 'A';
                if(size%26==0){
                    endSuffix = (char)('A'+25);
                    if(size>52&&size/26>0){
                        endPrefix = (char)(endPrefix + size/26-2);
                    }
                }else{
                    endSuffix = (char)('A'+size%26-1);
                    if(size>52&&size/26>0){
                        endPrefix = (char)(endPrefix + size/26-1);
                    }
                }
                return "$"+start+"$"+order+":$"+endPrefix+endSuffix+"$"+order;
            }
        }
    }

    /**
     * 根据明确得值集合创建数据验证项
     * @param explicitListValues
     * @param naturalRowIndex
     * @param rowOffset
     * @param naturalColumnIndex
     * @param colOffset
     * @return
     */
    public static DataValidation getDataValidationByExplicitListValues(String[] explicitListValues, int naturalRowIndex,int rowOffset, int naturalColumnIndex,int colOffset){
        //加载下拉列表内容
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(explicitListValues);
        return listDataValidation(naturalRowIndex,naturalRowIndex+rowOffset, naturalColumnIndex,naturalColumnIndex+colOffset, constraint);
    }

    /**
     * 根据明确得值集合创建数据验证项
     * @param explicitListValues
     * @param naturalRowIndex
     * @param rowOffset
     * @param naturalColumnIndex
     * @param colOffset
     * @return
     */
    public static DataValidation getDataValidationByExplicitListValues(Set<String> explicitListValues, int naturalRowIndex,int rowOffset, int naturalColumnIndex,int colOffset){
        //加载下拉列表内容
        final String[] strings = explicitListValues.toArray(new String[0]);
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(strings);
        return listDataValidation(naturalRowIndex,naturalRowIndex+rowOffset, naturalColumnIndex,naturalColumnIndex+colOffset, constraint);
    }

    /**
     * 根据表达式创建数据验证项
     * @param listFormula
     * @param naturalRowIndex
     * @param rowOffset
     * @param naturalColumnIndex
     * @param colOffset
     * @return
     */
    public static DataValidation getDataValidationByListFormula(String listFormula, int naturalRowIndex, int rowOffset, int naturalColumnIndex, int colOffset){
        //加载下拉列表内容
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(listFormula);
        return listDataValidation(naturalRowIndex,naturalRowIndex+rowOffset, naturalColumnIndex,naturalColumnIndex+colOffset, constraint);
    }

    /**
     * 在指定得单元格创建数据验证项
     * @param startRowIndex
     * @param endRowIndex
     * @param startColumnIndex
     * @param endColumnIndex
     * @param constraint
     * @return
     */
    private static DataValidation listDataValidation(int startRowIndex,int endRowIndex, int startColumnIndex,int endColumnIndex, DVConstraint constraint) {
        //设置数据有效性加载在哪些单元格上。
        //四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(startRowIndex, endRowIndex, startColumnIndex, endColumnIndex);
        //数据有效性对象
        DataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
        //设置输入信息提示信息
        data_validation_list.createPromptBox("下拉选择提示", "请使用下拉方式选择合适的值！");
        //设置输入错误提示信息
        data_validation_list.createErrorBox("选择错误提示", "你输入的值未在备选列表中，请下拉选择合适的值！");
        return data_validation_list;
    }

    /**
     * 下一行操作类（主要服务于创建名称管理器）
     */
    public static class NextRow {
        private NextRow(){

        }
        private NextRow(int nextRowIndex,CellStyle cellStyle,Workbook workbook,String sheetName){
            this.cellStyle = cellStyle;
            this.nextRowIndex = nextRowIndex;
            this.workbook = workbook;
            this.sheetName = sheetName;
        }

        private NextRow(int nextRowIndex,Workbook workbook,String sheetName){
            this.cellStyle = null;
            this.nextRowIndex = nextRowIndex;
            this.workbook = workbook;
            this.sheetName = sheetName;
        }

        private int nextRowIndex;
        private CellStyle cellStyle;
        private Workbook workbook;
        private String sheetName;

        /**
         * 接受一个map，从下一行开始创建名称
         * @param map
         * @return this
         */
        public NextRow nextMap(Map<String, Set<String>> map){
            AtomicInteger atomicInteger = new AtomicInteger(nextRowIndex);
            final Sheet sheet = workbook.getSheet(sheetName);
            map.forEach((k,v)->{
                final int rowIndex = atomicInteger.get();
                Row row = sheet.createRow(rowIndex);
                atomicInteger.incrementAndGet();
                createRowData(row, v);
                createExcelNameList(workbook,sheetName,k,rowIndex,v.size(),false);
            });
            nextRowIndex += map.size();
            return this;
        }

    }

    /**
     * 锁表
     * @param workbook
     */
    public static void lockSheet(Workbook workbook,String password){
        final Sheet sheet0 = workbook.getSheetAt(0);
        sheet0.protectSheet(password);
    }

    /**
     * 锁表
     * @param workbook
     * @param sheetIndex
     * @param plusClass
     * @throws Exception
     */
    public static void lockSheet(Workbook workbook, int sheetIndex, Class<? extends ExcelPlus> plusClass,String password) throws Exception {

        final short STRING_FORMAT = (short) BuiltinFormats.getBuiltinFormat("TEXT");
        final Sheet sheet0 = workbook.getSheetAt(sheetIndex);
        sheet0.protectSheet(password);
        final CellStyle lockCellStyle = workbook.createCellStyle();
        lockCellStyle.setAlignment(HorizontalAlignment.CENTER);
        lockCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        lockCellStyle.setDataFormat(STRING_FORMAT);
        lockCellStyle.setLocked(true);
        final CellStyle unlockCellStyle = workbook.createCellStyle();
        unlockCellStyle.setAlignment(HorizontalAlignment.CENTER);
        unlockCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        unlockCellStyle.setDataFormat(STRING_FORMAT);
        unlockCellStyle.setLocked(false);


        final Field[] fields = plusClass.getDeclaredFields();
        ExportParams entity = new ExportParams();
        List<PlusExcelExportEntity> excelParams = new ArrayList<PlusExcelExportEntity>();
        PlusExportCommonService plusExportCommonService = new PlusExportCommonService();
        ExcelTarget etarget = plusClass.getAnnotation(ExcelTarget.class);
        String targetId = etarget == null ? null : etarget.value();
        plusExportCommonService.getAllExcelFieldPlus(entity.getExclusions(), targetId, fields, excelParams, plusClass,
                null, null);
        //排序
        plusExportCommonService.sortAllParamsPlus(excelParams);
        final int lastRowNum = sheet0.getLastRowNum()+1;
        final ExcelPlus excelPlus = plusClass.newInstance();
        final int dataStartRowIndex = (Integer)plusClass.getMethod("dataStartRowIndex").invoke(excelPlus);
        for (int i = 0; i < excelParams.size(); i++) {

            if(excelParams.get(i).isLock()){

                for (int i1 = dataStartRowIndex; i1 < lastRowNum; i1++) {
//                    final CellStyle cellStyle = sheet0.getRow(i1).getCell(i).getCellStyle();
                    sheet0.getRow(i1).getCell(i).setCellStyle(lockCellStyle);
                }
            }else{
                for (int i1 = dataStartRowIndex; i1 < lastRowNum; i1++) {
//                    final Cell cell = sheet0.getRow(i1).getCell(i);
//                    CellUtil.setCellStyleProperty(cell,"locked",false);
//                    final CellStyle cellStyle = sheet0.getRow(i1).getCell(i).getCellStyle();

                    sheet0.getRow(i1).getCell(i).setCellStyle(unlockCellStyle);
                }
            }
        }
    }

    /**
     * 加强操作
     * @param workbook
     * @param sheetIndex
     * @param plusClass
     * @throws Exception
     */
    public static void plus(Workbook workbook, int sheetIndex, Class<? extends ExcelPlus> plusClass,String password) throws Exception {
        //TODO

    }

    private void copyStyle(CellStyle oldStyle,CellStyle newStyle){
        //TODO

    }

}
