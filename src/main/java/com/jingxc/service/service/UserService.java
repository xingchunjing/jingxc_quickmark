package com.jingxc.service.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jingxc.service.config.ReturnResult;

public interface UserService {

    /**
     * 确认身份接口：确定身份以及判断是否二维码过期等
     * 
     * @param uuid
     * @param token
     * @return
     * @throws Exception
     */
    public ReturnResult bindUserIdAndToken(String uuid, String token);

    /**
     * 创建二维码，放入token
     * 
     * @param request
     * @param response
     */
    public void createCodeImg(HttpServletRequest request, HttpServletResponse response);

}
