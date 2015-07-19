package sg.stac.lipsync;

import sg.stac.lipsync.ui.DopeSheet;
import sg.stac.lipsync.word.Lookup;
import sg.stac.app.StacMainWindow;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class LipsyncManager {

    public static Lookup lookup = new Lookup();
    public static DopeSheet dopeSheet;
    public static StacMainWindow mainWindow;

    public static Lookup getLookup() {
        return lookup;
    }

    public static void setDopeSheet(DopeSheet d) {
        dopeSheet = d;
    }
}
