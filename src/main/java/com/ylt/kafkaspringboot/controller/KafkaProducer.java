package com.ylt.kafkaspringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;


@RestController
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    // 发送消息
    @GetMapping("/kafka/normal/{message}")
    public void sendMessage1(@PathVariable("message") String normalMessage) {
        kafkaTemplate.send("topic1", normalMessage);
    }


    /**
     * 发送消息，消息发送成功就执行回调方法，参数包含发送消息参数。  流程：先发送成功，listener 后续才去topic中监听执行消费
     * @param callbackMessage
     */

    @GetMapping("/kafka/callbackOne/{message}")
    @Transactional(rollbackFor = RuntimeException.class)
    public void sendMessage2(@PathVariable("message") String callbackMessage) {
        kafkaTemplate.send("topic1", callbackMessage).addCallback(success -> {
            // 消息发送到的topic
            String topic = success.getRecordMetadata().topic();
            // 消息发送到的分区
            int partition = success.getRecordMetadata().partition();
            // 消息在分区内的offset
            long offset = success.getRecordMetadata().offset();
            System.out.println("发送消息成功:" + topic + "-" + partition + "-" + offset);
        }, failure -> {
            System.out.println("发送消息失败:" + failure.getMessage());
        });
    }


    @GetMapping("/kafka/callbackTwo/{message}")
    @Transactional(rollbackFor = RuntimeException.class)
    public void sendMessage3(@PathVariable("message") String callbackMessage) {
        kafkaTemplate.send("topic1", callbackMessage).addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("发送消息失败："+ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                System.out.println("发送消息成功：" + result.getRecordMetadata().topic() + "-"
                        + result.getRecordMetadata().partition() + "-" + result.getRecordMetadata().offset());
            }
        });
    }

    // 第一种 配置事务
    @GetMapping("/kafka/transaction")
    public void sendMessage7(){
        //使用executeInTransaction 需要在yml中配置事务参数，配置成功才能使用executeInTransaction方法，且运行报错后回滚
        kafkaTemplate.executeInTransaction(operations->{
             operations.send("topic1","test executeInTransaction");
             throw new RuntimeException("fail");
        });

        //没有在yml配置事务，这里就会出现消息发送成功，异常也出现了。如果配置事务，则改用executeInTransaction 替代send方法
        kafkaTemplate.send("topic1","test executeInTransaction");
        throw new RuntimeException("fail");
    }


    //第二种 配置事务 （注解方式）
    // [1] 需要在yml 配置 transaction-id-prefix: kafka_tx.
    // [2] 在方法上添加@Transactional(rollbackFor = RuntimeException.class)  做为开启事务并回滚
    // [3] 在注解方式中 任然可以使用.send方法，不需要使用executeInTransaction方法
    @GetMapping("/send2/{input}")
    @Transactional(rollbackFor = RuntimeException.class)
    public String sendToKafka2(@PathVariable String input){
//        this.template.send(topic,input);
        //事务的支持

        kafkaTemplate.send("topic1",input);
        if("error".equals(input))
        {
            throw new RuntimeException("input is error");
        }
        kafkaTemplate.send("topic1",input+"anthor");

        return "send success!"+input;

    }

}

