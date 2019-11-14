package org.yugong.snowflake.server.core;

/**
 * 单数据中心雪花算法 Id 生成器（数据中心Id的位数为 0）
 *
 * @author 小天
 * @date 2019/11/13 22:37
 */
public abstract class AbstractSingleDataCenterSnowflake extends AbstractSnowflake {


    public void init(long machineIdBitNumbers, long sequenceBitNumbers, long startTimeMillis, long machineId) {
        super.init(0, machineIdBitNumbers, sequenceBitNumbers, startTimeMillis, 0, machineId);
    }

    @Override
    public void init(long dataCenterIdBitNumbers, long machineIdBitNumbers, long sequenceBitNumbers, long startTimeMillis, long dataCenterId, long machineId) {
        throw new UnsupportedOperationException();
    }
}
