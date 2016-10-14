package aohuan.com.assess.utils;

import android.content.Context;
import android.widget.ImageView;

import aohuan.com.assess.R;


/**
 * Created by Administrator on 2015/12/16 0016.
 */
public class ImageLoad {
    /** 加载失败的默认图片 */
    private static final int ERROR_IMG = R.mipmap.default_image;
    /** 加载中的默认图片 */
    private static final int LOAD_IMG = R.mipmap.default_image;

    static ImageIf imageIf = new LoadImageGlide();

//    static int imageNum = 0;

    /** 加载正常图片 */
    public static void loadImgDefault(Context context,ImageView imageView,String url){
//        imageIf = new LoadImageGlide();
//        if (url==null)
//            return;
//        AhLog.e("loadImgDefault", imageNum++ +"loadImgDefault: url  -> " + url);
        imageIf.loadImageDefault(context, imageView, url, ERROR_IMG);
    }

    public static void loadImgDefault(Context context,ImageView imageView,String url,int errorImg){
        imageIf.loadImageDefault(context, imageView, url, errorImg, LOAD_IMG);
    }

    public static void loadImgDefault(Context context,ImageView imageView,String url,int errorImg,int loadImg){
        imageIf.loadImageDefault(context, imageView, url, errorImg, loadImg);
    }

//    /** 加载圆形图片 */
//    public static void loadImgCircle(Context context,ImageView imageView,String url){
//        imageIf.loadImageCircle(context, imageView, url, ERROR_IMG);
//    }
//    public static void loadImgCircle(Context context,ImageView imageView,String url,int errorImg){
//        imageIf.loadImageCircle(context, imageView, url, errorImg, LOAD_IMG);
//    }
//    public static void loadImgCircle(Context context,ImageView imageView,String url,int errorImg,int loadImg){
//        imageIf.loadImageCircle(context, imageView, url, errorImg, loadImg);
//    }
//
//    /** 加载圆角图片 */
//    public static void loadImgRound(Context context,ImageView imageView,String url){
//        imageIf.loadImageRound(context, imageView, url, ERROR_IMG);
//    }
//    public static void loadImgRound(Context context,ImageView imageView,String url,int errorImg){
//        imageIf.loadImageRound(context, imageView, url, errorImg, LOAD_IMG);
//    }
//    public static void loadImgRound(Context context,ImageView imageView,String url,int errorImg,int loadImg){
//        imageIf.loadImageRound(context, imageView, url, errorImg, loadImg);
//    }
}
