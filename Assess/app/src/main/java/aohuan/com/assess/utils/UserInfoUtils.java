package aohuan.com.assess.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by fml on 2016/10/13 0013.
 * Project_Name:
 * Project_Introduction:
 * Modified_By:
 */
public class UserInfoUtils {
    /** SharedPreferences名称  */
    public static final String SHARED_PREFERENCES = "AOHUANSHOP";
    /**临时存储用户头像*/
    public static final String TEMPORARY_USER_ICON="temporary_user_icon";

    /** 临时存储用户头像*/
    public static void setTemporaryUserIcon(Context mContext, String key) {
        SharedPreferences preferences = mContext.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TEMPORARY_USER_ICON, key);
        editor.commit();
    }
    public static String getTemporaryUserIcon(Context mContext){
        SharedPreferences preferences = mContext.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String id = preferences.getString(TEMPORARY_USER_ICON, "-1");
        return id;
    }

}
