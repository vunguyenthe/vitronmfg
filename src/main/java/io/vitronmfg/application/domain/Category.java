package io.vitronmfg.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "category_image_path")
    private byte[] categoryImagePath;

    @Column(name = "category_image_path_content_type")
    private String categoryImagePathContentType;

    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MainProduct> mainProducts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Category name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Category description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getCategoryImagePath() {
        return categoryImagePath;
    }

    public Category categoryImagePath(byte[] categoryImagePath) {
        this.categoryImagePath = categoryImagePath;
        return this;
    }

    public void setCategoryImagePath(byte[] categoryImagePath) {
        this.categoryImagePath = categoryImagePath;
    }

    public String getCategoryImagePathContentType() {
        return categoryImagePathContentType;
    }

    public Category categoryImagePathContentType(String categoryImagePathContentType) {
        this.categoryImagePathContentType = categoryImagePathContentType;
        return this;
    }

    public void setCategoryImagePathContentType(String categoryImagePathContentType) {
        this.categoryImagePathContentType = categoryImagePathContentType;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Category products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Category addProduct(Product product) {
        this.products.add(product);
        product.setCategory(this);
        return this;
    }

    public Category removeProduct(Product product) {
        this.products.remove(product);
        product.setCategory(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<MainProduct> getMainProducts() {
        return mainProducts;
    }

    public Category mainProducts(Set<MainProduct> mainProducts) {
        this.mainProducts = mainProducts;
        return this;
    }

    public Category addMainProduct(MainProduct mainProduct) {
        this.mainProducts.add(mainProduct);
        mainProduct.setCategory(this);
        return this;
    }

    public Category removeMainProduct(MainProduct mainProduct) {
        this.mainProducts.remove(mainProduct);
        mainProduct.setCategory(null);
        return this;
    }

    public void setMainProducts(Set<MainProduct> mainProducts) {
        this.mainProducts = mainProducts;
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
        Category category = (Category) o;
        if (category.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", categoryImagePath='" + getCategoryImagePath() + "'" +
            ", categoryImagePathContentType='" + getCategoryImagePathContentType() + "'" +
            "}";
    }
}
