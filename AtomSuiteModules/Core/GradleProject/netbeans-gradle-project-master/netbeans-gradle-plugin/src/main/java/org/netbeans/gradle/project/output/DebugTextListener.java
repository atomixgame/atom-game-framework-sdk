package org.netbeans.gradle.project.output;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jtrim.utils.ExceptionHelper;
import org.netbeans.gradle.project.api.task.TaskOutputProcessor;

public final class DebugTextListener implements TaskOutputProcessor {
    public static interface DebugeeListener {
        public void onDebugeeListening(int port);
    }

    private static final Logger LOGGER = Logger.getLogger(DebugTextListener.class.getName());

    private static final String LISTEN_TEXT = "Listening for transport dt_socket at address".toLowerCase(Locale.US);

    private final DebugeeListener listener;
    private final AtomicBoolean found;

    public DebugTextListener(DebugeeListener listener) {
        ExceptionHelper.checkNotNullArgument(listener, "listener");
        this.listener = listener;
        this.found = new AtomicBoolean(false);
    }

    @Override
    public void processLine(String line) {
        if (found.get()) {
            return;
        }

        String trimmedLine = line.trim().toLowerCase(Locale.US);
        if (trimmedLine.startsWith(LISTEN_TEXT)) {
            int portSeparatorIndex = trimmedLine.indexOf(':');

            if (portSeparatorIndex >= 0) {
                String portStr = trimmedLine.substring(portSeparatorIndex + 1).trim();
                int port;
                try {
                    port = Integer.parseInt(portStr);
                } catch (NumberFormatException ex) {
                    LOGGER.log(Level.WARNING, "Unexpected port number: {0}", portStr);
                    return;
                }

                if (found.compareAndSet(false, true)) {
                    listener.onDebugeeListening(port);
                }
            }
        }
    }
}
