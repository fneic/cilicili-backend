package com.cili.video.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author Zhou JunJie
 */
@Configuration
public class RabbitConfig {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void enableConfirmCallback() {
        //confirm 监听，当消息成功发到交换机 ack = true，没有发送到交换机 ack = false
        //correlationData 可在发送时指定消息唯一 id
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if(!ack){
                Message message = correlationData.getReturned().getMessage();
                //记录日志、发送邮件通知、落库定时任务扫描重发
            }
        });

        //当消息成功发送到交换机没有路由到队列触发此监听
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            Message message = returnedMessage.getMessage();
            //记录日志、发送邮件通知、落库定时任务扫描重发
        });
    }


    //新建一个扇形交换机
    @Bean
    public DirectExchange directExchange(){
        //交换机名称、是否持久化、没有绑定队列时是否删除
        return new DirectExchange("video.exchange",true,false);
    }

    //创建消息队列
    @Bean
    public Queue queue1(){
        return QueueBuilder.durable("video.play.queue").build();
    }

	//将消息队列与交换机绑定
    @Bean
    public Binding bindingBuilder1(DirectExchange directExchange1, Queue queue1){

        return BindingBuilder.bind(queue1).to(directExchange1).with("play");
    }


    //视频发布时消息队列
    @Bean
    public Queue queue2(){
        return QueueBuilder.durable("video.publish.queue").build();
    }

    //将消息队列与交换机绑定
    @Bean
    public Binding bindingBuilder2(DirectExchange directExchange, Queue queue2){

        return BindingBuilder.bind(queue2).to(directExchange).with("publish");
    }

    //接受弹幕消息队列
    @Bean
    public Queue queueDm(){
        return QueueBuilder.durable("video.dm.queue").build();
    }

    @Bean
    public Binding bindingBuilder3(DirectExchange directExchange, Queue queueDm){

        return BindingBuilder.bind(queueDm).to(directExchange).with("dm");
    }

}