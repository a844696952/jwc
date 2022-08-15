package com.yice.edu.cn.xw.service.cms;

import cn.hutool.core.collection.CollectionUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.cms.*;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.xw.dao.cms.IXwCmsColumnDao;
import com.yice.edu.cn.xw.dao.cms.IXwCmsContentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class XwCmsColumnService {
    @Autowired
    private IXwCmsColumnDao xwCmsColumnDao;
    @Autowired
    private XwCmsStyleConfigService xwCmsStyleConfigService;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IXwCmsContentDao xwCmsContentDao;
    @Autowired
    private XwCmsHomeLayoutService xwCmsHomeLayoutService;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public XwCmsColumn findXwCmsColumnById(String id) {
        return xwCmsColumnDao.findXwCmsColumnById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer saveXwCmsColumn(XwCmsColumn xwCmsColumn) {
        //String columnName ;
        Integer mark = 1 ;
        Integer level ;
        XwCmsColumn column = findXwCmsColumnById(xwCmsColumn.getParentId());
        if(Objects.isNull(column) || Objects.isNull(column.getColumnLevel())){
            //10000 根目录异常状态
            return 10000;
        }else{
            if(column.getColumnLevel() == -1){
                level = 1;
                //columnName = "一级栏目";
            }else if(column.getColumnLevel() == 1){
                level = 2;
                //columnName = "二级栏目";
            }else{
                return 10000;
            }
        }
        //先查出来之前的用来判断栏目名是否重复
        XwCmsColumn find = new XwCmsColumn();
        find.setParentId(xwCmsColumn.getParentId());
        List<XwCmsColumn> list = findXwCmsColumnListByConditionNotree(find);
        if(CollectionUtil.isNotEmpty(list)){
            //判断自动生成昵称是否重复
            mark = list.size()+1;
            //columnName =  columnName.concat(String.valueOf(mark));
            for(int i=0;i<list.size();i++){
                if(Objects.equals(xwCmsColumn.getColumnName(),list.get(i).getColumnName())){
                    return 10001;
                }
            }
        }else{
            //columnName =  columnName.concat(String.valueOf(mark));
            if(level == 2){
                //表示是添加的是2级栏目，并且是第一次添加，则制空该一级栏目
                column.setColumnType(null);
                column.setPosition(null);
                xwCmsColumnDao.updateXwCmsColumnForAll(column);
                //还要清空一级栏目的内容
                XwCmsContent xwCmsContent = new XwCmsContent();
                xwCmsContent.setColumnId(column.getId());
                xwCmsContentDao.deleteXwCmsContentByCondition(xwCmsContent);
            }
        }
        xwCmsColumn.setId(sequenceId.nextId());
        xwCmsColumn.setColumnName(xwCmsColumn.getColumnName());
        xwCmsColumn.setColumnNumber(mark);
        //因产品要求默认文章列表页
        xwCmsColumn.setColumnType(xwCmsColumn.getColumnType());
        xwCmsColumn.setIsShow(1);
        xwCmsColumn.setColumnLevel(level);
        xwCmsColumn.setPosition(chonse(xwCmsColumn.getColumnType()));
        xwCmsColumnDao.saveXwCmsColumn(xwCmsColumn);
        return 10002;
    }



    @Transactional(rollbackFor = Exception.class)
    public List<XwCmsColumn> findXwCmsColumnListByCondition(XwCmsColumn xwCmsColumn,Boolean tree) {
        List<XwCmsColumn> cmsColumnArrayList = xwCmsColumnDao.findXwCmsColumnListByCondition(xwCmsColumn);
        if(CollectionUtil.isEmpty(cmsColumnArrayList)){
            //说明初始化--->1 初始化栏目
            initXwCmsColumn(xwCmsColumn.getSchoolId());
            XwCmsStyleConfig xwCmsStyleConfig = new XwCmsStyleConfig();
            xwCmsStyleConfig.setSchoolId(xwCmsColumn.getSchoolId());
            xwCmsStyleConfigService.saveXwCmsStyleConfig(xwCmsStyleConfig);
            return findXwCmsColumnListByCondition(xwCmsColumn,tree);
        }else{
            if(Objects.nonNull(xwCmsColumn.getBanner()) && !xwCmsColumn.getBanner()){
                cmsColumnArrayList = cmsColumnArrayList.stream().filter(x -> !("Banner".equals(x.getColumnName()) ||
                        "应用中心".equals(x.getColumnName()))).collect(Collectors.toList());
            }
            if(tree){
                return ObjectKit.buildTree(cmsColumnArrayList,"-1");
            }else{
                return cmsColumnArrayList;
            }
        }
    }








    @Transactional(readOnly = true)
    public XwCmsColumn findOneXwCmsColumnByCondition(XwCmsColumn xwCmsColumn) {
        return xwCmsColumnDao.findOneXwCmsColumnByCondition(xwCmsColumn);
    }
    @Transactional(readOnly = true)
    public long findXwCmsColumnCountByCondition(XwCmsColumn xwCmsColumn) {
        return xwCmsColumnDao.findXwCmsColumnCountByCondition(xwCmsColumn);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean updateXwCmsColumn(XwCmsColumn xwCmsColumn) {
        XwCmsColumn find = new XwCmsColumn();
        find.setParentId(xwCmsColumn.getParentId());
        List<XwCmsColumn> list = findXwCmsColumnListByConditionNotree(find);
        if(CollectionUtil.isNotEmpty(list)){
            list = list.stream().filter(x->!Objects.equals(xwCmsColumn.getId(),x.getId())).collect(Collectors.toList());
            for(int i=0;i<list.size();i++){
                if(xwCmsColumn.getColumnName().equals(list.get(i).getColumnName())){
                    return false;
                }
            }
        }
        if(xwCmsColumn.getColumnType()== -1){
            xwCmsColumn.setColumnType(null);
            xwCmsColumn.setPosition(null);
        }else{
            xwCmsColumn.setPosition(chonse(xwCmsColumn.getColumnType()));
        }
        xwCmsColumnDao.updateXwCmsColumnForAll(xwCmsColumn);
        return true;
    }



    @Transactional(rollbackFor = Exception.class)
    public Boolean updateXwCmsColumnMove(XwCmsColumn xwCmsColumn) {
        if(Objects.isNull(xwCmsColumn.getColumnType())){
            xwCmsColumn.setPosition(null);
        }else{
            xwCmsColumn.setPosition(chonse(xwCmsColumn.getColumnType()));
        }
        xwCmsColumnDao.updateXwCmsColumn(xwCmsColumn);
        return true;
    }


    @Transactional
    public void updateXwCmsColumnForAll(XwCmsColumn xwCmsColumn) {
        xwCmsColumnDao.updateXwCmsColumnForAll(xwCmsColumn);
    }




    private void deleteSettingMove(XwCmsColumn column,Boolean flag){
        Integer number = column.getColumnNumber();
        XwCmsColumn find = new XwCmsColumn();
        find.setSchoolId(column.getSchoolId());
        find.setParentId(column.getParentId());
        List<XwCmsColumn> xwCmsColumns = findXwCmsColumnListByConditionNotree(find);
        if(flag && xwCmsColumns.size() == 1){
            //删除一级栏目下面所有二级栏目之后，默认给一个该一级栏目的文档类型和类别
            XwCmsColumn xwCmsColumn = xwCmsColumnDao.findXwCmsColumnById(column.getParentId());
            xwCmsColumn.setColumnType(1);
            xwCmsColumn.setPosition(chonse(1));
            xwCmsColumnDao.updateXwCmsColumnForAll(xwCmsColumn);
        }
        xwCmsColumns = xwCmsColumns.stream().filter(x-> x.getColumnNumber() > number).collect(Collectors.toList());
        if(CollectionUtil.isNotEmpty(xwCmsColumns)){
            //说明删除在尾端不必处理 不为空则处理
            xwCmsColumns.forEach(x->{
                x.setColumnNumber(x.getColumnNumber()-1);
                xwCmsColumnDao.updateXwCmsColumn(x);
            });
        }
        xwCmsColumnDao.deleteXwCmsColumn(column.getId());
    }




    @Transactional(rollbackFor = Exception.class)
    public void deleteXwCmsColumn(String id) {
        XwCmsColumn column = findXwCmsColumnById(id);
        if(Objects.isNull(column)){
            return;
        }
        if(column.getColumnLevel() == 2){
            // 首选如果是二级子栏目删除
            deleteSettingMove(column,true);
        }else{
            //说明是一级栏目删除
            deleteSettingMove(column,false);
            //删除级联栏目id
            XwCmsColumn xwCmsColumn = new XwCmsColumn();
            xwCmsColumn.setParentId(id);
            xwCmsColumnDao.deleteXwCmsColumnByCondition(xwCmsColumn);
        }
        //删除栏目下的所有文章
        XwCmsContent xwCmsContent = new XwCmsContent();
        xwCmsContent.setColumnId(id);
        xwCmsContentDao.deleteXwCmsContentByCondition(xwCmsContent);
        //删除栏目对应的首页布局行
        XwCmsHomeLayout cmsHomeLayout = xwCmsHomeLayoutService.findCmsHomeLayoutByCid(id);
        if(Objects.nonNull(cmsHomeLayout)){
            XwCmsLayoutCondition xwCmsLayoutCondition = new XwCmsLayoutCondition();
            xwCmsLayoutCondition.setSortNumber(cmsHomeLayout.getSortNumber());
            xwCmsLayoutCondition.setSchoolId(cmsHomeLayout.getSchoolId());
            xwCmsLayoutCondition.setFlag(false);
            xwCmsHomeLayoutService.addOrDeleteXwCmsHomeLayoutRow(xwCmsLayoutCondition);
        }
    }



    @Transactional
    public void deleteXwCmsColumnByCondition(XwCmsColumn xwCmsColumn) {
        xwCmsColumnDao.deleteXwCmsColumnByCondition(xwCmsColumn);
    }



    @Transactional
    public void batchSaveXwCmsColumn(List<XwCmsColumn> xwCmsColumns){
        xwCmsColumns.forEach(xwCmsColumn -> xwCmsColumn.setId(sequenceId.nextId()));
        xwCmsColumnDao.batchSaveXwCmsColumn(xwCmsColumns);
    }

 /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/



    @Transactional(rollbackFor = Exception.class)
    public Boolean move(XwCmsColumn xwCmsColumn) {
        XwCmsColumn columnById = findXwCmsColumnById(xwCmsColumn.getId());
        if(Objects.isNull(columnById)){
            return false;
        }
        Integer move ;
        Integer pastMove = columnById.getColumnNumber();
        if(xwCmsColumn.getBanner()){
            //false 上移动  true 下移动
            move = pastMove+1;
        }else{
            move = pastMove-1;
        }
        XwCmsColumn cmsColumn = new XwCmsColumn();
        cmsColumn.setSchoolId(xwCmsColumn.getSchoolId());
        cmsColumn.setColumnNumber(move);
        cmsColumn.setParentId(columnById.getParentId());
        XwCmsColumn column = findOneXwCmsColumnByCondition(cmsColumn);
        if(Objects.isNull(column)){
            return false;
        }else{
            columnById.setColumnNumber(move);
            updateXwCmsColumnMove(columnById);
            //更新被移动的
            column.setColumnNumber(pastMove);
            updateXwCmsColumnMove(column);
            return true;
        }

    }




    public void saveXwCmsColumnNoId(XwCmsColumn xwCmsColumn) {
        xwCmsColumnDao.saveXwCmsColumn(xwCmsColumn);
    }


    public List<XwCmsColumn> findXwCmsColumnListByConditionNotree(XwCmsColumn xwCmsColumn) {
        return xwCmsColumnDao.findXwCmsColumnListByCondition(xwCmsColumn);
    }



    public Integer chonse(Integer type){
        if(type == 1 || type == 3){
            return 1;
        }
        if(type == 2 || type == 7){
            return 2;
        }
        return null;
    }

    /**
     *
     * 每个学校初始化栏目相关参数
     * @return true 成功 false 失败
     * */
    public Boolean initXwCmsColumn(String schoolId){
        if(Objects.isNull(schoolId)){
            return false;
        }
        XwCmsColumn find = new XwCmsColumn();
        find.setSchoolId(schoolId);
        Pager pager = new Pager();
        pager.setPageSize(100);
        find.setPager(pager);
        List<XwCmsColumn> list = findXwCmsColumnListByConditionNotree(find);
        if(CollectionUtil.isNotEmpty(list)){
            return false;
        }
        String id = sequenceId.nextId();
        //初始化bannner
        XwCmsColumn xwCmsColumnBanner = new XwCmsColumn();
        xwCmsColumnBanner.setId(sequenceId.nextId());
        xwCmsColumnBanner.setColumnName("Banner");
        xwCmsColumnBanner.setColumnNumber(1);
        xwCmsColumnBanner.setIsShow(1);
        xwCmsColumnBanner.setParentId(id);
        xwCmsColumnBanner.setColumnLevel(1);
        xwCmsColumnBanner.setSchoolId(schoolId);
        saveXwCmsColumnNoId(xwCmsColumnBanner);
        //初始化App
        xwCmsColumnBanner.setId(sequenceId.nextId());
        xwCmsColumnBanner.setColumnName("应用中心");
        xwCmsColumnBanner.setColumnNumber(2);
        saveXwCmsColumnNoId(xwCmsColumnBanner);

        // 初始化栏目
        XwCmsColumn xwCmsColumn = new XwCmsColumn();
        xwCmsColumn.setId(id);
        xwCmsColumn.setColumnName("根目录");
        xwCmsColumn.setColumnNumber(1);
        xwCmsColumn.setParentId("-1");
        xwCmsColumn.setIsShow(1);
        xwCmsColumn.setColumnLevel(-1);
        xwCmsColumn.setSchoolId(schoolId);
        saveXwCmsColumnNoId(xwCmsColumn);
        //一级学校概况
        String id1 = sequenceId.nextId();
        xwCmsColumn.setId(id1);
        xwCmsColumn.setColumnName("学校概况");
        xwCmsColumn.setColumnNumber(3);
        xwCmsColumn.setParentId(id);
        xwCmsColumn.setColumnLevel(1);
       /* xwCmsColumn.setColumnType(1);
        xwCmsColumn.setPosition(chonse(1));*/
        saveXwCmsColumnNoId(xwCmsColumn);
        //二级栏目
        xwCmsColumn.setId( sequenceId.nextId());
        xwCmsColumn.setColumnName("学校简介");
        xwCmsColumn.setColumnNumber(1);
        xwCmsColumn.setParentId(id1);
        xwCmsColumn.setColumnLevel(2);
        xwCmsColumn.setColumnType(4);
        xwCmsColumn.setPosition(chonse(4));
        saveXwCmsColumnNoId(xwCmsColumn);
        xwCmsColumn.setId( sequenceId.nextId());
        xwCmsColumn.setColumnName("组织架构");
        xwCmsColumn.setColumnNumber(2);
        xwCmsColumn.setColumnType(7);
        xwCmsColumn.setPosition(chonse(7));
        saveXwCmsColumnNoId(xwCmsColumn);
        xwCmsColumn.setId( sequenceId.nextId());
        xwCmsColumn.setColumnName("规章制度");
        xwCmsColumn.setColumnNumber(3);
        xwCmsColumn.setColumnType(1);
        xwCmsColumn.setPosition(chonse(1));
        saveXwCmsColumnNoId(xwCmsColumn);

        //一级数字校园
        String id2 = sequenceId.nextId();
        xwCmsColumn.setId(id2);
        xwCmsColumn.setColumnName("数字校园");
        xwCmsColumn.setColumnNumber(4);
        xwCmsColumn.setParentId(id);
        xwCmsColumn.setColumnLevel(1);
        xwCmsColumn.setColumnType(null);
        xwCmsColumn.setPosition(null);
        saveXwCmsColumnNoId(xwCmsColumn);
        //二级栏目
        xwCmsColumn.setId(sequenceId.nextId());
        xwCmsColumn.setColumnName("党员e家");
        xwCmsColumn.setColumnNumber(1);
        xwCmsColumn.setParentId(id2);
        xwCmsColumn.setColumnLevel(2);
        xwCmsColumn.setColumnType(1);
        xwCmsColumn.setPosition(chonse(1));
        saveXwCmsColumnNoId(xwCmsColumn);

        //一级校园宣传
        String id3 = sequenceId.nextId();
        xwCmsColumn.setId(id3);
        xwCmsColumn.setColumnName("校园宣传");
        xwCmsColumn.setColumnNumber(5);
        xwCmsColumn.setParentId(id);
        xwCmsColumn.setColumnLevel(1);
        xwCmsColumn.setColumnType(null);
        xwCmsColumn.setPosition(null);
        saveXwCmsColumnNoId(xwCmsColumn);
        //二级栏目
        xwCmsColumn.setId(sequenceId.nextId());
        xwCmsColumn.setColumnName("新闻动态");
        xwCmsColumn.setColumnNumber(1);
        xwCmsColumn.setParentId(id3);
        xwCmsColumn.setColumnLevel(2);
        xwCmsColumn.setColumnType(1);
        xwCmsColumn.setPosition(chonse(1));
        saveXwCmsColumnNoId(xwCmsColumn);
        xwCmsColumn.setId(sequenceId.nextId());
        xwCmsColumn.setColumnName("通知公告");
        xwCmsColumn.setColumnNumber(2);
        xwCmsColumn.setColumnType(1);
        xwCmsColumn.setPosition(chonse(1));
        saveXwCmsColumnNoId(xwCmsColumn);
        xwCmsColumn.setId( sequenceId.nextId());
        xwCmsColumn.setColumnName("生活服务");
        xwCmsColumn.setColumnNumber(3);
        xwCmsColumn.setColumnType(3);
        xwCmsColumn.setPosition(chonse(3));
        saveXwCmsColumnNoId(xwCmsColumn);

        //一级教育教学
        xwCmsColumn.setId(sequenceId.nextId());
        xwCmsColumn.setColumnName("教育教学");
        xwCmsColumn.setColumnNumber(6);
        xwCmsColumn.setParentId(id);
        xwCmsColumn.setColumnLevel(1);
        xwCmsColumn.setColumnType(2);
        xwCmsColumn.setPosition(chonse(2));
        saveXwCmsColumnNoId(xwCmsColumn);

        //一级教育评价
        String id5 = sequenceId.nextId();
        xwCmsColumn.setId(id5);
        xwCmsColumn.setColumnName("教育评价");
        xwCmsColumn.setColumnNumber(7);
        xwCmsColumn.setParentId(id);
        xwCmsColumn.setColumnLevel(1);
        xwCmsColumn.setColumnType(null);
        xwCmsColumn.setPosition(null);
        saveXwCmsColumnNoId(xwCmsColumn);
        //二级栏目
        xwCmsColumn.setId( sequenceId.nextId());
        xwCmsColumn.setColumnName("教师发展");
        xwCmsColumn.setColumnNumber(1);
        xwCmsColumn.setParentId(id5);
        xwCmsColumn.setColumnLevel(2);
        xwCmsColumn.setColumnType(7);
        xwCmsColumn.setPosition(chonse(7));
        saveXwCmsColumnNoId(xwCmsColumn);
        xwCmsColumn.setId( sequenceId.nextId());
        xwCmsColumn.setColumnName("学生发展");
        xwCmsColumn.setColumnNumber(2);
        xwCmsColumn.setColumnType(7);
        xwCmsColumn.setPosition(chonse(7));
        saveXwCmsColumnNoId(xwCmsColumn);


        return true;
    }

    public List<XwCmsColumn> selectBannerAndApp(String schoolId) {
        return xwCmsColumnDao.selectBannerAndApp(schoolId);
    }
}
