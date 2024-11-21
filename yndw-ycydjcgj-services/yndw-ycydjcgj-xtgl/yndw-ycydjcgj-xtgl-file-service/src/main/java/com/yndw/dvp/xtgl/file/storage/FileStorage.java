package com.yndw.dvp.xtgl.file.storage;

import cn.hutool.core.util.ObjectUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件存储接口（可拓展）
 *
 */
public interface FileStorage {

    Object lock = new Object();
    Map<String, String> contentType = new HashMap<>();

    /**
     * 上传文件
     *
     * @param ObjectName 存储到文件服务器的全名，带后缀
     * @param object     具体文件对象
     */
    void upload(String ObjectName, File object);

    /**
     * 上传文件
     * <p>
     * 如果令牌桶不存在会自动创建
     *
     * @param bucktName  文件桶名称
     * @param objectName 存储到文件服务器的全名，带后缀
     * @param object     具体文件对象
     */
    void upload(String bucktName, String objectName, File object);

    /**
     * 上传文件
     *
     * @param objectName  存储到文件服务器的全名，带后缀
     * @param objectBytes 文件流
     */
    void upload(String objectName, byte[] objectBytes);

    /**
     * 上传文件
     *
     * @param bucktName   文件桶名称
     * @param objectName  存储到文件服务器的全名，带后缀
     * @param objectBytes 文件流
     */
    void upload(String bucktName, String objectName, byte[] objectBytes);

    /**
     * 从文件服务器获取文件流
     *
     * @param objectName 存储到文件服务器的全名，带后缀
     */
    byte[] getFileBytes(String objectName);

    /**
     * 从文件服务器获取文件流
     *
     * @param bucktName  文件桶名称
     * @param objectName 存储到文件服务器的全名，带后缀
     */
    byte[] getFileBytes(String bucktName, String objectName);

    /**
     * 获取文件的完整下载链接
     *
     * @param objectName 存储到文件服务器的全名，带后缀
     * @param fileName   具体文件下载下来的名称，带文件后缀
     * @return 返回文件的下载链接
     */
    String getDownloadUrl(String objectName, String fileName);

    /**
     * 获取文件的完整下载链接
     *
     * @param bucktName  文件桶名称
     * @param objectName 存储到文件服务器的全名，带后缀
     * @param fileName   具体文件下载下来的名称，带文件后缀
     * @return 返回文件的下载链接
     */
    String getDownloadUrl(String bucktName, String objectName, String fileName);

    /**
     * 获取文件的完整下载链接
     *
     * @param bucktName     文件桶名称
     * @param objectName    存储到文件服务器的全名，带后缀
     * @param fileName      具体文件下载下来的名称，带文件后缀
     * @param expireSeconds 文件url过期时间，单位：秒
     * @return 返回文件的下载链接
     */
    String getDownloadUrl(String bucktName, String objectName, String fileName, Integer expireSeconds);

    /**
     * 获取文件的完整预览链接
     *
     * @param objectName 存储到文件服务器的全名，带后缀
     * @return 返回文件的预览链接
     */
    String getPreviewUrl(String objectName);

    /**
     * 获取文件的完整预览链接
     *
     * @param bucktName  文件桶名称
     * @param objectName 存储到文件服务器的全名，带后缀
     * @return 返回文件的预览链接
     */
    String getPreviewUrl(String bucktName, String objectName);

    void delete(String objectName);

    void delete(String bucktName, String objectName);

    /**
     * 获取文件后缀对应的contentType
     *
     */
    default Map<String, String> getFileContentType() {
        synchronized (lock) {
            if (contentType.size() == 0) {
                contentType.put(".bmp", "application/x-bmp");
                contentType.put(".gif", "image/gif");
                contentType.put(".fax", "image/fax");
                contentType.put(".ico", "image/x-icon");
                contentType.put(".jfif", "image/jpeg");
                contentType.put(".jpe", "image/jpeg");
                contentType.put(".jpeg", "image/jpeg");
                contentType.put(".jpg", "image/jpeg");
                contentType.put(".png", "image/png");
                contentType.put(".rp", "image/vnd.rn-realpix");
                contentType.put(".tif", "image/tiff");
                contentType.put(".tiff", "image/tiff");
                contentType.put(".doc", "application/msword");
                contentType.put(".ppt", "application/x-ppt");
                contentType.put(".pdf", "application/pdf");
                contentType.put(".xls", "application/vnd.ms-excel");
                contentType.put(".txt", "text/plain");
                contentType.put(".java", "java/*");
                contentType.put(".html", "text/html");
                contentType.put(".avi", "video/avi");
                contentType.put(".movie", "video/x-sgi-movie");
                contentType.put(".mp4", "video/mpeg4");
                contentType.put(".mp3", "audio/mp3");
            }
        }
        return contentType;
    }

    /**
     * 获取文件后缀对应的contentType
     */
    default String getFileContentType(String fileSuffix) {
        String contentType = getFileContentType().get(fileSuffix);
        if (ObjectUtil.isEmpty(contentType)) {
            return "application/octet-stream";
        } else {
            return contentType;
        }
    }

}
