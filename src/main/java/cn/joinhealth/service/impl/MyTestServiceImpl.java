package cn.joinhealth.service.impl;

import cn.joinhealth.mapper.MyTestMapper;
import cn.joinhealth.model.AccountApplicationUserModel;
import cn.joinhealth.service.MyTestService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shilei on 2018/4/16.
 */
@Service
public class MyTestServiceImpl implements MyTestService {

    @Autowired
    private MyTestMapper myTestMapper;

    @Override
    public JSONObject sayhello(String params) {
        JSONObject respJson = new JSONObject();
        JSONObject reqJson = JSONObject.parseObject(params);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", reqJson.getString("name"));
        List<AccountApplicationUserModel> userModelList = myTestMapper.selectList(map);
        System.out.println(JSONObject.toJSON(userModelList));
        respJson.put("data", userModelList);
        System.out.println(respJson);
        return respJson;
    }
}
