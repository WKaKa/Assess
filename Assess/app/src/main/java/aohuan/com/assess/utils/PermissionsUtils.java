package aohuan.com.assess.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
/**
 * Created by Administrator on 2016/7/6 0006.
 */
public class PermissionsUtils extends Activity {
    private Activity mActivity;
    /**D动态权限返回码*/
    public static final int PERCODE= 1;
    public PermissionsUtils(Activity activity){
        mActivity = activity;
    }
    /**读取外部存储权限*/
    public boolean getPermissionRead (){
        if(Build.VERSION.SDK_INT >= 23){
                if(ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return true;
            }
        }
        return false;
    }
    /**拍照权限*/
    public boolean getPermissionCamera(){
        if(Build.VERSION.SDK_INT>= 23){
            if(ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, 1);
                return true;
            }
        }
        return false;
    }
    /**定位权限*/
    public boolean getPermissionLocation(){
        if(Build.VERSION.SDK_INT >=23){
            if(ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

                return true;

            }
        }
        return false;
    }
    int index=0;
    // 获取所有权限
    public boolean getPermissionLisr(String [] permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                ActivityCompat.requestPermissions(mActivity, permissions, 1);
                index++;
            }
        }
        if(index >0){
            index = 0;
            return true;
        }else {
            index = 0;
            return false;
        }
    }


    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }
    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mActivity, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

}
