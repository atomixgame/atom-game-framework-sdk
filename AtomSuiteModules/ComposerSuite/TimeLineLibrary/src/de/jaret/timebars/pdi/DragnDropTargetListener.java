/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jaret.timebars.pdi;

import de.jaret.util.ui.timebars.model.TimeBarRow;
import de.jaret.util.ui.timebars.swing.TimeBarViewer;
import de.jaret.util.ui.timebars.swing.dnd.IntervalListTransferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class DragnDropTargetListener extends DropTargetAdapter {

    TimeBarViewer _tbv;

    public DragnDropTargetListener(TimeBarViewer tbv) {
        _tbv = tbv;
    }

    public void dragOver(DropTargetDragEvent e) {
        if (e.isDataFlavorSupported(IntervalListTransferable.intervalListFlavor)) {
            TimeBarRow row = _tbv.rowForY(e.getLocation().y);
            if (row != null && e.isDataFlavorSupported(IntervalListTransferable.intervalListFlavor)) {
                // the transferable is not
                if (row.getIntervals(_tbv.dateForX(e.getLocation().x)).size() == 0) {
                    _tbv.highlightRow(row);
                    e.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
                    return;
                }
            }
            e.rejectDrag();
            _tbv.deHighlightRow();
        }
    }

    public void dragExit(DropTargetEvent dte) {
        _tbv.deHighlightRow();
    }

    public void drop(DropTargetDropEvent e) {
        /*
         try {
         Transferable tr = e.getTransferable();
         DataFlavor[] flavors = tr.getTransferDataFlavors();
         PersonenDisposition pdispo = (PersonenDisposition) _tbv.rowForY(e.getLocation().y);
         for (int i = 0; i < flavors.length; i++) {
         if (flavors[i].equals(IntervalListTransferable.intervalListFlavor)) {
         // e.rejectDrop();
         e.acceptDrop(e.getDropAction());
         List l = (List) e.getTransferable()
         .getTransferData(IntervalListTransferable.intervalListFlavor);
         List intervals = ((RowIntervalTuple) l.get(0)).getIntervals();
         Iterator it = intervals.iterator();
         while (it.hasNext()) {
         Interval interval = (Interval) it.next();

         }
         _tbv.deHighlightRow();
         e.dropComplete(true);
         }
         }
         } catch (Throwable t) {
         t.printStackTrace();
         }
         e.rejectDrop();
         * */
    }
}
