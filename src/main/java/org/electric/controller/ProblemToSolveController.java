package org.electric.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.log4j.Log4j2;
import org.electric.model.ProblemToSolve;
import org.electric.service.ManagerService;
import org.electric.service.ProblemToSolveService;
import org.electric.utils.Result;
import org.electric.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,RequestMethod.POST}, origins="*")
@RestController
@Log4j2
public class ProblemToSolveController extends UploadUtil {

    @Resource
    private ProblemToSolveService problemToSolveService;
    @Resource
    private ManagerService managerService;

    @GetMapping("/admin/problem_to_solve/list/{currentPage}/{pageSize}")
    public Result<IPage<Map<String, String>>> listByPage(@PathVariable Integer currentPage, @PathVariable Integer pageSize, HttpServletRequest request){
        IPage<Map<String, String>> result = problemToSolveService.listByPage(currentPage,pageSize,getParameters(request));
        return new Result<>(200,result);
    }
    @PostMapping("/admin/problem_to_solve/save")
    public Result<String> save(ProblemToSolve problemToSolve){
        String id = IdUtil.simpleUUID();
        problemToSolve.setId(id);

        String presenterId = managerService.getIdByName(problemToSolve.getPresenter());
        String personInChargeId = managerService.getIdByName(problemToSolve.getPersonInCharge());
        System.out.println(problemToSolve.getPersonInCharge());
        System.out.println(personInChargeId);
        problemToSolve.setPresenterId(presenterId);
        problemToSolve.setPersonInChargeId(personInChargeId);
        boolean result = problemToSolveService.save(problemToSolve);
        return new Result<>(result?200:400,result?"添加成功！":"添加失败！");
    }
    @GetMapping("/admin/problem_to_solve/get_by_user_id/{id}")
    public Result<List<ProblemToSolve>> getByUserId(@PathVariable String id){
        List<ProblemToSolve> problemToSolve = problemToSolveService.getByUserId(id);
        return new Result<>(200,problemToSolve);
    }
    @GetMapping("/admin/problem_to_solve/get/{id}")
    public Result<ProblemToSolve> getById(@PathVariable String id){
        ProblemToSolve problemToSolve = problemToSolveService.getById(id);
        return new Result<>(200,problemToSolve);
    }
    @PostMapping("/admin/problem_to_solve/update")
    public Result<String> update(ProblemToSolve problemToSolve, MultipartFile file){

        boolean result = problemToSolveService.updateById(problemToSolve);
        return new Result<>(result?200:400,result?"修改成功！":"修改失败！");
    }

    @GetMapping("/admin/problem_to_solve/delete/{id}")
    public Result<String> delete(@PathVariable String id){
        boolean result = problemToSolveService.removeById(id);
        return new Result<>(result?200:400,result?"删除成功！":"删除失败！");
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
