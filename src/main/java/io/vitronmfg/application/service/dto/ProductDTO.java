package io.vitronmfg.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Product entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    private String title;

    @Lob
    private byte[] productImagePath;
    private String productImagePathContentType;

    @Lob
    private byte[] detailedPdfPath;
    private String detailedPdfPathContentType;

    private Long categoryId;

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

    public byte[] getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(byte[] productImagePath) {
        this.productImagePath = productImagePath;
    }

    public String getProductImagePathContentType() {
        return productImagePathContentType;
    }

    public void setProductImagePathContentType(String productImagePathContentType) {
        this.productImagePathContentType = productImagePathContentType;
    }

    public byte[] getDetailedPdfPath() {
        return detailedPdfPath;
    }

    public void setDetailedPdfPath(byte[] detailedPdfPath) {
        this.detailedPdfPath = detailedPdfPath;
    }

    public String getDetailedPdfPathContentType() {
        return detailedPdfPathContentType;
    }

    public void setDetailedPdfPathContentType(String detailedPdfPathContentType) {
        this.detailedPdfPathContentType = detailedPdfPathContentType;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (productDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", productImagePath='" + getProductImagePath() + "'" +
            ", detailedPdfPath='" + getDetailedPdfPath() + "'" +
            ", category=" + getCategoryId() +
            "}";
    }
}
