package com.jingxc.service.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jingxc.service.config.ReturnResult;
import com.jingxc.service.service.UserService;

@RestController
public class QuickMarkController extends BaseController {

    @Autowired
    private UserService userServiceImpl;

    /**
     * 获取登陆二维码，放入token
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getLoginQr", method = RequestMethod.GET)
    public void createCodeImg(HttpServletRequest request, HttpServletResponse response) {

        userServiceImpl.createCodeImg(request, response);

    }

    /**
     * 确认身份接口：确定身份以及判断是否二维码过期等
     * 
     * @param token
     * @param uuid
     * @return
     */
    @RequestMapping(value = "/bindUserIdAndToken", method = RequestMethod.GET)
    public ReturnResult bindUserIdAndToken(@RequestParam("token") String token, @RequestParam("uuid") String uuid) {
        return userServiceImpl.bindUserIdAndToken(uuid, token);
    }
}
