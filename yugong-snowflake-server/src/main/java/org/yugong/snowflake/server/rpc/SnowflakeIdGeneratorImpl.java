package org.yugong.snowflake.server.rpc;

import org.apache.dubbo.config.annotation.Service;
import org.yugong.snowflake.api.SnowflakeIdGenerator;
import org.yugong.snowflake.server.core.AbstractSnowflakeIdGenerator;

import javax.annotation.Resource;

/**
 * @author 小天
 * @date 2019/11/14 23:50
 */
@Service(version = "1.0.0")
public class SnowflakeIdGeneratorImpl implements SnowflakeIdGenerator {

    @Resource
    private AbstractSnowflakeIdGenerator snowflake;

    @Override
    public long newId() {
        return snowflake.newId();
    }
}
