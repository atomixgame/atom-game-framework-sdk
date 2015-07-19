/*
 
 */
package com.jme3.gde.gui.extra.xam.model;

import javax.xml.namespace.QName;
import org.netbeans.modules.xml.xam.dom.ComponentFactory;

/**
 * Factory to create components in the Nifty domain. 
 * <p>
 * It has both methods to create domain components generically from a 
 * given DOM node and one specific create method for each of the defined 
 * component interfaces in the Nifty domain. 
 * The generic create method is used during reading in from document. 
 * The multiple specific create methods are for specific UI client code.
 *
 * @author cuongnguyen
 */
// Generated by XAM AutoGen Tool v0.2
public interface NiftyComponentFactory extends ComponentFactory<NiftyComponent>  {

    /**
     * Creates a domain component generically.
     * 
     * @param context parent component
     * @param qName   QName of the component being created
     *
     * @return the generic domain component being created
     */
    NiftyComponent create(NiftyComponent context, QName qName);
    
    // The following are specific create method for each of the defined 
    // component interfaces.

    ElementType createElementType();
    LayerType createLayerType();
    PanelType createPanelType();
    ImageType createImageType();
    TextType createTextType();
    ControlDefinition createControlDefinition();
    ControlType createControlType();
    UseStylesType createUseStylesType();
    UseControlsType createUseControlsType();
    RegisterSoundType createRegisterSoundType();
    RegisterEffectType createRegisterEffectType();
    RegisterMusicType createRegisterMusicType();
    RegisterMouseCursorType createRegisterMouseCursorType();
    ResourceBundleType createResourceBundleType();
    AttributesType createAttributesType();
    InteractType createInteractType();
    EffectValueType createEffectValueType();
    SingleEffectTypeBase createSingleEffectTypeBase();
    SingleEffectType createSingleEffectType();
    SingleEffectTypeOnHover createSingleEffectTypeOnHover();
    OnHoverType createOnHoverType();
    SingleEffectTypeHover createSingleEffectTypeHover();
    EffectsType createEffectsType();
    StyleType createStyleType();
    PopupType createPopupType();
    ScreenType createScreenType();
    NiftyType createNiftyType();
}