package org.yugong.snowflake.rpc.api;

/**
 * 雪花算法 Id 生成器接口定义
 *
 * @author 小天
 * @date 2019/11/14 23:47
 */
public interface SnowflakeIdGenerator {

    long newId();

}
