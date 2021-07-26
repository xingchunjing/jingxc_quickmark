package com.jingxc.service.service.impl;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jingxc.service.config.Constant;
import com.jingxc.service.config.ReturnResult;
import com.jingxc.service.config.ReturnResultSuccess;
import com.jingxc.service.exception.ReturnCode200Exception;
import com.jingxc.service.service.UserService;
import com.jingxc.service.socket.WebSocketServer;
import com.jingxc.service.util.MD5Util;
import com.jingxc.service.util.RedisCacheUtil;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Override
    public ReturnResult bindUserIdAndToken(String uuid, String token) {

        boolean exists = redisCacheUtil.exists(uuid);
        if (!exists) {// 当前时间大于校验时间

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 500);
            jsonObject.put("msg", "二维码失效！");
            try {
                WebSocketServer.sendInfo(jsonObject.toJSONString(), uuid);
                throw new ReturnCode200Exception("二维码失效！");

            } catch (IOException e) {
                throw new ReturnCode200Exception("连接失败");
            }
        }

        // 通过token获取用户信息
        String userId = MD5Util.getMD5(token);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 200);
        jsonObject.put("msg", "ok");
        jsonObject.put("userId", userId);

        try {
            WebSocketServer.sendInfo(jsonObject.toJSONString(), uuid);
        } catch (IOException e) {
            throw new ReturnCode200Exception("连接失败");
        }

        return ReturnResultSuccess.builder().code(Constant.RETURN_CODE_200).msg("success").data(jsonObject).count(1)
                .build();
    }

    @Override
    public void createCodeImg(HttpServletRequest request, HttpServletResponse response) {

        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");

        response.addHeader("Access-Control-Expose-Headers", "uuid");

        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        try {
            // 这里没啥操作 就是生成一个UUID插入 数据库的表里
            String uuid = UUID.randomUUID().toString();
            redisCacheUtil.set(uuid, "REDIS_VALUE_UUID", 60L);
            response.setHeader("uuid", uuid);
            // 这里是开源工具类 hutool里的QrCodeUtil
            // 网址：http://hutool.mydoc.io/

            QrConfig config = new QrConfig(300, 300);
            // 设置边距，既二维码和背景之间的边距
            config.setMargin(1);
            // 设置前景色，既二维码颜色（青色）
            config.setForeColor(Color.CYAN);
            // 设置背景色（灰色）
            config.setBackColor(Color.GRAY);
            // 设置logo图片
            ClassPathResource classPathResource = new ClassPathResource("/static/img/WechatIMG215.png");
            InputStream inputStreamImg = classPathResource.getInputStream();
            Image img = ImageIO.read(inputStreamImg);
            config.setImg(img);

            QrCodeUtil.generate(uuid, config, "jpg", response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
