package ru.tishin.springweb.cart.configs;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;
import ru.tishin.springweb.cart.properties.ProductServiceIntegrationProperties;
import ru.tishin.springweb.cart.properties.ProductServiceIntegrationTimeoutProperties;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class CartConfig {
    private final ProductServiceIntegrationProperties integrationProperties;
    private final ProductServiceIntegrationTimeoutProperties timeoutProperties;

    @Bean
    public WebClient productServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeoutProperties.getConnect())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(timeoutProperties.getRead(), TimeUnit.SECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(timeoutProperties.getWrite(), TimeUnit.SECONDS));
                });

        return WebClient
                .builder()
                .baseUrl(integrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}
