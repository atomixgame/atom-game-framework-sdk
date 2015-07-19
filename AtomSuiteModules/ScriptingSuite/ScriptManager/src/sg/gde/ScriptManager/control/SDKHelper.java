package sg.gde.ScriptManager.control;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

/**
 *
 * @author hungcuong
 */
public class SDKHelper {

    private static Logger logger = Logger.getLogger(NetbeanGroovyScriptEngine.class.getName());

    public SDKHelper() {
    }

    public void log(String msg) {
        logger.log(Level.FINE, msg);

    }

    public void ioLog(String msg) {
        InputOutput io = IOProvider.getDefault().getIO("Script Engine", false);
        io.getOut().println("Start Script Engine in" + msg);
    }
}
