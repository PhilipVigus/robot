package com.philvigus.robot.dtos;

import com.philvigus.robot.domain.Orientation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RobotDto {
    private Long roomId;
    private int x;
    private int y;
    private Orientation orientation;
}
