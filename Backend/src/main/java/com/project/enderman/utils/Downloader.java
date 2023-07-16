package com.project.enderman.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Downloader {

    public static boolean download(String url, String destPath) throws IOException {

        if (url.contains("www.curseforge.com")) {

            //Contains incoming data
            BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());

            File destDir = new File(destPath);

            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(in);
            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {

                System.out.println("Unzipping: " + zipEntry.getName());

                File newFile = newFile(destDir, zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory" + newFile);
                    }
                } else {
                    // fix for Windows files
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory" + parent);
                    }

                    // write file
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;

                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }

            return true;

        } else {

            return false;
        }

    }

    private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {

        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

}

