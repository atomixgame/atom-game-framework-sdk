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

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.console.ConsoleControl;
import com.jme3.gde.gui.base.model.GUIFactory;
import com.jme3.gde.gui.base.model.GUITypes;
import com.jme3.gde.gui.base.model.elements.GElement;
import org.w3c.dom.Element;

/**
 *
 * @author cris
 */
public class GConsole extends GElement{
    /*
    static{
        GUIFactory.registerProduct(new GConsole());
    }
    
    */
    public GConsole(){
        super();
    }
    public GConsole(String id,org.w3c.dom.Element docElement) throws IllegalArgumentException{
      super(id,docElement);
      if(!docElement.getTagName().equals("control"))
          throw new IllegalArgumentException("Illegal tag name");
      super.builder = new de.lessvoid.nifty.controls.console.builder.ConsoleBuilder(id);
    
    }
    @Override
    public GUITypes getType() {
        return GUITypes.NIFTYCONSOLE;
    }

    @Override
    public void createNiftyElement(Nifty nifty) {
        super.createNiftyElement(nifty);
        niftyElement.disableFocus();
        niftyElement.disable();
        niftyElement.findControl(this.id,ConsoleControl.class).getTextField().getElement().disable();
        
    }
    
  
    @Override
     public void reloadElement(Nifty manager){
         super.reloadElement(manager);
         niftyElement.disable();
         niftyElement.layoutElements();
         
     }
    @Override
    public GElement create(String id,Element ele) {
       return new GConsole(id,ele);
    }

    @Override
    public void initDefault() { 
    domElement.setAttribute("name", ""+GUITypes.NIFTYCONSOLE);
     domElement.setAttribute("width", "50%");
      
    }
    
}
