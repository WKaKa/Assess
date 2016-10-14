package aohuan.com.assess.baseactivity;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.zhy.autolayout.AutoLayoutActivity;

import aohuan.com.assess.R;
import aohuan.com.assess.utils.AhTost;
import aohuan.com.assess.utils.SystemBarTintManager;
import butterknife.ButterKnife;


public abstract class BaseActivity extends AutoLayoutActivity {
    private static Context context;
    private static long toastNextTime;
    @Override
    protected void onCreate(Bundle arg0) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(arg0);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context = getApplicationContext(); //要写成这个，不要用this;否则toast会文字不居中
        try {
            setContentView(InjectHelper.injectObject(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ButterKnife.inject(BaseActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.color_title);//通知栏所需颜色
        }
    }
    /** 设置状态 */
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    /** 显示Toast */
    public static void toast(String toast) {
        AhTost.toast(context, toast);
    }
    /** 跳转页面 */
    public static void startIntent(Class<?> cla) {
        Intent intent = new Intent(context, cla);
        context.startActivity(intent);
    }
    /** 跳转页面 */
    public static void startIntentClearTop(Class<?> cla) {
        Intent intent = new Intent(context, cla);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }
    /** 判断输入框是否为空 */
    public boolean isNull(Context context,EditText textContent,String toastContent){
        return AhTost.isNull(context, textContent.getText().toString(), toastContent);
    }
    /** 跳转页面(可以带一个参数的) */
    public static void startIntent(Class<?> cla,String key,String value) {
        Intent intent = new Intent(context, cla);
        intent.putExtra(key,value);
        context.startActivity(intent);
    }
    /** 显示Log */
    public static void log(String toast) {
        Log.i("TAG", toast);
    }
}
