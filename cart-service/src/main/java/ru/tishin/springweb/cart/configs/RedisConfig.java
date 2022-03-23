package ru.tishin.springweb.cart.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean("redisCartTemplate")
    public RedisTemplate<String, Object> redisCartTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    /*
    Сохраняет в кэш статистику по добавляемым в корзину за день
    Хранит значения типа :
        key: SPRING_WEB_STAT_{productId}
        value: ProductDto (дтошка с количеством добавлений со всех корзин за день)
     */
    @Bean("redisDailyCartStatisticTemplate")
    public RedisTemplate<String, Object> redisDailyCartStatisticTemplate() {
        RedisTemplate<String, Object> redisCartStatisticTemplate = new RedisTemplate<>();
        redisCartStatisticTemplate.setKeySerializer(new StringRedisSerializer());
        redisCartStatisticTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisCartStatisticTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisCartStatisticTemplate;
    }

    /*
    Сохраняет ежедневную статистику по добавленным товарам в корзину в редис за всё время
    Хранит значения типа :
        key: "2022-02-14" (день за который собиралась статистика)
        value: ListProductDtoWithDailyStat (лист дто с количеством добавлений со всех корзин за день)
     */
    @Bean("redisAllCartStatisticTemplate")
    public RedisTemplate<String, Object> redisAllCartStatisticTemplate() {
        RedisTemplate<String, Object> redisAllCartStatisticTemplate = new RedisTemplate<>();
        redisAllCartStatisticTemplate.setKeySerializer(new StringRedisSerializer());
        redisAllCartStatisticTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisAllCartStatisticTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisAllCartStatisticTemplate;
    }
}
