package com.philvigus.robot.mappers;

import com.philvigus.robot.domain.Robot;
import com.philvigus.robot.dtos.RobotDto;
import org.mapstruct.Mapper;

@Mapper
public interface RobotMapper {
    RobotDto robotToDto(Robot robot);

    Robot dtoToRobot(RobotDto dto);
}
