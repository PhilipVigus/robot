package com.philvigus.robot.mappers;

import com.philvigus.robot.domain.Room;
import com.philvigus.robot.dtos.RoomDto;
import org.mapstruct.Mapper;

@Mapper
public interface RoomMapper {
    RoomDto roomToDto(Room room);

    Room dtoToRoom(RoomDto dto);
}
