/*
 * Copyright (C) 2010 0xlab - http://0xlab.org/
 * Authored by: Julian Chu <walkingice@0xlab.org>
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
 */

package com.nea.nehe.lesson08;

import org.zeroxlab.benchmark.Tester;

import android.app.Activity;
import android.os.Bundle;

/**
 * The initial Android Activity, setting and initiating
 * the OpenGL ES Renderer Class @see Lesson08.java
 * 
 * @author Savas Ziplies (nea/INsanityDesign)
 */
public class Run extends Tester {

    public final static String FullName = "com.nea.nehe.lesson08.Run";

    /** Our own OpenGL View overridden */
    private Lesson08 lesson08;

    @Override
    public String getTag() {
        return "Nehe08";
    }

    @Override
    public int sleepBeforeStart() {
        return 1200; // 1.2 second
    }

    @Override
    public int sleepBetweenRound() {
        return 0; 
    }

    @Override
    protected void oneRound() {
//        lesson08.requestRender();
    }

    /**
     * Initiate our @see Lesson08.java,
     * which is GLSurfaceView and Renderer
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Initiate our Lesson with this Activity Context handed over
        lesson08 = new Lesson08(this);
        lesson08.setSpeedAndTester(1, 1, this);
        //Set the lesson as View to the Activity
        setContentView(lesson08);
        startTester();
    }

    /**
     * Remember to resume our Lesson
     */
    @Override
    protected void onResume() {
        super.onResume();
        lesson08.onResume();
    }

    /**
     * Also pause our Lesson
     */
    @Override
    protected void onPause() {
        super.onPause();
        lesson08.onPause();
    }

}

