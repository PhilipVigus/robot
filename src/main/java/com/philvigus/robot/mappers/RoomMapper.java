package com.philvigus.robot.mappers;

import com.philvigus.robot.domain.Room;
import com.philvigus.robot.dtos.RoomDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface RoomMapper {
    RoomDto roomToDto(Room room);

    Room dtoToRoom(RoomDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRoomFromDto(RoomDto dto, @MappingTarget Room room);
}
