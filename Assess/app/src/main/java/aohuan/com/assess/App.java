package aohuan.com.assess;

import android.app.Application;

import aohuan.com.asyhttp.InitAohuanClass;


/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化加载错误页
        InitAohuanClass.init();
    }

}
