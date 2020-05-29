package com.atis.message_broker.config;

import com.atis.message_broker.model.DirectMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@ComponentScan(basePackages = {"com.atis.message_broker"})
@EnableRedisRepositories(basePackages = "com.atis.message_broker.repository")
@PropertySource("classpath:application.properties")
public class RedisConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

//    @Bean
//    public LettuceConnectionFactory lettuceConnectionFactory() {
//        return new LettuceConnectionFactory();
//    }

    @Bean(name = "defaultTemplate")
    public RedisTemplate<String, DirectMessage> redisMessageTemplate() {
        RedisTemplate<String, DirectMessage> template = new RedisTemplate<>();
        JedisConnectionFactory jedisConnectionFactory = jedisConnectionFactory();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setEnableTransactionSupport(true);
        return template;
    }

    @Bean(name = "flushRedisTemplate")
    public RedisTemplate<String, DirectMessage> flushRedisTemplate() {
        RedisTemplate<String, DirectMessage> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
