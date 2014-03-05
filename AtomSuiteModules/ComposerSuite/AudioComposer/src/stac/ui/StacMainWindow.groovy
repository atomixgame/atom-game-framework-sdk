package stac.ui

import groovy.swing.SwingBuilder
import groovy.swing.SwingXBuilder
import groovy.swing.j2d.*

import javax.swing.*
import java.awt.*
import java.awt.BorderLayout as BL;
import java.awt.GridBagConstraints as GBC;

import com.nilo.plaf.nimrod.*
import griffon.builder.trident.TridentBuilder
import groovy.util.FactoryBuilderSupport
import org.pushingpixels.trident.*;

import ca.odell.glazedlists.*;
import ca.odell.glazedlists.swing.*;
import ca.odell.glazedlists.matchers.*;

import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.MultiSplitLayout;
import org.jdesktop.swingx.MultiSplitLayout.Divider;
import org.jdesktop.swingx.MultiSplitLayout.Leaf;
import org.jdesktop.swingx.MultiSplitLayout.Node;
import org.jdesktop.swingx.MultiSplitLayout.Split;

import stac.*
import stac.lipsync.*
import stac.audio.sample.*
import stac.audio.*
import stac.musiccg.*

import editor.ui.curves.*

lookup = new Lookup();
swing = new SwingXBuilder()
//groovy.inspect.swingui.ObjectBrowser.inspect(swing)
def trident = new TridentBuilder()
//groovy.inspect.swingui.ObjectBrowser.inspect(trident)

// Create canvas
app3d = new JME3DCanvas()
canvas = app3d.createAndStartCanvas(250,250)

// LIPSYNC Dope sheet
dopeSheet=new DopeSheet(200)
dopeSheet.setDialog("You are my angel")

LipsyncManager.setDopeSheet(dopeSheet)
/*
// GRAPH

graphDisplayer = new GraphDisplayer()
graph = new Graph()
graph.contructGraph()
graphPanel = null
*/
// CURVE
curvesUI = new CurvesUI()


