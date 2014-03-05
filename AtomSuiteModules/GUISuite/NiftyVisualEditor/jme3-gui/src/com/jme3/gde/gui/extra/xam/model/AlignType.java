/*
 
 */
package com.jme3.gde.gui.extra.xam.model;

/**
 * An enum type in the Nifty domain.
 * 
 * @author cuongnguyen
 */
// Generated by XAM AutoGen Tool v0.2
public enum AlignType {
    LEFT("left"),   // NOI18N
    CENTER("center"),   // NOI18N
    RIGHT("right"),   // NOI18N
    ;

    private AlignType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static AlignType getAlignType(String value) {
        if (value == null) {
            return null;
        } else if ("left".equals(value)) {   // NOI18N
            return LEFT;
        } else if ("center".equals(value)) {   // NOI18N
            return CENTER;
        } else if ("right".equals(value)) {   // NOI18N
            return RIGHT;
        } else {
            assert false : "Invalid choice: " + value; 
            return null;
        }
    }
    
    private final String value;
}
