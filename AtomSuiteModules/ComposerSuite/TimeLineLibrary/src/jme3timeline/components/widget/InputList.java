/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3timeline.components.widget;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleRole;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class InputList extends JPanel implements ListSelectionListener, ActionListener {
    protected JLabel label = new JLabel();
    protected JTextField textfield;
    protected JList list;
    protected JScrollPane scroll;

    public InputList(String[] data, String title) {
        setLayout(null);
        add(label);
        textfield = new OpelListText();
        textfield.addActionListener(this);
        label.setLabelFor(textfield);
        add(textfield);
        list = new OpelListList(data);
        list.setVisibleRowCount(4);
        list.addListSelectionListener(this);
        scroll = new JScrollPane(list);
        add(scroll);
    }

    public InputList(String title, int numCols) {
        setLayout(null);
        label = new OpelListLabel(title, JLabel.LEFT);
        add(label);
        textfield = new OpelListText(numCols);
        textfield.addActionListener(this);
        label.setLabelFor(textfield);
        add(textfield);
        list = new OpelListList();
        list.setVisibleRowCount(4);
        list.addListSelectionListener(this);
        scroll = new JScrollPane(list);
        add(scroll);
    }

    public void setToolTipText(String text) {
        super.setToolTipText(text);
        label.setToolTipText(text);
        textfield.setToolTipText(text);
        list.setToolTipText(text);
    }

    public void setDisplayedMnemonic(char ch) {
        label.setDisplayedMnemonic(ch);
    }

    public void setSelected(String sel) {
        list.setSelectedValue(sel, true);
        textfield.setText(sel);
    }

    public String getSelected() {
        return textfield.getText();
    }

    public void setSelectedInt(int value) {
        setSelected(Integer.toString(value));
    }

    public int getSelectedInt() {
        try {
            return Integer.parseInt(getSelected());
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        Object obj = list.getSelectedValue();
        if (obj != null) {
            textfield.setText(obj.toString());
        }
    }

    public void actionPerformed(ActionEvent e) {
        ListModel model = list.getModel();
        String key = textfield.getText().toLowerCase();
        for (int k = 0; k < model.getSize(); k++) {
            String data = (String) model.getElementAt(k);
            if (data.toLowerCase().startsWith(key)) {
                list.setSelectedValue(data, true);
                break;
            }
        }
    }

    public void addListSelectionListener(ListSelectionListener lst) {
        list.addListSelectionListener(lst);
    }

    public Dimension getPreferredSize() {
        Insets ins = getInsets();
        Dimension labelSize = label.getPreferredSize();
        Dimension textfieldSize = textfield.getPreferredSize();
        Dimension scrollPaneSize = scroll.getPreferredSize();
        int w = Math.max(Math.max(labelSize.width, textfieldSize.width), scrollPaneSize.width);
        int h = labelSize.height + textfieldSize.height + scrollPaneSize.height;
        return new Dimension(w + ins.left + ins.right, h + ins.top + ins.bottom);
    }

    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    public void doLayout() {
        Insets ins = getInsets();
        Dimension size = getSize();
        int x = ins.left;
        int y = ins.top;
        int w = size.width - ins.left - ins.right;
        int h = size.height - ins.top - ins.bottom;
        Dimension labelSize = label.getPreferredSize();
        label.setBounds(x, y, w, labelSize.height);
        y += labelSize.height;
        Dimension textfieldSize = textfield.getPreferredSize();
        textfield.setBounds(x, y, w, textfieldSize.height);
        y += textfieldSize.height;
        scroll.setBounds(x, y, w, h - y);
    }

    public void appendResultSet(ResultSet results, int index, boolean toTitleCase) {
        textfield.setText("");
        DefaultListModel model = new DefaultListModel();
        try {
            while (results.next()) {
                String str = results.getString(index);
                if (toTitleCase) {
                    str = Character.toUpperCase(str.charAt(0)) + str.substring(1);
                }
                model.addElement(str);
            }
        } catch (SQLException ex) {
            System.err.println("appendResultSet: " + ex.toString());
        }
        list.setModel(model);
        if (model.getSize() > 0) {
            list.setSelectedIndex(0);
        }
    }

    class OpelListLabel extends JLabel {

        public OpelListLabel(String text, int alignment) {
            super(text, alignment);
        }

        public AccessibleContext getAccessibleContext() {
            return InputList.this.getAccessibleContext();
        }
    }

    class OpelListText extends JTextField {

        public OpelListText() {
        }

        public OpelListText(int numCols) {
            super(numCols);
        }

        public AccessibleContext getAccessibleContext() {
            return InputList.this.getAccessibleContext();
        }
    }

    class OpelListList extends JList {

        public OpelListList() {
        }

        public OpelListList(String[] data) {
            super(data);
        }

        public AccessibleContext getAccessibleContext() {
            return InputList.this.getAccessibleContext();
        }
    }

    // Accessibility Support
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleOpenList();
        }
        return accessibleContext;
    }

    protected class AccessibleOpenList extends AccessibleJComponent {

        public String getAccessibleName() {
            System.out.println("getAccessibleName: " + accessibleName);
            if (accessibleName != null) {
                return accessibleName;
            }
            return label.getText();
        }

        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.LIST;
        }
    }
    
}
