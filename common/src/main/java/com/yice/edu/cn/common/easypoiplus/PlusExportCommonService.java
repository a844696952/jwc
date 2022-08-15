package com.yice.edu.cn.common.easypoiplus;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.export.base.ExportCommonService;
import cn.afterturn.easypoi.exception.excel.ExcelExportException;
import cn.afterturn.easypoi.exception.excel.enums.ExcelExportEnum;
import cn.afterturn.easypoi.util.PoiPublicUtil;
import cn.afterturn.easypoi.util.PoiReflectorUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;


/**
 * @author dengfengfeng
 * 加强版 主要目的为了配合 PlusExcelExportEntity 一起使用
 * @see ExportCommonService 里的代码
 */
public class PlusExportCommonService {

    /**
     * 对字段根据用户设置排序
     */
    public void sortAllParamsPlus(List<PlusExcelExportEntity> excelParams) {
        // 自然排序,group 内部排序,集合内部排序
        // 把有groupName的统一收集起来,内部先排序
        Map<String, List<PlusExcelExportEntity>> groupMap = new HashMap<String, List<PlusExcelExportEntity>>();
        for (int i = excelParams.size() - 1; i > -1; i--) {
            // 集合内部排序
            if (excelParams.get(i).getList() != null) {
                Collections.sort(excelParams.get(i).getList());
            } else if (StringUtils.isNoneEmpty(excelParams.get(i).getGroupName())) {
                if (!groupMap.containsKey(excelParams.get(i).getGroupName())) {
                    groupMap.put(excelParams.get(i).getGroupName(), new ArrayList<PlusExcelExportEntity>());
                }
                groupMap.get(excelParams.get(i).getGroupName()).add(excelParams.get(i));
                excelParams.remove(i);
            }
        }
        Collections.sort(excelParams);
        if (groupMap.size() > 0) {
            // group 内部排序
            for (Iterator it = groupMap.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, List<PlusExcelExportEntity>> entry = (Map.Entry) it.next();
                Collections.sort(entry.getValue());
                // 插入到excelParams当中
                boolean isInsert = false;
                String groupName = "START";
                for (int i = 0; i < excelParams.size(); i++) {
                    // 跳过groupName 的元素,防止破会内部结构
                    if (excelParams.get(i).getOrderNum() > entry.getValue().get(0).getOrderNum()
                            && !groupName.equals(excelParams.get(i).getGroupName())) {
                        if (StringUtils.isNotEmpty(excelParams.get(i).getGroupName())) {
                            groupName = excelParams.get(i).getGroupName();
                        }
                        excelParams.addAll(i, entry.getValue());
                        isInsert = true;
                        break;
                    } else if (!groupName.equals(excelParams.get(i).getGroupName()) &&
                            StringUtils.isNotEmpty(excelParams.get(i).getGroupName())) {
                        groupName = excelParams.get(i).getGroupName();
                    }
                }
                //如果都比他小就插入到最后
                if (!isInsert) {
                    excelParams.addAll(entry.getValue());
                }
            }
        }
    }

