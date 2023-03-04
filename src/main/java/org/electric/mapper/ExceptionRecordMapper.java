package org.electric.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.electric.model.ExceptionRecord;

import java.util.List;
@Mapper
public interface ExceptionRecordMapper extends BaseMapper<ExceptionRecord> {
    List<ExceptionRecord> getOneMonthData();

    List<ExceptionRecord> getOneWeekData();
}
