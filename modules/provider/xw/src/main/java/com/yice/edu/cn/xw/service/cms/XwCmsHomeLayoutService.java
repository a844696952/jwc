package com.yice.edu.cn.xw.service.cms;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.cms.*;
import com.yice.edu.cn.xw.dao.cms.IXwCmsColumnDao;
import com.yice.edu.cn.xw.dao.cms.IXwCmsHomeLayoutDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class XwCmsHomeLayoutService {

    @Autowired
    private IXwCmsHomeLayoutDao xwCmsHomeLayoutDao;
    @Autowired
    private XwCmsColumnService cmsColumnService;
    @Autowired
    private XwCmsStyleConfigService xwCmsStyleConfigService;
    @Autowired
    private XwCmsContentService xwCmsContentService;
    @Autowired
    private IXwCmsColumnDao xwCmsColumnDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional
    public Boolean saveCmsHomeLayout(List<Map<String,Object>> rows, String schoolId){
        if(CollUtil.isNotEmpty (rows)){
            List<XwCmsHomeLayout> xwCmsHomeLayoutList = new ArrayList<>();
            for (int i = 0; i < rows.size(); i++) {
                List<XwCmsHomeLayout> arr = JSONObject.parseArray(JSON.toJSONString(rows.get(i).get("arr")), XwCmsHomeLayout.class);
                for (XwCmsHomeLayout x: arr) {
                    x.setId(sequenceId.nextId());
                    x.setSchoolId(schoolId);
                    x.setSortNumber(i+1);
                    //设置时间
                    x.setCreateTime(DateUtil.now());
                    xwCmsHomeLayoutList.add(x);
                }
            }
            //1.删除原来布局样式
            deleteXwCmsHomeLayoutBySid(schoolId);
            //2.保存新的样式
            saveBatchXwCmsHomeLayout(xwCmsHomeLayoutList);
            return true;
        }
        return false;
    }

    @Transactional
    public List<Map<String,Object>> findAllCmsHomeLayout(String schoolId) {
        List<XwCmsHomeLayout> allCmsHomeLayout = xwCmsHomeLayoutDao.findAllCmsHomeLayout(schoolId);
        List<Map<String,Object>> rows = new ArrayList<>();
        if(CollUtil.isNotEmpty(allCmsHomeLayout)){
            List<Integer> num = new ArrayList<>();
            Map<String,Object> map = new HashMap<>();
            List<XwCmsHomeLayout> twoCmsHomeLayout = new ArrayList<>();
            for (XwCmsHomeLayout x : allCmsHomeLayout) {
                if(!num.contains(x.getSortNumber())){
                    num.add(x.getSortNumber());
                }
            }
            Collections.sort(num);
            int cNum = 0;
            int tNum = 0;
            for (Integer i : num) {
                for (XwCmsHomeLayout x : allCmsHomeLayout) {
                    if(i == x.getSortNumber()){
                        if(x.getContentId() != null){
                            x.setPosition(3);
                            tNum++;
                        }else {
                            cNum++;
                        }
                        twoCmsHomeLayout.add(x);
                    }
                }
                if(cNum == 2){
                    map.put("isOne",true);
                    cNum++;

                }else if(tNum == 1){
                    map.put("isOne",true);
                    tNum++;
                }else {
                    map.put("isOne",false);
                }
                map.put("arr",twoCmsHomeLayout);
                rows.add(map);
                map = new HashMap<>();
                twoCmsHomeLayout = new ArrayList<>();
            }

        }
        //查询当前首页布局模式
        XwCmsStyleConfig xwCmsStyleConfig = new XwCmsStyleConfig();
        xwCmsStyleConfig.setSchoolId(schoolId);
        XwCmsStyleConfig oneXwCmsStyleConfigByCondition = xwCmsStyleConfigService.findOneXwCmsStyleConfigByCondition(xwCmsStyleConfig);
        updateSortCmsHomeLayou(oneXwCmsStyleConfigByCondition.getLayoutMode(),rows);
        return rows;
    }

    //校验首行删除
    @Transactional
    public Boolean checkCmsHomeLayouTopRow(XwCmsHomeLayout xwCmsHomeLayout){
        if(Objects.isNull(xwCmsHomeLayout)){
            return false;
        }
        XwCmsColumn xwCmsColumn = new XwCmsColumn();
        xwCmsColumn.setParentId(xwCmsHomeLayout.getColumnId());
        xwCmsColumn.setSchoolId(xwCmsHomeLayout.getSchoolId());
        List<XwCmsColumn> list = xwCmsColumnDao.findXwCmsColumnListByCondition(xwCmsColumn);
        //获取首行栏目两个id
        List<String> cids = findCmsHomeLayouTopRowCids(xwCmsHomeLayout.getSchoolId());
        for (String cid : cids) {
            if(CollUtil.isNotEmpty(list)){
                for (XwCmsColumn cmsColumn : list) {
                    if(Objects.equals(cmsColumn.getId(),cid)){
                        return false;
                    }
                }
            }else {
                if(Objects.equals(xwCmsHomeLayout.getColumnId(),cid)){
                    return false;
                }
            }
        }
        return true;
    }

    private List<String> findCmsHomeLayouTopRowCids(String schoolId){
        return xwCmsHomeLayoutDao.findCmsHomeLayouTopRowCids(schoolId);
    }

    public void updateSortCmsHomeLayou(Integer mode,List<Map<String,Object>> rows){
        XwCmsHomeLayout xwCmsHomeLayout = new XwCmsHomeLayout();
        for (Map<String, Object> row : rows) {
            List<XwCmsHomeLayout> cmsHomeLayouts = (List<XwCmsHomeLayout>) row.get("arr");
            if(cmsHomeLayouts.get(0).getPosition() != null && cmsHomeLayouts.get(0).getPosition() != 3 && cmsHomeLayouts.get(0).getPosition() == mode){
                xwCmsHomeLayout = cmsHomeLayouts.get(0);
                cmsHomeLayouts.set(0,cmsHomeLayouts.get(1));
                cmsHomeLayouts.set(1,xwCmsHomeLayout);
                xwCmsHomeLayout = new XwCmsHomeLayout();
            }
        }
    }

    @Transactional
    public Boolean addOrDeleteXwCmsHomeLayoutRow(XwCmsLayoutCondition xwCmsLayoutCondition){
        if(Objects.isNull(xwCmsLayoutCondition)){
            return false;
        }
        if(xwCmsLayoutCondition.getFlag()){
            xwCmsHomeLayoutDao.updateXwCmsHomeLayoutSort(xwCmsLayoutCondition.getSortNumber(),xwCmsLayoutCondition.getSchoolId(),1);
            XwCmsHomeLayout xwCmsHomeLayout = new XwCmsHomeLayout();
            xwCmsHomeLayout.setSchoolId(xwCmsLayoutCondition.getSchoolId());
            xwCmsHomeLayout.setSortNumber(xwCmsLayoutCondition.getSortNumber() + 1);
            if(xwCmsLayoutCondition.getPosition() != null && xwCmsLayoutCondition.getPosition() == 3){
                xwCmsHomeLayout.setColumnId(xwCmsLayoutCondition.getColumnId());
                saveXwCmsHomeLayout(xwCmsHomeLayout);
            }else {
                for (int i = 0; i < 2; i++) {
                    if(i == 0){
                        xwCmsHomeLayout.setColumnId(xwCmsLayoutCondition.getColumnId1());
                    }else {
                        xwCmsHomeLayout.setColumnId(xwCmsLayoutCondition.getColumnId2());
                    }
                    saveXwCmsHomeLayout(xwCmsHomeLayout);
                }
            }

        }else {
            if(xwCmsLayoutCondition.getPosition() != null && xwCmsLayoutCondition.getPosition() == 3){
                XwCmsContent content = new XwCmsContent();
                content.setColumnId(xwCmsLayoutCondition.getColumnId());
                xwCmsContentService.deleteXwCmsContentByCondition(content);
            }
            xwCmsHomeLayoutDao.deleteXwCmsHomeLayoutBySortNumber(xwCmsLayoutCondition.getSortNumber(),xwCmsLayoutCondition.getSchoolId());
            xwCmsHomeLayoutDao.updateXwCmsHomeLayoutSort(xwCmsLayoutCondition.getSortNumber(), xwCmsLayoutCondition.getSchoolId(),-1);
        }
        return true;
    }


    @Transactional
    public List<XwCmsColumn> findLiftHomeLayoutList(String schoolId) {
        List<XwCmsHomeLayout> list = xwCmsHomeLayoutDao.findAllCmsHomeLayout(schoolId);
        XwCmsStyleConfig xwCmsStyleConfig = new XwCmsStyleConfig();
        XwCmsColumn xwCmsColumn = new XwCmsColumn();
        xwCmsColumn.setSchoolId(schoolId);
        xwCmsStyleConfig.setSchoolId(schoolId);
        XwCmsStyleConfig oneXwCmsStyleConfigByCondition = xwCmsStyleConfigService.findOneXwCmsStyleConfigByCondition(xwCmsStyleConfig);
        List<XwCmsColumn> xwCmsColumnList=null;
        if (oneXwCmsStyleConfigByCondition.getLayoutMode()==1){
            xwCmsColumn.setPosition(2);
            xwCmsColumnList = cmsColumnService.findXwCmsColumnListByCondition(xwCmsColumn,false);
        }else {
            xwCmsColumn.setPosition(1);
            xwCmsColumnList = cmsColumnService.findXwCmsColumnListByCondition(xwCmsColumn,false);
        }
        return deWeighting(list, xwCmsColumnList);
    }

    @Transactional
    public List<XwCmsColumn> findRigntHomeLayoutList(String schoolId) {
        List<XwCmsHomeLayout> list = xwCmsHomeLayoutDao.findAllCmsHomeLayout(schoolId);
        XwCmsStyleConfig xwCmsStyleConfig = new XwCmsStyleConfig();
        XwCmsColumn xwCmsColumn = new XwCmsColumn();
        xwCmsColumn.setSchoolId(schoolId);
        xwCmsStyleConfig.setSchoolId(schoolId);
        XwCmsStyleConfig oneXwCmsStyleConfigByCondition = xwCmsStyleConfigService.findOneXwCmsStyleConfigByCondition(xwCmsStyleConfig);
        List<XwCmsColumn> xwCmsColumnList=null;
        if (oneXwCmsStyleConfigByCondition.getLayoutMode()==1){
            xwCmsColumn.setPosition(1);
            xwCmsColumnList = cmsColumnService.findXwCmsColumnListByCondition(xwCmsColumn,false);
        }else {
            xwCmsColumn.setPosition(2);
            xwCmsColumnList = cmsColumnService.findXwCmsColumnListByCondition(xwCmsColumn,false);
        }
        return deWeighting(list, xwCmsColumnList);
    }

    @Transactional
    public XwCmsHomeLayout findCmsHomeLayoutByCid(String columnId){
        return xwCmsHomeLayoutDao.findCmsHomeLayoutByCid(columnId);
    }

    @Transactional
    public void updateXwCmsHomeLayout(XwCmsHomeLayout xwCmsHomeLayout){
        xwCmsHomeLayoutDao.updateXwCmsHomeLayout(xwCmsHomeLayout);
    }

    @Transactional
    public void saveXwCmsHomeLayout(XwCmsHomeLayout xwCmsHomeLayout){
        xwCmsHomeLayout.setId(sequenceId.nextId());
        xwCmsHomeLayout.setCreateTime(DateUtil.now());
        xwCmsHomeLayoutDao.saveXwCmsHomeLayout(xwCmsHomeLayout);
    }

    @Transactional
    public void deleteXwCmsHomeLayoutByCid(String columnId){
        xwCmsHomeLayoutDao.deleteXwCmsHomeLayoutByCid(columnId);
    }

    @Transactional
    public List<String> findAllColumnIds(){
        return xwCmsHomeLayoutDao.findAllColumnIds();
    }

    @Transactional
    public void deleteXwCmsHomeLayoutBySid(String schoolId){
        xwCmsHomeLayoutDao.deleteXwCmsHomeLayoutBySid(schoolId);
    }

    @Transactional
    public void saveBatchXwCmsHomeLayout(List<XwCmsHomeLayout> xwCmsHomeLayoutList){
        xwCmsHomeLayoutDao.saveBatchXwCmsHomeLayout(xwCmsHomeLayoutList);
    }

    /**
     * 初始化首页布局默认栏目
     * @param schoolId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean initCmsHomeLayout(String schoolId) {
        deleteXwCmsHomeLayoutBySid(schoolId);
        XwCmsColumn xwCmsColumn = new XwCmsColumn();
        XwCmsHomeLayout xwCmsHomeLayout = new XwCmsHomeLayout();
        List<String> columns = Arrays.asList("新闻动态", "组织架构", "教师发展", "规章制度","教育教学","生活服务");
        //初始化通栏
        XwCmsContent content = new XwCmsContent();
        //通栏栏目id
        String contentColumnId = sequenceId.nextId();
        content.setSchoolId(schoolId);
        content.setType(1);
        content.setColumnId(contentColumnId);
        content.setUrl("");
        content.setFontCoverUrl("");
        xwCmsContentService.saveXwCmsContent(content);
        XwCmsHomeLayout cmsHomeLayout = new XwCmsHomeLayout();
        cmsHomeLayout.setColumnId(contentColumnId);
        cmsHomeLayout.setSchoolId(schoolId);
        cmsHomeLayout.setSortNumber(2);
        saveXwCmsHomeLayout(cmsHomeLayout);
        //初始化普通栏目
        int sortNumber = 0;
        for (int i = 0; i < columns.size(); i++) {
            if(i%2 == 0){
                sortNumber++;
            }
            if(i == 2){
                sortNumber = 3;
            }
            xwCmsColumn.setColumnName(columns.get(i));
            setXwCmsHomeLayout(schoolId, xwCmsColumn, xwCmsHomeLayout, sortNumber);

        }

        return true;
    }

    /**
     * 对象赋值
     * @param schoolId
     * @param xwCmsColumn
     * @param xwCmsHomeLayout
     */
    private void setXwCmsHomeLayout(String schoolId, XwCmsColumn xwCmsColumn, XwCmsHomeLayout xwCmsHomeLayout, Integer sortNumber) {
        xwCmsColumn.setSchoolId(schoolId);
        XwCmsColumn xwCmsColumn1 = cmsColumnService.findOneXwCmsColumnByCondition(xwCmsColumn);
        xwCmsHomeLayout.setColumnId(xwCmsColumn1.getId());
        xwCmsHomeLayout.setSchoolId(schoolId);
        xwCmsHomeLayout.setSortNumber(sortNumber);
        saveXwCmsHomeLayout(xwCmsHomeLayout);
    }

    public List<XwCmsColumn> deWeighting(List<XwCmsHomeLayout> list, List<XwCmsColumn> xwCmsColumnList) {
        if (CollUtil.isNotEmpty(list)&&CollUtil.isNotEmpty(xwCmsColumnList)) {
            for (int i = 0; i < xwCmsColumnList.size(); i++) {
                for (XwCmsHomeLayout li : list) {
                    if (xwCmsColumnList.get(i).getId().equals(li.getColumnId())) {
                        xwCmsColumnList.remove(i);
                        i--;
                        break;
                    }
                }
            }
            for (XwCmsColumn xwCmsColumn : xwCmsColumnList) {
                XwCmsColumn xwCmsColumn1 = new XwCmsColumn();
                xwCmsColumn1.setParentId(xwCmsColumn.getId());
                long xwCmsColumnCountByCondition = cmsColumnService.findXwCmsColumnCountByCondition(xwCmsColumn1);
                if (xwCmsColumnCountByCondition!=0){
                    xwCmsColumn.setChildren(new ArrayList<>());
                }
            }
            xwCmsColumnList= xwCmsColumnList.stream().filter(c -> c.getChildren() == null && c.getColumnType()!=4 && c.getColumnType()!=5).collect(Collectors.toList());
        }
        return xwCmsColumnList;
    }

}
