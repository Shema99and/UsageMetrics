package com.idbs.ewb.services;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Getter;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

@Getter
@Service
public class ServiceProvider {

    private final WebClient webClient;

    public ServiceProvider(
        @Value("${connection.maxConnections}") int maxConnections,
        @Value("${connection.durationInSeconds}") int durationInSeconds,
        @Value("${connection.durationInMin}") int durationInMin,
        @Value("${connection.durationInMillis}") int durationInMillis,
//        @Value("${entity_auth}") String authHeader,
        @Value("${scheme}") String scheme,
        @Value("${idbs.host}") String host,
        @Value("${idbs.port}") int port,
        @Value("${servicebase}") String servicebase
    ) {
    String baseUrl= scheme + "://" + host + ":" + port+servicebase;
        ConnectionProvider provider = ConnectionProvider.builder("EWB-Access-conn-provider")
            .maxConnections(maxConnections)
            .maxIdleTime(Duration.ofSeconds(durationInSeconds))
            .maxLifeTime(Duration.ofMinutes(durationInMin))
            .pendingAcquireTimeout(Duration.ofSeconds(durationInSeconds))
            .evictInBackground(Duration.ofSeconds(durationInSeconds))
            .build();

        HttpClient httpClient = HttpClient.create(provider)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, durationInMillis)
            .doOnConnected(conn -> conn
                .addHandlerLast(new ReadTimeoutHandler(durationInSeconds))
                .addHandlerLast(new WriteTimeoutHandler(durationInSeconds)))
            .responseTimeout(Duration.ofSeconds(durationInSeconds))
            .wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);

        this.webClient = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .baseUrl(baseUrl)
            .defaultHeaders(headers -> {
                headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            })
            .exchangeStrategies(ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .build())
            .build();
    }
}
