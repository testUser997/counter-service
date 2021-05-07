package com.user.counterservice.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mateusz Zyla
 * @since 04.05.2021
 */
@Configuration
public class RedisConfiguration {

    @Value("${spring.redis.host:localhost}")
    private String host;

    @Value("${spring.redis.port:6379}")
    private int port;

    @Value("${spring.redis.password:test123}")
    private String password;

    @Value("${spring.redis.database:1}")
    private int databaseIndex;

    @Value("${spring.redis.timeout:500}")
    private int timeout;

    @Value("${spring.redis.max-attempts:3}")
    private int maxAttempts;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer()
                .setTimeout(timeout)
                .setRetryAttempts(maxAttempts)
                .setDatabase(databaseIndex)
                .setAddress(parseRedissonAddress(host.concat(":").concat(String.valueOf(port))));

        if (!StringUtils.isEmpty(password)) {
            singleServerConfig.setPassword(password);
        }

        return Redisson.create(config);
    }

    private String parseRedissonAddress(String address) {
        return String.format("redis://%s", address);
    }
}
