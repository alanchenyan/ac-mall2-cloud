package com.ac.member.mq.listener;

import com.ac.common.qm.MqTopicConstant;
import com.ac.common.qm.MqConsumerConstant;
import com.ac.common.qm.msg.MqOrderMsg;
import com.ac.member.component.MemberIntegralComponent;
import com.ac.member.enums.IntegralSourceTypeEnum;
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
@RocketMQMessageListener(consumerGroup = MqConsumerConstant.CONSUMER_MEMBER_ORDER,
        topic = MqTopicConstant.TOPIC_ORDER,
        selectorExpression = "PAID",
        messageModel = MessageModel.CLUSTERING)
public class MemberOrderListener implements RocketMQListener<MessageExt> {

    @Resource
    private MemberIntegralComponent memberIntegralComponent;

    @Override
    public void onMessage(MessageExt message) {
        MqOrderMsg mqMsg = JSONObject.parseObject(message.getBody(), MqOrderMsg.class);
        log.info(MemberOrderListener.class.getName() + "msgId={},msg={}", message.getMsgId(), mqMsg);
        try {
            IntegralLogEditVO integralVO = new IntegralLogEditVO();
            integralVO.setMemberId(mqMsg.getMemberId());
            integralVO.setSourceType(IntegralSourceTypeEnum.AWARD_ORDER);
            integralVO.setSourceRemark("下单获得积分");
            integralVO.setIntegral(mqMsg.getPayAmount().longValue());

            memberIntegralComponent.recordIntegral(integralVO);
        } catch (Exception e) {
            log.error(MemberOrderListener.class.getName() + "消费失败,mqMsg={},e={}", mqMsg, e.getMessage());
        }
    }
}
