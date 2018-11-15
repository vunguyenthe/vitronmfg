package io.vitronmfg.application.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Category entity.
 */
public class CategoryDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @Lob
    private String description;

    @Lob
    private byte[] categoryImagePath;
    private String categoryImagePathContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getCategoryImagePath() {
        return categoryImagePath;
    }

    public void setCategoryImagePath(byte[] categoryImagePath) {
        this.categoryImagePath = categoryImagePath;
    }

    public String getCategoryImagePathContentType() {
        return categoryImagePathContentType;
    }

    public void setCategoryImagePathContentType(String categoryImagePathContentType) {
        this.categoryImagePathContentType = categoryImagePathContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoryDTO categoryDTO = (CategoryDTO) o;
        if (categoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", categoryImagePath='" + getCategoryImagePath() + "'" +
            "}";
    }
}
