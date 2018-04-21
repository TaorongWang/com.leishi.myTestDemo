package cn.joinhealth;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.stereotype.Component;

/**
 * Created by felix on 2017/1/29.
 */
//@Component
public class JerseyApplicationConfig extends ResourceConfig {

    // 初始化
    public JerseyApplicationConfig() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        packages("cn.joinhealth.filter");
    }
}
