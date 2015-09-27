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

import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Id util generator
 *
 * @author Cris
 */
public class IDGenerator {

    private static EnumMap<GUITypes, ArrayList<String>> ids;
    private int[] counter;
    private static IDGenerator instance = null;

    public static IDGenerator getInstance() {
        if (instance == null) {
            instance = new IDGenerator();
            return instance;
        } else {
            return instance;
        }

    }

    private IDGenerator() {
        ids = new EnumMap<GUITypes, ArrayList<String>>(GUITypes.class);
        counter = new int[GUITypes.values().length];
        for (int i = 0; i < counter.length; i++) {
            counter[i] = 0;
        }
    }

    public boolean isUnique(GUITypes t, String id) {
        if (ids.containsKey(t)) {
            return !ids.get(t).contains(id);
        } else {
            return true;
        }
    }

    public String generate(GUITypes t) {
        int i = t.ordinal();
        String res = "" + t + "" + counter[i]++;

        while (!isUnique(t, res)) {
            res = "" + t + "" + counter[i]++;
        }
        this.addID(res, t);
        return res;
    }

    public void addID(String id, GUITypes t) {
        if (!isUnique(t, id)) {
            throw new IllegalArgumentException("The ID is not unique");
        }

        if (this.ids.containsKey(t)) {
            ids.get(t).add(id);
        } else {
            ArrayList<String> tmp = new ArrayList<String>();
            tmp.add(id);
            ids.put(t, tmp);
        }

    }
}
