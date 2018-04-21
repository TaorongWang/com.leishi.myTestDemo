package cn.joinhealth.controller;

import cn.joinhealth.service.MyTestService;
import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by shilei on 2018/4/16.
 */
@Controller
@RequestMapping("/hello")
public class MyTestController {

    @Autowired
    private MyTestService myTestService;

    @RequestMapping(value = "/myTest", method = RequestMethod.POST)
    public void sayhello(@RequestBody String params,  HttpServletRequest request, HttpServletResponse response){
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            JSONObject resultJson = myTestService.sayhello(params);
            pw.write(resultJson.toJSONString());
            pw.flush();
            pw.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
