package com.sunnyday.utils;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionMethod;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

/**
 * zip压缩文件工具类
 *
 * @author TMW
 * @since 2020/11/26 17:31
 */
public class ZipUtil {

    /***
     * 压缩文件
     * @param srcFileList 文件列表
     * @param toPath  目标路径
     */
    public static void zipFile(List<File> srcFileList, String toPath) throws Exception {
        ZipFile zip = new ZipFile(toPath);
        zip.setCharset(Charset.defaultCharset());
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(CompressionMethod.DEFLATE);
        for (File file : srcFileList) {
            zip.addFile(file, parameters);
        }
    }

    /**
     * 解压ZIP
     *
     * @param srcPath 源文件路径
     * @param toPath  目标文件路径
     */
    public static void unZip(String srcPath, String toPath) throws Exception {
        unZip(srcPath, toPath, false);
    }

    /**
     * 解压ZIP
     *
     * @param srcPath 源文件路径
     * @param toPath  目标文件路径
     * @param delSrc  删除源文件
     */
    public static void unZip(String srcPath, String toPath, boolean delSrc) throws Exception {
        ZipFile zipFile = new ZipFile(srcPath);
        if (!zipFile.isValidZipFile()) {
            return;
        }
        zipFile.setCharset(Charset.defaultCharset());
        zipFile.extractAll(toPath);
        if (delSrc) {
            Optional.ofNullable(zipFile.getFile())
                    .ifPresent(File::delete);
        }
    }

    /**
     * 删除文件
     *
     * @param fileList
     */
    public static void delFiles(List<File> fileList) {
        fileList.stream().filter(file -> file != null && file.exists()).forEach(File::delete);
    }
}
