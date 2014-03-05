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
package com.jme3.gde.gui.io.xml;

import java.io.File;

/**
 * Util class to filter xml files
 * @author cris and atomix
 */
public class XmlFileFilter extends javax.swing.filechooser.FileFilter{

    @Override
    public boolean accept(File pathname) {
        if(pathname.getName().endsWith(".xml") || pathname.isDirectory()){
            return true;
        }
        return false;
    }

    @Override
    public String getDescription() {
       return "Xml gui file";
    }
    
}
