package com.vac.vmusic.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by vac on 16/10/31.
 *
 */
public class FileUtil {
    public static boolean saveBitmap(String singerName,String picUrl,Bitmap bmp){
        boolean b =false;
        String  rootPath = Constants.ROOT_PATH+Constants.CHILR_ARTIST_PIC+File.separator;
        File appDir = new File(rootPath,singerName);
        if (!appDir.exists()) {
            b = appDir.mkdirs();
        }
        String fileName = getFileName(picUrl);
        File currentFile = new File(appDir, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            b = bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    public static String[] getArtistByName(String name){
        String path = Constants.ROOT_PATH + Constants.CHILR_ARTIST_PIC+File.separator;
        File appDir = new File(path,name);
        if (appDir.exists()){
            return appDir.list();
        }
        return null;
    }

    public static boolean isExitAritstPic(String name){
        boolean isExit;
        String path = Constants.ROOT_PATH + Constants.CHILR_ARTIST_PIC+File.separator;
        File appDir = new File(path,name);
        isExit = appDir.exists();
        if (isExit){
            String[] nameList = appDir.list();
            if (nameList.length==0){
                isExit =false;
            }
        }
        return isExit;
    }

    /**
     * get file name from path, include suffix
     *
     * <pre>
     *      getFileName(null)               =   null
     *      getFileName("")                 =   ""
     *      getFileName("   ")              =   "   "
     *      getFileName("a.mp3")            =   "a.mp3"
     *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
     *      getFileName("abc")              =   "abc"
     *      getFileName("c:\\")              =   ""
     *      getFileName("c:\\a")             =   "a"
     *      getFileName("c:\\a.b")           =   "a.b"
     *      getFileName("c:a.txt\\a")        =   "a"
     *      getFileName("/home/admin")      =   "admin"
     *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
     * </pre>
     *
     * @param filePath
     * @return file name from path, include suffix
     */
    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
    }
}
