/*
 *  Copyright (c) 2009-2010 jMonkeyEngine
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are
 *  met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 *  TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *  PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jme3.gde.cinematics.controls;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 *
 * @author tomas
 */
public class MovementControl extends AbstractControl {

    private Vector3f traveled = new Vector3f();
    private Vector3f delta = new Vector3f();
    private Vector3f start;
    private Vector3f end;
    private float time;
    private float elapsedTime = 0.0f;

    public MovementControl(Spatial spatial, Vector3f start, Vector3f end, float time) {
        super();
        this.start = start;
        this.end = end;
        this.time = time;

        end.subtract(start, delta);
    }

    protected float getElapsedFraction() {
        return elapsedTime / time;
    }

    @Override
    protected void controlUpdate(float tpf) {
        System.out.println("Update " + tpf + " elapsed: " + elapsedTime + " time: " + time);
        elapsedTime += tpf;

        if (elapsedTime > time) {
            elapsedTime = time;
            setEnabled(false);
        }

        spatial.setLocalTranslation(-traveled.x, -traveled.y, -traveled.z);
        delta.mult(getElapsedFraction(), traveled);
        spatial.setLocalTranslation(traveled);
        System.out.println("Traveled: " + traveled + " of " + delta);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return null;
    }
}
