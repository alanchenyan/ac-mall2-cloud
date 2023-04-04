package com.ac.member.mq.listener;

import com.ac.common.qm.MqTopicConstant;
import com.ac.common.qm.MqConsumerConstant;
import com.ac.common.qm.msg.MqOrderAction;
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
@RocketMQMessageListener(
        consumerGroup = MqConsumerConstant.CONSUMER_MEMBER_ORDER,
        topic = MqTopicConstant.TOPIC_ORDER,
        selectorExpression = "PAID||REFUND",
        messageModel = MessageModel.CLUSTERING)
public class MemberOrderListener implements RocketMQListener<MessageExt> {

    @Resource
    private MemberIntegralComponent memberIntegralComponent;

    @Override
    public void onMessage(MessageExt message) {
        MqOrderMsg mqMsg = JSONObject.parseObject(message.getBody(), MqOrderMsg.class);
        log.info(MemberOrderListener.class.getSimpleName() + ",msgId={},msg={}", message.getMsgId(), mqMsg);
        try {
            //Topic+Tag更精准接收消息
            MqOrderAction action = mqMsg.getAction();
            if (MqOrderAction.PAID == action) {
                dealPaid(mqMsg);
            } else if (MqOrderAction.REFUND == action) {
                dealRefund(mqMsg);
            }
        } catch (Exception e) {
            log.error(MemberOrderListener.class.getSimpleName() + ",消费失败,mqMsg={},e={}", mqMsg, e.getMessage());
        }
    }

    /**
     * 处理订单付款事件
     *
     * @param mqMsg
     */
    private void dealPaid(MqOrderMsg mqMsg) {
        IntegralLogEditVO integralVO = new IntegralLogEditVO();
        integralVO.setMemberId(mqMsg.getMemberId());
        integralVO.setSourceType(IntegralSourceTypeEnum.AWARD_ORDER);
        integralVO.setSourceRemark("下单获得积分");
        integralVO.setIntegral(mqMsg.getPayAmount().longValue());

        memberIntegralComponent.recordIntegral(integralVO);
    }

    private void dealRefund(MqOrderMsg mqMsg) {
        log.info("处理退单事件");
    }
}
