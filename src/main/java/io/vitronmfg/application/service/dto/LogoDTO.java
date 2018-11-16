package io.vitronmfg.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Logo entity.
 */
public class LogoDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] logoId;
    private String logoIdContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getLogoId() {
        return logoId;
    }

    public void setLogoId(byte[] logoId) {
        this.logoId = logoId;
    }

    public String getLogoIdContentType() {
        return logoIdContentType;
    }

    public void setLogoIdContentType(String logoIdContentType) {
        this.logoIdContentType = logoIdContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LogoDTO logoDTO = (LogoDTO) o;
        if (logoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), logoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LogoDTO{" +
            "id=" + getId() +
            ", logoId='" + getLogoId() + "'" +
            "}";
    }
}
