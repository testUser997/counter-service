package com.user.counterservice.mapper;

import com.user.counterservice.api.model.UserResource;
import com.user.counterservice.client.model.UserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

/**
 * @author Mateusz Zyla
 * @since 04.05.2021
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "calculations", expression = "java(calculate(userDetails.getFollowers(), userDetails.getPublicRepos()))")
    UserResource map(UserDetails userDetails);

    default BigDecimal calculate(Long followers, Long publicRepos) {
        if (followers != null && followers != 0) {
            return mapToBigDecimal(6L).divide(mapToBigDecimal(followers), 10, BigDecimal.ROUND_HALF_EVEN)
                    .multiply((mapToBigDecimal(2L).add(mapToBigDecimal(publicRepos)))).setScale(10, BigDecimal.ROUND_HALF_EVEN);
        } else {
            return mapToBigDecimal(0L);
        }
    }

    default BigDecimal mapToBigDecimal(Long value) {
        return BigDecimal.valueOf(value).setScale(10, BigDecimal.ROUND_HALF_EVEN);
    }
}
