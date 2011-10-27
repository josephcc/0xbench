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

import org.zeroxlab.gc.GCBenchmark;

import android.app.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.widget.TextView;

public class TesterGC extends Tester {

    private TextView mTextView1;

    public static double time = 0.0;
    public static Handler mHandler;
    public static final int GUINOTIFIER = 0x1234;


    protected String getTag() {
        return "GC";
    }

    protected int sleepBeforeStart() {
        return 1000;
    }

    protected int sleepBetweenRound() {
        return 0;
    }

    protected void oneRound() {
        GCBenchmark.benchmark();
        decreaseCounter();
    }

    @Override
    protected boolean saveResult(Intent intent) {
        intent.putExtra(CaseGC.GCRESULT, GCBenchmark.out.toString());
        intent.putExtra(CaseGC.TIME, time);
        return true;
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gc);

        mTextView1 = (TextView) findViewById(R.id.myTextView1);

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case GUINOTIFIER:
                        mTextView1.setText(GCBenchmark.out);
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        startTester();
    }
}
