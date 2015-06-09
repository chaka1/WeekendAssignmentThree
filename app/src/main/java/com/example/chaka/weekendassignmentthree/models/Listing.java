package com.example.chaka.weekendassignmentthree.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 *@Generated("org.jsonschema2pojo")
 */
public class Listing {

    @Expose
    private Double BasePrice;
    @Expose
    private String Brand;
    @Expose
    private String CurrentPrice;
    @Expose
    private Boolean HasMoreColours;
    @Expose
    private Boolean IsInSet;
    @Expose
    private String PreviousPrice;
    @Expose
    private String ProductId;
    @Expose
    private List<String> ProductImageUrl = new ArrayList<String>();
    @Expose
    private String RRP;
    @Expose
    private String Title;

    /**
     *
     * @return
     * The BasePrice
     */
    public Double getBasePrice() {
        return BasePrice;
    }

    /**
     *
     * @param BasePrice
     * The BasePrice
     */
    public void setBasePrice(Double BasePrice) {
        this.BasePrice = BasePrice;
    }

    /**
     *
     * @return
     * The Brand
     */
    public String getBrand() {
        return Brand;
    }

    /**
     *
     * @param Brand
     * The Brand
     */
    public void setBrand(String Brand) {
        this.Brand = Brand;
    }

    /**
     *
     * @return
     * The CurrentPrice
     */
    public String getCurrentPrice() {
        return CurrentPrice;
    }

    /**
     *
     * @param CurrentPrice
     * The CurrentPrice
     */
    public void setCurrentPrice(String CurrentPrice) {
        this.CurrentPrice = CurrentPrice;
    }

    /**
     *
     * @return
     * The HasMoreColours
     */
    public Boolean getHasMoreColours() {
        return HasMoreColours;
    }

    /**
     *
     * @param HasMoreColours
     * The HasMoreColours
     */
    public void setHasMoreColours(Boolean HasMoreColours) {
        this.HasMoreColours = HasMoreColours;
    }

    /**
     *
     * @return
     * The IsInSet
     */
    public Boolean getIsInSet() {
        return IsInSet;
    }

    /**
     *
     * @param IsInSet
     * The IsInSet
     */
    public void setIsInSet(Boolean IsInSet) {
        this.IsInSet = IsInSet;
    }

    /**
     *
     * @return
     * The PreviousPrice
     */
    public String getPreviousPrice() {
        return PreviousPrice;
    }

    /**
     *
     * @param PreviousPrice
     * The PreviousPrice
     */
    public void setPreviousPrice(String PreviousPrice) {
        this.PreviousPrice = PreviousPrice;
    }

    /**
     *
     * @return
     * The ProductId
     */
    public String getProductId() {
        return ProductId;
    }

    /**
     *
     * @param ProductId
     * The ProductId
     */
    public void setProductId(String ProductId) {
        this.ProductId = ProductId;
    }

    /**
     *
     * @return
     * The ProductImageUrl
     */
    public List<String> getProductImageUrl() {
        return ProductImageUrl;
    }

    /**
     *
     * @param ProductImageUrl
     * The ProductImageUrl
     */
    public void setProductImageUrl(List<String> ProductImageUrl) {
        this.ProductImageUrl = ProductImageUrl;
    }

    /**
     *
     * @return
     * The RRP
     */
    public String getRRP() {
        return RRP;
    }

    /**
     *
     * @param RRP
     * The RRP
     */
    public void setRRP(String RRP) {
        this.RRP = RRP;
    }

    /**
     *
     * @return
     * The Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     *
     * @param Title
     * The Title
     */
    public void setTitle(String Title) {
        this.Title = Title;
    }

}
