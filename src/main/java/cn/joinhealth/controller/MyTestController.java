package cn.joinhealth.controller;

import cn.joinhealth.service.MyTestService;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import java.util.UUID;

/**
 * Created by shilei on 2018/4/16.
 */
@Controller
@RequestMapping("/hello")
public class MyTestController {

    @Autowired
    private MyTestService myTestService;
    public static int count = 0;

    @RequestMapping(value = "/myTest", method = RequestMethod.POST)
    public void sayhello(HttpServletRequest request, HttpServletResponse response, String name) {
        try {
            String method = request.getMethod();
            System.out.println("method-------" + method);
            InputStream in = request.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            String strResult = sb.toString();
            // 字符串首尾都有个引号，这里先行处理掉
            if (strResult.indexOf("\"") == 0 && strResult.lastIndexOf("\"") == strResult.length() - 1)
                strResult = strResult.substring(1, strResult.length() - 1);
            reader.close();
            inputStreamReader.close();
            in.close();
            JSONObject respJson = JSONObject.parseObject(sb.toString());
            System.out.println("sb.toString-----" + sb.toString());
            System.out.println("context-----" + respJson);
            String requestUri = request.getRequestURI();
            System.out.println("requestUri--------" + requestUri);
            JSONObject params = new JSONObject();
            params.put("name", name);
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            JSONObject resultJson = myTestService.sayhello(params.toJSONString());
//            addCookies(response, null, null);
//            getCookie(request);
            pw.write(resultJson.toJSONString());
            pw.flush();
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return;
        for (Cookie cookie : cookies) {
            System.out.println("key=" + cookie.getName() + "； value=" + cookie.getValue() + "\r\n");
        }
    }

    private void addCookies(HttpServletResponse response, Object o, Object o1) {
        String uuid = String.valueOf(UUID.randomUUID());
        System.out.println("uuid-----" + uuid);
        Cookie cookie = new Cookie("cookies_test" + (count++), uuid);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
