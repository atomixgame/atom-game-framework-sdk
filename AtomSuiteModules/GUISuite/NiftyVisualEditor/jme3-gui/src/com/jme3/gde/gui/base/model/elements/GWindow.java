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
package com.jme3.gde.gui.base.model.elements;

import de.lessvoid.nifty.controls.window.WindowControl;
import de.lessvoid.nifty.controls.window.builder.WindowBuilder;
import de.lessvoid.xml.xpp3.Attributes;
import com.jme3.gde.gui.base.model.GUIFactory;
import com.jme3.gde.gui.base.model.GUITypes;
import org.w3c.dom.Element;

/**
 *
 * @author cris
 */
public class GWindow extends GElement {
    /*
     static{
     GUIFactory.registerProduct(new GWindow());
     }
     */

    public GWindow() {
        super();
    }

    public GWindow(String id, org.w3c.dom.Element docElement) throws IllegalArgumentException {
        super(id, docElement);
        if (!docElement.getTagName().equals("control")) {
            throw new IllegalArgumentException("Illegal tag name");
        }
        builder = new WindowBuilder();


    }

    @Override
    public GUITypes getType() {
        return GUITypes.WINDOW;
    }

    @Override
    protected de.lessvoid.nifty.elements.Element getDropContext() {
        return niftyElement.getControl(WindowControl.class).getContent();
    }

    @Override
    public void addAttribute(String key, String val) {
        if (key.equals("id")) {
            this.id = val;
        }
        if (key.equals("childLayout")) {
            Attributes att = getDropContext().getElementType().getAttributes();
            att.set(key, val);
        } else {
            this.domElement.setAttribute(key, val);
            Attributes att = this.niftyElement.getElementType().getAttributes();
            att.set(key, val);
        }
    }

    @Override
    public GElement create(String id, Element ele) {
        return new GWindow(id, ele);
    }

    @Override
    public void initDefault() {
        domElement.setAttribute("name", GUITypes.WINDOW.toString());
        domElement.setAttribute("width", "50%");
        domElement.setAttribute("height", "30%");
        domElement.setAttribute("title", "window");


    }
}