    /**
     * 获取需要导出的全部字段
     *
     * @param targetId 目标ID
     */
    public void getAllExcelFieldPlus(String[] exclusions, String targetId, Field[] fields,
                                 List<PlusExcelExportEntity> excelParams, Class<?> pojoClass,
                                 List<Method> getMethods, ExcelEntity excelGroup) throws Exception {
        List<String> exclusionsList = exclusions != null ? Arrays.asList(exclusions) : null;
        PlusExcelExportEntity excelEntity;
        // 遍历整个filed
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            // 先判断是不是collection,在判断是不是java自带对象,之后就是我们自己的对象了
            if (PoiPublicUtil.isNotUserExcelUserThis(exclusionsList, field, targetId)) {
                continue;
            }
            // 首先判断Excel 可能一下特殊数据用户回自定义处理
            if (field.getAnnotation(Excel.class) != null) {
                Excel excel = field.getAnnotation(Excel.class);
                String name = PoiPublicUtil.getValueByTargetId(excel.name(), targetId, null);
                if (StringUtils.isNotBlank(name)) {
                    excelParams.add(createExcelExportEntity(field, targetId, pojoClass, getMethods, excelGroup));
                }
            } else if (PoiPublicUtil.isCollection(field.getType())) {
                ExcelCollection excel = field.getAnnotation(ExcelCollection.class);
                ParameterizedType pt = (ParameterizedType) field.getGenericType();
                Class<?> clz = (Class<?>) pt.getActualTypeArguments()[0];
                List<PlusExcelExportEntity> list = new ArrayList<>();
                getAllExcelFieldPlus(exclusions,
                        StringUtils.isNotEmpty(excel.id()) ? excel.id() : targetId,
                        PoiPublicUtil.getClassFields(clz), list, clz, null, null);
                excelEntity = new PlusExcelExportEntity();
                excelEntity.setName(PoiPublicUtil.getValueByTargetId(excel.name(), targetId, null));
                excelEntity.setOrderNum(Integer
                        .valueOf(PoiPublicUtil.getValueByTargetId(excel.orderNum(), targetId, "0")));
                excelEntity
                        .setMethod(PoiReflectorUtil.fromCache(pojoClass).getGetMethod(field.getName()));
                excelEntity.setList(excelEntity.superList());
                excelEntity.setPlusList(list);
                excelParams.add(excelEntity);
            } else {
                List<Method> newMethods = new ArrayList<Method>();
                if (getMethods != null) {
                    newMethods.addAll(getMethods);
                }
                newMethods.add(PoiReflectorUtil.fromCache(pojoClass).getGetMethod(field.getName()));
                ExcelEntity excel = field.getAnnotation(ExcelEntity.class);
                if (excel.show() && StringUtils.isEmpty(excel.name())) {
                    throw new ExcelExportException("if use ExcelEntity ,name mus has value ,data: " + ReflectionToStringBuilder.toString(excel), ExcelExportEnum.PARAMETER_ERROR);
                }
                getAllExcelFieldPlus(exclusions,
                        StringUtils.isNotEmpty(excel.id()) ? excel.id() : targetId,
                        PoiPublicUtil.getClassFields(field.getType()), excelParams, field.getType(),
                        newMethods, excel.show() ? excel : null);
            }
        }
    }

    /**
     * 创建导出实体对象
     */
    private PlusExcelExportEntity createExcelExportEntity(Field field, String targetId,
                                                      Class<?> pojoClass,
                                                      List<Method> getMethods, ExcelEntity excelGroup) throws Exception {
        Excel excel = field.getAnnotation(Excel.class);
        PlusExcelExportEntity excelEntity = new PlusExcelExportEntity();
        excelEntity.setType(excel.type());
        getExcelField(targetId, field, excelEntity, excel, pojoClass, excelGroup);
        if (getMethods != null) {
            List<Method> newMethods = new ArrayList<Method>(getMethods);
            newMethods.add(excelEntity.getMethod());
            excelEntity.setMethods(newMethods);
        }
        return excelEntity;
    }

    /**
     * 注解到导出对象的转换
     */
    private void getExcelField(String targetId, Field field, PlusExcelExportEntity excelEntity,
                               Excel excel, Class<?> pojoClass, ExcelEntity excelGroup) throws Exception {
        excelEntity.setName(PoiPublicUtil.getValueByTargetId(excel.name(), targetId, null));
        excelEntity.setWidth(excel.width());
        excelEntity.setHeight(excel.height());
        excelEntity.setNeedMerge(excel.needMerge());
        excelEntity.setMergeVertical(excel.mergeVertical());
        excelEntity.setMergeRely(excel.mergeRely());
        excelEntity.setReplace(excel.replace());
        excelEntity.setOrderNum(
                Integer.valueOf(PoiPublicUtil.getValueByTargetId(excel.orderNum(), targetId, "0")));
        excelEntity.setWrap(excel.isWrap());
        excelEntity.setExportImageType(excel.imageType());
        excelEntity.setSuffix(excel.suffix());
        excelEntity.setDatabaseFormat(excel.databaseFormat());
        excelEntity.setFormat(
                StringUtils.isNotEmpty(excel.exportFormat()) ? excel.exportFormat() : excel.format());
        excelEntity.setStatistics(excel.isStatistics());
        excelEntity.setHyperlink(excel.isHyperlink());
        excelEntity.setMethod(PoiReflectorUtil.fromCache(pojoClass).getGetMethod(field.getName()));
        excelEntity.setNumFormat(excel.numFormat());
        excelEntity.setColumnHidden(excel.isColumnHidden());
        excelEntity.setDict(excel.dict());
        excelEntity.setEnumExportField(excel.enumExportField());
        if (excelGroup != null) {
            excelEntity.setGroupName(PoiPublicUtil.getValueByTargetId(excelGroup.name(), targetId, null));
        } else {
            excelEntity.setGroupName(excel.groupName());
        }

        excelEntity.setLock(field.getAnnotation(LockExcelColumn.class) != null);

    }

    /**
     * 判断是否锁列
     * @param field
     * @return
     */
    private boolean isLock(Field field){
        final ExcelPlusA excelPlusA = field.getAnnotation(ExcelPlusA.class);
        return  excelPlusA != null && excelPlusA.lockColumn();
    }
}
