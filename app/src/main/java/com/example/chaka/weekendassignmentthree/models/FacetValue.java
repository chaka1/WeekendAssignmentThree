package com.example.chaka.weekendassignmentthree.models;

import com.google.gson.annotations.Expose;

/**
 * @Generated("org.jsonschema2pojo")
 */
public class FacetValue {

    @Expose
    private Integer Count;
    @Expose
    private String Id;
    @Expose
    private String Name;

    /**
     *
     * @return
     * The Count
     */
    public Integer getCount() {
        return Count;
    }

    /**
     *
     * @param Count
     * The Count
     */
    public void setCount(Integer Count) {
        this.Count = Count;
    }

    /**
     *
     * @return
     * The Id
     */
    public String getId() {
        return Id;
    }

    /**
     *
     * @param Id
     * The Id
     */
    public void setId(String Id) {
        this.Id = Id;
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

}