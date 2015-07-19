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
package com.jme3.gde.gui.nbeditor.controller;

import com.jme3.gde.gui.base.model.GUITypes;
import com.jme3.gde.gui.base.model.elements.GElement;
import de.lessvoid.nifty.elements.Element;

/**
 *
 * @author cris
 */
public class ElementEditor {

    public static int DEFAULT_SIZE_X = 100;
    public static int DEFAULT_SIZE_Y = 50;
    private GElement selected;

    public ElementEditor(GElement toEdit) {
        selected = toEdit;
    }

    public ElementEditor setAttribute(String key, String value) {
        if (selected != null) {
            selected.addAttribute(key, value);
            selected.refresh();
        }
        return this;
    }

    public ElementEditor removeAttribute(String key) {
        if (selected != null) {
            selected.removeAttribute(key);
            selected.refresh();
        }
        return this;
    }

    public void normalizeSize() {
        Element parent = selected.getParent().getNiftyElement();
        Element sel = selected.getNiftyElement();
        
        float width = (float) sel.getWidth() / parent.getWidth();
        float height = (float) sel.getHeight() / parent.getHeight();
        int percW = (int) (width * 100);
        int percH = (int) (height * 100);

        selected.addAttribute("width", "" + percW + "%");
        selected.addAttribute("height", "" + percH + "%");
        selected.lightRefresh();
    }

    public void setEdited(GElement toEdit) {
        selected = toEdit;
    }

    public void setVisibile(boolean visibility) {
        selected.getNiftyElement().setVisible(visibility);
    }

    public int getIndex() {
        return selected.getParent().getElements().indexOf(selected);
    }

    public void setVisibileSelected(boolean vis) {
        if (!this.selected.getType().equals(GUITypes.SCREEN)) {
            if (vis) {
                this.selected.getNiftyElement().showWithoutEffects();
            } else {
                this.selected.getNiftyElement().hideWithoutEffect();
            }
        }
    }

    public Element getNiftyElement() {
        return selected.getNiftyElement();
    }
}
