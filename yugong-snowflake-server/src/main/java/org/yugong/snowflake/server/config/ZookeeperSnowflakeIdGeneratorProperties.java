package org.yugong.snowflake.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 小天
 * @date 2019/11/15 0:08
 */
@ConfigurationProperties(prefix = "yg.snowflake.zookeeper")
public class ZookeeperSnowflakeIdGeneratorProperties {
    private boolean enable = false;
    private long    machineIdBitNumbers;
    private long    sequenceBitNumbers;
    private long    startTimeMillis;
    private String  zkAddress;
    private String  namespace;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public long getMachineIdBitNumbers() {
        return machineIdBitNumbers;
    }

    public void setMachineIdBitNumbers(long machineIdBitNumbers) {
        this.machineIdBitNumbers = machineIdBitNumbers;
    }

    public long getSequenceBitNumbers() {
        return sequenceBitNumbers;
    }

    public void setSequenceBitNumbers(long sequenceBitNumbers) {
        this.sequenceBitNumbers = sequenceBitNumbers;
    }

    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    public void setStartTimeMillis(long startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }

    public String getZkAddress() {
        return zkAddress;
    }

    public void setZkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
