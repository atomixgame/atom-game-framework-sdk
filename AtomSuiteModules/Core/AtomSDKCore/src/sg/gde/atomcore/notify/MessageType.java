package sg.gde.atomcore.notify;

import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.openide.NotifyDescriptor;

/**
 *
 * @author qbeukes.blogspot.com, used by metalklesk
 */
public enum MessageType {
    PLAIN   (NotifyDescriptor.PLAIN_MESSAGE,       null),
    INFO    (NotifyDescriptor.INFORMATION_MESSAGE, "sg/gde/atomcore/icons/app/info.png"),
    QUESTION(NotifyDescriptor.QUESTION_MESSAGE,    "sg/gde/atomcore/icons/app/question.png"),
    ERROR   (NotifyDescriptor.ERROR_MESSAGE,       "sg/gde/atomcore/icons/app/error.png"),
    WARNING (NotifyDescriptor.WARNING_MESSAGE,     "sg/gde/atomcore/icons/app/warning.png");

    private int notifyDescriptorType;

    private Icon icon;

    private MessageType(int notifyDescriptorType, String resourceName) {
        this.notifyDescriptorType = notifyDescriptorType;
        if (resourceName == null) {
            icon = new ImageIcon();
        } else {
            icon = loadIcon(resourceName);
        }
    }

    private static Icon loadIcon(String resourceName) {
        URL resource = MessageType.class.getResource("images/" + resourceName);
        System.out.println(resource);
        if (resource == null) {
            return new ImageIcon();
        }
        
        return new ImageIcon(resource);
    }

    int getNotifyDescriptorType() {
        return notifyDescriptorType;
    }

    Icon getIcon() {
        return icon;
    }
}
