package io.vitronmfg.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Slide.
 */
@Entity
@Table(name = "slide")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Slide implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "slide_id")
    private byte[] slideId;

    @Column(name = "slide_id_content_type")
    private String slideIdContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getSlideId() {
        return slideId;
    }

    public Slide slideId(byte[] slideId) {
        this.slideId = slideId;
        return this;
    }

    public void setSlideId(byte[] slideId) {
        this.slideId = slideId;
    }

    public String getSlideIdContentType() {
        return slideIdContentType;
    }

    public Slide slideIdContentType(String slideIdContentType) {
        this.slideIdContentType = slideIdContentType;
        return this;
    }

    public void setSlideIdContentType(String slideIdContentType) {
        this.slideIdContentType = slideIdContentType;
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
        Slide slide = (Slide) o;
        if (slide.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), slide.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Slide{" +
            "id=" + getId() +
            ", slideId='" + getSlideId() + "'" +
            ", slideIdContentType='" + getSlideIdContentType() + "'" +
            "}";
    }
}
