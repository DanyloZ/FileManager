package com.danylo.fileManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileManager {
    public static int calculateFiles(String path) {
        File dir = new File(path);
        int fileCount = 0;
        if (dir.exists() && dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                if (item.isDirectory()) {
                    fileCount += calculateFiles(item.getAbsolutePath());
                }
                else {
                    fileCount++;
                }
            }
        }
        return fileCount;
    }

    public static int calculateDirs(String path) {
        File dir = new File(path);
        int dircount = 0;
        if (dir.exists() && dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                if (item.isDirectory()) {
                    dircount++;
                    dircount += calculateDirs(item.getAbsolutePath());
                }
            }
        }
        return dircount;
    }

    public static boolean copy(String from, String to) {
        File src = new File(from);
        if (src.isFile()) {
            try {
                File newFile = new File(to, src.getName());
                Files.copy(src.toPath(), newFile.toPath());
                return true;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (src.isDirectory()) {
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
