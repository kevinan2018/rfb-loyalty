package com.rfb.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RfbUser entity.
 */
public class RfbUserDTO implements Serializable {

    private Long id;

    private String username;

    private String userId;

    private Long homeLocationId;

    private String homeLocationName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getHomeLocationId() {
        return homeLocationId;
    }

    public void setHomeLocationId(Long rfbLocationId) {
        this.homeLocationId = rfbLocationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RfbUserDTO rfbUserDTO = (RfbUserDTO) o;
        if (rfbUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rfbUserDTO.getId());
    }

    public String getHomeLocationName() {
        return homeLocationName;
    }

    public void setHomeLocationName(String homeLocationName) {
        this.homeLocationName = homeLocationName;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RfbUserDTO{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", user='" + getUserId() + "'" +
            ", homeLocation=" + getHomeLocationId() +
            ", homeLocationName=" + getHomeLocationName() +
            "}";
    }
}
