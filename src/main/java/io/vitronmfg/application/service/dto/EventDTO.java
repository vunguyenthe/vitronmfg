package io.vitronmfg.application.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Event entity.
 */
public class EventDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @Lob
    private byte[] newIconPath;
    private String newIconPathContentType;

    @Lob
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getNewIconPath() {
        return newIconPath;
    }

    public void setNewIconPath(byte[] newIconPath) {
        this.newIconPath = newIconPath;
    }

    public String getNewIconPathContentType() {
        return newIconPathContentType;
    }

    public void setNewIconPathContentType(String newIconPathContentType) {
        this.newIconPathContentType = newIconPathContentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventDTO eventDTO = (EventDTO) o;
        if (eventDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", newIconPath='" + getNewIconPath() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
