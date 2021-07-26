package com.jingxc.service.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alibaba.fastjson.JSON;
import com.jingxc.service.config.Constant;
import com.jingxc.service.config.ReturnResultError;
import com.jingxc.service.exception.ReturnCode1Exception;
import com.jingxc.service.exception.ReturnCode200Exception;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BaseController {

    @ExceptionHandler(ReturnCode200Exception.class)
    public ReturnResultError exception200Handler(Exception e) {
        log.error("进入BaseController错误日志收集exception200Handler——————————————" + e.getMessage(), e);
        return ReturnResultError.builder().code(Constant.RETURN_CODE_999)
                .msg(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "error").data("").build();
    }

    @ExceptionHandler(ReturnCode1Exception.class)
    public String exception1Handler(Exception e) {
        log.error("进入BaseController错误日志收集exception1Handler——————————————" + e.getMessage(), e);
        Map<String, Object> map = new HashMap<>();
        map.put("msg", StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "error");
        map.put("result", Constant.RETURN_CODE_01);
        return JSON.toJSONString(map);
    }

    @ExceptionHandler(Exception.class)
    public String exception(Exception e) {
        log.error("进入BaseController错误日志收集exception_handler——————————————" + e.getMessage(), e);
        Map<String, Object> map = new HashMap<>();
        map.put("msg", StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "error");
        map.put("code", Constant.RETURN_CODE_999);
        map.put("result", Constant.RETURN_CODE_01);
        return JSON.toJSONString(map);
    }

}
