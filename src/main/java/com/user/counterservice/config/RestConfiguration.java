package com.user.counterservice.config;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.client.RestTemplate;

/**
 * @author Mateusz Zyla
 * @since 02.05.2021
 */
@Configuration
@EnableWebSecurity
public class RestConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${http.connect-timeout:30}")
    private int connectTimeout;

    @Value("${http.read-timeout:30}")
    private int readTimeout;

    @Value("${github.authorization}")
    private String basicAuthorization;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/users/**").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getClientHttpRequestInitializers().add(clientHttpRequest -> clientHttpRequest.getHeaders().add("Authorization", basicAuthorization));

        CloseableHttpClient httpClient
                = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        requestFactory.setConnectTimeout(connectTimeout * 1000);
        requestFactory.setReadTimeout(readTimeout * 1000);

        restTemplate.setRequestFactory(requestFactory);

        return restTemplate;
    }
}
