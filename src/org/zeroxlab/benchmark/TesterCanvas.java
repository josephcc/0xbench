/*
 * Copyright (C) 2010 0xlab - http://0xlab.org/
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

package org.zeroxlab.zeroxbenchmark;

import android.util.Log;

import android.os.SystemClock;

import android.app.Activity;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.graphics.Canvas;
import android.os.*;

import java.util.Random;
import android.graphics.Color;

public class TesterCanvas extends Tester {
    public final String TAG = "TesterCanvas";
    public final static String PACKAGE = "org.zeroxlab.zeroxbenchmark";
    MyView mView;

    public String getTag() {
        return TAG;
    }

    public static String getPackage() {
        return PACKAGE;
    }

    public static String getFullClassName() {
        return getPackage() + ".TesterCanvas";
    }

    public int sleepBetweenRound() {
        return 0;
    }
    public int sleepBeforeStart() {
        return 1000;
    }

    public void oneRound() {
        mView.postInvalidate();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mView = new MyView(this);
        setContentView(mView);
    }

    class MyView extends View {
        int i = 0;
        Random mRandom;

        MyView(Context context) {
            super(context);
            mRandom = new Random();
        }

        @Override
        protected void onWindowVisibilityChanged(int visibility) {
            super.onWindowVisibilityChanged(visibility);
            if (visibility != View.VISIBLE) {
                return;
            }

            startTester();
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int r = (0x00151515| mRandom.nextInt() ) | Color.BLACK;
            canvas.drawRGB(r, r, r);
            decreaseCounter();
        }
    }
}
