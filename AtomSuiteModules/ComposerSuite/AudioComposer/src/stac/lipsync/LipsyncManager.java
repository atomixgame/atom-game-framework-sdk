/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stac.lipsync;

/**
 *
 * @author cuong.nguyenmanh2
 */

import stac.ui.*;

class LipsyncManager {

    static Lookup lookup = new Lookup();
    static DopeSheet dopeSheet;
    static StacMainWindow mainWindow;
    public static Lookup getLookup() {
        return lookup;
    }
    public static void setDopeSheet(DopeSheet d){
        dopeSheet = d;
    }
}
