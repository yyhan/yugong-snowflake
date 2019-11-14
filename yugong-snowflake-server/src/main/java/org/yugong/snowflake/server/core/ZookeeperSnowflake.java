package org.yugong.snowflake.server.core;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 基于 zookeeper 的单数据中心雪花算法 Id 生成器
 *
 * @author 小天
 * @date 2019/11/12 22:44
 */
public class ZookeeperSnowflake extends AbstractSingleDataCenterSnowflake implements Closeable {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 命名空间
     */
    private String           namespace;
    /**
     * curator client
     */
    private CuratorFramework client;
    /**
     * curator client 是否需要关闭。当 client 由自身创建时，需要关闭
     */
    private boolean          isClientCloseable = false;


    /**
     * 单数据中心实现
     *
     * @param machineIdBitNumbers 机器Id位数，建议：10
     * @param sequenceBitNumbers  序列号位数，建议：12
     * @param startTimeMillis     起始时间戳毫秒数
     * @param zkAddress           zk集群地址，格式：host1:port1;host2:port2...
     * @param namespace           命名空间，只允许使用大小写英文字母和数字的组合
     * @throws Exception
     */
    public ZookeeperSnowflake(long machineIdBitNumbers, long sequenceBitNumbers, long startTimeMillis, String zkAddress, String namespace) throws Exception {
        this.client = CuratorFrameworkFactory.newClient(zkAddress, new ExponentialBackoffRetry(1000, 3));
        this.client.start();
        this.isClientCloseable = true;
        this.init(machineIdBitNumbers, sequenceBitNumbers, startTimeMillis, client, namespace);
    }

    /**
     * 单数据中心实现
     *
     * @param machineIdBitNumbers 机器Id位数，建议：10
     * @param sequenceBitNumbers  序列号位数，建议：12
     * @param startTimeMillis     起始时间戳毫秒数
     * @param client              curator 客户端，由调用方自行处理客户端的生命周期
     * @param namespace           命名空间，只允许使用大小写英文字母和数字的组合
     * @throws Exception
     */
    public ZookeeperSnowflake(long machineIdBitNumbers, long sequenceBitNumbers, long startTimeMillis, CuratorFramework client, String namespace) throws Exception {
        this.init(machineIdBitNumbers, sequenceBitNumbers, startTimeMillis, client, namespace);
    }

    private void init(long machineIdBitNumbers, long sequenceBitNumbers, long startTimeMillis, CuratorFramework client, String namespace) throws Exception {
        this.client = client;
        this.namespace = namespace;

        long maxMachineId = ~(-1 << machineIdBitNumbers);
        long machineId = 0;
        while (true) {
            // 首先判断节点是否存在
            if (!exists(machineId)) {
                // 尝试加锁
                InterProcessSemaphoreMutex mutex = new InterProcessSemaphoreMutex(client, generateMachineIdLockNodeName(machineId));
                if (mutex.acquire(1000, TimeUnit.MILLISECONDS)) {
                    // 注册机器Id
                    client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(generateMachineIdNodeName(machineId));

                    // 释放锁
                    mutex.release();
                    break;
                }
            }
            machineId++;
            if (machineId >= maxMachineId) {
                machineId = 0;
            }
        }

        super.init(machineIdBitNumbers, sequenceBitNumbers, startTimeMillis, machineId);
    }

    private String generateMachineIdNodeName(long machineId) {
        return "/" + this.namespace + "/machine/" + machineId;
    }

    private String generateMachineIdLockNodeName(long machineId) {
        return "/" + this.namespace + "/lock/" + machineId;
    }

    /**
     * 判断节点是否存在
     */
    private boolean exists(long machineId) throws Exception {
        return this.client.checkExists().forPath(generateMachineIdNodeName(machineId)) != null;
    }

    @Override
    public void close() throws IOException {
        if (isClientCloseable) {
            if (client != null) {
                client.close();
            }
        }
    }
}
