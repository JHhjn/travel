package com.hniu.travel;

import com.hniu.travel.util.MailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestMail {
    @Autowired
    private MailUtils mailUtils;
    @Test
    public void t1(){
        mailUtils.sendMail("1592513693@qq.com","hello 年轻人","年轻人");
    }
}
