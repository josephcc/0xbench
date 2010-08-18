/*
 * Copyright (C) 2008-2009 Koansin Tan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Origin: http://code.google.com/p/android-utah-teapot/
 */

package org.itri.teapot;

import org.zeroxlab.benchmark.Tester;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

class MyGLSurfaceView extends GLSurfaceView {
    private TeapotRenderer mMyRenderer;
    private Tester mTester;
    private float xspeed;
    private float yspeed;
    private float zspeed;

    public MyGLSurfaceView(Context context) {
        super(context);
    }

    @Override 
    public void setRenderer(GLSurfaceView.Renderer renderer) {
        mMyRenderer = (TeapotRenderer) renderer;
        super.setRenderer(renderer);
    }
}
