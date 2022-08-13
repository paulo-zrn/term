package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.LambdaConversionException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    //第二次提交

    /**
     * 员工登录
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) { //RequestBody一json形式接收
        /**
         * 1.将页面提交的密码password进行md5进行加密处理
         * 2.根据页面提交的用户名username查询数据库
         * 3.如果没有查询到则返回登录失败结果
         * 4.密码对比如果不一致返回登录失败
         * 5.查询员工状态是否是否为已禁用状态，有则返回已禁用结果
         * 6.登录成功将员工的id存入Session并返回成功结果
         * */
        //1.将页面提交的密码password进行md5进行加密处理
        String passord = employee.getPassword();
        passord = DigestUtils.md5DigestAsHex(passord.getBytes());
        //2.根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        // * 3.如果没有查询到则返回登录失败结果
        if (emp == null) {
            return R.error("登录失败");

        }
        //* 4.密码对比如果不一致返回登录失败
        if(!emp.getPassword().equals(passord)){
            return R.error("登录失败");
        }
        // * 5.查询员工状态是否是否为已禁用状态，有则返回已禁用结果
        if(emp.getStatus()==0){
            return R.error("账号已禁用");
        }
        //6.登录成功将员工的id存入Session并返回成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

}
