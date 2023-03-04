package org.electric.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import lombok.extern.log4j.Log4j2;
import org.electric.model.Devices;
import org.electric.model.DevicesChange;
import org.electric.model.Manager;
import org.electric.service.DevicesChangeService;
import org.electric.service.DevicesService;
import org.electric.service.ManagerService;
import org.electric.utils.Result;
import org.electric.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author Administrator
 * @version 1.0
 * @description //TODO
 **/

@CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,RequestMethod.POST}, origins="*")
@RestController @Log4j2
public class DevicesController extends UploadUtil {

    @Value("${spring.servlet.upload.drive.letter}")
    private String driveLetter;
    @Value("${spring.servlet.upload.base-path}")
    private String uploadPath;

    @Resource
    private DevicesService devicesService;
    @Resource
    private ManagerService managerService;

    @Resource
    private DevicesChangeService devicesChangeService;

    @GetMapping("/admin/devices/list/{currentPage}/{pageSize}")
    public Result<IPage<Map<String, String>>> listByPage(@PathVariable Integer currentPage, @PathVariable Integer pageSize, HttpServletRequest request){
        IPage<Map<String, String>> result = devicesService.listByPage(currentPage,pageSize,getParameters(request));
        return new Result<>(200,result);
    }

    @GetMapping("/admin/devices/listByParam")
    public Result<List<Map<String, String>>> listByAll(HttpServletRequest request){
        List<Map<String, String>> result = devicesService.listByParam(getParameters(request));
        return new Result<>(result.size()>0?200:300,result);
    }


    /**
     * 增加设备
     */
    @PostMapping("/admin/devices/save")
    public Result<String> save(Devices devices,MultipartFile file){
        String id = IdUtil.simpleUUID();
        devices.setId(id);
        devices.setPicture(fileUpload(file,"picture",devices.getPicture(),id));
        String fileName = id + ".jpg";
        QrCodeUtil.generate(id, 300, 300, FileUtil.file(driveLetter+":\\" + uploadPath + "\\qrcode\\" + fileName));
        devices.setQrcode(fileName);
        boolean result = devicesService.save(devices);
        return new Result<>(result?200:400,result?"添加成功！":"添加失败！");
    }

    @GetMapping("/admin/devices/get/{id}")
    public Result<Devices> getById(@PathVariable String id){
        Devices devices = devicesService.getById(id);
        return new Result<>(200,devices);
    }

    @PostMapping("/admin/devices/updateState") @Transactional
    public Result<String> updateState(Devices devices){
        Devices oldDevices = devicesService.getById(devices.getId());
        DevicesChange devicesChange = new DevicesChange();
        devicesChange.setChangeDevice(devices.getId());
        devicesChange.setOldManager(oldDevices.getPersonInCharge());
        devicesChange.setOldPrice(oldDevices.getPrice());
        devicesChange.setOldAddress(oldDevices.getHisLocation());
        devicesChange.setOldState(oldDevices.getState());
        devicesChange.setNewManager(devices.getPersonInCharge());
        devicesChange.setNewPrice(devices.getPrice());
        devicesChange.setNewAddress(devices.getHisLocation());
        devicesChange.setNewState(devices.getState());
        boolean result = devicesService.updateById(devices);
        boolean dcResult = devicesChangeService.save(devicesChange);
        return new Result<>(result==dcResult?200:400,result==dcResult?"修改成功！":"修改失败！");
    }

    @PostMapping("/admin/devices/update") @Transactional
    public Result<String> update(Devices devices, MultipartFile file){
        Devices oldDevices = devicesService.getById(devices.getId());
        if(null!=file&&(!file.isEmpty())){
            String devicePicture = fileUpload(file,"devicePicture",devices.getPicture(),devices.getId());
            devices.setPicture(devicePicture);
        }
        DevicesChange devicesChange = new DevicesChange();
        devicesChange.setChangeDevice(devices.getId());
        devicesChange.setOldManager(oldDevices.getPersonInCharge());
        devicesChange.setOldPrice(oldDevices.getPrice());
        devicesChange.setOldAddress(oldDevices.getHisLocation());
        devicesChange.setOldState(oldDevices.getState());
        devicesChange.setNewManager(devices.getPersonInCharge());
        devicesChange.setNewPrice(devices.getPrice());
        devicesChange.setNewAddress(devices.getHisLocation());
        devicesChange.setNewState(devices.getState());
        System.out.println(devices);
        boolean result = devicesService.updateById(devices);
        boolean dcResult = devicesChangeService.save(devicesChange);
        return new Result<>(result==dcResult?200:400,result==dcResult?"修改成功！":"修改失败！");
    }

    @GetMapping("/admin/devices/delete/{id}")
    public Result<String> delete(@PathVariable String id){
        boolean result = devicesService.removeById(id);
        return new Result<>(result?200:400,result?"删除成功！":"删除失败！");
    }

    /**
     * 删除多条
     * @param ids 多个id主键
     * @return java.lang.String 跳转页面
     */
    @GetMapping("/admin/devices/deleteAll/{ids}")
    public Result<String> deleteAll(@PathVariable List<String> ids){
        boolean result = devicesService.removeByIds(ids);
        return new Result<>(result?200:400,result?"删除成功！":"删除失败！");
    }

