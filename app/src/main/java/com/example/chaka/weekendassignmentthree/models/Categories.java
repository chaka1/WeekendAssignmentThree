package com.example.chaka.weekendassignmentthree.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/*
*@Generated("org.jsonschema2pojo")
 */
public class Categories {

    @Expose
    private String Description;
    @Expose
    private List<Category> Category= new ArrayList<Category>();
    @Expose
    private String SortType;

    /**
     *
     * @return
     * The Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     *
     * @param Description
     * The Description
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     *
     * @return
     * The Listing
     */
    public List<Category> getListing() {
        return Category;
    }

    /**
     *
     * @param Category
     * The Category
     */
    public void setListing(List<Category> Category) {
        this.Category = Category;
    }

    /**
     *
     * @return
     * The SortType
     */
    public String getSortType() {
        return SortType;
    }

    /**
     *
     * @param SortType
     * The SortType
     */
    public void setSortType(String SortType) {
        this.SortType = SortType;
    }

}
