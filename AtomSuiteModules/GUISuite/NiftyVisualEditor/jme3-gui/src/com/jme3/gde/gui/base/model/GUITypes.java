/* Copyright 2012 Aguzzi Cristiano

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.jme3.gde.gui.base.model;

/**
 * All type of elements available in this editor
 * if you want to add one new , you need first to add a new Type here
 * @author cris
 */
public enum GUITypes {
    
    SCREEN("screen",false),
    LAYER("layer",false),
    PANEL("panel",false),
    BUTTON("button",true),
    LABEL("label",true),
    CHECKBOX("checkbox",true),
    TEXTFIELD("textfield",true),
    NIFTYCONSOLE("nifty-console",true),
    WINDOW("window",true),
    RADIOBUTTON("radioButton",true),
    IMAGE("image",false),
    LISTBOX("listBox",true);
    
    public static String CONTROL_TAG = "control";
    private String val;
    private boolean isNiftyControl;
    
    private GUITypes(String type,boolean isControl){
        this.val = type;
        this.isNiftyControl=isControl;
    }
    
    public boolean isControl(){
        return this.isNiftyControl;
    }
    
    public static boolean isControlTag(String tag){
        return tag.equals(CONTROL_TAG);
    }
    
    
    public static String convert(String tag){
        String tmp = tag.toUpperCase();
        String result="";
        for(int i = 0;i<tmp.length();i++){
             char c = tmp.charAt(i);
             if(c!='-')
                 result+=c;
        }
        return result;
    }
    
    @Override
    public String toString(){
        return val;
    }
}
