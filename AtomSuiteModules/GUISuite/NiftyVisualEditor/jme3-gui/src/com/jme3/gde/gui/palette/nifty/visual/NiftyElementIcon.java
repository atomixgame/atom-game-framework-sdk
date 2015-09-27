package com.jme3.gde.gui.palette.nifty.visual;

import com.jme3.gde.gui.base.model.GUITypes;

public class NiftyElementIcon {

    private Integer number;
    private String category;
    private String image;
    private GUITypes type;
    private String style;
    
    public NiftyElementIcon(Integer number, String category, GUITypes type, String image) {
        this.number = number;
        this.category = category;
        this.image = image;
        this.type = type;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public GUITypes getType() {
        return type;
    }

    public void setType(GUITypes type) {
        this.type = type;
    }
}