package org.electric.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.log4j.Log4j2;
import org.electric.model.Devices;
import org.electric.model.Inspect;
import org.electric.service.DevicesService;
import org.electric.service.InspectService;
import org.electric.utils.Result;
import org.electric.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
 **/

@CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,RequestMethod.POST}, origins="*")
@RestController @Log4j2
public class InspectController extends UploadUtil {

    @Value("${spring.servlet.upload.drive.letter}")
    private String driveLetter;
    @Value("${spring.servlet.upload.base-path}")
    private String uploadPath;

    @Resource
    private InspectService inspectService;
    @Resource
    private DevicesService devicesService;

    @GetMapping("/admin/inspect/list/{currentPage}/{pageSize}")
    public Result<IPage<Map<String, String>>> listByPage(@PathVariable Integer currentPage, @PathVariable Integer pageSize, HttpServletRequest request){
        IPage<Map<String, String>> result = inspectService.listByPage(currentPage,pageSize,getParameters(request));
        return new Result<>(200,result);
    }

    @PostMapping("/admin/inspect/save")
    public Result<String> save(Inspect inspect,MultipartFile file){
        String id = IdUtil.simpleUUID();
        inspect.setPicture(fileUpload(file,"inspect",inspect.getPicture(),id));
        inspect.setId(id);
        Devices devices = devicesService.getById(inspect.getDeviceid());
        devices.setState(inspect.getState());
        devicesService.updateById(devices);
        boolean result = inspectService.save(inspect);
        return new Result<>(result?200:400,result?"添加成功！":"添加失败！");
    }

    @GetMapping("/admin/inspect/get/{id}")
    public Result<Inspect> getById(@PathVariable String id){
        Inspect inspect = inspectService.getById(id);
        return new Result<>(200,inspect);
    }

    @PostMapping("/admin/inspect/update")
    public Result<String> update(Inspect inspect, MultipartFile file){
        boolean result = inspectService.updateById(inspect);
        return new Result<>(result?200:400,result?"修改成功！":"修改失败！");
    }

    @GetMapping("/admin/inspect/delete/{id}")
    public Result<String> delete(@PathVariable String id){
        boolean result = inspectService.removeById(id);
        return new Result<>(result?200:400,result?"删除成功！":"删除失败！");
    }

    /**
     * 删除多条
     * @param ids 多个id主键
     * @return java.lang.String 跳转页面
     */
    @GetMapping("/admin/inspect/deleteAll/{ids}")
    public Result<String> deleteAll(@PathVariable List<String> ids){
        boolean result = inspectService.removeByIds(ids);
        return new Result<>(result?200:400,result?"删除成功！":"删除失败！");
    }

    @GetMapping("/admin/inspect/export")
    public void export(HttpServletResponse response) {
        try {
            List<Map<String,String>> list = inspectService.listAll();
            for(Map<String, String> map : list){
                switch (map.get("state")){
                    case "2":
                        map.put("state","故障");
                        break;
                    case "3":
                        map.put("state","报废");
                        break;
                    case "4":
                        map.put("state","风险");
                        break;
                    default:
                        map.put("state","合格");
                        break;
                }

            }
            String fileName = "设备检查信息.xlsx";
            File file = new File(driveLetter+":\\" + uploadPath + "\\excel" + "\\" + fileName);
            if(file.exists()){
                file.delete();
            }
            ExcelWriter writer = ExcelUtil.getBigWriter(driveLetter+":\\" + uploadPath + "\\excel" + "\\" + fileName);
            String[] columnNames = {"real_name","device_name","inspect_time","description","state","y","x"};
            String[] titleNames = {"检查人","设备", "检查时间","备注","状态","经度","纬度"};
            for (int i = 0; i < columnNames.length; i++) {
                writer.addHeaderAlias(columnNames[i], titleNames[i]);
            }
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
