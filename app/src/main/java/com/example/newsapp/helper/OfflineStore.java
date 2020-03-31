package com.example.newsapp.helper;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class OfflineStore {

    static final int READ_BLOCK_SIZE = 100;

    public static final String BOOKMARKS_SEPARATOR = "~A~";

    public static void writeOnFile(String response, String fileName, FragmentActivity activity) {
        try {
            FileOutputStream fileOutput = activity.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutput);
            outputWriter.write(response);
            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void appendOnFile(String response, String fileName, FragmentActivity activity) {
        try {
            FileOutputStream fileOutput = activity.openFileOutput(fileName, Context.MODE_APPEND);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutput);
            outputWriter.append(response);
            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(String fileName, FragmentActivity activity) {
        //reading text from file
        try {
            FileInputStream fileInput = activity.openFileInput(fileName);
            InputStreamReader inputReader = new InputStreamReader(fileInput);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";
            int charRead;

            while ((charRead = inputReader.read(inputBuffer)) > 0) {
                // char to string conversion
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                s += readString;
            }
            inputReader.close();
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
