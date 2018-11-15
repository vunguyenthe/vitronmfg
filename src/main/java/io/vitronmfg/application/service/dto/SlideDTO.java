package io.vitronmfg.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Slide entity.
 */
public class SlideDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] slideId;
    private String slideIdContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getSlideId() {
        return slideId;
    }

    public void setSlideId(byte[] slideId) {
        this.slideId = slideId;
    }

    public String getSlideIdContentType() {
        return slideIdContentType;
    }

    public void setSlideIdContentType(String slideIdContentType) {
        this.slideIdContentType = slideIdContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SlideDTO slideDTO = (SlideDTO) o;
        if (slideDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), slideDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SlideDTO{" +
            "id=" + getId() +
            ", slideId='" + getSlideId() + "'" +
            "}";
    }
}
