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

package org.zeroxlab.graphics;

import org.zeroxlab.benchmark.Tester;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.os.Bundle;
import android.view.View;

public class DrawCircle extends Tester {
    /** Called when the activity is first created. */

    SampleView mView;

    public String getTag() {
        return "DrawCircle";
    }

    public int sleepBeforeStart() {
        return 1000;
    }

    public int sleepBetweenRound() {
        return 0;
    }

    public void oneRound() {
        mView.postInvalidate();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = new SampleView(this);
        setContentView(mView);
    }

    private class SampleView extends View {
        private Paint[] mPaints;
        private Paint mFramePaint;

        private float mSweep;
        private int mBigIndex;

        private final float SWEEP_INC = 2;
        private int counter = 0;
        int i, row;
        long current;
        long last;

        public SampleView(Context context) {
            super(context);

            mPaints = new Paint[4];

            mPaints[0] = new Paint();
            mPaints[0].setAntiAlias(true);
            mPaints[0].setStyle(Paint.Style.FILL);
            mPaints[0].setColor(0x88FF0000);

            mPaints[1] = new Paint(mPaints[0]);
            mPaints[1].setColor(0x8800FF00);

            mPaints[2] = new Paint(mPaints[0]);
            mPaints[2].setStyle(Paint.Style.STROKE);
            mPaints[2].setStrokeWidth(4);
            mPaints[2].setColor(0x880000FF);

            mPaints[3] = new Paint(mPaints[2]);
            mPaints[3].setColor(0x88888888);

            mFramePaint = new Paint();
            mFramePaint.setAntiAlias(true);
            mFramePaint.setTextSize(40);
            mFramePaint.setStyle(Paint.Style.STROKE);
            mFramePaint.setStrokeWidth(0);
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
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);

            canvas.drawCircle(160f, 150f, 120f, mPaints[mBigIndex]);
            canvas.drawText(counter + "th time", 30, 160, mFramePaint);
            canvas.drawText((current - last) + "ms", 30, 200, mFramePaint);

            for (row = 0; row < 8; row++) {
                for (i = 0; i < 4; i++) {
                    canvas.drawCircle(40.0f + i * 80, 40.0f + row * 60, mSweep,
                            mPaints[i]);
                }
            }

            mSweep += SWEEP_INC;
            if (mSweep > 80) {
                mSweep -= 80;
                mBigIndex = (mBigIndex + 1) % 4;
                counter++;
                last = current;
                current = System.currentTimeMillis();

            }

            invalidate();
            decreaseCounter();
        }
    }
}
