package org.yugong.snowflake.rpc.impl;

import org.apache.dubbo.config.annotation.Service;
import org.yugong.snowflake.rpc.api.SnowflakeIdGenerator;
import org.yugong.snowflake.core.AbstractSnowflakeIdGenerator;

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
