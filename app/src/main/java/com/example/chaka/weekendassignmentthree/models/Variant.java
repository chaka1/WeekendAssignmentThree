package com.example.chaka.weekendassignmentthree.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/*
*@Generated("org.jsonschema2pojo")
*/
public class Variant {

    @Expose
    private Double BasePrice;
    @Expose
    private Object Brand;
    @Expose
    private String Colour;
    @Expose
    private String CurrentPrice;
    @Expose
    private Boolean InStock;
    @Expose
    private Boolean IsInSet;
    @Expose
    private String PreviousPrice;
    @Expose
    private String PriceType;
    @Expose
    private Integer ProductId;
    @Expose
    private List<String> ProductImageUrls = new ArrayList<String>();
    @Expose
    private String RRP;
    @Expose
    private String Size;
    @Expose
    private Object Sku;
    @Expose
    private Object Title;

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
    public Object getBrand() {
        return Brand;
    }

    /**
     *
     * @param Brand
     * The Brand
     */
    public void setBrand(Object Brand) {
        this.Brand = Brand;
    }

    /**
     *
     * @return
     * The Colour
     */
    public String getColour() {
        return Colour;
    }

    /**
     *
     * @param Colour
     * The Colour
     */
    public void setColour(String Colour) {
        this.Colour = Colour;
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
     * The InStock
     */
    public Boolean getInStock() {
        return InStock;
    }

    /**
     *
     * @param InStock
     * The InStock
     */
    public void setInStock(Boolean InStock) {
        this.InStock = InStock;
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
     * The PriceType
     */
    public String getPriceType() {
        return PriceType;
    }

    /**
     *
     * @param PriceType
     * The PriceType
     */
    public void setPriceType(String PriceType) {
        this.PriceType = PriceType;
    }

    /**
     *
     * @return
     * The ProductId
     */
    public Integer getProductId() {
        return ProductId;
    }

    /**
     *
     * @param ProductId
     * The ProductId
     */
    public void setProductId(Integer ProductId) {
        this.ProductId = ProductId;
    }

    /**
     *
     * @return
     * The ProductImageUrls
     */
    public List<String> getProductImageUrls() {
        return ProductImageUrls;
    }

    /**
     *
     * @param ProductImageUrls
     * The ProductImageUrls
     */
    public void setProductImageUrls(List<String> ProductImageUrls) {
        this.ProductImageUrls = ProductImageUrls;
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
     * The Size
     */
    public String getSize() {
        return Size;
    }

    /**
     *
     * @param Size
     * The Size
     */
    public void setSize(String Size) {
        this.Size = Size;
    }

    /**
     *
     * @return
     * The Sku
     */
    public Object getSku() {
        return Sku;
    }

    /**
     *
     * @param Sku
     * The Sku
     */
    public void setSku(Object Sku) {
        this.Sku = Sku;
    }

    /**
     *
     * @return
     * The Title
     */
    public Object getTitle() {
        return Title;
    }

    /**
     *
     * @param Title
     * The Title
     */
    public void setTitle(Object Title) {
        this.Title = Title;
    }

}
