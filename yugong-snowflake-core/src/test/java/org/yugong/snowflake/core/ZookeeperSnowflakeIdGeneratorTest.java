package org.yugong.snowflake.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * @author 小天
 * @date 2019/11/19 21:05
 */
public class ZookeeperSnowflakeIdGeneratorTest {

    private ZookeeperSnowflakeIdGenerator zookeeperSnowflake;

    @Before
    public void setUp() throws Exception {
        zookeeperSnowflake = new ZookeeperSnowflakeIdGenerator(10, 12, 0, "127.0.0.1:2181", "snowflake");
    }

    @After
    public void tearDown() throws Exception {
        zookeeperSnowflake.close();
    }

    @Test
    public void test() {
        long id = zookeeperSnowflake.newId();

        System.out.println(~(-1 << 4));

        System.out.printf("id: %d \n", id);
        System.out.printf("时间戳： %s \n", new Date(id >> 22));
        System.out.printf("时间戳： %d \n", id >> 22);
        System.out.printf("机器Id： %d \n", id >> 12 & (~(-1 << 10)));
        System.out.printf("序号： %d \n", id & (~(-1 << 12)));
    }
}