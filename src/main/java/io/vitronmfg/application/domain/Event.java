package io.vitronmfg.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Event implements Serializable {

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
    @Column(name = "content")
    private String content;

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

    public Event title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getNewIconPath() {
        return newIconPath;
    }

    public Event newIconPath(byte[] newIconPath) {
        this.newIconPath = newIconPath;
        return this;
    }

    public void setNewIconPath(byte[] newIconPath) {
        this.newIconPath = newIconPath;
    }

    public String getNewIconPathContentType() {
        return newIconPathContentType;
    }

    public Event newIconPathContentType(String newIconPathContentType) {
        this.newIconPathContentType = newIconPathContentType;
        return this;
    }

    public void setNewIconPathContentType(String newIconPathContentType) {
        this.newIconPathContentType = newIconPathContentType;
    }

    public String getContent() {
        return content;
    }

    public Event content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
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
        Event event = (Event) o;
        if (event.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", newIconPath='" + getNewIconPath() + "'" +
            ", newIconPathContentType='" + getNewIconPathContentType() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
