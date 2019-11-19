package org.yugong.snowflake.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yugong.snowflake.core.AbstractSnowflakeIdGenerator;
import org.yugong.snowflake.core.ZookeeperSnowflakeIdGenerator;

/**
 * @author 小天
 * @date 2019/11/15 0:08
 */
@Configuration
@EnableConfigurationProperties({ZookeeperSnowflakeIdGeneratorProperties.class})
public class SnowflakeAutoConfiguration {

    @ConditionalOnProperty(value = "yg.snowflake.zookeeper.enable", havingValue = "true")
    @Bean
    public AbstractSnowflakeIdGenerator snowflake(ZookeeperSnowflakeIdGeneratorProperties config) throws Exception {
        return new ZookeeperSnowflakeIdGenerator(config.getMachineIdBitNumbers(), config.getSequenceBitNumbers(), config.getStartTimeMillis(), config.getZkAddress(), config.getNamespace());
    }
}