def createNimRODLAF(){
        
    NimRODTheme nt = new NimRODTheme();
    nt.setPrimary1( new Color(255,255,255));
    //nt.setPrimary2( new Color(20,20,20));
    //nt.setPrimary3( new Color(30,30,30));
    nt.setPrimary( new Color(0,150,250))
    nt.setBlack( new Color(255,255,250))
    nt.setWhite( Color.lightGray)
    nt.setSecondary( Color.gray)
 
    NimRODLookAndFeel NimRODLF = new NimRODLookAndFeel();
    NimRODLF.setCurrentTheme( nt);

    //lookAndFeel("com.nilo.plaf.nimrod.NimRODLookAndFeel")
    return NimRODLF;
}

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
        
        d=dialog(title:'Choose gradient',id:'myDialog',modal:true,size:[400,300]) { //-- 3 --//
            gradientChooser()
        }
        menu(text: "View", mnemonic: 'C') {
            menuItem(text: "Spectrum Gradient", mnemonic: 'L', actionPerformed: {d.show() })
            
        }
        menu(text: "Lipsync", mnemonic: 'C') {

            
        }
        menu(text: "Filter", mnemonic: 'C') {

            
        }
        menu(text: "Play", mnemonic: 'C') {

            
        }
        menu(text: "Character", mnemonic: 'C') {

            
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
def getRow={list, point->
                                
    return list.locationToIndex(point);
}

def createToolbar(def builder){
    toolbar = builder.toolBar(rollover:true, constraints:BorderLayout.NORTH,floatable :true) {
        
        button( text:"New",icon:createIcon("icon/mimi/File_add32.png"))
        button( text:"Open")
        button( text:"Save")
        separator(orientation:SwingConstants.VERTICAL)
    }
    return toolbar
}
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

def createIcon(String path){
    return swing.imageIcon(resource:"../images/"+path,class:StacMainWindow.class)
}

swing.edt{
    //lookAndFeel("com.oyoaha.swing.plaf.oyoaha.OyoahaLookAndFeel")

    lookAndFeel(createNimRODLAF())
     
    frame(title: 'Audio Composer - Lip sync Tool', defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
        size: screenSize, 
        minimumSize: [350,500],
        show: true, locationRelativeTo: null) {
        
        borderLayout()
        toolBar(rollover:true, constraints:BorderLayout.NORTH,floatable :true) {
        
            button( toolTipText:"New",icon:createIcon("icon/mimi/File_add24.png"))
            button( toolTipText:"Open",icon:createIcon("icon/mimi/Folder24.png"))
            button( toolTipText:"Save",icon:createIcon("icon/mimi/Save24.png"))
            separator(orientation:SwingConstants.VERTICAL)
            button( toolTipText:"Cut",icon:createIcon("icon/mimi/Cut24.png"))
            button( toolTipText:"Copy",icon:createIcon("icon/mimi/Copy24.png"))
            button( toolTipText:"Paste",icon:createIcon("icon/mimi/Paste24.png"))  
            toolBar(rollover:true, floatable :true) {
        
                button( toolTipText:"Play",icon:createIcon("icon/sound/24/start.png"))
                button( toolTipText:"Stop",icon:createIcon("icon/sound/24/stop.png"))
                button( toolTipText:"Back",icon:createIcon("icon/sound/24/start.png"))
                separator(orientation:SwingConstants.VERTICAL)
                button( toolTipText:"Record",icon:createIcon("icon/sound/24/record.png"))
                button( toolTipText:"Lipsync",icon:createIcon("icon/sound/24/kiss.png"))
                button( toolTipText:"Devices",icon:createIcon("icon/sound/24/speaker-.png"))
            }
        }
        
        //toolbar = createToolbar(swing)
        menubar = createMenuBar(swing)
        
        
        String layoutDef ="(ROW (COLUMN weight=0.9 (LEAF name=leftup weight=0.8) (LEAF name=leftdown weight=0.2)) (COLUMN weight=0.1 (LEAF name=up weight=0.5) (LEAF name=down weight=0.5)))";
        MultiSplitLayout.Node modelRoot = MultiSplitLayout.parseModel(layoutDef);

        JXMultiSplitPane multiSplitPane1 = new JXMultiSplitPane();
        multiSplitPane1.getMultiSplitLayout().setModel(modelRoot);
    
        /*
        def children =[ 
        new Leaf("left"),
        new Divider(), 
        new Leaf("right")];
        Split modelRoot = new Split();
        modelRoot.setChildren(children);

        JXMultiSplitPane multiSplitPane1 = new JXMultiSplitPane();
        multiSplitPane1.getMultiSplitLayout().setModel(modelRoot);
         */
        

        multiSplitPane1.setDividerSize(3)
        multiSplitPane(multiSplitPane1,constraints:BL.CENTER){
            tabbedPane(constraints:"leftup"){

                panel(title:"Lipsync"){
                    borderLayout()
                    panel(background:Color.darkGray,constraints:BorderLayout.CENTER){
                        
                        gridLayout(rows:4)
                        panel(background:Color.darkGray){
                            borderLayout()
                            //label(text:"wave")
                            def clipViewer=new ClipViewerPanel()
                            def rc = new RecordableClip(new File("./audio/1-welcome.wav"))
                            clipViewer.showClip(rc)
                            widget(clipViewer.getPanel(),constraints:BL.CENTER)
                        }
                        panel(background:Color.darkGray){
                            borderLayout()
                            //label(text:"spectrals")
                            def sp = new SpectrogramPanel("./audio/1-welcome.wav")
                            widget(sp,constraints:BL.CENTER)
                        }
                        curvesPanel = widget(constraints:BL.CENTER, new GraphicsPanel(),id:"curvesPanel") 

                        scrollPane(){
                            widget(dopeSheet)
                        }
                        
                    }
                    toolBar(rollover:true, floatable :true,constraints:BorderLayout.SOUTH) {
                        button( toolTipText:"Play",icon:createIcon("icon/sound/24/start.png"))
                        button( toolTipText:"Stop",icon:createIcon("icon/sound/24/stop.png"))
                        comboBox()
                        separator(orientation:SwingConstants.VERTICAL)
                        button( toolTipText:"Record",icon:createIcon("icon/sound/24/record.png"))
                        button( toolTipText:"Lipsync",icon:createIcon("icon/sound/24/kiss.png"))
                        button( toolTipText:"Devices",icon:createIcon("icon/sound/24/speaker-.png"))
                    }
                }
                panel(title:"Graph"){
                    borderLayout()
                    graphPanel = widget(constraints:BL.CENTER, new GraphicsPanel(),id:"graphPanel") 
                }
                
                panel(title:"Juke"){
                    widget(new Juke("./audio"))
                }
                scrollPane (title:"Mapping"){
                    
                    data = [[first:'qwer', last:'asdf'],
                        [first:'zxcv', last:'tyui'],
                        [first:'ghjk', last:'bnm']]

                    table {
                        tableModel( list : data ) {
                            propertyColumn(header:'First Name', propertyName:'first')
                            propertyColumn(header:'last Name', propertyName:'last')
                        }
                    }
                }

            }
            tabbedPane(constraints:"leftdown"){
                scrollPane (title:"Log"){
                    textArea(text:"Log")
                }
                scrollPane (title:"Log2"){
                    textArea(text:"Log2")
                }
            }
            
            panel(constraints:"up",preferredSize:[300,500]){
                borderLayout()
                label(text:"Preview",constraints:BL.NORTH)       

                previewPanel = panel(constraints:BL.CENTER,preferredSize:[300,500],maximumSize:[300,300]){
                    borderLayout()
                }
                
                previewPanel.add(canvas,BL.CENTER)
                comboBox(constraints:BL.SOUTH)
            }
            panel(constraints:"down"){
                borderLayout()
                label(constraints:BL.NORTH,text:"Preview")
                tabbedPane(constraints:BL.CENTER){
                    panel(title:"Project"){
                        borderLayout()
                        tree(constraints:BL.CENTER)    
                    }
                    panel(title:"Audio"){
                        borderLayout()
                        tree(constraints:BL.CENTER)    
                    }
                }
                
            }
        }
        panel(constraints:BL.SOUTH){
            label(text:"Ready")
            separator(orientation:SwingConstants.VERTICAL)
            label(text:"ms")
            label(text:"ms")
            label(text:"File")
            
        }
    }
    
}
/*
graphPanel.graphicsOperation= graphDisplayer.display(graphPanel,graph)
graphDisplayer.addGraphChangeListener(this)
*/
curvesPanel.graphicsOperation=curvesUI.getDrawGrid()