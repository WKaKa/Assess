package aohuan.com.assess.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import aohuan.com.assess.R;


/**
 * Created by Administrator on 2015/12/16 0016.
 */
public class LoadImageGlide implements ImageIf {
    /** 加载失败的默认图片 */
    private static final int ERROR_IMG = R.mipmap.default_image;
    /** 加载中的默认图片 */
    private static final int LOAD_IMG = R.mipmap.default_image;
    @Override
    public void loadImageDefault(Context context, ImageView imageView, String url) {
        loadImgDefault(context, imageView, url, ERROR_IMG);
    }

    @Override
    public void loadImageDefault(Context context, ImageView imageView, String url, int error) {
        loadImgDefault(context, imageView, url, error);
    }

    @Override
    public void loadImageDefault(Context context, ImageView imageView, String url, int error, int load) {
        loadImgDefault(context, imageView, url, error,load);
    }

//    @Override
//    public void loadImageCircle(Context context, ImageView imageView, String url) {
//        loadImgCircle(context, imageView, url, ERROR_IMG);
//    }
//
//    @Override
//    public void loadImageCircle(Context context, ImageView imageView, String url, int error) {
//        loadImgCircle(context, imageView, url, error);
//    }
//
//    @Override
//    public void loadImageCircle(Context context, ImageView imageView, String url, int error, int load) {
//        loadImgCircle(context, imageView, url, error,load);
//    }
//
//    @Override
//    public void loadImageRound(Context context, ImageView imageView, String url) {
//        loadImgDefault(context, imageView, url, ERROR_IMG);
//    }
//
//    @Override
//    public void loadImageRound(Context context, ImageView imageView, String url, int error) {
//        loadImgDefault(context, imageView, url, error);
//    }
//
//    @Override
//    public void loadImageRound(Context context, ImageView imageView, String url, int error, int load) {
//        loadImgDefault(context, imageView, url, error,load);
//    }

    /** 加载正常图片 */
    public static void loadImgDefault(Context context,ImageView imageView,String url){
        loadImgDefault(context, imageView, url, ERROR_IMG);
    }
    public static void loadImgDefault(Context context,ImageView imageView,String url,int errorImg){
        loadImgDefault(context, imageView, url, errorImg, LOAD_IMG);
    }
    public static void loadImgDefault(Context context,ImageView imageView,String url,int errorImg,int loadImg){
        loadImage(context, imageView, url,errorImg,loadImg,null);
    }

//    /** 加载圆形图片 */
//    public static void loadImgCircle(Context context,ImageView imageView,String url){
//        loadImgCircle(context, imageView, url, ERROR_IMG);
//    }
//    public static void loadImgCircle(Context context,ImageView imageView,String url,int errorImg){
//        loadImgCircle(context, imageView, url, errorImg, LOAD_IMG);
//    }
//    public static void loadImgCircle(Context context,ImageView imageView,String url,int errorImg,int loadImg){
//        loadImage(context, imageView, url, errorImg, loadImg, new GlideCircleTransform(context));
//    }
//
//    /** 加载圆角图片 */
//    public static void loadImgRound(Context context,ImageView imageView,String url){
//        loadImgRound(context, imageView, url, ERROR_IMG);
//    }
//    public static void loadImgRound(Context context,ImageView imageView,String url,int errorImg){
//        loadImgRound(context, imageView, url, errorImg, LOAD_IMG);
//    }
//    public static void loadImgRound(Context context,ImageView imageView,String url,int errorImg,int loadImg){
//        loadImage(context, imageView, url, errorImg, loadImg, new GlideRoundTransform(context,10));
//    }

    private static void loadImage(Context context,ImageView imageView,String url,int errorImg,int loadImg,BitmapTransformation type){
        if(url == null || url.isEmpty() ){
            imageView.setImageResource(errorImg);
            return;
        }if(url.length()>=8){
            if( url.substring(0, 7).equals("http://") || url.substring(0, 8).equals("https://")){
                Log.e("imgLoad", "imgLoad: " + url);
            }else{
                url = UrlConstants.getInstance(context).getImgUrl()+url;
            }
        }
        if(type == null && context!= null){
//            Glide.getPhotoCacheDir()
            try {
                Glide.with(context)
                        .load(url)
                        .animate(R.anim.anim1)//设置一些简单的动画  如图片逐渐清晰
                                // 自己实现BitmapTransformation
                        .error(errorImg)    //设置加载失败后的图片
    //                    .placeholder(loadImg) //设置加载中的图片
                        .into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Glide::", "===IllegalArgumentException: You cannot start a load for a destroyed activity");
            }
            return ;
        }
//        AhLog.e("有类型");
//        if(url.isEmpty() || !url.substring(url.length()-4, url.length()).equals(".jpg")){
//            AhLog.e("为空或者是");
//            Glide.with(context)
//                    .load(errorImg)
//                    .animate(R.anim.anim1)//设置一些简单的动画  如图片逐渐清晰
//                    .transform(type) //设置图片的样式  如圆角 和 圆形图片
//                            // 自己实现BitmapTransformation
//                    .error(errorImg)    //设置加载失败后的图片
////                .placeholder(loadImg) //设置加载中的图片
//                    .into(imageView);
//            return ;
//        }
//        AhLog.e("有类型不为空");
//        Glide.with(context)
//                .load(url)
//                .animate(R.anim.anim1)//设置一些简单的动画  如图片逐渐清晰
//                .transform(type) //设置图片的样式  如圆角 和 圆形图片
//                        // 自己实现BitmapTransformation
//                .error(errorImg)    //设置加载失败后的图片
////                .placeholder(loadImg) //设置加载中的图片
//                .into(imageView);
//
//
    }
}
