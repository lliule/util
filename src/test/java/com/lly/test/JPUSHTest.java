package com.lly.test;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.wms.jpush.api.JPushApi;
import com.wms.jpush.service.model.PushModel;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import static java.lang.System.out;

public class JPUSHTest {

    @Test
    public void testPush() throws APIConnectionException, APIRequestException, UnsupportedEncodingException {
        JPushApi pushApi = new JPushApi();
//        {"andriodAliases":["3541919","3700937"...],"title":"happy new year","content":"新年将近，微差事祝大家新年快乐！","diviceType":2}
       /* PushModel pushModel = new PushModel();
        pushModel.setTitle("test");
        pushModel.setContent("app push test测试");
        pushModel.setAndriodAliases(new String[]{"3700937"});
        pushModel.setDiviceType(2);
        Object byAliases = pushApi.pushAndroidMessageByAliases(pushModel);*/
        PushModel pushModel = new PushModel();
        pushModel.setIosAliases(new String[]{"3387651"});//,"3356679"
        pushModel.setTitle("test");
        pushModel.setDiviceType(0);
        pushModel.setContent("app 测试");
        Object byAliases = pushApi.pushIosMessageByAliases(pushModel);

        out.println(byAliases);
    }
}
//Got Response code: 200 content: {"sendno":"252448522","msg_id":"1824779880"}