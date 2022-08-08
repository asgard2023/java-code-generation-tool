package org.ccs.opendfl.mysql.dflsystem.biz;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.ccs.opendfl.mysql.MysqlApplication;
import org.ccs.opendfl.mysql.dflsystem.po.DflResourcePo;
import org.ccs.opendfl.mysql.dflsystem.po.DflUserPo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

@SpringBootTest(classes = MysqlApplication.class)
@ActiveProfiles(value = "test")
@Slf4j
public class IDflResourceBizTest {
    @Autowired
    private IDflResourceBiz dflResourceBiz;

    @Test
    void getDataById() {
        DflResourcePo dflUserPo = dflResourceBiz.getDataById(5, "ifDel,createTime,createUser");
        System.out.println(JSONUtil.toJsonStr(dflUserPo));
        Assertions.assertEquals("test", dflUserPo.getUri());
        Assertions.assertNull(dflUserPo.getIfDel(), "ifDel ignored");
    }

    @Test
    public void initInsertTest(){
        for(int i=0; i<100; i++){
            DflResourcePo resourcePo=new DflResourcePo();
            resourcePo.setName("test_"+i);
            resourcePo.setUri("test_"+i);
            resourcePo.setIfDel(0);
            resourcePo.setCreateUser(0);
            resourcePo.setCreateTime(new Date());
            this.dflResourceBiz.saveDflResource(resourcePo);
        }
    }
}
