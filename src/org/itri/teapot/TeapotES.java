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

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

//public class TeapotES extends Tester implements SensorEventListener {
public class TeapotES extends Tester {

    public final static String FullName = "org.itri.teapot.TeapotES";

    /** Our own OpenGL View overridden */
    private MyGLSurfaceView mGLSurfaceView;

    @Override
    public String getTag() {
        return "Teapot";
    }

    @Override
    public int sleepBeforeStart() {
        return 1200; // 1.2 second
    }

    @Override
    public int sleepBetweenRound() {
        return 1500; // 15 ms
    }

    @Override
    protected void oneRound() {
//        mGLSurfaceView.requestRender();
    }
    
    public static final int ACCEL_ID = Menu.FIRST;
    public static final int COMPASS_ID = Menu.FIRST + 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLSurfaceView = new MyGLSurfaceView(this);
        mGLSurfaceView.setRenderer(new TeapotRenderer(5,1,1,this));
        setContentView(mGLSurfaceView);
        startTester();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }
    
}
