package com.user.counterservice.mapper;

import com.user.counterservice.api.model.UserResource;
import com.user.counterservice.client.model.UserDetails;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mateusz Zyla
 * @since 07.05.2021
 */
public class UserMapperTest {

    UserMapper userMapper = new UserMapperImpl();

    @Test
    public void mapUserFromGitHubResponse() {
        //for
        UserDetails response = new UserDetails();
        response.setType("User");
        response.setPublicRepos(10L);
        response.setLogin("tmp");
        response.setId(123L);
        response.setFollowers(1000L);
        response.setAvatarUrl("wwww");
        response.setCreatedAt(new Date());

        //when
        UserResource result = userMapper.map(response);

        //then
        assertThat(result.getAvatarUrl()).isEqualTo(response.getAvatarUrl());
        assertThat(result.getType()).isEqualTo(response.getType());
        assertThat(result.getId()).isEqualTo(response.getId());
        assertThat(result.getLogin()).isEqualTo(response.getLogin());
        assertThat(result.getName()).isEqualTo(response.getName());
        assertThat(result.getCalculations()).isEqualByComparingTo(BigDecimal.valueOf(0.0720000000D).setScale(10, BigDecimal.ROUND_HALF_EVEN));
    }
}