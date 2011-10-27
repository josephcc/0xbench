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

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;
import android.os.Handler;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Message;

/* code adapted from Caliper Project */

class MicroBenchmark extends Thread {
    final static int FAILED = -1;
    final static int DONE = 0;
    final static int RUNNING = 1;

    final static String STATE = "STATE";
    final static String MSG = "MSG";
    final static String TAG = "MicroBenchmarkThread";

    Handler mHandler;

    String xml;
    String postUrl;
    String apiKey;
    String benchmarkName;

    MicroBenchmark(String _xml, String _postUrl, String _apiKey, String _benchmarkName, Handler h) {
        xml = _xml;
        postUrl = _postUrl;
        apiKey = _apiKey;
        benchmarkName = _benchmarkName;
        mHandler = h;
    }

    private void updateState(int state, String info) {
        Bundle b = new Bundle();
        b.putInt(STATE, state);
        b.putString(MSG, info);
        Message msg = mHandler.obtainMessage();
        msg.setData(b);
        mHandler.sendMessage(msg);

        Log.e(TAG, "set state: " + state);
    }

    private void updateState(int state) {
        updateState(state, "");
    }

    public void upload() {
        updateState(RUNNING);
        try {
            URL url = new URL(postUrl + apiKey + "/" + benchmarkName);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);

            OutputStream post = urlConnection.getOutputStream();
            Log.e(TAG, xml);
            post.write(xml.getBytes());

            int responseCode = urlConnection.getResponseCode();
            Log.e(TAG, ""+responseCode);

            if (responseCode != 200) {
                updateState(FAILED, "Connection failed with response code " + responseCode);
                return;
            }
        } catch (IOException e) {
            updateState(FAILED, e.toString());
            return;
        }
        updateState(DONE);

    }

    public void run() {
        upload();
    }
}

