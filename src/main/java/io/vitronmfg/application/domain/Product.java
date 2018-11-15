package io.vitronmfg.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "product_image_path")
    private byte[] productImagePath;

    @Column(name = "product_image_path_content_type")
    private String productImagePathContentType;

    @Lob
    @Column(name = "detailed_pdf_path")
    private byte[] detailedPdfPath;

    @Column(name = "detailed_pdf_path_content_type")
    private String detailedPdfPathContentType;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Category category;

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

    public Product title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getProductImagePath() {
        return productImagePath;
    }

    public Product productImagePath(byte[] productImagePath) {
        this.productImagePath = productImagePath;
        return this;
    }

    public void setProductImagePath(byte[] productImagePath) {
        this.productImagePath = productImagePath;
    }

    public String getProductImagePathContentType() {
        return productImagePathContentType;
    }

    public Product productImagePathContentType(String productImagePathContentType) {
        this.productImagePathContentType = productImagePathContentType;
        return this;
    }

    public void setProductImagePathContentType(String productImagePathContentType) {
        this.productImagePathContentType = productImagePathContentType;
    }

    public byte[] getDetailedPdfPath() {
        return detailedPdfPath;
    }

    public Product detailedPdfPath(byte[] detailedPdfPath) {
        this.detailedPdfPath = detailedPdfPath;
        return this;
    }

    public void setDetailedPdfPath(byte[] detailedPdfPath) {
        this.detailedPdfPath = detailedPdfPath;
    }

    public String getDetailedPdfPathContentType() {
        return detailedPdfPathContentType;
    }

    public Product detailedPdfPathContentType(String detailedPdfPathContentType) {
        this.detailedPdfPathContentType = detailedPdfPathContentType;
        return this;
    }

    public void setDetailedPdfPathContentType(String detailedPdfPathContentType) {
        this.detailedPdfPathContentType = detailedPdfPathContentType;
    }

    public Category getCategory() {
        return category;
    }

    public Product category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
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
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", productImagePath='" + getProductImagePath() + "'" +
            ", productImagePathContentType='" + getProductImagePathContentType() + "'" +
            ", detailedPdfPath='" + getDetailedPdfPath() + "'" +
            ", detailedPdfPathContentType='" + getDetailedPdfPathContentType() + "'" +
            "}";
    }
}
