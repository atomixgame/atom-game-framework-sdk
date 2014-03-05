package pythonconvertor

import org.antlr.runtime.*;
import org.antlr.Tool;
import pythonconvertor.python.grammar.PythonLexer

def translateAllDir(dirName){
    new File(dirName).eachFile() { file -> 
        if (file.getName() =~ /.png/){
            def newName = file.getName().replace(".py",".java")
            File f = new File(dirName + "/" + newName)
            file.renameTo(f)
            println file.getName() + " -> " + f.getName() 
            //println file.getName() + " -> " + newName
        }
    } 
}

def toGraph(){
    
}

def displayGraph(){
    
}

def displayGraphDot(){
    
}

class MyLexer extends PythonLexer {

    public MyLexer(CharStream lexer) {
        super(lexer);
    }
    public Token nextToken() {
        startPos = getCharPositionInLine();
        return super.nextToken();
    }
}


CharStream input = new ANTLRFileStream(args[0]);
PythonLexer lexer = new MyLexer(input);
CommonTokenStream tokens = new CommonTokenStream(lexer);
PythonTokenSource indentedSource = new PythonTokenSource(tokens);
tokens = new CommonTokenStream(indentedSource);
//System.out.println("tokens="+tokens.getTokens());
PythonParser parser = new PythonParser(tokens);
parser.file_input();
    
// FILE
public abstract static class FileAction extends AbstractAction {

	private final JFileChooser chooser = new JFileChooser();
	private final JPanel parent;

	FileAction(JPanel parent) {
		this.parent = parent;
	}

	@Override public void actionPerformed(ActionEvent evt) {
		// Show dialog; won't return until dialog is closed:
		chooser.showSaveDialog(parent);

		// Get the selected file
		File file = chooser.getSelectedFile();
		if (file != null) {
			handleFile(file);
		}
	}

	public abstract void handleFile(File file);
}



myButton.addActionListener(new FileAction(getMainPanel()) {
	@Override public void handleFile(File file) {
		// do whatever you want with the file...
	}
});

// D N D 
import java.awt.datatransfer.*;
import java.awt.dnd.*;

public class TFileDropHandler
{	
	// TFileDropHandler.groovy
	// Laurence Toenjes - 2010-08-18
	// Based on 1999 example from Cay Horstmann found at
	// http://java.itags.org/java-core-gui-apis/33457/
	
	// dropped files closure example: { files -> println files };
	def TFileDropHandler( aSwingControl, Closure a_onDroppedFiles )
	{   // ctor
		def dropTarget = new DropTarget( aSwingControl, new TFileDropTargetListener( a_onDroppedFiles ) )
		aSwingControl.setDropTarget( dropTarget )
	}
	
	// --------------------------------------------------------
	class TFileDropTargetListener implements DropTargetListener 
	{
		def Closure onDroppedFiles =  null // our callback event
		public TFileDropTargetListener( a_onDroppedFiles ) { // ctor
					 onDroppedFiles = a_onDroppedFiles }
			
		public void dragEnter(DropTargetDragEvent event) {}	
		public void dragExit(DropTargetEvent event) {}
		public void dropActionChanged(DropTargetDragEvent event) {}
		public void dragOver(DropTargetDragEvent event) {
			// provide visual feedback
			event.acceptDrag(DnDConstants.ACTION_COPY);
		}
		
		public void drop(DropTargetDropEvent event) {
			if ( (event.getSourceActions() & DnDConstants.ACTION_COPY) != 0 ) {
				event.acceptDrop(DnDConstants.ACTION_COPY);
			}
			else {
				event.rejectDrop();
				return; // !!!!!!!!!!!!!!!!!!!!!
			}
			Transferable transferable = event.getTransferable();
			DataFlavor[] flavors = event.getCurrentDataFlavors();
			for (int i = 0; i < flavors.length; i++) {
				DataFlavor dataFlavor = flavors[i];
				try {
					// file flavor
					if ( dataFlavor.equals( DataFlavor.javaFileListFlavor ) ) {
						java.util.List fileList = (java.util.List) transferable.getTransferData(dataFlavor);					
						def files = []	
						fileList.each { files << it }
						this?.onDroppedFiles( files )
						event.dropComplete(true);
						return;
					}	
				} 
				catch(Exception e) {
						event.dropComplete(false);
						return;
					}
			} // end for
			event.dropComplete(false);		
		} // end drop
	}
	// --------------------------------------------------------
	
	public static void main(String[] args) {
		// simple example
		def f = new javax.swing.JFrame()
		f.setDefaultCloseOperation( javax.swing.JFrame.EXIT_ON_CLOSE )
		f.size = [400,300]
		def ta = new javax.swing.JTextArea()
		f.add( ta )
		
		new TFileDropHandler( ta,  { files -> 
			files.each { 
				ta.append("${it}\n")
				println it
			} 
		} )
		
		f.show()
	}
}