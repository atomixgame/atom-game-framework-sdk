package sg.gde.rpg.editor.visual.dialoge;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.netbeans.api.visual.action.*;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.graph.layout.GraphLayout;
import org.netbeans.api.visual.graph.layout.GraphLayoutFactory;
import org.netbeans.api.visual.graph.layout.GraphLayoutSupport;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.SceneLayout;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

import org.openide.util.ImageUtilities;
import sg.gde.rpg.editor.visual.dialoge.UIConversationNodeWidget;
import sg.gde.rpg.editor.visual.dialoge.InplaceTextEditor;
import sg.gde.rpg.gamebase.RPGGraphNode;
import sg.gde.rpg.gamebase.character.GameCharacter;
import sg.gde.rpg.gamebase.story.dialogue.Dialogue;

public class DialogueGraphSceneImpl extends GraphScene<RPGGraphNode, String> {

    private RPGGraphNode rootNode = null;
    private Dialogue dialogue;
    public static final Image NO_IMAGE = ImageUtilities.loadImage("sg/gde/rpg/icons/nodes/class.gif"); // NOI18N
    private LayerWidget mainLayer;
    private LayerWidget connectionLayer = new LayerWidget(this);
    private LayerWidget interractionLayer = new LayerWidget(this);
    private WidgetAction editorAction = ActionFactory.createInplaceEditorAction(new InplaceTextEditor());
    private GraphLayout<RPGGraphNode, String> graphLayout;
    private SceneLayout sceneGraphLayout;
    private WidgetAction moveAction = ActionFactory.createMoveAction();
    private WidgetAction mouseHoverAction = ActionFactory.createHoverAction(new NodeHoverProvider());
    private WidgetAction popupMenuAction = ActionFactory.createPopupMenuAction(new NodePopupMenuProvider());
    private WidgetAction connectAction = ActionFactory.createConnectAction(interractionLayer, new SceneConnectProvider());
    private WidgetAction reconnectAction = ActionFactory.createReconnectAction(new SceneReconnectProvider());
    private long nodeCounter = 0;
    private long edgeCounter = 0;
    
            
    public DialogueGraphSceneImpl() {
        dialogue = new Dialogue();
        mainLayer = new LayerWidget(this);
        addChild(mainLayer);
        addChild(connectionLayer);
        addChild(interractionLayer);

        // Setup action
        getActions().addAction(ActionFactory.createZoomAction());
        getActions().addAction(ActionFactory.createPanAction());
        getActions().addAction(ActionFactory.createAcceptAction(new AcceptProvider() {
            @Override
            public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
                Image dragImage = getImageFromTransferable(transferable);
                JComponent view = getView();
                Graphics2D g2 = (Graphics2D) view.getGraphics();
                Rectangle visRect = view.getVisibleRect();
                view.paintImmediately(visRect.x, visRect.y, visRect.width, visRect.height);
                g2.drawImage(dragImage,
                        AffineTransform.getTranslateInstance(point.getLocation().getX(),
                        point.getLocation().getY()),
                        null);
                return ConnectorState.ACCEPT;
            }

            @Override
            public void accept(Widget widget, Point point, Transferable transferable) {
                Image image = getImageFromTransferable(transferable);
                Widget w = DialogueGraphSceneImpl.this.addNode(new RPGGraphNode(image));
                w.setPreferredLocation(widget.convertLocalToScene(point));
            }
        }));
        rootNode = dialogue.getRootNode();
        attachNodeWidgetAt(rootNode, 100, 100);

    }

    private Image getImageFromTransferable(Transferable transferable) {
        Object o = null;
        try {
            o = transferable.getTransferData(DataFlavor.imageFlavor);
        } catch (IOException ex) {
        } catch (UnsupportedFlavorException ex) {
        }
        return o instanceof Image ? (Image) o : NO_IMAGE;
    }

    @Override
    protected Widget attachNodeWidget(RPGGraphNode node) {


        UIConversationNodeWidget widget = new UIConversationNodeWidget(this);
        widget.setAvatarImage(node.getImage());
        widget.setTitle(node.getCharacterName());
        //widget.getActions().addAction(moveAction);
        widget.getActions().addAction(mouseHoverAction);
        widget.getActions().addAction(popupMenuAction);
        widget.getActions().addAction(connectAction);
        mainLayer.addChild(widget);

        return widget;
    }

    protected Widget attachNodeWidgetAt(RPGGraphNode node, int x, int y) {
        Widget widget = attachNodeWidget(node);
        Point point = new Point(x, y);
        widget.setPreferredLocation(point);
        return widget;
    }

    protected Widget attachEdgeWidget(String edge) {
        ConnectionWidget connection = new ConnectionWidget(this);
        connection.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
        connection.setEndPointShape(PointShape.SQUARE_FILLED_BIG);
        connection.getActions().addAction(createObjectHoverAction());
        connection.getActions().addAction(createSelectAction());
        connection.getActions().addAction(reconnectAction);
        connectionLayer.addChild(connection);
        return connection;
    }

    protected void attachEdgeSourceAnchor(String edge, RPGGraphNode oldSourceNode, RPGGraphNode sourceNode) {
        Widget w = sourceNode != null ? findWidget(sourceNode) : null;
        ((ConnectionWidget) findWidget(edge)).setSourceAnchor(AnchorFactory.createRectangularAnchor(w));
    }

    protected void attachEdgeTargetAnchor(String edge, RPGGraphNode oldTargetNode, RPGGraphNode targetNode) {
        Widget w = targetNode != null ? findWidget(targetNode) : null;
        ((ConnectionWidget) findWidget(edge)).setTargetAnchor(AnchorFactory.createRectangularAnchor(w));
    }

    private class SceneCreateAction extends WidgetAction.Adapter {

        public WidgetAction.State mousePressed(Widget widget, WidgetAction.WidgetMouseEvent event) {
            if (event.getClickCount() == 1) {
                if (event.getButton() == MouseEvent.BUTTON1 || event.getButton() == MouseEvent.BUTTON2) {

                    //addNode("node" + nodeCounter++).setPreferredLocation(widget.convertLocalToScene(event.getPoint()));

                    return WidgetAction.State.CONSUMED;
                }
            }
            return WidgetAction.State.REJECTED;
        }
    }

    private class SceneConnectProvider implements ConnectProvider {

        private RPGGraphNode source = null;
        private RPGGraphNode target = null;

        public boolean isSourceWidget(Widget sourceWidget) {
            Object object = findObject(sourceWidget);
            source = isNode(object) ? (RPGGraphNode) object : null;
            return source != null;
        }

        public ConnectorState isTargetWidget(Widget sourceWidget, Widget targetWidget) {
            Object object = findObject(targetWidget);
            target = isNode(object) ? (RPGGraphNode) object : null;
            if (target != null) {
                return !source.equals(target) ? ConnectorState.ACCEPT : ConnectorState.REJECT_AND_STOP;
            }
            return object != null ? ConnectorState.REJECT_AND_STOP : ConnectorState.REJECT;
        }

        public boolean hasCustomTargetWidgetResolver(Scene scene) {
            return false;
        }

        public Widget resolveTargetWidget(Scene scene, Point sceneLocation) {
            return null;
        }

        public void createConnection(Widget sourceWidget, Widget targetWidget) {
            String edge = "edge" + edgeCounter++;
            addEdge(edge);
            setEdgeSource(edge, source);
            setEdgeTarget(edge, target);
        }
    }

    private class SceneReconnectProvider implements ReconnectProvider {

        String edge;
        RPGGraphNode originalNode;
        RPGGraphNode replacementNode;

        public void reconnectingStarted(ConnectionWidget connectionWidget, boolean reconnectingSource) {
        }

        public void reconnectingFinished(ConnectionWidget connectionWidget, boolean reconnectingSource) {
        }

        public boolean isSourceReconnectable(ConnectionWidget connectionWidget) {
            Object object = findObject(connectionWidget);
            edge = isEdge(object) ? (String) object : null;
            originalNode = edge != null ? getEdgeSource(edge) : null;
            return originalNode != null;
        }

        public boolean isTargetReconnectable(ConnectionWidget connectionWidget) {
            Object object = findObject(connectionWidget);
            edge = isEdge(object) ? (String) object : null;
            originalNode = edge != null ? getEdgeTarget(edge) : null;
            return originalNode != null;
        }

        public ConnectorState isReplacementWidget(ConnectionWidget connectionWidget, Widget replacementWidget, boolean reconnectingSource) {
            Object object = findObject(replacementWidget);
            replacementNode = isNode(object) ? (RPGGraphNode) object : null;
            if (replacementNode != null) {
                return ConnectorState.ACCEPT;
            }
            return object != null ? ConnectorState.REJECT_AND_STOP : ConnectorState.REJECT;
        }

        public boolean hasCustomReplacementWidgetResolver(Scene scene) {
            return false;
        }

        public Widget resolveReplacementWidget(Scene scene, Point sceneLocation) {
            return null;
        }

        public void reconnect(ConnectionWidget connectionWidget, Widget replacementWidget, boolean reconnectingSource) {
            if (replacementWidget == null) {
                removeEdge(edge);
            } else if (reconnectingSource) {
                //setEdgeSource(edge, replacementNode);
            } else {
                //setEdgeTarget(edge, replacementNode);
            }
        }
    }

    public void setupLayout() {
        graphLayout = GraphLayoutFactory.createTreeGraphLayout(100, 100, 50, 50, true);
        GraphLayoutSupport.setTreeGraphLayoutRootNode(graphLayout, rootNode);
        sceneGraphLayout = LayoutFactory.createSceneGraphLayout(this, graphLayout);

        getActions().addAction(ActionFactory.createEditAction(new EditProvider() {
            public void edit(Widget widget) {
                // new implementation
                sceneGraphLayout.invokeLayoutImmediately();
                // old implementation
//                new TreeGraphLayout<String, String> (TreeGraphLayoutTest.this, 100, 100, 50, 50, true).layout ("root");
            }
        }));
    }

    private static class NodeHoverProvider implements TwoStateHoverProvider {

        public void unsetHovering(Widget widget) {
            widget.setBackground(Color.WHITE);
        }

        public void setHovering(Widget widget) {
            widget.setBackground(Color.CYAN);
        }
    }

    private static class NodePopupMenuProvider implements PopupMenuProvider {

        public JPopupMenu getPopupMenu(Widget widget, Point localLocation) {
            JPopupMenu popupMenu = new JPopupMenu();
            popupMenu.add(new JMenuItem("Open "));
            return popupMenu;
        }
    }

    public void setDialogue(Dialogue dialogue) {
        this.dialogue = dialogue;
    }
}
