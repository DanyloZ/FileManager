package com.danylo.fileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class FileManager {
    public static int calculateFiles(String path) {
        File dir = new File(path);
        int fileCount = 0;
        if (dir.isDirectory()) {
            try {
                for (File item : dir.listFiles()) {
                    if (item.isDirectory()) {
                        fileCount += calculateFiles(item.getAbsolutePath());
                    } else {
                        fileCount++;
                    }
                }
            } catch (NullPointerException e) {/*NOP*/}
        }
        return fileCount;
    }

    public static int calculateDirs(String path) {
        File dir = new File(path);
        int dircount = 0;
        if (dir.isDirectory()) {
            try {
                for (File item : dir.listFiles()) {
                    if (item.isDirectory()) {
                        dircount++;
                        dircount += calculateDirs(item.getAbsolutePath());
                    }
                }
            } catch (NullPointerException e) {/*NOP*/}
        }
        return dircount;
    }

    public static boolean copy(String from, String to) {
        File src = new File(from);
        if (src.isFile()) {
            File dest = new File(to, src.getName());
            try(FileInputStream inputStream = new FileInputStream(src);
                FileOutputStream outputStream = new FileOutputStream(dest)) {
                byte[] buff = new byte[1024];
                int length;
                while ((length = inputStream.read(buff)) > 0) {
                    outputStream.write(buff, 0, length);
                }
                return true;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (src.isDirectory()) {
            try {
                File newFolder = new File(to + "/" + src.getName());
                Files.copy(src.toPath(), newFolder.toPath());
                String newDest = newFolder.getAbsolutePath();
                for (File item : src.listFiles()) {
                    copy(item.getAbsolutePath(), newDest);
                }
                return true;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean move(String from, String to) {
        if (copy(from, to)) {
            del(from);
            return true;
        }
        return false;
    }

    public static boolean del(String path) {
        File file = new File(path);
        if (file.isFile()) {
            file.delete();
        }
        if (file.isDirectory()) {
            for (File item : file.listFiles()) {
                del(item.getAbsolutePath());
            }
            file.delete();
        }
        return true;
    }
}
