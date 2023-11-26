package com.epam.training.ticketservice.dto;

import com.epam.training.ticketservice.model.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class UserDto {

    private final String username;

    private final Roles role;

}
