package com.muhikira.springbootrabbitmqtutorial.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("javamuhikira")
    private String queue;

    @Value("javamuhikira_json")
    private String jsonQueue;

    @Value("javamuhikira_exchange")
    private String exchange;

    @Value("javamuhikira_routing_key")
    private String routingKey;

    @Value("javamuhikira_routing_json_key")
    private String routingJsonKey;

    // spring bean for rabbitmq queue
    @Bean
    public Queue queue(){
        return new Queue(queue);
    }

    // spring bean for queue (store json messages)
    @Bean
    public Queue jsonQueue(){
        return new Queue(jsonQueue);
    }

    // spring bean for rabbitmq exchange

    @Bean
    public TopicExchange exchange(){

        return new TopicExchange(exchange);
    }

    // binding between queue and exchange using routing key

    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    // binding between json queue and exchange using routing key

    @Bean
    public Binding jsonBinding(){
        return BindingBuilder
                .bind(jsonQueue())
                .to(exchange())
                .with(routingJsonKey);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter ();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    // ConnectionFactory
    // RabbitTemplate
    // RabbitAdmin
}