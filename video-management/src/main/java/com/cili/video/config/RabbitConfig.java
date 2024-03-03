package com.cili.video.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zhou JunJie
 */
@Configuration
public class RabbitConfig {
    //新建一个扇形交换机
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("video.exchange");
    }

    //创建消息队列
    @Bean
    public Queue queue1(){
        return new Queue("video.play.queue");
    }

	//将消息队列与交换机绑定
    @Bean
    public Binding bindingBuilder1(DirectExchange directExchange1, Queue queue1){

        return BindingBuilder.bind(queue1).to(directExchange1).with("play");
    }


    //创建消息队列
    @Bean
    public Queue queue2(){
        return new Queue("video.publish.queue");
    }

    //将消息队列与交换机绑定
    @Bean
    public Binding bindingBuilder2(DirectExchange directExchange, Queue queue2){

        return BindingBuilder.bind(queue2).to(directExchange).with("publish");
    }

}