package com.smatworld.iniencrypt.data.security.utils;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;

public class DataUtil {

    private DataUtil() {
    }

    public static String getEncodedKey(Key key) {
        return Base64.encodeToString(key.getEncoded(), Base64.DEFAULT);
    }

    public static void writeToFile(InputStream inputStream, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[inputStream.available()];
            while (inputStream.read(buffer) != -1)
                fos.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Converts a byte to hex digit and writes to the supplied buffer
    public static void byte2hex(byte b, StringBuffer buf) {
        char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    }


    // Converts a byte array to hex string
    public static String toHexString(byte[] block) {
        StringBuffer buf = new StringBuffer();
        int len = block.length;
        for (int i = 0; i < len; i++) {
            byte2hex(block[i], buf);
            if (i < len - 1) {
                buf.append(":");
            }
        }
        return buf.toString();
    }

    public static InputStream getInputStreamFromFile(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileInputStream;
    }

    public static String getEncodedStream(InputStream stream) {
        String encodedString = null;
        try {
            byte[] buffer = new byte[stream.available()];
            stream.read(buffer);
            encodedString = Base64.encodeToString(buffer, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedString;
    }

    public static String getTextStream(InputStream stream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            byte[] buffer = new byte[1024];
            while (stream.read(buffer) != -1) {
                String encodedString = Base64.encodeToString(buffer, Base64.DEFAULT);
                stringBuilder.append(encodedString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
