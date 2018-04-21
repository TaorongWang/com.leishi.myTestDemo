package cn.joinhealth.quartz;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * Created by shilei on 2018/4/19.
 * <p>
 * 描述： 定时任务
 */

public class QuartzJob {
    private static Logger log = LoggerFactory.getLogger(QuartzJob.class);
    private static ResourceBundle RES = ResourceBundle.getBundle("config");

    public void sendEmailTask() throws Exception {

        if ("false".equals(RES.getString("debug"))) {
            log.info("Job--------not open-------------");
            return;
        }

        log.info("Job--------start-------------");
        //0.1 确定连接位置
        Properties props = new Properties();
        //获取163邮箱smtp服务器的地址，
        props.setProperty("mail.host", "smtp.163.com");
        //是否进行权限验证。
        props.setProperty("mail.smtp.auth", "true");

        try {
            //0.2确定权限（账号和密码）
            Authenticator authenticator = new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    //填写自己的163邮箱的登录帐号和授权密码，授权密码的获取，在后面会进行讲解。
                    return new PasswordAuthentication("shilei2017ahtcm@163.com", "shilei1216");
                }
            };

            // 1.获得连接
            /**
             * props：包含配置信息的对象，Properties类型
             *         配置邮箱服务器地址、配置是否进行权限验证(帐号密码验证)等
             * authenticator：确定权限(帐号和密码)
             * 所以就要在上面构建这两个对象。
             */
            Session session = Session.getDefaultInstance(props, authenticator);

            // 2.创建消息
            Message message = new MimeMessage(session);
            // 2.1发件人        xxx@163.com 我们自己的邮箱地址，就是名称
            message.setFrom(new InternetAddress("shilei2017ahtcm@163.com"));
            /**
             * 2.2 收件人
             *         第一个参数：
             *             RecipientType.TO    代表收件人
             *             RecipientType.CC    抄送
             *             RecipientType.BCC    暗送
             *         比如A要给B发邮件，但是A觉得有必要给要让C也看看其内容，就在给B发邮件时，
             *         将邮件内容抄送给C，那么C也能看到其内容了，但是B也能知道A给C抄送过该封邮件
             *         而如果是暗送(密送)给C的话，那么B就不知道A给C发送过该封邮件。
             *     第二个参数
             *         收件人的地址，或者是一个Address[]，用来装抄送或者暗送人的名单。或者用来群发。可以是相同邮箱服务器的，也可以是不同的
             *         这里我们发送给我们的qq邮箱
             */
            List<Object> addressesList = new ArrayList<Object>();
            addressesList.add(new InternetAddress("397655005@qq.com"));
            addressesList.add(new InternetAddress("lshi01@joinhealth.cn"));
            Address[] address = new Address[addressesList.size()];
            message.setRecipients(Message.RecipientType.TO, addressesList.toArray(address));
            // 2.3主题（标题）
            message.setSubject("服务到期通知");
            // 2.4正文
            VelocityEngine velocityEngine = new VelocityEngine();
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("userName", "shilei");
            model.put("email", "shilei2017ahtcm@163.com");
            model.put("expireTime", "2018-04-21 00:00:00");
            model.put("supportTeam", "shilei2017ahtcm@163.com");
            String str = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "AccountReminder.vm", "UTF-8", model);
            message.setContent(str, "text/html;charset=UTF-8");

            // 3.发送消息
            Transport.send(message);
            log.info("Job--------end-------------");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Job--------erro-------------");
        }

    }
}
