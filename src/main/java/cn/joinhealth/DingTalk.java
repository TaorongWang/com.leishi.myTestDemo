package cn.joinhealth;

import cn.joinhealth.util.Common;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ResourceBundle;

/**
 * Created by shilei on 2018/4/19.
 * 描述：钉钉自动报警
 */

@Component
public class DingTalk {

    private static Logger log = LoggerFactory.getLogger(DingTalk.class);
    /* 是否开启 */
    public static Boolean open = false;

    /* 机器人地址 */
    public static String robot = null;

    /* 通知的手机号 */
    public static String phone = null;

    /* 是否通知所有人 */
    public static Boolean isAtAll = false;

    protected static JSONObject dingJson = new JSONObject();
    protected static JSONObject textJson = new JSONObject();

    /**
     * 初始化配置资源
     */
    @PostConstruct
    public void init() {
        log.info("dingTalk loading resource start-----------------------------------------------");
        ResourceBundle RES = ResourceBundle.getBundle("config");
        JSONObject json = new JSONObject();
        if ("true".equals(RES.getString("dingTalk.open")))
            open = true;
        else{
            log.info("dingTalk is not open----------------------------------------------------------");
            return;
        }
        if ("true".equals(RES.getString("dingTalk.isAtAll")))
            isAtAll = true;
        robot = RES.getString("dingTalk.robot");
        if (robot == null || "".equals(robot))
            open = false;
        phone = RES.getString("dingTalk.phone");
        if (phone == null || "".equals(phone))
            open = false;
        else {
            String[] phones = phone.split(",");
            json.put("atMobiles", phones);
        }

        json.put("isAtAll", isAtAll);
        dingJson.put("msgtype", "text");
        dingJson.put("at", json);
        log.info("dingTalk loading resource end-----------------------------------------------");
    }


    /**
     * 发送消息
     *
     * @param content
     */
    public static void sendMessageToRobot(String content) {
        if (!open.equals(true))
            return;
        textJson.put("content", content);
        dingJson.put("text", textJson);
        try {
            String result = Common.httpPost(robot, dingJson.toJSONString());
            log.info("sendMessageToRobot  response------------" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
