package org.ccs.opendfl.mysql.dflsystem.biz;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.ccs.opendfl.mysql.MysqlApplication;
import org.ccs.opendfl.mysql.dflsystem.po.DflRolePo;
import org.ccs.opendfl.mysql.dflsystem.po.DflUserPo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = MysqlApplication.class)
@ActiveProfiles(value = "test")
@Slf4j
public class IDfUserBizTest {
    @Autowired
    private IDflUserBiz dflUserBiz;

    @Test
    void getDataById() {
        DflUserPo dflUserPo = dflUserBiz.getDataById(5, "ifDel,createTime,createUser");
        System.out.println(JSONUtil.toJsonStr(dflUserPo));
        Assertions.assertEquals("test", dflUserPo.getUsername());
        Assertions.assertNull(dflUserPo.getIfDel(), "ifDel ignored");
    }
}
