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

import java.util.Date;
import java.util.ArrayList;

public class Scenario {
    String mName;
    String mType;
    String [] mTags;
    Date mTime;

    public ArrayList<Double> mResults;
    public String mStringResults;
    public boolean useStringResults;
    String mLog;

    public Scenario(String name, String type, String [] tags) {
        useStringResults = false;

        mName = name;
        mType = type;
        mTags = tags;

        mTime = new Date();
        mResults = new ArrayList<Double>();
    }

    public Scenario(String name, String type, String [] tags, boolean useString) {
        if (!useString) {
            useStringResults = false;
            mResults = new ArrayList<Double>();
        } else {
            useStringResults = true;
        }

        mName = name;
        mType = type;
        mTags = tags;

        mTime = new Date();
    }
}

