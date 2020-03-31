package com.example.newsapp.helper;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class OfflineStore {

    static final int READ_BLOCK_SIZE = 100;

    public static void writeOnFile(String response, String fileName, FragmentActivity activity) {
        try {
            FileOutputStream fileout = activity.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(response);
            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(String fileName, FragmentActivity activity) {
        //reading text from file
        try {
            FileInputStream fileIn = activity.openFileInput(fileName);
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";
            int charRead;

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                // char to string conversion
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                s += readString;
            }
            InputRead.close();
            return s;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
