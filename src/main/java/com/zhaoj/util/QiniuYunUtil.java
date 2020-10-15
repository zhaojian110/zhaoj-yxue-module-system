package com.zhaoj.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class QiniuYunUtil {
    /**
     *
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        String loaclFile = "D:\\backdemotest\\backdemotest1\\zhaoj-yxue-module-system\\target\\classes\\META-INF\\resources\\upload_file\\video\\1601253485325-1.mp4";
        upload(loaclFile);
    }

    public static String upload(String localFile) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        // 赵健自身的包
        String accessKey = "ytKVQ7484QOsvNTbSXfQJVrVnTpPKgNRszfFSdw4";
        String secretKey = "1xzroOHz4Ryxps5khVJHqyo1mWSKm9eJOY90Q34V";
        String bucket = "zhaojianx";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        //文件路径+文件名
        String localFilePath = localFile;
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();
        // 自定义返回的数据结果
/*
        putPolicy.put("returnBody", "{\"hash\":\""+MD5Util.getStringMD5(localFile)+"\",\"key\":\""+key+"\"}");
*/
        // 超时时间  超过3600秒后抛异常
        long expireSeconds = 3600;
        /**
         * bucket 上传到空间的名字
         * key 默认不指定Key的情况下,以文件内容的hash值作为文件名
         *
         * 过期时间
         */

        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        /*System.out.println(upToken);*/
        String netNileName = null;
        try {
            // 上传文件
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            /**
             * http://qh5yvg3ne.hn-bkt.clouddn.com/eat.mp4  将资源在七牛云的外网访问链接存入数据库
             */
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            netNileName=putRet.hash;
            System.out.println("我传到七牛云的视频是"+netNileName);
            //http://qh5yvg3ne.hn-bkt.clouddn.com/FsfolBFS8UsHn5mZdksamXNcd0bS
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return netNileName;
    }
}
