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

import jnt.scimark2.commandline;

import android.util.Log;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.widget.TextView;
import java.util.HashMap;
import java.util.ArrayList;

public class TesterScimark2 extends Tester {

    TextView mTextView;
    Bundle mInfo[];
    public final static String COMPOSITE    = "COMPOSITE";
    public final static String FFT          = "FTT";
    public final static String SOR          = "SOR";
    public final static String MONTECARLO   = "MONTECARLO";
    public final static String SPARSEMATMULT= "SPARSEMATMULT";
    public final static String LU           = "LU";

    protected String getTag() {
        return "Scimark2";
    }

    protected int sleepBeforeStart() {
        return 1000;
    }

    protected int sleepBetweenRound() {
        return 200;
    }

    protected void oneRound() {
        commandline.main(mInfo[mNow - 1]);
        decreaseCounter();
    }

    @Override
    protected boolean saveResult(Intent intent) {
        Bundle result = new Bundle();
        TesterScimark2.average(result, mInfo);
    
        intent.putExtra(CaseScimark2.LIN_RESULT, result);
        return true;
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int length = mRound;
        mInfo = new Bundle[length];
        for (int i = 0; i < length; i++) {
            mInfo[i] = new Bundle();
        }

        mTextView = new TextView(this);
        mTextView.setText("Running benchmark....");
        mTextView.setTextSize(mTextView.getTextSize() + 5);
        setContentView(mTextView);
        startTester();
    }

    public static void average(Bundle result, Bundle[] list) {
        if (result == null) {
            result = new Bundle();
        }

        if (list == null) {
            Log.i("Scimark2", "Array is null");
            return;
        }

        int length = list.length;
        double composite_total     = 0.0;
        double fft_total           = 0.0;
        double sor_total           = 0.0;
        double montecarlo_total    = 0.0;
        double sparsematmult_total = 0.0;
        double lu_total            = 0.0;

        HashMap<String, double[]> store = new HashMap<String, double[]>();
        store.put(COMPOSITE    , new double[length] );
        store.put(FFT          , new double[length] );
        store.put(SOR          , new double[length] );
        store.put(MONTECARLO   , new double[length] );
        store.put(SPARSEMATMULT, new double[length] );
        store.put(LU           , new double[length] );


        for (int i = 0; i < length; i ++) {
            Bundle info = list[i];

            if (info == null) {
                Log.i("Scimark2", "one item of array is null!");
                return;
            }

            composite_total     += info.getDouble(COMPOSITE    );
            fft_total           += info.getDouble(FFT          );
            sor_total           += info.getDouble(SOR          );
            montecarlo_total    += info.getDouble(MONTECARLO   );
            sparsematmult_total += info.getDouble(SPARSEMATMULT);
            lu_total            += info.getDouble(LU           );

            store.get(COMPOSITE    )[i] = info.getDouble(COMPOSITE    );
            store.get(FFT          )[i] = info.getDouble(FFT          );
            store.get(SOR          )[i] = info.getDouble(SOR          );
            store.get(MONTECARLO   )[i] = info.getDouble(MONTECARLO   );
            store.get(SPARSEMATMULT)[i] = info.getDouble(SPARSEMATMULT);
            store.get(LU           )[i] = info.getDouble(LU           );
        }

        result.putDouble(COMPOSITE    , composite_total    / length);
        result.putDouble(FFT          , fft_total          / length);
        result.putDouble(SOR          , sor_total          / length);
        result.putDouble(MONTECARLO   , montecarlo_total   / length);
        result.putDouble(SPARSEMATMULT, sparsematmult_total/ length);
        result.putDouble(LU           , lu_total           / length);

        result.putDoubleArray(COMPOSITE +"array"    , store.get(COMPOSITE    ) );
        result.putDoubleArray(FFT +"array"          , store.get(FFT          ) );  
        result.putDoubleArray(SOR +"array"          , store.get(SOR          ) ); 
        result.putDoubleArray(MONTECARLO +"array"   , store.get(MONTECARLO   ) );
        result.putDoubleArray(SPARSEMATMULT +"array", store.get(SPARSEMATMULT) );
        result.putDoubleArray(LU +"array"           , store.get(LU           ) );
    }

