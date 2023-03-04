package org.electric.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.log4j.Log4j2;
import org.electric.model.DevicesChange;
import org.electric.service.DevicesChangeService;
import org.electric.utils.Result;
import org.electric.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 * @description //TODO
 **/

@CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,RequestMethod.POST}, origins="*")
@RestController @Log4j2
public class DevicesChangeController extends UploadUtil {

    @Value("${spring.servlet.upload.drive.letter}")
    private String driveLetter;
    @Value("${spring.servlet.upload.base-path}")
    private String uploadPath;

    @Resource
    private DevicesChangeService devicesChangeService;

    @GetMapping("/admin/devicesChange/list/{currentPage}/{pageSize}")
    public Result<IPage<Map<String, String>>> listByPage(@PathVariable Integer currentPage, @PathVariable Integer pageSize, HttpServletRequest request){
        IPage<Map<String, String>> result = devicesChangeService.listByPage(currentPage,pageSize,getParameters(request));
        return new Result<>(200,result);
    }

    @PostMapping("/admin/devicesChange/save")
    public Result<String> save(DevicesChange devicesChange){
        String id = IdUtil.simpleUUID();
        devicesChange.setId(id);
        boolean result = devicesChangeService.save(devicesChange);
        return new Result<>(result?200:400,result?"添加成功！":"添加失败！");
    }

    @GetMapping("/admin/devicesChange/get/{id}")
    public Result<DevicesChange> getById(@PathVariable String id){
        DevicesChange devicesChange = devicesChangeService.getById(id);
        return new Result<>(200,devicesChange);
    }

    @PostMapping("/admin/devicesChange/update")
    public Result<String> update(DevicesChange devicesChange){
        boolean result = devicesChangeService.updateById(devicesChange);
        return new Result<>(result?200:400,result?"修改成功！":"修改失败！");
    }

    @GetMapping("/admin/devicesChange/delete/{id}")
    public Result<String> delete(@PathVariable String id){
        boolean result = devicesChangeService.removeById(id);
        return new Result<>(result?200:400,result?"删除成功！":"删除失败！");
    }

    /**
     * 删除多条
     * @param ids 多个id主键
     * @return java.lang.String 跳转页面
     */
    @GetMapping("/admin/devicesChange/deleteAll/{ids}")
    public Result<String> deleteAll(@PathVariable List<String> ids){
        boolean result = devicesChangeService.removeByIds(ids);
        return new Result<>(result?200:400,result?"删除成功！":"删除失败！");
    }

    @GetMapping("/admin/devicesChange/export")
    public void export(HttpServletResponse response) {
        try {
            List<Map<String,String>> list = devicesChangeService.listAll();
            String fileName = "设备信息变更记录.xlsx";
            File file = new File(driveLetter+":\\" + uploadPath + "\\excel" + "\\" + fileName);
            if(file.exists()){
                file.delete();
            }
            ExcelWriter writer = ExcelUtil.getBigWriter(driveLetter+":\\" + uploadPath + "\\excel" + "\\" + fileName);
            String[] columnNames = {"device_name", "old_manager", "old_price", "old_address", "old_state", "new_manager", "new_price", "new_address", "new_state"};
            String[] titleNames = {"设备名称","原负责人","原设备价值","原设备区域","原状态","现负责人","现设备价值","现设备区域","现状态"};
            for (int i = 0; i < columnNames.length; i++) {
                writer.addHeaderAlias(columnNames[i], titleNames[i]);
            }
            writer.setColumnWidth(3,18);
            writer.setColumnWidth(4,18);
            writer.setColumnWidth(5,18);
            writer.write(list,true);

            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,"utf-8"));
            ServletOutputStream out = null;
            try {
                out = response.getOutputStream();
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                writer.close();
            }
            IoUtil.close(out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> getParameters(HttpServletRequest request){
        Map<String, String> params = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    params.put(paramName, paramValue);
                }
            }
        }
        return params;
    }

}
