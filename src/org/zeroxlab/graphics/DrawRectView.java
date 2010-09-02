/*
 * Copyright (C) 2010 0xlab - http://0xlab.org/
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

package org.zeroxlab.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;


class DrawRectView extends SurfaceView {

    class ColoredRect {
        public Rect mRect;
        public int mColor;
        ColoredRect(int color, int left, int top, int right, int bottom) {
            this.mRect = new Rect(left, top, right, bottom);
            this.mColor = color;
        }
    }

    private SurfaceHolder mSurfaceHolder;
    private ArrayList<ColoredRect> rectengleList = new ArrayList<ColoredRect>();

    protected void doDraw() {
        Canvas canvas = mSurfaceHolder.lockCanvas();
        generateNewRect();
        drawAll(canvas);
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    private void drawAll(Canvas canvas) {
        for(ColoredRect cr : rectengleList) {
            Paint p = new Paint();
            p.setAntiAlias(false);
            p.setStyle(Paint.Style.FILL);
            p.setColor(cr.mColor);

            canvas.drawRect(cr.mRect, p);
        }
    }

    private void generateNewRect() {
        Random mRandom = new Random();
        int height = getHeight();
        int width  = getWidth();

        int cx = (int)((mRandom.nextInt() % (width*0.8) ) + (width*0.1));
        int cy = (int)((mRandom.nextInt() % (height*0.8) ) + (height*0.1));
        int hw = (int)(mRandom.nextInt() % (width*0.4) + width*0.2)/2;
        int hh = (int)(mRandom.nextInt() % (height*0.4) + height*0.2)/2;

        int color = (0x00252525 | mRandom.nextInt() ) & 0x00FFFFFF | 0x77000000; 

        rectengleList.add(new ColoredRect(color, cx-hw, cy-hh, cx+hw, cy+hh));
    }

    public DrawRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSurfaceHolder = getHolder();
    }
}

