package com.example.performanceoptimize;

import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * @author wanlijun
 * @description  数据解压缩，在android 客户端 request 头中加入 "Accept-Encoding", "gzip" ，来让服务器传送gzip 数据
 * xUtils网络请求框架源码的HttpUtils类里面就用到了
 * Gzip开启以后会将输出到用户浏览器的数据进行压缩的处理，这样就会减小通过网络传输的数据量，提高浏览的速度
 * @time 2018/5/8 9:41
 */

public class GzipUtils {
    //GZIP压缩（文件格式.gz）
    public static String compressForGzip(String ungzipStr){
        if(TextUtils.isEmpty(ungzipStr))return null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(baos);
            gzipOutputStream.write(ungzipStr.getBytes());
            gzipOutputStream.close();
            byte[] encode = baos.toByteArray();
            baos.flush();
            baos.close();
            return new BASE64Encoder().encode(encode);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    //GZIP解压
    public static String decompressForGzip(String gzipStr){
        if(TextUtils.isEmpty(gzipStr))return null;
        try {
            byte[] temp = new BASE64Decoder().decodeBuffer(gzipStr);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ByteArrayInputStream bais = new ByteArrayInputStream(temp);
            GZIPInputStream gzipInputStream = new GZIPInputStream(bais);
            byte[] buffer = new byte[2048];
            int n = 0;
            while ((n= gzipInputStream.read(buffer,0,buffer.length))>0){
                baos.write(buffer,0,n);
            }
            gzipInputStream.close();
            bais.close();
            baos.close();
            return baos.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    //ZIP压缩（文件格式.zip）
    public static String compressForZip(String unzipStr){
        if(TextUtils.isEmpty(unzipStr))return null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(baos);
            zipOutputStream.putNextEntry(new ZipEntry("0"));
            zipOutputStream.write(unzipStr.getBytes());
            zipOutputStream.closeEntry();
            zipOutputStream.close();
            byte[] encode = baos.toByteArray();
            baos.flush();
            baos.close();
            return new BASE64Encoder().encode(encode);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    //ZIP解压
    public static String decompressForZip(String zipStr){
        if(TextUtils.isEmpty(zipStr)) return null;
        try {
            byte[] temp = new BASE64Decoder().decodeBuffer(zipStr);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ByteArrayInputStream bais = new ByteArrayInputStream(temp);
            ZipInputStream zipInputStream = new ZipInputStream(bais);
            zipInputStream.getNextEntry();
            byte[] buffer = new byte[2048];
            int n=0;
            while ((n=zipInputStream.read(buffer,0,buffer.length)) > 0){
                baos.write(buffer,0,n);
            }
            zipInputStream.close();
            bais.close();
            baos.close();
            return baos.toString("UTF-8");
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
