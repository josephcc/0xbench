/* 
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.zeroxlab.kubench;

import org.zeroxlab.benchmark.Tester;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import javax.microedition.khronos.egl.*;
import javax.microedition.khronos.opengles.*;


/**
 * Example of how to use OpenGL|ES in a custom view
 *
 */
public class GLSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder mHolder;
    private GLThread mGLThread;
    private GLWorld mWorld;
    private float mAngle;
    private GLSurfaceViewClient mClient;
    private Tester mTester;
    
    /**
     * The View constructor is a good place to allocate our OpenGL context
     */
    public GLSurfaceView(Context context, GLWorld world, Tester tester) {
        super(context);
    mTester = tester;
        mWorld = world;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, start our main acquisition thread.
        Log.d("GLSurfaceView", "surfaceCreated");
        mGLThread = new GLThread();
        mGLThread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // Surface will be destroyed when we return
        Log.d("GLSurfaceView", "surfaceDestroyed");
        mGLThread.requestExitAndWait();
        mGLThread = null;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // Surface size or format has changed. This should not happen in this
        // example.
        Log.d("GLSurfaceView", "surfaceChanged");
        mGLThread.onWindowResize(w, h);
    }
    
    public float getAngle() {
        return mAngle;
    }
    
    public void setAngle(float angle) {
        mAngle = angle;
    }
    
    public void setClient(GLSurfaceViewClient client) {
        mClient = client;
    }
 
    // ----------------------------------------------------------------------
    
    public interface GLSurfaceViewClient {
        public void animate();
    }
    
    public float getFramerate() {
        return mGLThread.getFramerate();
    }
    
    // ----------------------------------------------------------------------

    class GLThread extends Thread {
        private boolean mDone;
        private int mWidth;
        private int mHeight;
        private float mFramerate;

        GLThread() {
            super();
            mDone = false;
            mWidth = 0;
            mHeight = 0;
            mFramerate = 0.0f;
        }
    
        public float getFramerate() {
            return mFramerate;
        }
        
        @Override
        public void run() {
            /*
             * Get an EGL instance
             */
            EGL10 egl = (EGL10)EGLContext.getEGL();
            
            /*
             * Get to the default display.
             */
            EGLDisplay dpy = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);

            /*
             * We can now initialize EGL for that display
             */
            int[] version = new int[2];
            egl.eglInitialize(dpy, version);
            
            /*
             * Specify a configuration for our opengl session
             * and grab the first configuration that matches is
             */
            int[] configSpec = {
                    EGL10.EGL_DEPTH_SIZE,   16,
                    EGL10.EGL_NONE
            };
            EGLConfig[] configs = new EGLConfig[1];
            int[] num_config = new int[1];
            egl.eglChooseConfig(dpy, configSpec, configs, 1, num_config);
            EGLConfig config = configs[0];

            /* 
             * Create an OpenGL ES context. This must be done only once, an
             * OpenGL context is a somewhat heavy object.
             */
            EGLContext context = egl.eglCreateContext(dpy, config,
                    EGL10.EGL_NO_CONTEXT, null);

            /* 
             * Create an EGL surface we can render into.
             */
            EGLSurface surface = egl.eglCreateWindowSurface(dpy, config, mHolder, null);

            /*
             * Before we can issue GL commands, we need to make sure 
             * the context is current and bound to a surface.
             */
            egl.eglMakeCurrent(dpy, surface, surface, context);
            
            /*
             * Get to the appropriate GL interface.
             * This is simply done by casting the GL context to either
             * GL10 or GL11.
             */
            GL10 gl = (GL10)context.getGL();

           
            /*
             * Some one-time OpenGL initialization can be made here
             * probably based on features of this particular context
             */
             gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);


            // This is our main acquisition thread's loop, we go until
            // asked to quit.
             long starttime=0, stoptime=0, drawcount=0;
         long startTester = System.currentTimeMillis();
            while (!mDone) {
                // Update the asynchronous state (window size, key events)
                int w, h;
                synchronized(this) {
                    w = mWidth;
                    h = mHeight;
                }
                if (starttime == 0) {
                    starttime = System.currentTimeMillis();
                }

                /* draw a frame here */
                drawFrame(gl, w, h);

                /*
                 * Once we're done with GL, we need to call post()
                 */
                egl.eglSwapBuffers(dpy, surface);
        gl.glFinish();
        mTester.decreaseCounter();
        mDone = mTester.isTesterFinished();
                drawcount++;
                stoptime = System.currentTimeMillis();
                if (stoptime - starttime >= 1) {
                    mFramerate = (float)(1000 * drawcount)/(float)(stoptime - starttime);
                    drawcount = 0;
                }

                /*
                 * Always check for EGL_CONTEXT_LOST, which means the context
                 * and all associated data were lost (For instance because
                 * the device went to sleep). We need to quit immediately.
                 */
                if (egl.eglGetError() == EGL11.EGL_CONTEXT_LOST) {
                    // we lost the gpu, quit immediately
                    Context c = getContext();
                    if (c instanceof Activity) {
                        ((Activity)c).finish();
                    }
                }
            }
            
         long stopTester = System.currentTimeMillis();
            /*
             * clean-up everything...
             */
            egl.eglMakeCurrent(dpy,
                    EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            egl.eglDestroyContext(dpy, context);
            egl.eglDestroySurface(dpy, surface);
            egl.eglTerminate(dpy);
        mTester.finishTester(startTester, stopTester);
        }
        
        private void drawFrame(GL10 gl, int w, int h) {
            gl.glViewport(0, 0, w, h);
        
            /*
             * Set our projection matrix. This doesn't have to be done
             * each time we draw, but usually a new projection needs to be set
             * when the viewport is resized.
             */

            float ratio = (float)w / h;
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glFrustumf(-ratio, ratio, -1, 1, 2, 12);

            /*
             * By default, OpenGL enables features that improve quality
             * but reduce performance. One might want to tweak that
             * especially on software renderer.
             */
            gl.glDisable(GL10.GL_DITHER);
            gl.glActiveTexture(GL10.GL_TEXTURE0);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
            gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
            gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
            gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
            gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
            gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_REPLACE);

            /*
             * Usually, the first thing one might want to do is to clear
             * the screen. The most efficient way of doing this is to use
             * glClear(). However we must make sure to set the scissor
             * correctly first. The scissor is always specified in window
             * coordinates:
             */

            gl.glClearColor(0.5f,0.5f,0.5f,1);
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

            /*
             * Now we're ready to draw some 3D object
             */

            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            gl.glTranslatef(0, 0, -3.0f);
            gl.glScalef(0.5f, 0.5f, 0.5f);
            gl.glRotatef(mAngle,        0, 1, 0);
            gl.glRotatef(mAngle*0.25f,  1, 0, 0);

            gl.glColor4f(0.7f, 0.7f, 0.7f, 1.0f);
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            gl.glEnable(GL10.GL_CULL_FACE); 
            gl.glShadeModel(GL10.GL_SMOOTH);
            gl.glEnable(GL10.GL_DEPTH_TEST);   

            mWorld.draw(gl);

            if (mClient != null) {
                mClient.animate();
            }
        }

        public void onWindowResize(int w, int h) {
            synchronized(this) {
                mWidth = w;
                mHeight = h;
            }
        }
        
        public void requestExitAndWait() {
            // don't call this from GLThread thread or it a guaranteed
            // deadlock!
            mDone = true;
            try {
                join();
            } catch (InterruptedException ex) { }
        }
    }
}


