package io.vitronmfg.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Logo.
 */
@Entity
@Table(name = "logo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Logo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "logo_id")
    private byte[] logoId;

    @Column(name = "logo_id_content_type")
    private String logoIdContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getLogoId() {
        return logoId;
    }

    public Logo logoId(byte[] logoId) {
        this.logoId = logoId;
        return this;
    }

    public void setLogoId(byte[] logoId) {
        this.logoId = logoId;
    }

    public String getLogoIdContentType() {
        return logoIdContentType;
    }

    public Logo logoIdContentType(String logoIdContentType) {
        this.logoIdContentType = logoIdContentType;
        return this;
    }

    public void setLogoIdContentType(String logoIdContentType) {
        this.logoIdContentType = logoIdContentType;
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
        Logo logo = (Logo) o;
        if (logo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), logo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Logo{" +
            "id=" + getId() +
            ", logoId='" + getLogoId() + "'" +
            ", logoIdContentType='" + getLogoIdContentType() + "'" +
            "}";
    }
}