    public static String bundleToString(Bundle bundle) {
        String result = "";
        result += "\nComposite:\n  " + bundle.getDouble(COMPOSITE, 0.0);
        result += "\nFast Fourier Transform:\n  " + bundle.getDouble(FFT, 0.0);
        result += "\nJacobi Successive Over-relaxation:\n  " + bundle.getDouble(SOR, 0.0);
        result += "\nMonte Carlo integration:\n  " + bundle.getDouble(MONTECARLO, 0.0);
        result += "\nSparse matrix multiply:\n  " + bundle.getDouble(SPARSEMATMULT, 0.0);
        result += "\ndense LU matrix factorization:\n  " + bundle.getDouble(LU, 0.0);

        return result;
    }

    public static String bundleListToXML(Bundle[] mInfo) {

        int length = mInfo.length;
        double composite_total     = 0.0;
        double fft_total           = 0.0;
        double sor_total           = 0.0;
        double montecarlo_total    = 0.0;
        double sparsematmult_total = 0.0;
        double lu_total            = 0.0;

        for (int i = 0; i < length; i++) {
            Bundle info = mInfo[i];

            composite_total     += info.getDouble(COMPOSITE    );
            fft_total           += info.getDouble(FFT          );
            sor_total           += info.getDouble(SOR          );
            montecarlo_total    += info.getDouble(MONTECARLO   );
            sparsematmult_total += info.getDouble(SPARSEMATMULT);
            lu_total            += info.getDouble(LU           );
        }


        String benchName = "Scimark2";

        String result = "";
        
        if (composite_total != 0.0) {
            result += "<scenario";
            result += " benchmark=\"" + benchName + "-COMPOSITE\" unit=\"mflops\"";
            result += ">";
            for (int i = 0; i < length; i++) {
                result += "" + mInfo[i].getDouble(COMPOSITE) + " ";
            }
            result += "</scenario>";
        }

        if (fft_total != 0.0) {
            result += "<scenario";
            result += " benchmark=\"" + benchName + "-FFT\" unit=\"mflops\"";
            result += ">";
            for (int i = 0; i < length; i++) {
                result += "" + mInfo[i].getDouble(FFT) + " ";
            }
            result += "</scenario>";
        }

        if (sor_total != 0.0) {
            result += "<scenario";
            result += " benchmark=\"" + benchName + "-SOR\" unit=\"mflops\"";
            result += ">";
            for (int i = 0; i < length; i++) {
                result += "" + mInfo[i].getDouble(SOR) + " ";
            }
            result += "</scenario>";
        }

        if (montecarlo_total != 0.0) {
            result += "<scenario";
            result += " benchmark=\"" + benchName + "-MonteCarlo\" unit=\"mflops\"";
            result += ">";
            for (int i = 0; i < length; i++) {
                result += "" + mInfo[i].getDouble(MONTECARLO) + " ";
            }
            result += "</scenario>";
        }

        if (sparsematmult_total != 0.0) {
            result += "<scenario";
            result += " benchmark=\"" + benchName + "-SparseMatrixMult\" unit=\"mflops\"";
            result += ">";
            for (int i = 0; i < length; i++) {
                result += "" + mInfo[i].getDouble(SPARSEMATMULT) + " ";
            }
            result += "</scenario>";
        }

        if (lu_total != 0.0) {
            result += "<scenario";
            result += " benchmark=\"" + benchName + "-LU\" unit=\"mflops\"";
            result += ">";
            for (int i = 0; i < length; i++) {
                result += "" + mInfo[i].getDouble(LU) + " ";
            }
            result += "</scenario>";
        }

        return result;
    }
}

