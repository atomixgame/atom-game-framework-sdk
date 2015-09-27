/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.gde.rpg.editor.visual.dialoge;

import java.awt.Font;
import java.awt.Image;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.SeparatorWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.ImageUtilities;
import org.openide.util.Utilities;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class UIConversationNodeWidget extends Widget {

    private static final Image IMAGE_CLASS = ImageUtilities.loadImage("sg/gde/rpg/icons/nodes/class.gif"); // NOI18N
    private static final Image IMAGE_MEMBER = ImageUtilities.loadImage("sg/gde/rpg/icons/nodes/variablePublic.gif"); // NOI18N
    private static final Image IMAGE_OPERATION = ImageUtilities.loadImage("sg/gde/rpg/icons/nodes/methodPublic.gif"); // NOI18N
    private LabelWidget titleWidget;
    private Widget members;
    private Widget operations;
    private static final Border BORDER_4 = BorderFactory.createEmptyBorder(4);
    private ImageWidget avatarImage;

    public UIConversationNodeWidget(Scene scene) {
        super(scene);
        setLayout(LayoutFactory.createVerticalFlowLayout());
        setBorder(BorderFactory.createLineBorder());
        setOpaque(true);
        setCheckClipping(true);

        Widget classWidget = new Widget(scene);
        classWidget.setLayout(LayoutFactory.createHorizontalFlowLayout());
        classWidget.setBorder(BORDER_4);

        avatarImage = new ImageWidget(scene);
        avatarImage.setImage(IMAGE_CLASS);
        classWidget.addChild(avatarImage);

        titleWidget = new LabelWidget(scene);
        titleWidget.setFont(scene.getDefaultFont().deriveFont(Font.BOLD));
        classWidget.addChild(titleWidget);
        addChild(classWidget);

        addChild(new SeparatorWidget(scene, SeparatorWidget.Orientation.HORIZONTAL));

        members = new Widget(scene);
        members.setLayout(LayoutFactory.createVerticalFlowLayout());
        members.setOpaque(false);
        members.setBorder(BORDER_4);
        addChild(members);

        addChild(new SeparatorWidget(scene, SeparatorWidget.Orientation.HORIZONTAL));

        operations = new Widget(scene);
        operations.setLayout(LayoutFactory.createVerticalFlowLayout());
        operations.setOpaque(false);
        operations.setBorder(BORDER_4);
        addChild(operations);
    }

    public String getTitle() {
        return titleWidget.getLabel();
    }

    public void setAvatarImage(Image image) {
        avatarImage.setImage(image);
    }

    public void setTitle(String className) {
        this.titleWidget.setLabel(className);
    }

    public Widget createMember(String member) {
        Scene scene = getScene();
        Widget widget = new Widget(scene);
        widget.setLayout(LayoutFactory.createHorizontalFlowLayout());

        ImageWidget imageWidget = new ImageWidget(scene);
        imageWidget.setImage(IMAGE_MEMBER);
        widget.addChild(imageWidget);

        LabelWidget labelWidget = new LabelWidget(scene);
        labelWidget.setLabel(member);
        widget.addChild(labelWidget);

        return widget;
    }

    public Widget createOperation(String operation) {
        Scene scene = getScene();
        Widget widget = new Widget(scene);
        widget.setLayout(LayoutFactory.createHorizontalFlowLayout());

        ImageWidget imageWidget = new ImageWidget(scene);
        imageWidget.setImage(IMAGE_OPERATION);
        widget.addChild(imageWidget);

        LabelWidget labelWidget = new LabelWidget(scene);
        labelWidget.setLabel(operation);
        widget.addChild(labelWidget);

        return widget;
    }

    public void addMember(Widget memberWidget) {
        members.addChild(memberWidget);
    }

    public void removeMember(Widget memberWidget) {
        members.removeChild(memberWidget);
    }

    public void addOperation(Widget operationWidget) {
        operations.addChild(operationWidget);
    }

    public void removeOperation(Widget operationWidget) {
        operations.removeChild(operationWidget);
    }
}