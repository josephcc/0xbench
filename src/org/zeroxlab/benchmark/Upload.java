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

import org.zeroxlab.zeroxbenchmark.MicroBenchmark;

import android.util.Log;
import android.content.SharedPreferences;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import android.view.View;

import android.text.TextWatcher;
import android.text.Editable;

import android.app.Dialog;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.os.SystemClock;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;

import java.util.HashSet;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.JsResult;
import android.graphics.Bitmap;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;


public class Upload extends Activity implements View.OnClickListener {

    public final static String TAG = "Upload";
    public final static String XML = "XML";
    public final static String AUTOUPLOAD = "AUTOUPLOAD";

    public final static String mMobileLoginUrl = "http://0xbenchmark.appspot.com/mobileLogin";

    EditText mBenchName;
    EditText mEmail;
    EditText mAPIKey;
    Button mLoginGoogle;
    Button mSend;
    CheckBox mLogin;

    String mURL;
    String mXML;
    String mFailMsg;

    String mHash;
    HashSet<String> mHashSet = new HashSet<String>();

    MicroBenchmark mb;

    Handler mUploadHandler;
    Handler mLoginHandler;

    boolean mLogedin = false;
    boolean mAutoUpload = false;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.upload);

        mLogin = (CheckBox)findViewById(R.id.login);
        mLogin.setChecked(false);
        mLogin.setOnClickListener(this);

        // prevent scrollview from scrolling to middle initially
        // (caused by default focus to EditText)
        mLogin.setFocusableInTouchMode(true);
        mLogin.requestFocus();


        mBenchName = (EditText)findViewById(R.id.benchName);
        mEmail     = (EditText)findViewById(R.id.email);
        mAPIKey    = (EditText)findViewById(R.id.api);
        mLoginGoogle    = (Button)findViewById(R.id.login_google);
        mLoginGoogle.setOnClickListener(this);
        mBenchName.setEnabled(false);
        mEmail.setEnabled(false);
        mAPIKey.setEnabled(false);
        mLoginGoogle.setEnabled(false);

        mSend      = (Button)findViewById(R.id.send);
        mSend.setOnClickListener(this);

        Intent intent = getIntent();
        mXML = intent.getStringExtra(XML);
        mAutoUpload = intent.getBooleanExtra(AUTOUPLOAD, false);

        mUploadHandler = new Handler() {
            public void handleMessage(Message msg) {
                int state = msg.getData().getInt(MicroBenchmark.STATE);
                if (state != MicroBenchmark.RUNNING) {
                    try {
                        dismissDialog(0);
                        removeDialog(0);
                    } catch (Exception e) {
                    }
                    if (state == MicroBenchmark.DONE) {
                        showDialog(3);
                        showDialog(1);
                        mHashSet.add(mHash);
                    }
                    else {
                        showDialog(2);
                    }
                    Log.e(TAG, msg.getData().getString(MicroBenchmark.MSG));
                }
            }
        };


        mLoginHandler = new Handler() {
            public void handleMessage(Message msg) {
                dismissDialog(5);
            }
        };

        if (mAutoUpload) {
            onClick(mSend);
        }
    }

    private String trimTail(String text) {
        int index;
        if (text == null) {
            return text;
        }
        index = text.length() -1;
        while (text.charAt(index) == ' ') {
            if (--index < 0) {
                return "";
            }
        }
        return text.substring(0, index + 1);
    }

    public void onClick(View v) {
        Log.i(TAG, "onclick listener");
        if (v == mSend) {
            StringBuffer _mXML;
            int _index;
            String attr;

            String benchName = getString(R.string.default_benchname);
            String apiKey = getString(R.string.default_api);
            String eMail = getString(R.string.default_email);
            if (mLogin.isChecked()) {
                benchName = mBenchName.getText().toString();
                benchName = trimTail(benchName);
                apiKey = mAPIKey.getText().toString();
                eMail = mEmail.getText().toString();
            }

            String versionName = "";
            int versionCode = 0;
            int flag = 0;
            try {
                PackageInfo pinfo = getPackageManager().getPackageInfo("org.zeroxlab.zeroxbenchmark", flag);
                versionCode = pinfo.versionCode;
                versionName = pinfo.versionName;
            }
            catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "PackageManager.NameNotFoundException");
            }

            attr = "";
            attr += " BenchVersionCode=\"" + String.valueOf(versionCode) + "\"";
            attr += " BenchVersionName=\"" + versionName + "\"";
            attr += " apiKey=\"" + apiKey + "\"";
            attr += " benchmark=\"" + benchName + "\"";
            _mXML = new StringBuffer(mXML);
            _index = _mXML.indexOf("result") + 6;
            _mXML.insert(_index, attr);
            Log.e(TAG, _mXML.toString());

            mURL = "http://" + getString(R.string.default_appspot) + ".appspot.com:80/run/";
            mb = new MicroBenchmark(_mXML.toString(), mURL, apiKey, benchName, mUploadHandler) ;
            // this is not really a hash
            mHash = apiKey + benchName;
            if (!mHashSet.contains(mHash)){
                showDialog(0);
                mb.start();
            } else {
                showDialog(4);
            }
        } else if (v == mLogin) {
            if (mLogin.isChecked()) {
                mBenchName.setEnabled(true);
                mEmail.setEnabled(true);
                mAPIKey.setEnabled(true);
                mLoginGoogle.setEnabled(true);
            } else {
                mBenchName.setEnabled(false);
                mEmail.setEnabled(false);
                mAPIKey.setEnabled(false);
                mLoginGoogle.setEnabled(false);
            }
        } else if (v == mLoginGoogle) {
            showDialog(5);
        }

    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case (0):
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("Uploading, please wait...");
                return dialog;
            case (1):
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Upload complete.")
                       .setCancelable(false)
                       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                           }
                       });
                return builder.create();
            case (2):
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setMessage("Upload failed.")
                       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                           }
                       });
                return builder2.create();
            case (3):
                String url = "http://" + getString(R.string.default_appspot) + ".appspot.com/";

                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                builder3.setMessage( "Please goto " + url + " for results" )
                        .setTitle("Result URL")
                        .setPositiveButton("OK", null)
                ;
                return builder3.create();
            case (4):
                AlertDialog.Builder builder4 = new AlertDialog.Builder(this);
                builder4.setMessage( "You have already uploaded results to this location." )
                        .setTitle("Error")
                        .setPositiveButton("OK", null)
                ;
                return builder4.create();
            case (5): // webview
                Dialog mWebDialog = new Dialog(this);
                mWebDialog.setContentView(R.layout.login_dialog);
                mWebDialog.setTitle("Login to Google");

                WebView mWebView = (WebView) mWebDialog.findViewById(R.id.web_view);
                mWebView.setWebViewClient(new MyWebViewClient());
                mWebView.setWebChromeClient(new MyWebChromeClient());

                WebSettings webSettings = mWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setSupportZoom(false);

                mWebView.loadUrl(mMobileLoginUrl);
                return mWebDialog;

            case (6):
                ProgressDialog dialog2 = new ProgressDialog(this);
                dialog2.setMessage("Connecting, please wait...");
                return dialog2;

            default:
                return null;
        }
    }

    final class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.e(TAG, message);
            String [] values = message.split("\t");
            if (values.length == 3 && values[0].equals("returnValue")) {
                mEmail.setText(values[1]);
                mAPIKey.setText(values[2]);
                mLoginHandler.sendMessage(new Message());
            }
            result.confirm();
            return true;
        }
    }

    final class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted (WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showDialog(6);
        }
        @Override
        public void onPageFinished (WebView view, String url) {
            super.onPageFinished(view, url);
            dismissDialog(6);
            removeDialog(6);
            dismissDialog(5);
            showDialog(5);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getPreferences(0); 
        String restoredText;
        restoredText = prefs.getString("mBenchName", null);
        if (restoredText != null) 
            mBenchName.setText(restoredText, TextView.BufferType.EDITABLE);
        restoredText = prefs.getString("mEmail", null);
        if (restoredText != null) 
            mEmail.setText(restoredText, TextView.BufferType.EDITABLE);
        restoredText = prefs.getString("mAPIKey", null);
        if (restoredText != null) 
            mAPIKey.setText(restoredText, TextView.BufferType.EDITABLE);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = getPreferences(0).edit();
        editor.putString("mBenchName",mBenchName.getText().toString());
        editor.putString("mEmail",mEmail.getText().toString());
        editor.putString("mAPIKey",mAPIKey.getText().toString());
        editor.commit();
    }

    public static String fullClassName() {
        return "org.zeroxlab.zeroxbenchmark.Upload";
    }

    public static String packageName() {
        return "org.zeroxlab.zeroxbenchmark";
    }

}
