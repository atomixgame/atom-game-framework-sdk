package pythonconvertor

import groovy.swing.SwingBuilder
import groovy.swing.j2d.*

import javax.swing.*
import java.awt.*
import java.awt.BorderLayout as BL;
import java.awt.GridBagConstraints as GBC;

import groovy.util.FactoryBuilderSupport

swing = new SwingBuilder()
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

def createMenuBar(def builder){
    
    menubar = builder.menuBar() {
        menu(text: "File", mnemonic: 'F') {
            menuItem(text: "Open", mnemonic: 'L', actionPerformed: { })
            menuItem(text: "Save", mnemonic: 'L', actionPerformed: { })
            separator()
            menuItem(text: "Exit", mnemonic: 'X', actionPerformed: {dispose() })
        }
        menu(text: "Edit", mnemonic: 'C') {

            
        }
        
        menu(text: "View", mnemonic: 'C') {
            menuItem(text: "Spectrum Gradient", mnemonic: 'L', actionPerformed: {d.show() })
            
        }

        menu(text: "Window", mnemonic: 'C') {

            
        }
        menu(text: "Help", mnemonic: 'C') {

            
        }

    }
    return menubar
}

def createFriendPopupMenu(def builder){
    /*
    popupMenu = builder.popupMenu {
    //menuItem(text: "Help", mnemonic: 'R', actionPerformed: { })
    //menuItem(text: "About iChat", mnemonic: 'R', actionPerformed: { })
            
    }
     */
   
    def popupMenu = new JPopupMenu();
    popupMenu.add(jmi1= new JMenuItem("Add"));
    popupMenu.add(new JPopupMenu.Separator());
    popupMenu.add(jmi2 = new JMenuItem("Clear"));

    return popupMenu
}


def createToolbar(def builder){
    toolbar = builder.toolBar(rollover:true, constraints:BorderLayout.NORTH,floatable :true) {
        
        button( text:"New",icon:createIcon("ToolbarIcons/24/New.png"))
        button( text:"Open")
        button( text:"Save")
        separator(orientation:SwingConstants.VERTICAL)
    }
    return toolbar
}


def createIcon(String path){
    return swing.imageIcon(resource:"../../images/"+path,class:ConvertorApp.class)
}

swing.edt{
    //lookAndFeel("com.oyoaha.swing.plaf.oyoaha.OyoahaLookAndFeel")

    //lookAndFeel(createNimRODLAF())
     
    frame(title: 'ConvertorApp', defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
        size: screenSize, 
        minimumSize: [350,500],
        show: true, locationRelativeTo: null) {
        
        borderLayout()
        /*
        toolBar(rollover:true, constraints:BorderLayout.NORTH,floatable :true) {
        
        button( toolTipText:"New",icon:createIcon("ToolbarIcons/24/New.png"))
        button( toolTipText:"Open",icon:createIcon("ToolbarIcons/24/Open.png"))
        button( toolTipText:"Save",icon:createIcon("ToolbarIcons/24/Save.png"))
        separator(orientation:SwingConstants.VERTICAL)
        button( toolTipText:"Copy",icon:createIcon("ToolbarIcons/24/Copy.png"))
        button( toolTipText:"Cut",icon:createIcon("ToolbarIcons/24/Cut.png"))
        button( toolTipText:"Paste",icon:createIcon("ToolbarIcons/24/Paste.png"))
        button( toolTipText:"Delete",icon:createIcon("ToolbarIcons/24/Delete.png"))
        separator(orientation:SwingConstants.VERTICAL)
        }
         */
        //toolbar = createToolbar(swing)
        menubar = createMenuBar(swing)
        
        tabbedPane(constraints:BL.CENTER){

            panel(title:"Source"){
                borderLayout()
                panel(background:Color.darkGray,constraints:BorderLayout.CENTER){
                        
                    gridLayout(rows:2)
                    panel(background:Color.darkGray){
                        borderLayout()
                        scrollPane (constraints:BL.CENTER){
                            textArea(text:"sampleText")
                        }
                    }
                    panel(background:Color.gray){
                        borderLayout()
                        scrollPane (constraints:BL.CENTER){
                            textArea(text:"sampleText")
                        }
                    }

                }

            }
            scrollPane (title:"Graph"){
                textArea(text:"Log")
            }
        }
            
        tabbedPane(constraints:BL.SOUTH){
            scrollPane (title:"Result"){

            }
            scrollPane (title:"Log"){
                textArea(text:"Log")
            }
        }
        /*
        panel(constraints:"down",preferredSize:[300,500]){
        borderLayout()
                
        panel(constraints:BL.CENTER){
        borderLayout()
        label(text:"Properties",constraints:BL.NORTH)       
        table {
        data = [[first:'qwer', last:'asdf'],
        [first:'zxcv', last:'tyui'],
        [first:'ghjk', last:'bnm']]
        tableModel( list : data,constraints:BL.CENTER ) {
        propertyColumn(header:'First Name', propertyName:'first')
        propertyColumn(header:'last Name', propertyName:'last')
        }
        }
        }
                

        comboBox(constraints:BL.SOUTH)
        }
         */
    }
    
}
def defineProject(){
    
}
def buildPythonTree(){
    
    
}
