package com.nextplate.core.util;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtils
{
    private static final String TAG = ZipUtils.class.getSimpleName();

    private ZipUtils()
    {
    }

    public static ZipInputStream getFileFromZip(InputStream zipFileStream) throws IOException
    {
        ZipInputStream zis = new ZipInputStream(zipFileStream);
        ZipEntry ze;
        while((ze = zis.getNextEntry()) != null)
        {
            Log.w(TAG, "extracting file: '" + ze.getName() + "'...");
            return zis;
        }
        return null;
    }
}
