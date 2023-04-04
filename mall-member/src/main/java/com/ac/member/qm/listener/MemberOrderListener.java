package com.ac.member.qm.listener;

import com.ac.member.component.MemberIntegralComponent;
import com.ac.member.qm.msg.OrderMsg;
import com.ac.member.vo.IntegralLogEditVO;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "consumerGroup", topic = "topic", messageModel = MessageModel.CLUSTERING)
public class MemberOrderListener implements RocketMQListener<MessageExt> {

    @Resource
    private MemberIntegralComponent memberIntegralComponent;

    @Override
    public void onMessage(MessageExt message) {
        OrderMsg msg = JSONObject.parseObject(message.getBody(), OrderMsg.class);
        log.info(MemberOrderListener.class.getName() + "msgId={},msg={}", message.getMsgId(), msg);
        try {

            IntegralLogEditVO integralVO = new IntegralLogEditVO();

            memberIntegralComponent.recordIntegral(integralVO);
        } catch (Exception e) {
            log.error(MemberOrderListener.class.getName() + "消费失败,msg={},message={}", msg, e.getMessage());
        }
    }

}
