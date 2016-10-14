package aohuan.com.assess.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


//import android.support.design.widget.Snackbar;


/**
 * Created by Administrator on 2015/9/26.
 */
public class AhTost {
    /**
     * 上次的时间
     */
    private static long lastTime;
    private static ViewGroup mGroup;
    private static View layout;
    public static void toast(Context context,String toast) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
}

    /** 判断输入框是否为空 */
    public static boolean isNull(Context context,String textContent,String toastContent){
        if(textContent.isEmpty()){
            toast(context,toastContent);
        }
        return textContent.isEmpty();
    }
}
