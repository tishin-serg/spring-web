package ru.tishin.springweb.recommend.configs;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class RecommendConfig {
    @Value("${integrations.core-service.url}")
    private String coreServiceUrl;

    @Value("${integrations.cart-service.url}")
    private String cartServiceUrl;

    /*
    Что если использовать такой подход, когда делаем интеграцию с целым МС, а не с отдельными сервисами?
    Потом уже в слое интеграции дописывать эндпоинт нужного сервиса
     */
    @Bean
    public WebClient coreServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(10000, TimeUnit.SECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.SECONDS));
                });

        return WebClient
                .builder()
                .baseUrl(coreServiceUrl)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }

    @Bean
    public WebClient cartStatisticServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(10000, TimeUnit.SECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.SECONDS));
                });

        return WebClient
                .builder()
                .baseUrl(cartServiceUrl + "/statistics")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }

}
