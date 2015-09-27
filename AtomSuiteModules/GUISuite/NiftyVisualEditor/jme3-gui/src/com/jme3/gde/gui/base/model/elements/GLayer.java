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
 */package com.jme3.gde.gui.base.model.elements;

import de.lessvoid.nifty.builder.LayerBuilder;
import com.jme3.gde.gui.base.model.GUIFactory;
import com.jme3.gde.gui.base.model.GUITypes;

/**
 *
 * @author cris
 */
public class GLayer extends GElement {
/*
    static {
        GUIFactory.registerProduct(new GLayer());
    }
*/
    public GLayer() {
        super();
    }

    public GLayer(String id, org.w3c.dom.Element docElement) throws IllegalArgumentException {
        super(id, docElement);
        if (!docElement.getTagName().equals(GUITypes.LAYER.toString())) {
            throw new IllegalArgumentException("Illegal tag name");
        }
        builder = new LayerBuilder(id);

    }

    @Override
    public GUITypes getType() {
        return GUITypes.LAYER;
    }

    @Override
    public void initDefault() {
        domElement.setAttribute("childLayout", "absolute");

    }

    @Override
    public GElement create(String id, org.w3c.dom.Element ele) {
        return new GLayer(id, ele);
    }
}
