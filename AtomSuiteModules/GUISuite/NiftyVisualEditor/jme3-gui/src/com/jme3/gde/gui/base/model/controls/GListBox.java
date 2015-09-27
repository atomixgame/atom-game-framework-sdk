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
package com.jme3.gde.gui.base.model.controls;

import de.lessvoid.nifty.controls.listbox.builder.ListBoxBuilder;
import com.jme3.gde.gui.base.model.GUIFactory;
import com.jme3.gde.gui.base.model.GUITypes;
import com.jme3.gde.gui.base.model.elements.GElement;
import org.w3c.dom.Element;

/**
 *
 * @author cris
 */
public class GListBox extends GElement{
    /*
     static{
         GUIFactory.registerProduct(new GListBox());
     }
     */ 
    public GListBox(){
        super();
    }
    public GListBox(String id,org.w3c.dom.Element docElement){
      super(id,docElement);
      if(!docElement.getTagName().equals(GUITypes.CONTROL_TAG))
          throw new IllegalArgumentException("Illegal tag name");
      builder = new ListBoxBuilder(id);
      
    }
    @Override
    public GUITypes getType() {
       return GUITypes.LISTBOX;
    }

   

    @Override
    public GElement create(String id, Element ele) {
        return new GListBox(id,ele);
    }

    @Override
    public void initDefault() {
        domElement.setAttribute("name", "listBox");
        domElement.setAttribute("width", "50%");
    }
    
}
