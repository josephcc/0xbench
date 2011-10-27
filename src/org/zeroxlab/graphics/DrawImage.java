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

import org.zeroxlab.zeroxbenchmark.R;

import org.zeroxlab.zeroxbenchmark.Tester;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.os.Bundle;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.zeroxlab.graphics.DrawImageView;

public class DrawImage extends Tester {
    /** Called when the activity is first created. */

    private DrawImageView mView;

    public String getTag() {
        return "DrawImage";
    }

    public int sleepBeforeStart() {
        return 1000;
    }

    public int sleepBetweenRound() {
        return 0;
    }

    public void oneRound() {
        mView.doDraw();
        decreaseCounter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);

        mView = (DrawImageView) findViewById(R.id.image);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        mView.setImage(bmp);

        startTester();
    }
}

