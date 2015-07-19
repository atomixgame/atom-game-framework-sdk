package com.lightdev.demo.gantt;

import java.awt.Component;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.jdesktop.swingx.JXDatePicker;

/**
 * Custom cell editor to manipulate dates.
 *
 * @author Ulrich Hilger
 * @author Light Development
 * @author <a href="http://www.lightdev.com">http://www.lightdev.com</a>
 * @author <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author published under the terms and conditions of the BSD License, for
 * details see file license.txt in the distribution package of this software
 *
 * @version 1, 30.03.2006
 */
public class DateCellEditor extends AbstractCellEditor implements TableCellEditor {

    /**
     * create a new instance of a DateCellEditor object
     */
    public DateCellEditor() {
        datePicker = new JXDatePicker();
        datePicker.setFormats(new DateFormat[]{DateFormat.getDateInstance(DateFormat.SHORT)});
    }

    /**
     * get the value of this DateCellEditor
     *
     * @return the value of this editor
     */
    public Object getCellEditorValue() {
        return datePicker.getDate();
    }

    /**
     * get the component to use to edit date cells
     *
     * @param table table object this editor serves
     * @param value the value to be edited
     * @param isSelected indicates whether or not the cell is selected
     * @param row number of row being edited
     * @param column number of column being edited
     * @return editor component to use
     */
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {
        if (value != null && value instanceof Date) {
            datePicker.setDate((Date) value);
        }
        return datePicker;
    }
    /**
     * reference to date picker component to use to edit dates
     */
    private JXDatePicker datePicker;
}