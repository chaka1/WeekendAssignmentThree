package com.example.chaka.weekendassignmentthree.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * @Generated("org.jsonschema2pojo")
 */
public class Products {

    @Expose
    private List<Object> AlsoSearched = new ArrayList<Object>();
    @Expose
    private String Description;
    @Expose
    private List<Facet> Facets = new ArrayList<Facet>();
    @Expose
    private Integer ItemCount;
    @Expose
    private List<Listing> Listings = new ArrayList<Listing>();
    @Expose
    private String RedirectUrl;
    @Expose
    private String SortType;

    /**
     *
     * @return
     * The AlsoSearched
     */
    public List<Object> getAlsoSearched() {
        return AlsoSearched;
    }

    /**
     *
     * @param AlsoSearched
     * The AlsoSearched
     */
    public void setAlsoSearched(List<Object> AlsoSearched) {
        this.AlsoSearched = AlsoSearched;
    }

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
     * The Facets
     */
    public List<Facet> getFacets() {
        return Facets;
    }

    /**
     *
     * @param Facets
     * The Facets
     */
    public void setFacets(List<Facet> Facets) {
        this.Facets = Facets;
    }

    /**
     *
     * @return
     * The ItemCount
     */
    public Integer getItemCount() {
        return ItemCount;
    }

    /**
     *
     * @param ItemCount
     * The ItemCount
     */
    public void setItemCount(Integer ItemCount) {
        this.ItemCount = ItemCount;
    }

    /**
     *
     * @return
     * The Listings
     */
    public List<Listing> getListings() {
        return Listings;
    }

    /**
     *
     * @param Listings
     * The Listings
     */
    public void setListings(List<Listing> Listings) {
        this.Listings = Listings;
    }

    /**
     *
     * @return
     * The RedirectUrl
     */
    public String getRedirectUrl() {
        return RedirectUrl;
    }

    /**
     *
     * @param RedirectUrl
     * The RedirectUrl
     */
    public void setRedirectUrl(String RedirectUrl) {
        this.RedirectUrl = RedirectUrl;
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
