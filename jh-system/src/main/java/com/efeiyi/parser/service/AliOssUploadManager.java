package com.efeiyi.parser.service;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/2/29.
 * 重写文件上传方法
 */
public interface AliOssUploadManager {
    Boolean uploadFile(InputStream var1, String var2, String var3, Long size) throws Exception;
}
