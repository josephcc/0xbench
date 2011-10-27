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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.content.*;
import java.nio.*;

import java.util.LinkedList;

/* Construct a basic UI */
public class Report extends Activity implements View.OnClickListener {

    public final static String TAG = "Repord";
    public final static String REPORT = "REPORT";
    public final static String XML = "XML";
    public final static String AUTOUPLOAD = "AUTOUPLOAD";
    private TextView mTextView;

    private Button mUpload;
    private Button mBack;
    private String mXMLResult;
    boolean mAutoUpload = false;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.report);

        mTextView = (TextView)findViewById(R.id.report_text);

        mUpload = (Button)findViewById(R.id.btn_upload);
        mUpload.setOnClickListener(this);

        mBack = (Button)findViewById(R.id.btn_back);
        mBack.setOnClickListener(this);

        Intent intent = getIntent();
        String report = intent.getStringExtra(REPORT);
        mXMLResult = intent.getStringExtra(XML);
        mAutoUpload = intent.getBooleanExtra(AUTOUPLOAD, false);

        if (report == null || report.equals("")) {
            mTextView.setText("oooops...report not found");
        } else {
            mTextView.setText(report);
        }

        if (mXMLResult == null) {
            mUpload.setEnabled(false);
        }

        if (mAutoUpload) {
            onClick(mUpload);
        }
    }

    public void onClick(View v) {
        if (v == mBack) {
            finish();
        } else if (v == mUpload) {
            Intent intent = new Intent();
            intent.putExtra(Upload.XML, mXMLResult);
            if (mAutoUpload) {
                intent.putExtra(Upload.AUTOUPLOAD, true);
            }
            intent.setClassName(Upload.packageName(), Upload.fullClassName());
            
            startActivity(intent);
        }
    }

    public static String fullClassName() {
        return "org.zeroxlab.zeroxbenchmark.Report";
    }

    public static String packageName() {
        return "org.zeroxlab.zeroxbenchmark";
    }
}
