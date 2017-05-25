package com.alpha.lib_sdk.app.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kenway on 17/5/22 16:49
 * Email : xiaokai090704@126.com
 * File工具类,创建文件,判读文件是否存在,copy文件,删除文件。从流中读取文件
 */

public class FileUtils {
    private static final String TAG = "tool.FileUtils";

    /**
     * Create a new file if it is not exists and its parent {@link File}s are
     * exists.
     *
     * @param file
     * @return true : if a new file was created, false : otherwise.
     * @see #createFileIfNeed(File)
     */
    public static boolean createNewFile(File file) {
        if (file != null) {
            try {
                if (!file.exists()) {
                    File parent = file.getParentFile();
                    if (parent != null && parent.exists() && parent.isDirectory()) {
                        file.createNewFile();
                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * Create a new file if it is not exists. If parent {@link File}s are not
     * exists that it will make them automatically.
     *
     * @param file
     * @see #createNewFile(File)
     * @return true : if a new file was created, false : otherwise.
     */
    public static boolean createFileIfNeed(File file) {
        if (file != null) {
            try {
                if (!file.exists()) {
                    File parent = file.getParentFile();
                    if (parent != null) {
                        parent.mkdirs();
                    }
                    file.createNewFile();
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Whether the file exist or not
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        if(Util.isNullOrBlank(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * Copy file from srcFilePath to desFilePath.
     *
     * @param srcFilePath
     * @param desFilePath
     * @return
     * @throws IOException
     */
    public static boolean copy(String srcFilePath, String desFilePath)
            throws IOException {
        if (Util.isNullOrNil(desFilePath) || Util.isNullOrNil(srcFilePath)) {
            return false;
        }
        File srcFile = new File(srcFilePath);
        if (!srcFile.exists()) {
            return false;
        }
        File desFile = new File(desFilePath);
        if (!desFile.exists()) {
            desFile.mkdirs();
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            if (srcFile.isFile()) {
                fis = new FileInputStream(srcFile);
                File f = new File(desFilePath + File.separator
                        + srcFile.getName());
                fos = new FileOutputStream(f);
                byte[] b = new byte[1024 * 5];
                int len;
                while ((len = fis.read(b)) != -1) {
                    fos.write(b, 0, len);
                }
                fis.close();
                fos.flush();
                fos.close();
            } else if (srcFile.isDirectory()) {
                File[] file = srcFile.listFiles();
                for (int i = 0; i < file.length; i++) {
                    copy(file[i].getAbsolutePath(), desFilePath
                            + File.separator + srcFile.getName());
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fis != null)
                fis.close();
            if (fos != null)
                fos.close();
        }
    }

    /**
     * Delete all file in the File.
     *
     * @param file
     * @return
     */
    public static boolean deleteAll(File file) {
        if (file == null || !file.exists()) {
            return true;
        } else {
            if (file.isFile()) {
                return file.delete();
            } else {
                File[] childs = file.listFiles();
                for (File f : childs) {
                    deleteAll(f);
                }
                return file.delete();
            }
        }
    }
}
