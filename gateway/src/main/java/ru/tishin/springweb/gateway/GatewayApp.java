package ru.tishin.springweb.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// https://cloud.spring.io/spring-cloud-gateway/reference/html/

/*
    Реактивный сервер на Netty. Он просто принимает запрос с фронта, модифицирует его и отправляет к нужному МС.
    Основная задача — взять из запроса токен, провалидировать его, подшить юзернейм в хедер и пропустить запрос дальше.
    Если запрос пришёл с неизвестного адреса, то он его не пропускает к МС.
 */

@SpringBootApplication
public class GatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }
}
