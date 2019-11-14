package org.yugong.snowflake.server.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 雪花算法
 *
 * @author 小天
 * @date 2019/11/12 20:56
 */
public abstract class AbstractSnowflake implements IdGenerator {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 起始的时间戳
     */
    private long startTimeMillis;

    /**
     * 序列号占用的位数
     */
    private long dataCenterIdBitNumbers;
    /**
     * 机器标识占用的位数
     */
    private long machineIdBitNumbers;
    /**
     * 数据中心占用的位数
     */
    private long sequenceBitNumbers;

    /**
     * 数据中心Id 最大值
     */
    private long MAX_DATA_CENTER_NUM;
    /**
     * 机器Id 最大值
     */
    private long MAX_MACHINE_NUM;
    /**
     * 序列号最大值
     */
    private long MAX_SEQUENCE;

    /**
     * 时间戳左移位数
     */
    private long TIMESTAMP_LEFT;
    /**
     * 数据中心
     */
    private long dataCenterIdBit;
    /**
     * 机器标识
     */
    private long machineIdBit;
    /**
     * 数据中心
     */
    private long dataCenterId;
    /**
     * 机器标识
     */
    private long machineId;
    /**
     * 序列号
     */
    private long sequence   = 0L;
    /**
     * 上一次的毫秒数
     */
    private long lastMillis = -1L;

    public AbstractSnowflake() {
    }

    public AbstractSnowflake(long dataCenterId, long machineId) {
        init(5, 5, 12, 0, dataCenterId, machineId);
    }

    public void init(long dataCenterIdBitNumbers, long machineIdBitNumbers, long sequenceBitNumbers, long startTimeMillis, long dataCenterId, long machineId) {
        if ((TIMESTAMP_LEFT = dataCenterIdBitNumbers + machineIdBitNumbers + sequenceBitNumbers) > 22) {
            throw new IllegalArgumentException("(dataCenterIdBit + machineIdBit + sequenceBit) can't be greater than 22");
        }
        MAX_DATA_CENTER_NUM = ~(-1L << dataCenterIdBitNumbers);
        MAX_MACHINE_NUM = ~(-1L << machineIdBitNumbers);
        MAX_SEQUENCE = ~(-1L << sequenceBitNumbers);

        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("dataCenterId can't be greater than MAX_DATA_CENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }

        this.dataCenterIdBitNumbers = dataCenterIdBitNumbers;
        this.machineIdBitNumbers = machineIdBitNumbers;
        this.sequenceBitNumbers = sequenceBitNumbers;
        this.startTimeMillis = startTimeMillis;

        this.dataCenterId = dataCenterId;
        this.dataCenterIdBit = dataCenterId << (machineIdBitNumbers + sequenceBitNumbers);
        this.machineId = machineId;
        this.machineIdBit = machineId << sequenceBitNumbers;

        logger.info("Snowflake init param: dataCenterIdBitNumbers={}", this.dataCenterIdBitNumbers);
        logger.info("Snowflake init param: machineIdBitNumbers={}", this.machineIdBitNumbers);
        logger.info("Snowflake init param: sequenceBitNumbers={}", this.sequenceBitNumbers);
        logger.info("Snowflake init param: startTimeMillis={}", this.startTimeMillis);
        logger.info("Snowflake init param: dataCenterId={}", this.dataCenterId);
        logger.info("Snowflake init param: machineId={}", this.machineId);
    }

    /**
     * 产生下一个ID
     */
    @Override
    public long newId() {
        synchronized (this) {
            long currentMillis = nowMillis();
            if (currentMillis < this.lastMillis) {
                // 时钟回拨
                throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
            }

            if (currentMillis == this.lastMillis) {
                // 相同毫秒内，序列号自增
                this.sequence = (this.sequence + 1) & this.MAX_SEQUENCE;
                // 同一毫秒的序列数已经达到最大
                if (this.sequence == 0L) {
                    // 获取下一毫秒
                    currentMillis = getNextMill();
                }
            } else {
                // 不同毫秒内，序列号置为0
                this.sequence = 0L;
            }

            // 重置上一次的毫秒数
            this.lastMillis = currentMillis;

            return (currentMillis - this.startTimeMillis) << this.TIMESTAMP_LEFT
                    | this.dataCenterIdBit
                    | this.machineIdBit
                    | this.sequence;
        }
    }

    /**
     * 获取下一毫米
     */
    private long getNextMill() {
        long tempMillis = nowMillis();
        while (tempMillis <= this.lastMillis) {
            tempMillis = nowMillis();
        }
        return tempMillis;
    }

    /**
     * 获取当前时间毫秒数
     */
    private long nowMillis() {
        return System.currentTimeMillis();
    }
}
