package com.orion.util;


import com.orion.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


public class FastDFSClient {
    // global init
    static {
        String path = new ClassPathResource("fdfs_client.conf").getPath();
        try {
            ClientGlobal.init(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 图片上传
    public static String[] upload(FastDFSFile file) {
        try {
            // 获取tracker 客户端client
            TrackerClient client = new TrackerClient();
            // 获取tracker 服务端server  此时可能出现异常
            TrackerServer server = client.getConnection();
            // 获取storage客户端client
            StorageClient storageClient = new StorageClient(server, null);
            // 通过storageclient提供的上传方法进行上传，参数：字节数组，扩展名，元数据list
            // 首先获取文件的元数据，即附加信息
            NameValuePair[] meta_list = new NameValuePair[]{new NameValuePair("author", file.getAuthor()), new NameValuePair(file.getName())};
            // 返回值为数组：[0] 标识组 [1] 表示 文件名 包含： M00/01/02/dfafadf.jpg
            String[] strings = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
            return strings;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 图片下载
    public static InputStream downFile(String group, String remoteFileName) throws IOException {
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            // 获取trackerclient
            TrackerClient trackerClient = new TrackerClient();
            // 获取trackerserver
            TrackerServer trackerServer = trackerClient.getConnection();
            // 获取storageclient
            StorageClient storageClient = new StorageClient(trackerServer, null);
            // storageclient提供了下载方法，但是需要提供两个参数，组名和文件路径名
            // 返回值是字节数组
            byte[] bytes = storageClient.download_file(group, remoteFileName);
            // 我们更期望以inputstream的方式返回 ,
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            return byteArrayInputStream;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(byteArrayInputStream != null){
                byteArrayInputStream.close();
            }
        }
        return null;
    }

    // 图片删除，注意，在docker容器中，需要设置不保存缓存，保证达到立即删除的目标。
    public static boolean deleteFile(String group, String remoteFileName) {
        try {
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer, null);
            // 如果返回值为0 表示删除成功， 否则删除失败
            int i = storageClient.delete_file(group, remoteFileName);
            return i==0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // 获取文件信息
    public static FileInfo getFile(String group, String remoteFileName) {
        try {
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer, null);
            // fileInfo 包含了四种信息：源文件ip、创建时间、文件大小、crc32循环冗余校验
            FileInfo fileInfo = storageClient.get_file_info(group, remoteFileName);
            return fileInfo;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // 获取tracker的端口和ip信息， 用于构建url
    public static String getTrackerUrl() {
        TrackerClient trackerClient = new TrackerClient();
        try {
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer, null);
/*           使用trackerserver获取即可
             使用getInetSocketAddress().getHostString()   &&ClientGlobal.getG_tracker_http_port*/
            String host = trackerServer.getInetSocketAddress().getHostString();
            int port = ClientGlobal.getG_tracker_http_port();
            return "http://"+host+":"+port;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
