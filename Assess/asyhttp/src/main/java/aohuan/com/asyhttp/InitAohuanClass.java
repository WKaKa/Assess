package aohuan.com.asyhttp;

import aohuan.com.asyhttp.empty.LoadingAndRetryManager;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class InitAohuanClass {
    public static void init(){
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_loading;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty;
    }
}
