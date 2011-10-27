/*
 * Copyright (C) 2011 0xlab - http://0xlab.org/
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
 * Authored by Julian Chu <walkingice@0xlab.org>
 */

package org.zeroxlab.zeroxbenchmark;

import android.util.Log;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import org.zeroxlab.utils.BenchUtil;

public class ActivitySettings extends Activity implements View.OnClickListener {

    public final static String TAG = "Benchmark";
    private Context mContext;
    private Button mEdit;
    private TextView mPathView;
    private String mPath;
    private RadioListener mRadioListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initViews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View v) {
        if (v == mEdit) {
            alertInput();
        }
    }

    private void initViews() {
        setContentView(R.layout.settings);
        mRadioListener = new RadioListener();
        RadioButton radio_sdcard = (RadioButton) findViewById(R.id.radio_sdcard);
        RadioButton radio_custom = (RadioButton) findViewById(R.id.radio_custom);
        mPathView = (TextView) findViewById(R.id.path_selection);
        mEdit = (Button) findViewById(R.id.edit_dir);

        mEdit.setOnClickListener(this);

        radio_sdcard.setOnClickListener(mRadioListener);
        radio_custom.setOnClickListener(mRadioListener);

        radio_sdcard.setText(BenchUtil.DEFAULT_RESULT_DIR);

        /* Retrieve preference of path selection */
        int selection = BenchUtil.getResultSelection(this);
        if (selection == BenchUtil.RESULT_SELECTION_SDCARD) {
            radio_sdcard.performClick();
        } else if (selection == BenchUtil.RESULT_SELECTION_CUSTOM) {
            radio_custom.performClick();
        } else {
            Log.e(TAG, "Choosen an unknown radio button in Settings Activity");
        }

    }

    private void alertInput() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edit = new EditText(this);
        edit.setText(BenchUtil.getCustomDir(this));
        alert.setView(edit);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                BenchUtil.setCustomDir(mContext, edit.getText().toString());
                updateHint();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();
    }

    private void updateHint() {
        final int selection = BenchUtil.getResultSelection(this);
        if (selection == BenchUtil.RESULT_SELECTION_SDCARD) {
            mPath = BenchUtil.DEFAULT_RESULT_DIR;
        } else if (selection == BenchUtil.RESULT_SELECTION_CUSTOM) {
            mPath = BenchUtil.getCustomDir(mContext);
        } else {
            Log.e(TAG, "I don't understand what did you choose!");
        }

        mPathView.setText(mPath);
    }

    private class RadioListener implements View.OnClickListener {
        public void onClick(View v) {
            if (v.getId() == R.id.radio_sdcard) {
                BenchUtil.setResultSelection(mContext, BenchUtil.RESULT_SELECTION_SDCARD);
            } else if (v.getId() == R.id.radio_custom) {
                BenchUtil.setResultSelection(mContext, BenchUtil.RESULT_SELECTION_CUSTOM);
            } else {
                Log.e(TAG, "I don't know what did you click!");
                return;
            }

            updateHint();
        }
    }
}
