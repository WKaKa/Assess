package aohuan.com.assess.utils;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015/12/16 0016.
 */
public interface ImageIf {
    /** 加载正常图片 */
    void loadImageDefault(Context context, ImageView imageView, String url);
    void loadImageDefault(Context context, ImageView imageView, String url, int error);
    void loadImageDefault(Context context, ImageView imageView, String url, int error, int load);
    /** 加载圆角图片 */
//    void loadImageCircle(Context context,ImageView imageView,String url);
//    void loadImageCircle(Context context,ImageView imageView,String url,int error);
//    void loadImageCircle(Context context,ImageView imageView,String url,int error,int load);
    /** 加载圆形图片 */
//    void loadImageRound(Context context,ImageView imageView,String url);
//    void loadImageRound(Context context,ImageView imageView,String url,int error);
//    void loadImageRound(Context context,ImageView imageView,String url,int error,int load);
}
