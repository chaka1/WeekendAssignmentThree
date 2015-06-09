package com.example.chaka.weekendassignmentthree.models;


import com.google.gson.annotations.Expose;

/*
@Generated("org.jsonschema2pojo")
 */
public class Category {

    @Expose
    private String CategoryId;
    @Expose
    private String Name;
    @Expose
    private Integer ProductCount;

    /**
     *
     * @return
     * The CategoryId
     */
    public String getCategoryId() {
        return CategoryId;
    }

    /**
     *
     * @param CategoryId
     * The CategoryId
     */
    public void setCategoryId(String CategoryId) {
        this.CategoryId = CategoryId;
    }

    /**
     *
     * @return
     * The Name
     */
    public String getName() {
        return Name;
    }

    /**
     *
     * @param Name
     * The Name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     *
     * @return
     * The ProductCount
     */
    public Integer getProductCount() {
        return ProductCount;
    }

    /**
     *
     * @param ProductCount
     * The ProductCount
     */
    public void setProductCount(Integer ProductCount) {
        this.ProductCount = ProductCount;
    }

}
