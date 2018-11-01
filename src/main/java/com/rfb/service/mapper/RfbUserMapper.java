package com.rfb.service.mapper;

import com.rfb.domain.*;
import com.rfb.service.dto.RfbUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RfbUser and its DTO RfbUserDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, RfbLocationMapper.class})
public interface RfbUserMapper extends EntityMapper<RfbUserDTO, RfbUser> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "homeLocation.id", target = "homeLocationId")
    @Mapping(source = "homeLocation.locationName", target = "homeLocationName")
    @Mapping(source = "user", target = "userDTO")
    RfbUserDTO toDto(RfbUser rfbUser);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "rfbeventAttendances", ignore = true)
    @Mapping(source = "homeLocationId", target = "homeLocation")
    RfbUser toEntity(RfbUserDTO rfbUserDTO);

    default RfbUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        RfbUser rfbUser = new RfbUser();
        rfbUser.setId(id);
        return rfbUser;
    }
}
