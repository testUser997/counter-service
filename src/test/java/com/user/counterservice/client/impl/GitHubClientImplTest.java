package com.user.counterservice.client.impl;


import com.user.counterservice.client.GitHubClient;
import com.user.counterservice.client.model.UserDetails;
import com.user.counterservice.data.repository.UserRepository;
import com.user.counterservice.message.producer.UserCounterProducer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


/**
 * @author Mateusz Zyla
 * @since 07.05.2021
 */
@RunWith(SpringRunner.class)
@RestClientTest(GitHubClient.class)
@TestPropertySource(properties = "github.url=http://test")
@MockBean(UserRepository.class)
public class GitHubClientImplTest {

    @Autowired
    private GitHubClient gitHubClient;

    @Autowired
    private MockRestServiceServer server;

    @Value("${github.url}")
    private String endpointUri;

    @Before
    public void setUp() {
        server.reset();
    }

    @After
    public void tearDown() {
        server.verify();
    }

    @Test
    public void getUserDetailsTest() {
        String responseBody = "{\n" +
                "    \"id\": 123,\n" +
                "    \"login\": \"tmp\",\n" +
                "    \"name\": \"The Tmp\",\n" +
                "    \"type\": \"User\",\n" +
                "    \"avatar_url\": \"https://avatars.githubusercontent.com/u/45555?v=4\",\n" +
                "    \"created_at\": \"2011-01-25T18:44:36.000Z\"\n" +
                "}";

        server.expect(requestTo(endpointUri + "/tmp"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        UserDetails response = gitHubClient.getUserDetails("tmp");
        assertThat(response.getId()).isEqualTo(123L);
        assertThat(response.getLogin()).isEqualTo("tmp");
        assertThat(response.getName()).isEqualTo("The Tmp");
        assertThat(response.getType()).isEqualTo("User");
        assertThat(response.getAvatarUrl()).isEqualTo("https://avatars.githubusercontent.com/u/45555?v=4");
        assertThat(response.getCreatedAt()).isEqualTo("2011-01-25T18:44:36.000Z");
    }


    @TestConfiguration
    static class Config {

        @Bean
        RestTemplate solarisRestTemplate(RestTemplateBuilder builder) {
            return builder.build();
        }


        @Bean
        RabbitTemplate rabbitTemplate() {
            return mock(RabbitTemplate.class);
        }

        @Bean
        UserCounterProducer userCounterProducer(RabbitTemplate rabbitTemplate) {
            return new UserCounterProducer(rabbitTemplate);
        }

//        @Bean
//        UserRepository userRepository() {
//            return mock(UserRepository.class);
//        }
    }
}