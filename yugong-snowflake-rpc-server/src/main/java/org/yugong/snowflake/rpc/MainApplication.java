package org.yugong.snowflake.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * @author 小天
 * @date 2019/5/5 22:51
 */
@SpringBootApplication
public class MainApplication implements CommandLineRunner, ApplicationListener<ContextClosedEvent> {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("spring 容器关闭");
    }

    @Override
    public void run(String... args) throws Exception {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("YuGong Snowflake 服务启动成功");
    }
}
