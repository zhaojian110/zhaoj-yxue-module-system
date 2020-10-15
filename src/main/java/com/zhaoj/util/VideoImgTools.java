package com.zhaoj.util;

/**
 * Author：
 * Description：
 * Time：2020/09/27/17:33
 */

import org.apache.commons.codec.binary.Base64;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import sun.misc.BASE64Encoder;
import org.bytedeco.javacv.Frame;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@SuppressWarnings("restriction")
public class VideoImgTools {
    /*
     * 测试效果
     */

    public static String mai(String newpath,String imgPath) {//视频路径  图片保存路径
        File file = new File(newpath);
        String fileName=null;//返回的图片名
        try {
            String imgFileString = fetchPic(file);
            String tempPath = imgPath; //图片保存目录
            fileName = System.currentTimeMillis() + ".jpg"; //使用毫秒数作为图片名
            File tempFile = new File(tempPath + fileName);
            if (imgFileString.indexOf("base64,") >= 0) {
                imgFileString = imgFileString.split("base64,")[1];
            }
            byte[] b = Base64.decodeBase64(imgFileString);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) { // 调整异常数据
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(tempFile);
            out.write(b);
            out.flush();
            out.close();
            System.out.println("获取成功");
            System.out.println("视频时长" + getVideoTime(file));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取失败");
        }
        return fileName;
    }

    /**
     * 获取指定视频的帧并保存为图片至指定目录
     *
     * @param file 源视频文件
     * @throws Exception
     */
    public static String fetchPic(File file) throws Exception {
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file);
        ff.start();
        int lenght = ff.getLengthInFrames();
        int i = 0;
        int[] rgb = new int[3];
        Frame frame = null;
        while (i < lenght) {
            // 过滤前50帧，避免出现全黑的图片，依自己情况而定
            frame = ff.grabFrame();
            // 判断图片黑色区域是否过高
            if ((i > 50) && (frame.image != null)) {
                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage bif = converter.getBufferedImage(frame);
                int width = bif.getWidth();
                int height = bif.getHeight();
                int minx = bif.getMinX();
                int miny = bif.getMinY();
                int sumPixel = (width - minx) * (height - miny); // 总像素
                int blackPixel = 0; // 黑色像素
                for (int m = minx; m < width; m++) {
                    for (int j = miny; j < height; j++) {
                        //得到指定像素（i,j)上的RGB值，
                        int pixel = bif.getRGB(m, j);
                        // 分别进行位操作得到 r g b上的值
                        rgb[0] = (pixel & 0xff0000) >> 16;
                        rgb[1] = (pixel & 0xff00) >> 8;
                        rgb[2] = (pixel & 0xff);
                        if (rgb[0] < 99 && rgb[0] >= 0 && rgb[1] < 99 && rgb[1] >= 0 && rgb[2] < 99 && rgb[2] >= 0) {
                            blackPixel++;
                        }
                    }
                }
                if (blackPixel / (sumPixel / 100) < 90) {
                    break;
                }
            }
            i++;
        }

        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage srcBi = converter.getBufferedImage(frame);
        int owidth = srcBi.getWidth();
        int oheight = srcBi.getHeight();
        // 对截取的帧进行等比例缩放
        int width = 800;
        int height = (int) (((double) width / owidth) * oheight);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        bi.getGraphics().drawImage(srcBi.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpg", baos);

        byte[] bytes = baos.toByteArray();// 转换成字节
        BASE64Encoder encoder = new BASE64Encoder();
        String png_base64 = encoder.encodeBuffer(bytes).trim();// 转换成base64串
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");// 删除// \r\n
        return png_base64;
    }

    /**
     * 获取视频时长，单位为秒
     *
     * @param file
     * @return 时长（s）
     */
    public static Long getVideoTime(File file) {
        Long times = 0L;
        try {
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file);
            ff.start();
            times = ff.getLengthInTime() / (1000 * 1000);
            ff.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }
}

