package com.jme3.gde.gui.palette.visual;

import com.jme3.gde.gui.nbeditor.model.Types;

public class NiftyElementIcon {

    private Integer number;
    private String category;
    private String image;
    private Types type;
    private String style;
    
    public NiftyElementIcon(Integer number, String category, Types type, String image) {
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

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }
}