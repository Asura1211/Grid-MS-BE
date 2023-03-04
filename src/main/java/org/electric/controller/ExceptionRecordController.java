package org.electric.controller;

import cn.hutool.core.util.IdUtil;
import lombok.extern.log4j.Log4j2;
import org.electric.model.ExceptionRecord;
import org.electric.model.Manager;
import org.electric.service.ExceptionRecordService;
import org.electric.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,RequestMethod.POST}, origins="*")
@RestController @Log4j2
public class ExceptionRecordController {
    @Resource
    private ExceptionRecordService exceptionRecordService;
    @GetMapping("/admin/exceptionRecord/getOneMonthData")
    public Result<List<ExceptionRecord>> getOneMonthData(){
        List<ExceptionRecord> list = exceptionRecordService.getOneMonthData();
        return new Result<>(200,list);
    }

    @PostMapping("/admin/exceptionRecord/save")
    public Result<String> save(ExceptionRecord exceptionRecord){
        String id = IdUtil.simpleUUID();
        exceptionRecord.setId(id);
        boolean result = exceptionRecordService.save(exceptionRecord);
        return new Result<>(result?200:400,result?"添加成功！":"添加失败！");
    }

    @GetMapping("/admin/exceptionRecord/getOneWeekData")
    public Result<List<ExceptionRecord>> getOneWeekData(){
        List<ExceptionRecord> list = exceptionRecordService.getOneWeekData();
        return new Result<>(200,list);
    }
}
