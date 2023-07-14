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

        /*
        if(url.contains("www.curseforge.com")){

            URL resource = new URL(url);
            HttpURLConnection con = (HttpURLConnection) resource.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();

            System.out.println(status);

            if(status == 200) {

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String inputLine;

                StringBuffer content = new StringBuffer();

                while((inputLine = in.readLine()) != null){
                    content.append(inputLine);
                }

                //Content contains the zipped folder

                in.close();
                con.disconnect();

                return content.toString();

            } else {
                return null;
            }

        } else {

            return null;
        }
    }

    */

        System.out.println("Gets here 1");

        if (url.contains("www.curseforge.com")) {

            System.out.println("Gets here 2");
            //Contains incoming data
            BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());

            System.out.println("Gets here 3");

            File destDir = new File(destPath);

            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(in);
            ZipEntry zipEntry = zis.getNextEntry();

            System.out.println("Gets here 4");

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

            System.out.println("Gets here 5");
            return true;


        } else {
            System.out.println("Gets here 6");
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

