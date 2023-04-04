package com.ac.order.mq.send;

import com.ac.common.qm.MqTopicConstant;
import com.ac.common.qm.msg.MqOrderMsg;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Lazy
@Component
public class OrderSender {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void asyncSend(MqOrderMsg mqMsg) {
        String payload = JSONObject.toJSONString(mqMsg);
        String destination = MqTopicConstant.TOPIC_ORDER + ":" + mqMsg.getAction().getCode();

        rocketMQTemplate.asyncSend(destination, payload, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info(OrderSender.class.getName() + "消息发送成功, result: {}", sendResult);
            }

            @Override
            public void onException(Throwable e) {
                log.error(OrderSender.class.getName() + "消息发送失败");
                e.printStackTrace();
            }
        });
    }
}
