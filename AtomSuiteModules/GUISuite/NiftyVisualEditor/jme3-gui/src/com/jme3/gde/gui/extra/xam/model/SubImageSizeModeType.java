/*
 
 */
package com.jme3.gde.gui.extra.xam.model;

/**
 * An enum type in the Nifty domain.
 * 
 * @author cuongnguyen
 */
// Generated by XAM AutoGen Tool v0.2
public enum SubImageSizeModeType {
    SCALE("scale"),   // NOI18N
    RESIZEHINT("resizeHint"),   // NOI18N
    ;

    private SubImageSizeModeType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static SubImageSizeModeType getSubImageSizeModeType(String value) {
        if (value == null) {
            return null;
        } else if ("scale".equals(value)) {   // NOI18N
            return SCALE;
        } else if ("resizeHint".equals(value)) {   // NOI18N
            return RESIZEHINT;
        } else {
            assert false : "Invalid choice: " + value; 
            return null;
        }
    }
    
    private final String value;
}
