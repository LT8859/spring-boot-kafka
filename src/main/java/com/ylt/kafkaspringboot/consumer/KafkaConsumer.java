package com.ylt.kafkaspringboot.consumer;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 监听服务器上的kafka是否有相关的消息发过来
 */


@Component
public class KafkaConsumer {


    // 消费监听
/*
    @KafkaListener(topics = {"topic1"})
    public void onMessage1(ConsumerRecord<?, ?> record){
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("简单消费："+record.topic()+"-"+record.partition()+"-"+record.value());
    }*/



    /**
     * @Title 指定topic、partition、offset消费
     * @Description 同时监听topic1和topic2，监听topic1的0号分区、topic2的 "0号和1号" 分区，指向1号分区的offset初始值为8
     * @Author long.yuan
     * @Date 2020/3/22 13:38
     * @Param [record]
     * @return void
     **/
//    @KafkaListener(id = "consumer1",groupId = "felix-group",topicPartitions = {
//            @TopicPartition(topic = "topic1", partitions = { "0" }),
//            @TopicPartition(topic = "topic2", partitions = {"0"})
//            //没有1号分区，先去掉
////            @TopicPartition(topic = "topic2", partitions = {"0"}, partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "8"))
//    })
//    public void onMessage2(ConsumerRecord<?, ?> record) {
//        System.out.println("topic:"+record.topic()+"|partition:"+record.partition()+"|offset:"+record.offset()+"|value:"+record.value());
//    }

    /**
     * 属性解释：
     *
     * ① id：消费者ID；
     *
     * ② groupId：消费组ID；
     *
     * ③ topics：监听的topic，可监听多个；
     *
     * ④ topicPartitions：可配置更加详细的监听信息，可指定topic、parition、offset监听。
     *
     * 上面onMessage2监听的含义：监听topic1的0号分区，同时监听topic2的0号分区和topic2的1号分区里面offset从8开始的消息。
     * 注意：topics和topicPartitions不能同时使用；
     */

    /**
     * 批量消费，  批量消费和普通一条的消费 只能二选一 使用
     * @param records
     */
//    @KafkaListener(id = "consumer2",groupId = "felix-group", topics = "topic1")
//    public void onMessage3(List<ConsumerRecord<?, ?>> records) {
//        System.out.println(">>>批量消费一次，records.size()="+records.size());
//        for (ConsumerRecord<?, ?> record : records) {
//            System.out.println(record.value());
//        }
//    }

//    // 将这个异常处理器的BeanName放到@KafkaListener注解的errorHandler属性里面
//    @KafkaListener(topics = {"topic1"},errorHandler = "consumerAwareErrorHandler")
//    public void onMessage4(ConsumerRecord<?, ?> record) throws Exception {
//        throw new Exception("简单消费-模拟异常");
//    }


    // 批量消费也一样，异常处理器的message.getPayload()也可以拿到各条消息的信息
//    @KafkaListener(topics = "topic1",errorHandler="consumerAwareErrorHandler")
//    public void onMessage5(List<ConsumerRecord<?, ?>> records) throws Exception {
//        System.out.println("批量消费一次...");
//        throw new Exception("批量消费-模拟异常");
//    }


    /**
     * 绑定一个filter bean 的name； 如果调用会先判断filter， 进来或者消失
     * @param record
     */
    // 消息过滤监听
//    @KafkaListener(topics = {"topic1"}/*,containerFactory = "filterContainerFactory"*/)
//    public void onMessage6(ConsumerRecord<?, ?> record) {
//        System.out.println(record.value());
//    }


    /**
     * @Title 消息转发
     * @Description 从topic1接收到的消息经过处理后转发到topic2
     * @Author long.yuan
     * @Date 2020/3/23 22:15
     * @Param [record]
     * @return void
     **/
//    @KafkaListener(topics = {"topic1"})
//    @SendTo("topic2")
//    public String onMessage7(ConsumerRecord<?, ?> record) {
//        return record.value()+"-forward message";
//    }
//
//    @KafkaListener(topics = {"topic2"})
//    public void onMessage8(ConsumerRecord<?, ?> record) {
//        System.out.println( record.value()+"");;
//    }


}
