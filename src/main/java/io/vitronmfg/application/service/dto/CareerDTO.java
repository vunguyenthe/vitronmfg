package io.vitronmfg.application.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Career entity.
 */
public class CareerDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @Lob
    private byte[] newIconPath;
    private String newIconPathContentType;

    @Lob
    private String description;

    @Lob
    private String details;

    private String emailContact;

    private String mobile;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CareerDTO careerDTO = (CareerDTO) o;
        if (careerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), careerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CareerDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", newIconPath='" + getNewIconPath() + "'" +
            ", description='" + getDescription() + "'" +
            ", details='" + getDetails() + "'" +
            ", emailContact='" + getEmailContact() + "'" +
            ", mobile='" + getMobile() + "'" +
            "}";
    }
}
