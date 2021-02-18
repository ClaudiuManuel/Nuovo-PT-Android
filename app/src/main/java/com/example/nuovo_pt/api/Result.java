package com.example.nuovo_pt.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("category")
    @Expose
    private Integer category;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name_original")
    @Expose
    private String nameOriginal;
    @SerializedName("muscles")
    @Expose
    private List<Integer> muscles = null;
    @SerializedName("muscles_secondary")
    @Expose
    private List<Integer> musclesSecondary = null;
    @SerializedName("equipment")
    @Expose
    private List<Integer> equipment = null;
    @SerializedName("creation_date")
    @Expose
    private String creationDate;
    @SerializedName("language")
    @Expose
    private Integer language;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("variations")
    @Expose
    private Integer variations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameOriginal() {
        return nameOriginal;
    }

    public void setNameOriginal(String nameOriginal) {
        this.nameOriginal = nameOriginal;
    }

    public List<Integer> getMuscles() {
        return muscles;
    }

    public void setMuscles(List<Integer> muscles) {
        this.muscles = muscles;
    }

    public List<Integer> getMusclesSecondary() {
        return musclesSecondary;
    }

    public void setMusclesSecondary(List<Integer> musclesSecondary) {
        this.musclesSecondary = musclesSecondary;
    }

    public List<Integer> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Integer> equipment) {
        this.equipment = equipment;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getVariations() {
        return variations;
    }

    public void setVariations(Integer variations) {
        this.variations = variations;
    }

}
