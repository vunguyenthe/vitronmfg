package io.vitronmfg.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Career.
 */
@Entity
@Table(name = "career")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Career implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "new_icon_path")
    private byte[] newIconPath;

    @Column(name = "new_icon_path_content_type")
    private String newIconPathContentType;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "details")
    private String details;

    @Column(name = "email_contact")
    private String emailContact;

    @Column(name = "mobile")
    private String mobile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Career title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getNewIconPath() {
        return newIconPath;
    }

    public Career newIconPath(byte[] newIconPath) {
        this.newIconPath = newIconPath;
        return this;
    }

    public void setNewIconPath(byte[] newIconPath) {
        this.newIconPath = newIconPath;
    }

    public String getNewIconPathContentType() {
        return newIconPathContentType;
    }

    public Career newIconPathContentType(String newIconPathContentType) {
        this.newIconPathContentType = newIconPathContentType;
        return this;
    }

    public void setNewIconPathContentType(String newIconPathContentType) {
        this.newIconPathContentType = newIconPathContentType;
    }

    public String getDescription() {
        return description;
    }

    public Career description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public Career details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public Career emailContact(String emailContact) {
        this.emailContact = emailContact;
        return this;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public String getMobile() {
        return mobile;
    }

    public Career mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Career career = (Career) o;
        if (career.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), career.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Career{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", newIconPath='" + getNewIconPath() + "'" +
            ", newIconPathContentType='" + getNewIconPathContentType() + "'" +
            ", description='" + getDescription() + "'" +
            ", details='" + getDetails() + "'" +
            ", emailContact='" + getEmailContact() + "'" +
            ", mobile='" + getMobile() + "'" +
            "}";
    }
}
