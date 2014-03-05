/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jaret.timebars.pdi;

import java.awt.Component;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class TimeBarViewerDragGestureListener implements DragGestureListener {

    public void dragGestureRecognized(DragGestureEvent e) {
        Component c = e.getComponent();
        System.out.println("component " + c);
        // TimeeBarViewer tbv = (D)
            /*
         * TimeBarViewer tbv = (TimeBarViewer) ((Diagram) c)._timeBarViewer;
         * TimeBarSelectionModel selModel = tbv.getSelectionModel(); if
         * (selModel.hasIntervalSelection()) { List l =
         * selModel.getSelectedIntervals(); List rowIntervalTuples =
         * buildRowIntervalTuples(l);
         * 
         * IntervalListTransferable ilt = new
         * IntervalListTransferable(rowIntervalTuples); e.startDrag(null,
         * ilt); } else { // nothing to drag System.out.println("nothing to
         * drag"); } } private List buildRowIntervalTuples(List l) { List
         * rowIntervalTuples = new ArrayList(); List list = new
         * ArrayList(l); RowIntervalTuple tuple = new RowIntervalTuple(null,
         * list); rowIntervalTuples.add(tuple); return rowIntervalTuples; }
         */
    }
}
