/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.stac.app;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import java.awt.Canvas;
import java.util.ArrayList;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class JME3DCanvas extends SimpleApplication {

    int layer = 10;
    int numOfPoint = 4;
    ArrayList<Vector3f> initShape = new ArrayList();
    Vector3f direction;

    public Canvas createAndStartCanvas(int width, int height) {
        AppSettings settings = new AppSettings(true);
        settings.setWidth(width);
        settings.setHeight(height);

        setPauseOnLostFocus(true);
        setSettings(settings);
        createCanvas();
        startCanvas(true);

        JmeCanvasContext context = (JmeCanvasContext) getContext();
        Canvas canvas = context.getCanvas();
        canvas.setSize(settings.getWidth(), settings.getHeight());

        return canvas;
    }

    public void initInput() {
        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);

    }

    public void initModel() {
        Spatial headModel = assetManager.loadModel("Models/Head1/FemaleVersion3.j3o");
        rootNode.attachChild(headModel);
        headModel.setLocalScale(3f);
        // Test multiple listeners per mapping
        //inputManager.addListener(actionListener, "changeTexture");
        rootNode.addLight(new PointLight());
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(40f);
        setDisplayStatView(false);
        viewPort.setBackgroundColor(ColorRGBA.DarkGray);
        
        initInput();
        initModel();
    }
}
