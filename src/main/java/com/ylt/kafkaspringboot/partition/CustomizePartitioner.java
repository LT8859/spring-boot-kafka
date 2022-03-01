package com.ylt.kafkaspringboot.partition;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class CustomizePartitioner implements Partitioner {
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        //自定义分区规则（这里假设全部发到0号分区）
        return 0;
        //应该是只有一个分区0，设置其他的无效果，可以试试创建分区
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
