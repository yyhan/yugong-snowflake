package org.yugong.snowflake.server.core;

/**
 * 基本雪花算法
 *
 * @author 小天
 * @date 2019/11/13 22:40
 */
public class BasicSnowflakeIdGenerator extends AbstractSnowflakeIdGenerator {
    /**
     * 雪花算法基本配置。5 位数据中心Id，5 位机器Id，12 位序号
     *
     * @param dataCenterId 数据中心Id
     * @param machineId    机器Id
     */
    public BasicSnowflakeIdGenerator(long dataCenterId, long machineId) {
        init(5, 5, 12, 0, dataCenterId, machineId);
    }

    public BasicSnowflakeIdGenerator(long dataCenterIdBitNumbers, long machineIdBitNumbers, long sequenceBitNumbers, long startTimeMillis, long dataCenterId, long machineId) {
        init(dataCenterIdBitNumbers, machineIdBitNumbers, sequenceBitNumbers, startTimeMillis, dataCenterId, machineId);
    }
}
