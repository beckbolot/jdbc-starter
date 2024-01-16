package com.beck.model;

import java.math.BigDecimal;

public class Product {

    private Integer id;
    private String name;
    private BigDecimal price;

    private Category category;

    private Integer categoryId;

    public Product() {
    }


    public Product(String name, BigDecimal price, Integer categoryId) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

    public Product(Integer id, String name, BigDecimal price, Integer categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", categoryId=" + categoryId +
                '}';
    }
}
