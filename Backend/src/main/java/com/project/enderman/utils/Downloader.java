package com.project.enderman.utils;

import java.io.*;
import java.net.URL;
import java.util.zip.ZipInputStream;

public class Downloader {

    public static boolean download(String url, String destPath) throws IOException {

        if (url.contains("www.curseforge.com")) {

            //Contains incoming data
            BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());

            File destDir = new File(destPath);
            ZipInputStream zis = new ZipInputStream(in);

            Compression.unzip(destDir, zis);
            zis.close();

            return true;

        } else {

            return false;
        }

    }
}

