package com.example.chaka.weekendassignmentthree.models;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

/**
 * @Generated("org.jsonschema2pojo")
 */
public class Facet {

    @Expose
    private List<FacetValue> FacetValues = new ArrayList<FacetValue>();
    @Expose
    private String Id;
    @Expose
    private String Name;
    @Expose
    private Integer Sequence;

    /**
     *
     * @return
     * The FacetValues
     */
    public List<FacetValue> getFacetValues() {
        return FacetValues;
    }

    /**
     *
     * @param FacetValues
     * The FacetValues
     */
    public void setFacetValues(List<FacetValue> FacetValues) {
        this.FacetValues = FacetValues;
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

    /**
     *
     * @return
     * The Sequence
     */
    public Integer getSequence() {
        return Sequence;
    }

    /**
     *
     * @param Sequence
     * The Sequence
     */
    public void setSequence(Integer Sequence) {
        this.Sequence = Sequence;
    }

}
