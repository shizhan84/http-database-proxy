package cn.okcoming.example;

import cn.okcoming.baseutils.DateUtils;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class MessageBean {
    private Long id;
    private String username;
    private Date lostContact;
    private Date gmtCreated;

}
