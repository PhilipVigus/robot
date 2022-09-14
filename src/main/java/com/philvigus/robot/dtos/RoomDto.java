package com.philvigus.robot.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long id;

    @NotNull(message = "A length must be specified")
    @Range(min = 1, max = 1024, message = "The length must be between 1 and 1024 inclusive")
    private int length;

    @NotNull(message = "A width must be specified")
    @Range(min = 1, max = 1024, message = "The width must be between 1 and 1024 inclusive")
    private int width;
}
