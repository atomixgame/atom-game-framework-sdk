package sg.gde.ScriptManager.control;

import groovy.inspect.swingui.ObjectBrowser;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.swing.SwingBuilder;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.codehaus.groovy.control.CompilationFailedException;
import org.openide.util.Exceptions;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import sg.atom.script.engine.groovy.AtomGroovyScriptEngine;
import sg.gde.atomcore.notify.MessageUtil;
import sg.gde.atomcore.notify.NotifyUtil;

/**
 *
 * @author hungcuong
 */
public class NetbeanGroovyScriptEngine extends AtomGroovyScriptEngine {

    private static Logger logger = Logger.getLogger(NetbeanGroovyScriptEngine.class.getName());
    private SwingBuilder swingBuilder;

    public NetbeanGroovyScriptEngine() {
        super();
        //factory = new ScriptEngineManager();
        //headerLine = "== Groovy Script Engine . Version 1.0 :";
    }

    @Override
    public void startEngine(String path) {
        try {
            //genericScriptEngine = factory.getEngineByName("groovy");
            scriptEngine = new GroovyScriptEngine(path);

            binding = new Binding();
            shell = new GroovyShell(binding);
            ClassLoader parent = getClass().getClassLoader();
            loader = new GroovyClassLoader(parent);
            logger.log(Level.FINE, "Start Script Engine in" + path);
            InputOutput io = IOProvider.getDefault().getIO("Script Engine", true);
            io.getOut().println("Start Script Engine in" + path);
            swingBuilder = new SwingBuilder();
        } catch (IOException ex) {
            NotifyUtil.warn("title", "Error when load Engine", false);
        }
    }
    /*
     public boolean check(String text) {
     try {
     if (text.equals("")) {
     return false;
     }
     shell.parse(text);
     } catch (CompilationFailedException ex) {
     return false;
     } catch (Exception ex) {
     return false;
     }
     return true;
     }
     */

    public Object eval(String line) {
        return null;

    }

    public JPanel runPanel(String scriptName) {
        try {
            Object[] args = {};
            if (viaLoader) {
                //Object script = scriptEngine.run(scriptName, new Binding());
                Class groovyClass = loader.parseClass(new File(scriptName));
                // let's call some method on an instance
                GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();

                return (JPanel) groovyObject.invokeMethod("onGUI", args);

            } else {
                binding.setVariable("swing", swingBuilder);
                binding.setVariable("__sdk", new SDKHelper());
                Script groovyScriptObject = scriptEngine.createScript(scriptName, binding);
                //ObjectBrowser.inspect(groovyScriptObject);
                
                //MessageUtil.warn("Created a script:" + groovyScriptObject.toString());
                logger.info(groovyScriptObject.getBinding().toString());
                return (JPanel) groovyScriptObject.invokeMethod("onGUI", args);
            }

        } catch (ResourceException ex) {
            Exceptions.printStackTrace(ex);
            NotifyUtil.warn("Error", "Error when load Engine :" + ex.getMessage(), false);
        } catch (ScriptException ex) {
            Exceptions.printStackTrace(ex);
            NotifyUtil.warn("Error", "Error when load Engine :" + ex.getMessage(), false);
        } catch (CompilationFailedException ex) {
            Exceptions.printStackTrace(ex);
            NotifyUtil.warn("Error", "Error when load Engine :" + ex.getMessage(), false);
        } catch (IOException ex) {
            InputOutput io = IOProvider.getDefault().getIO("Script Engine", true);
            io.getOut().println("Error: Can not find script :" + ex.getMessage());
            Exceptions.printStackTrace(ex);
            NotifyUtil.warn("Error", "Error when load Engine :" + ex.getMessage(), false);
        } catch (InstantiationException ex) {
            Exceptions.printStackTrace(ex);
            NotifyUtil.warn("Error", "Error when load Engine :" + ex.getMessage(), false);
        } catch (IllegalAccessException ex) {
            Exceptions.printStackTrace(ex);
            NotifyUtil.warn("Error", "Error when load Engine :" + ex.getMessage(), false);
        }
        return null;
    }
}
