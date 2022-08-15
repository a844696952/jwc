package  com.yice.edu.cn.jw.dao.timetable;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yice.edu.cn.common.pojo.jw.timetable.Timetable;
import com.yice.edu.cn.common.pojo.jw.timetable.TimetableAdjustCondition;

@Mapper
public interface ITimetableDao {
    List<Timetable> findTimetableListByCondition(Timetable timetable);

    long findTimetableCountByCondition(Timetable timetable);

    Timetable findOneTimetableByCondition(Timetable timetable);

    Timetable findTimetableById(@Param("id") String id);

    void saveTimetable(Timetable timetable);

    void updateTimetable(Timetable timetable);

    void deleteTimetable(@Param("id") String id);

    void deleteTimetableByCondition(Timetable timetable);

    void batchSaveTimetable(List<Timetable> timetables);
    
    long detectionOfConflict(TimetableAdjustCondition condition);
}
