package cn.joinhealth.springBoot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(value = {"cn.joinhealth"}) // 配置需要扫描的包
@MapperScan(value = "cn.joinhealth.mapper")  // 将dao层注入到spring容器
@ImportResource(locations = {"classpath:*.xml"})
public class SrpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrpringBootApplication.class, args);
    }
}