    @GetMapping("/admin/devices/export")
    public void export(HttpServletResponse response) {
        try {
            List<Map<String,String>> list = devicesService.listAll();
            String fileName = "故障设备信息.xlsx";
            File file = new File(driveLetter+":\\" + uploadPath + "\\excel" + "\\" + fileName);
            if(file.exists()){
                file.delete();
            }
            ExcelWriter writer = ExcelUtil.getBigWriter(driveLetter+":\\" + uploadPath + "\\excel" + "\\" + fileName);
            String[] columnNames = {"identifier", "name", "brand", "make_date", "use_date", "his_location", "username", "price"};
            String[] titleNames = {"编号","名称","品牌","生产日期","预计使用时间","所在地区","负责人","价值"};
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

    @GetMapping("/admin/devices/qualifiedExport")
    public void qualifiedExport(HttpServletResponse response) {
        try {
            List<Map<String,String>> list = devicesService.qualifiedExport();
            String fileName = "合格设备信息.xlsx";
            File file = new File(driveLetter+":\\" + uploadPath + "\\excel" + "\\" + fileName);
            if(file.exists()){
                file.delete();
            }
            ExcelWriter writer = ExcelUtil.getBigWriter(driveLetter+":\\" + uploadPath + "\\excel" + "\\" + fileName);
            String[] columnNames = {"identifier", "name", "brand", "make_date", "use_date", "his_location", "username", "price"};
            String[] titleNames = {"编号","名称","品牌","生产日期","预计使用时间","所在地区","负责人","价值"};
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

    @PostMapping("/admin/devices/import")
    public ResponseEntity<String> importExcel(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            String[] excelHead = {"负责人","编号","名称","品牌","生产日期","预计使用时间","所在地区","价值","状态","种类"};
            String[] excelHeadAlias = {"personInCharge","identifier", "name", "brand", "makeDate", "useDate", "hisLocation", "price", "state","types"};
            List<Devices> list = importExcel(inputStream, excelHead, excelHeadAlias, Devices.class);
            if (ObjectUtil.isEmpty(list)) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("导入模版不正确或数据为空");
            }
            for(Devices d : list){
                String id = IdUtil.simpleUUID();
                d.setId(id);
                String fileName = id + ".jpg";
                QrCodeUtil.generate(id, 300, 300, FileUtil.file(driveLetter+":\\" + uploadPath + "\\qrcode\\" + fileName));
                d.setQrcode(fileName);
                switch (d.getState()){
                    case "故障":
                        d.setState("2");
                        break;
                    case "报废":
                        d.setState("3");
                        break;
                    case "风险":
                        d.setState("4");
                        break;
                    default:
                        d.setState("1");
                        break;
                }
                devicesService.save(d);
            }
            return ResponseEntity.ok().body("导入成功");
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("导入失败");
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

    private String getState(String code){
        String state = "正常";
        switch (code){
            case "2":
                state = "故障";
                break;
            case "3":
                state = "报废";
                break;
            case "4":
                state = "风险";
                break;
        }
        return state;
    }

    @GetMapping("/admin/devices/rate")
    public Result<List<Map<String,String>>> rate(){
        List<Map<String,String>> maps = new ArrayList<>();
        for(Map<String,String> map : managerService.listAll()){
            String userid = map.get("id");
            Integer total = this.getCount(userid,"");
            Integer num1 = this.getCount(userid,"1");
            Integer num2 = this.getCount(userid,"2");
            Integer num3 = this.getCount(userid,"3");
            Integer num4 = this.getCount(userid,"4");
            map.put("total",total+"");
            map.put("rate1",total==0?"-":100*num1/total+"%");
            map.put("rate2",total==0?"-":100*num2/total+"%");
            map.put("rate3",total==0?"-":100*num3/total+"%");
            map.put("rate4",total==0?"-":100*num4/total+"%");
            maps.add(map);
        }
        return new Result<>(200,maps);
    }

    private Integer getCount(String userid, String state){
        Map<String,String> map = new HashMap<String, String>();
        if(userid != null && !"".equals(userid)){
            map.put("person_in_charge",userid);
        }
        if(state != null && !"".equals(state)){
            map.put("state",state);
        }
        return devicesService.getCount(map);
    }

    /**
     * 读取excel表格内容返回List<Bean>
     *
     * @param inputStream excel文件流
     * @param head        表头数组
     * @param headerAlias 表头别名数组
     * @return <T>List<T>
     */
    public static <T> List<T> importExcel(InputStream inputStream, String[] head, String[] headerAlias, Class<T> bean) {
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 多个sheet可以指定名称读取
        //ExcelReader reader = ExcelUtil.getReader(inputStream, "全部人员信息");
        List<Object> header = reader.readRow(0);
        // 替换表头关键字
        if (ArrayUtils.isEmpty(head) || ArrayUtils.isEmpty(headerAlias) || head.length != headerAlias.length) {
            return null;
        } else {
            for (int i = 0; i < head.length; i++) {
                if (head[i].equals(header.get(i))) {
                    reader.addHeaderAlias(head[i], headerAlias[i]);
                } else {
                    return null;
                }
            }
        }
        //读取指点行开始的表数据（从0开始）
        return reader.read(0, 0, bean);
    }
}
