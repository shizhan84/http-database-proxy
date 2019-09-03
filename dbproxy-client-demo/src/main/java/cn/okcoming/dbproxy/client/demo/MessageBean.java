package cn.okcoming.dbproxy.client.demo;


import lombok.Data;

import java.util.Date;

@Data
public class MessageBean {
    private Long id;
    private String username;
    private Date lostContact;
    private Date gmtCreated;

}
