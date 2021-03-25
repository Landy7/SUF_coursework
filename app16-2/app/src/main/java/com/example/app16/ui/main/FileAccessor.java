package com.example.app16.ui.main;

import android.Manifest;
import android.content.Context;


import android.content.Context;
import android.app.Activity;
import android.os.Bundle;


import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.getExternalStoragePublicDirectory;

//public class FileAccessor
//{ Context myContext;
//
//  public FileAccessor(Context context)
//  { myContext = context; }
//
//  public void createFile(String filename)
//  { try
//    { File newFile = new File(myContext.getFilesDir(), filename);
//      newFile.mkdirs();
//        System.out.println("file"+newFile);}
//    catch (Exception _e) { _e.printStackTrace(); }
//  }
//
//   public ArrayList<String> readFile(String filename)
//   { ArrayList<String> result = new ArrayList<String>();
//
//     try {
//           InputStream inStrm = myContext.openFileInput(filename);
//           if (inStrm != null) {
//               InputStreamReader inStrmRdr = new InputStreamReader(inStrm);
//               BufferedReader buffRdr = new BufferedReader(inStrmRdr);
//               String fileContent;
//
//               while ((fileContent = buffRdr.readLine()) != null)
//               { result.add(fileContent); }
//               inStrm.close();
//           }
//       } catch (Exception _e) { _e.printStackTrace(); }
//     return result;
//   }
//
//   public void writeFile(String filename, ArrayList<String> contents)
//   { try {
//       OutputStreamWriter outStrm =
//               new OutputStreamWriter(myContext.openFileOutput(filename, Context.MODE_PRIVATE));
//       try {
//         for (int i = 0; i < contents.size(); i++)
//         { outStrm.write(contents.get(i) + "\n"); }
//       }
//       catch (IOException _ix) { }
//       outStrm.close();
//     }
//     catch (Exception e) { e.printStackTrace(); }
//   }
//
//}
public class FileAccessor {
    Context myContext;

    FileAccessor(Context context) {
        myContext = context;
    }

    public void initData() {
        File file = getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);
        String filePath = file.getPath()+File.separator;
        String fileName = "DailyQuote.txt";
        ArrayList<String> content = DailyQuote_DAO.content;
        System.out.println(content);
//        for(int i = 0; i < content.size();i++){
//            writeTxtToFile(content.get(i), filePath, fileName);
//        }
        writeTxtToFile(content, filePath, fileName);
    }

    // the content write into File
    public void writeTxtToFile(ArrayList<String> strcontent, String filePath, String fileName) {
        //create a directory and then create a file
        makeFilePath(filePath, fileName);

        String strFilePath = filePath+fileName;
//            String strContent = strcontent + "\r\n";
            try {
                File file = new File(strFilePath);
                if (!file.exists()) {
                    Log.d("TestFile", "Create the file:" + strFilePath);
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }else{
                    FileOutputStream out = null;
                    out = new FileOutputStream(file);
                    out.flush();
                }
                for(int i = 0; i < strcontent.size(); i++){
                    RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                    String strContent = strcontent.get(i) + "\r\n";
                    raf.seek(file.length());
                    raf.write(strContent.getBytes());
                    raf.close();
                }
            } catch (Exception e) {
                Log.e("TestFile", "Error on write File:" + e);
            }
    }

    // create the file
    public File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // create directory
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }
}
