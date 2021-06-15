package com.github.dreamsmoke.tweaker.transformers;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.launchwrapper.IClassTransformer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassTransformer implements IClassTransformer {

    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        byte[] bytes = this.getPatch(name + ".class", transformedName
                .replace(".", "/") + ".class");
        return bytes == null ? basicClass : bytes;
    }

    public final byte[] getPatch(String name, String transformedName) {
        InputStream inputStreamName = this.getPatchedClass(name);
        InputStream inputStreamTransformedName = this.getPatchedClass(transformedName);
        return inputStreamName == null ? this.getBytesPatchedClass(inputStreamTransformedName,
                transformedName) : this.getBytesPatchedClass(inputStreamName, name);
    }

    public final InputStream getPatchedClass(String name) {
        URLClassLoader urlClassLoader = (URLClassLoader) ClassTransformer.class.getClassLoader();
        for (URL url :urlClassLoader.getURLs()) {
            try {
                ZipFile zipFile = new ZipFile(new File(url.toURI()));
                ZipEntry zipEntry = zipFile.getEntry(name);
                if (zipEntry == null || zipFile.getEntry(ClassTransformer.class.getName()
                        .replace(".", "/") + ".class") == null) continue;
                return zipFile.getInputStream(zipEntry);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        return null;
    }

    public final byte[] getBytesPatchedClass(InputStream inputStream, String name) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[64];
        if (inputStream != null) {
            FMLLog.info("Patching %s...", new Object[]{name});
            do {
                try {
                    do {
                        int bytes;
                        if ((bytes = inputStream.read(buffer)) < 0) {
                            inputStream.close();
                            FMLLog.info("Patched %s %d", new Object[]{name, byteArrayOutputStream.size()});
                            return byteArrayOutputStream.toByteArray();
                        }

                        byteArrayOutputStream.write(buffer, 0, bytes);
                    } while (true);
                } catch (IOException exception) {
                    exception.printStackTrace();
                    continue;
                }
            } while (true);
        }

        return null;
    }

}

