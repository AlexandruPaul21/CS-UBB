package com.example.entityserver.model.mapper;

import com.example.entityserver.model.Flight;
import com.example.entityserver.model.dto.FlightDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    FlightDto entityToDto(Flight flight);

    Flight dtoToEntity(FlightDto flightDto);
}
