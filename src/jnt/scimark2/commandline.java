package jnt.scimark2;

import java.util.Properties;

import org.zeroxlab.benchmark.TesterScimark2;
import android.os.Bundle;

/**
    SciMark2: A Java numerical benchmark measuring performance
    of computational kernels for FFTs, Monte Carlo simulation,
    sparse matrix computations, Jacobi SOR, and dense LU matrix
    factorizations.  
*/


public class commandline
{

  /* Benchmark 5 kernels with individual Mflops.
     "results[0]" has the average Mflop rate.

  */


    public static String main(Bundle info) {
        // default to the (small) cache-contained version

        double min_time = Constants.RESOLUTION_DEFAULT;

        int FFT_size = Constants.FFT_SIZE;
        int SOR_size =  Constants.SOR_SIZE;
        int Sparse_size_M = Constants.SPARSE_SIZE_M;
        int Sparse_size_nz = Constants.SPARSE_SIZE_nz;
        int LU_size = Constants.LU_SIZE;

        // run the benchmark

        double res[] = new double[6];
        Random R = new Random(Constants.RANDOM_SEED);

        res[1] = kernel.measureFFT( FFT_size, min_time, R);
        res[2] = kernel.measureSOR( SOR_size, min_time, R);
        res[3] = kernel.measureMonteCarlo(min_time, R);
        res[4] = kernel.measureSparseMatmult( Sparse_size_M, 
                    Sparse_size_nz, min_time, R);
        res[5] = kernel.measureLU( LU_size, min_time, R);


        res[0] = (res[1] + res[2] + res[3] + res[4] + res[5]) / 5;


        // print out results



        System.out.println();
        System.out.println("SciMark 2.0a");
        System.out.println();

        System.out.println("Composite Score: " + res[0]);
        info.putDouble(TesterScimark2.COMPOSITE, res[0]);

        System.out.print("FFT ("+FFT_size+"): ");
        if (res[1]==0.0)
            System.out.println(" ERROR, INVALID NUMERICAL RESULT!");
        else
            System.out.println(res[1]);
        info.putDouble(TesterScimark2.FFT, res[1]);

        System.out.println("SOR ("+SOR_size+"x"+ SOR_size+"): "
                + "  " + res[2]);
        info.putDouble(TesterScimark2.SOR, res[2]);

        System.out.println("Monte Carlo : " + res[3]);
        info.putDouble(TesterScimark2.MONTECARLO, res[3]);

        System.out.println("Sparse matmult (N="+ Sparse_size_M+ 
                ", nz=" + Sparse_size_nz + "): " + res[4]);
        info.putDouble(TesterScimark2.SPARSEMATMULT, res[4]);

        System.out.print("LU (" + LU_size + "x" + LU_size + "): ");
        if (res[5]==0.0)
            System.out.println(" ERROR, INVALID NUMERICAL RESULT!");
        else
            System.out.println(res[5]);
        info.putDouble(TesterScimark2.LU, res[5]);

        return "";

    }
  
}
