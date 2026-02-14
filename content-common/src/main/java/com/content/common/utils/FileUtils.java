package com.content.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class FileUtils {

    public static byte[] readFileToBytes(String filePath) {
        File file = new File(filePath);
        return readFileToBytes(file);
    }

    public static byte[] readFileToBytes(File file) {
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            log.error("读取文件失败，filePath={}", file.getAbsolutePath(), e);
            return null;
        }
    }

    public static String readFileToString(String filePath) {
        File file = new File(filePath);
        return readFileToString(file);
    }

    public static String readFileToString(File file) {
        byte[] bytes = readFileToBytes(file);
        return bytes == null ? null : new String(bytes, StandardCharsets.UTF_8);
    }

    public static void writeBytesToFile(byte[] bytes, String filePath) {
        File file = new File(filePath);
        writeBytesToFile(bytes, file);
    }

    public static void writeBytesToFile(byte[] bytes, File file) {
        if (bytes == null) {
            return;
        }
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
        } catch (IOException e) {
            log.error("写入文件失败，filePath={}", file.getAbsolutePath(), e);
        }
    }

    public static void writeStringToFile(String content, String filePath) {
        File file = new File(filePath);
        writeStringToFile(content, file);
    }

    public static void writeStringToFile(String content, File file) {
        if (content == null) {
            return;
        }
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        writeBytesToFile(bytes, file);
    }

    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return deleteFile(file);
    }

    public static boolean deleteFile(File file) {
        if (!file.exists()) {
            return true;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteFile(f);
                }
            }
        }
        return file.delete();
    }

    public static boolean exists(String filePath) {
        File file = new File(filePath);
        return exists(file);
    }

    public static boolean exists(File file) {
        return file.exists();
    }

    public static boolean isFile(String filePath) {
        File file = new File(filePath);
        return isFile(file);
    }

    public static boolean isFile(File file) {
        return file.exists() && file.isFile();
    }

    public static boolean isDirectory(String filePath) {
        File file = new File(filePath);
        return isDirectory(file);
    }

    public static boolean isDirectory(File file) {
        return file.exists() && file.isDirectory();
    }

    public static long getFileSize(String filePath) {
        File file = new File(filePath);
        return getFileSize(file);
    }

    public static long getFileSize(File file) {
        if (!file.exists() || !file.isFile()) {
            return 0;
        }
        return file.length();
    }

    public static String getFileName(String filePath) {
        File file = new File(filePath);
        return getFileName(file);
    }

    public static String getFileName(File file) {
        if (!file.exists()) {
            return null;
        }
        return file.getName();
    }

    public static String getFileNameWithoutExtension(String filePath) {
        File file = new File(filePath);
        return getFileNameWithoutExtension(file);
    }

    public static String getFileNameWithoutExtension(File file) {
        if (!file.exists()) {
            return null;
        }
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return fileName;
        }
        return fileName.substring(0, lastDotIndex);
    }

    public static String getFileExtension(String filePath) {
        File file = new File(filePath);
        return getFileExtension(file);
    }

    public static String getFileExtension(File file) {
        if (!file.exists()) {
            return null;
        }
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return null;
        }
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }

    public static String getParentDirectory(String filePath) {
        File file = new File(filePath);
        return getParentDirectory(file);
    }

    public static String getParentDirectory(File file) {
        if (!file.exists()) {
            return null;
        }
        File parent = file.getParentFile();
        return parent == null ? null : parent.getAbsolutePath();
    }

    public static boolean createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        return createDirectory(directory);
    }

    public static boolean createDirectory(File directory) {
        if (directory.exists()) {
            return directory.isDirectory();
        }
        return directory.mkdirs();
    }

    public static boolean copyFile(String sourceFilePath, String targetFilePath) {
        File sourceFile = new File(sourceFilePath);
        File targetFile = new File(targetFilePath);
        return copyFile(sourceFile, targetFile);
    }

    public static boolean copyFile(File sourceFile, File targetFile) {
        if (!sourceFile.exists() || !sourceFile.isFile()) {
            return false;
        }
        createDirectory(targetFile.getParentFile());
        byte[] bytes = readFileToBytes(sourceFile);
        if (bytes == null) {
            return false;
        }
        writeBytesToFile(bytes, targetFile);
        return true;
    }

    public static boolean moveFile(String sourceFilePath, String targetFilePath) {
        File sourceFile = new File(sourceFilePath);
        File targetFile = new File(targetFilePath);
        return moveFile(sourceFile, targetFile);
    }

    public static boolean moveFile(File sourceFile, File targetFile) {
        if (!sourceFile.exists() || !sourceFile.isFile()) {
            return false;
        }
        createDirectory(targetFile.getParentFile());
        boolean copied = copyFile(sourceFile, targetFile);
        if (copied) {
            return sourceFile.delete();
        }
        return false;
    }

    public static String getFileMimeType(String filePath) {
        File file = new File(filePath);
        return getFileMimeType(file);
    }

    public static String getFileMimeType(File file) {
        if (!file.exists() || !file.isFile()) {
            return "application/octet-stream";
        }
        String extension = getFileExtension(file);
        if (extension == null) {
            return "application/octet-stream";
        }
        switch (extension.toLowerCase()) {
            case "txt":
                return "text/plain";
            case "html":
            case "htm":
                return "text/html";
            case "css":
                return "text/css";
            case "js":
                return "application/javascript";
            case "json":
                return "application/json";
            case "xml":
                return "application/xml";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "bmp":
                return "image/bmp";
            case "webp":
                return "image/webp";
            case "svg":
                return "image/svg+xml";
            case "pdf":
                return "application/pdf";
            case "doc":
            case "docx":
                return "application/msword";
            case "xls":
            case "xlsx":
                return "application/vnd.ms-excel";
            case "ppt":
            case "pptx":
                return "application/vnd.ms-powerpoint";
            case "zip":
                return "application/zip";
            case "rar":
                return "application/x-rar-compressed";
            case "7z":
                return "application/x-7z-compressed";
            case "tar":
                return "application/x-tar";
            case "gz":
                return "application/gzip";
            case "mp3":
                return "audio/mpeg";
            case "wav":
                return "audio/wav";
            case "mp4":
                return "video/mp4";
            case "avi":
                return "video/x-msvideo";
            case "mov":
                return "video/quicktime";
            case "wmv":
                return "video/x-ms-wmv";
            default:
                return "application/octet-stream";
        }
    }

    public static boolean isImageFile(String filePath) {
        File file = new File(filePath);
        return isImageFile(file);
    }

    public static boolean isImageFile(File file) {
        String mimeType = getFileMimeType(file);
        return mimeType.startsWith("image/");
    }

    public static boolean isAudioFile(String filePath) {
        File file = new File(filePath);
        return isAudioFile(file);
    }

    public static boolean isAudioFile(File file) {
        String mimeType = getFileMimeType(file);
        return mimeType.startsWith("audio/");
    }

    public static boolean isVideoFile(String filePath) {
        File file = new File(filePath);
        return isVideoFile(file);
    }

    public static boolean isVideoFile(File file) {
        String mimeType = getFileMimeType(file);
        return mimeType.startsWith("video/");
    }

    public static boolean isTextFile(String filePath) {
        File file = new File(filePath);
        return isTextFile(file);
    }

    public static boolean isTextFile(File file) {
        String mimeType = getFileMimeType(file);
        return mimeType.startsWith("text/");
    }

    public static boolean isDocumentFile(String filePath) {
        File file = new File(filePath);
        return isDocumentFile(file);
    }

    public static boolean isDocumentFile(File file) {
        String mimeType = getFileMimeType(file);
        return mimeType.equals("application/msword") ||
                mimeType.equals("application/vnd.ms-excel") ||
                mimeType.equals("application/vnd.ms-powerpoint") ||
                mimeType.equals("application/pdf");
    }

    public static boolean isArchiveFile(String filePath) {
        File file = new File(filePath);
        return isArchiveFile(file);
    }

    public static boolean isArchiveFile(File file) {
        String mimeType = getFileMimeType(file);
        return mimeType.equals("application/zip") ||
                mimeType.equals("application/x-rar-compressed") ||
                mimeType.equals("application/x-7z-compressed") ||
                mimeType.equals("application/x-tar") ||
                mimeType.equals("application/gzip");
    }

    public static String getFileBase64(String filePath) {
        File file = new File(filePath);
        return getFileBase64(file);
    }

    public static String getFileBase64(File file) {
        byte[] bytes = readFileToBytes(file);
        return bytes == null ? null : Base64.getEncoder().encodeToString(bytes);
    }

    public static void saveBase64ToFile(String base64, String filePath) {
        File file = new File(filePath);
        saveBase64ToFile(base64, file);
    }

    public static void saveBase64ToFile(String base64, File file) {
        if (base64 == null) {
            return;
        }
        String[] parts = base64.split(",");
        String base64Data = parts.length > 1 ? parts[1] : parts[0];
        byte[] bytes = Base64.getDecoder().decode(base64Data);
        writeBytesToFile(bytes, file);
    }

    public static String getFileUrl(String filePath) {
        File file = new File(filePath);
        try {
            return file.toURI().toURL().toString();
        } catch (Exception e) {
            log.error("获取文件URL失败，filePath={}", file.getAbsolutePath(), e);
            return null;
        }
    }

    public static boolean downloadFile(String url, String savePath) {
        File file = new File(savePath);
        return downloadFile(url, file);
    }

    public static boolean downloadFile(String url, File file) {
        createDirectory(file.getParentFile());
        try (BufferedInputStream bis = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fos = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            return true;
        } catch (Exception e) {
            log.error("下载文件失败，url={}", url, e);
            return false;
        }
    }

    public static String getFileNameFromUrl(String url) {
        try {
            URL uri = new URL(url);
            String path = uri.getPath();
            return path.substring(path.lastIndexOf('/') + 1);
        } catch (Exception e) {
            log.error("从URL获取文件名失败，url={}", url, e);
            return null;
        }
    }
}
