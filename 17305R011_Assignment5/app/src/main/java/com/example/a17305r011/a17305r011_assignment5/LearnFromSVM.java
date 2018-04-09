package com.example.a17305r011.a17305r011_assignment5;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import umich.cse.yctung.androidlibsvm.LibSVM;

/**
 * Created by root on 7/4/18.
 */

public class LearnFromSVM {

    public static Context context;

    public static void preprocessFile(String filePath) {
        String aDataRow = "";
        String aBuffer = "";

        String fileName = "train.csv";
        String testFileName = "test";
        File mydir = new File(context.getExternalFilesDir(null), fileName);   //new File(dirPath);
        File testDir = new File(context.getExternalFilesDir(null), testFileName);

        try {
            File myFile = new File(filePath);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(fIn));

            FileOutputStream outputStreamWriter = new FileOutputStream(mydir, true);
            FileOutputStream testOutputStreamWriter = new FileOutputStream(testDir, true);

            myReader.readLine();
            myReader.readLine();
            myReader.readLine();
            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer += aDataRow + "\n";
                String[] singleRow = aDataRow.split(",");
                outputStreamWriter.write((singleRow[6] + "," + singleRow[3] + "," + singleRow[4] + "," + singleRow[5] + "\n").getBytes());
                testOutputStreamWriter.write((singleRow[3] + "," + singleRow[4] + "," + singleRow[5] + "\n").getBytes());
            }
            Toast.makeText(context, "" + aBuffer.length() + "", Toast.LENGTH_SHORT).show();
            //Log.w("Final Datarow", );
            outputStreamWriter.close();
            myReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void applySVM(){

        LibSVM svm = new LibSVM();
        String systemPath = String.format("%s/", context.getExternalFilesDir(null).getAbsolutePath());
        String appFolderPath = systemPath; // your datasets folder

        String fileName = "train.csv";
        String scaledFileName = "Scalled_Train";
        String predictedFile = "test";
// NOTE the space between option parameters, which is important to
// keep the options consistent to the original LibSVM format
        svm.scale(appFolderPath + fileName, appFolderPath + scaledFileName);
        svm.train("-t 2 "/* svm kernel */ + appFolderPath + scaledFileName+" " + appFolderPath + "model");
        svm.predict(appFolderPath + predictedFile+" " + appFolderPath + "model " + appFolderPath + "result");

    }
}
