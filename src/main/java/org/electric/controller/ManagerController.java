package org.electric.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import lombok.extern.log4j.Log4j2;
import org.electric.model.Manager;
import org.electric.service.ManagerService;
import org.electric.utils.Result;
import org.electric.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
public class ManagerController extends UploadUtil {

    @Value("${spring.servlet.upload.drive.letter}")
    private String driveLetter;
    @Value("${spring.servlet.upload.base-path}")
    private String uploadPath;

    private static List<List<Object>> lineList = new ArrayList<>();

    @Resource
    private ManagerService managerService;

    @GetMapping("/admin/manager/list/{currentPage}/{pageSize}")
    public Result<IPage<Manager>> listByPage(@PathVariable Integer currentPage, @PathVariable Integer pageSize, HttpServletRequest request){
        IPage<Manager> result = managerService.listByPage(currentPage,pageSize,getParameters(request));
        return new Result<>(200,result);
    }

    @PostMapping("/admin/manager/save")
    public Result<String> save(Manager manager){
        String id = IdUtil.simpleUUID();
        manager.setHead(fileUpload(manager.getFile(),"head",manager.getHead(),id));
        boolean result = managerService.save(manager);
        return new Result<>(result?200:400,result?"添加成功！":"添加失败！");
    }

    @GetMapping("/admin/manager/get/{id}")
    public Result<Manager> getById(@PathVariable String id){
        Manager manager = managerService.getById(id);
        return new Result<>(200,manager);
    }

    @PostMapping("/admin/manager/update")
    public Result<String> update(Manager managers, MultipartFile file){
        if(null!=file&&(!file.isEmpty())){
            String head = fileUpload(file,"head",managers.getHead(),managers.getId());
            managers.setHead(head);
        }
        boolean result = managerService.updateById(managers);
        return new Result<>(result?200:400,result?"修改成功！":"修改失败！");
    }

    @GetMapping("/admin/manager/delete/{id}")
    public Result<String> delete(@PathVariable String id){
        boolean result = managerService.removeById(id);
        return new Result<>(result?200:400,result?"删除成功！":"删除失败！");
    }

    /**
     * @description 删除多条
     * @param ids 多个id主键
     * @return java.lang.String 跳转页面
     */
    @GetMapping("/admin/manager/deleteAll/{ids}")
    public Result<String> deleteAll(@PathVariable List<String> ids){
        boolean result = managerService.removeByIds(ids);
        return new Result<>(result?200:400,result?"删除成功！":"删除失败！");
    }

    @GetMapping("/admin/manager/statistics")
    public Result<List<Map<String,String>>> statistics(){
        List<Map<String,String>> list = managerService.statistics();
        return new Result<>(200,list);
    }

    @GetMapping("/admin/login")
    public Result<Manager> login(Manager managers,HttpServletRequest request){
        return managerService.login(managers,request);
    }

    @GetMapping("/admin/logout")
    public Result<String> logout(HttpServletRequest request){
        request.getSession().invalidate();
        return new Result<>(200,"退出成功！");
    }

    @GetMapping("/admin/manager/export")
    public void export(HttpServletResponse response) {
        try {
            List<Map<String,String>> list = managerService.listAll();
            String fileName = "用户名单.xlsx";
            File file = new File(driveLetter+":\\" + uploadPath + "\\excel" + "\\" + fileName);
            if(file.exists()){
                file.delete();
            }
            ExcelWriter writer = ExcelUtil.getBigWriter(driveLetter+":\\" + uploadPath + "\\excel" + "\\" + fileName);
            String[] columnNames = {"real_name","username","password","age","sex","tel","create_time"};
            String[] titleNames = {"姓名","帐号", "密码","年龄","性别","电话","创建时间"};
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

    @PostMapping("/admin/manager/import")
    public ResponseEntity<String> importExcel(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            String[] excelHead = {"姓名", "帐号","密码", "年龄", "性别", "电话", "创建时间"};
            String[] excelHeadAlias = {"realName", "username","password", "age", "sex", "tel", "createTime"};
            List<Manager> userList = importExcel(inputStream, excelHead, excelHeadAlias, Manager.class);
            if (ObjectUtil.isEmpty(userList)) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("导入模版不正确或数据为空");
            }
            managerService.saveBatch(userList);
            return ResponseEntity.ok().body("导入成功");
        } catch (Exception ignored) {
            ignored.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("导入失败");
        }
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
