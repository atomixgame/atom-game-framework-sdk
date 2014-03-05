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
package com.jme3.gde.cinematics.curves;

import java.util.TreeSet;

/**
 * Defines a how a value changes over time. Non linear curves will be defined
 * by lookup tables calculated externally, intermediate values will be interpolated.
 * 
 * Subclasses must only define the linear interpolation between values. The
 * rest should be taken care of.
 *
 * @author tomas
 */
public abstract class InterpolationCurve<T> {

    protected T start;
    protected T end;
    protected float startTime;
    protected float endTime;

    public static class Instant<V> implements Comparable<Instant> {

        public float time;
        public V value;

        public Instant(float time, V value) {
            this.time = time;
            this.value = value;
        }

        @Override
        public int compareTo(Instant o) {
            if (o.time > time) {
                return 1;
            } else if (o.time == time) {
                return 0;
            } else {
                return -1;
            }

        }
    }
    protected TreeSet<Instant<T>> data = new TreeSet<Instant<T>>();

    public InterpolationCurve() {
    }

    public InterpolationCurve(T start, T end, float startTime, float endTime) {
        this.start = start;
        this.end = end;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public abstract Instant<T> interpolate(Instant<T> a, Instant<T> b, float time);

    public TreeSet<Instant<T>> getData() {
        return data;
    }

    public void setData(TreeSet<Instant<T>> data) {
        this.data = data;
    }

    public float getDuration() {
        return endTime - startTime;
    }

    public T getEnd() {
        return end;
    }

    public void setEnd(T end) {
        this.end = end;
    }

    public float getEndTime() {
        return endTime;
    }

    public void setEndTime(float endTime) {
        this.endTime = endTime;
    }

    public T getStart() {
        return start;
    }

    public void setStart(T start) {
        this.start = start;
    }

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }
}
