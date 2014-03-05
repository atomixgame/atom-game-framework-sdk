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
package com.jme3.gde.gui.nbeditor.model.elements;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import com.jme3.gde.gui.nbeditor.model.GUIFactory;
import com.jme3.gde.gui.nbeditor.model.Types;

/**
 *
 * @author cris
 */
public class GTextfield extends GElement {
    /*
     static{
     GUIFactory.registerProduct(new GTextfield());
     }
     */

    public GTextfield() {
        super();
    }

    public GTextfield(String id, org.w3c.dom.Element docElement) {
        super(id, docElement);
        if (!docElement.getTagName().equals("control")) {
            throw new IllegalArgumentException("Illegal tag name");
        }
        builder = new TextFieldBuilder(id);
    }

    @Override
    public Types getType() {
        return Types.TEXTFIELD;
    }

    @Override
    public void createNiftyElement(Nifty nifty) {
        super.createNiftyElement(nifty);
        niftyElement.disable();

    }

    @Override
    public void initDefault() {
        domElement.setAttribute("name", "" + Types.TEXTFIELD);
        domElement.setAttribute("width", "50%");

    }

    @Override
    public GElement create(String id, org.w3c.dom.Element ele) {
        return new GTextfield(id, ele);
    }
}
