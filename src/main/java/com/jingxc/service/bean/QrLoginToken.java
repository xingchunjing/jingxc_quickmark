package com.jingxc.service.bean;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrLoginToken {

    private String token;
    private Date createTime;
    private Date loginTime;
    private int userId;

}
