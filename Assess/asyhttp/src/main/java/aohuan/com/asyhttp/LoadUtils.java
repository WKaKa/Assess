package aohuan.com.asyhttp;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import aohuan.com.asyhttp.empty.LoadingAndRetryManager;
import aohuan.com.asyhttp.empty.OnLoadingAndRetryListener;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class LoadUtils {
    private Activity mActivity;
    private static LoadUtils mLoadUtils= null;
    private LoadingAndRetryManager mLoadingAndRetryManager;
    private Map<String, LoadingAndRetryManager> mLamMap = new HashMap<>();

    private LoadUtils(){

    }
    public static LoadUtils getNewInstance(){
        if(mLoadUtils == null){
            mLoadUtils = new LoadUtils();
        }
        return mLoadUtils;
    }
    public LoadingAndRetryManager getmLoadingAndRetryManager(View view, final OnRetryRefreashListener onRetryRefreashListener){
        if (view==null){
            Log.e("LoadUtils:", "view==null");
            return null;
        };
        if(mLamMap.containsKey(view.getTag()+"")){
            mLoadingAndRetryManager=  mLamMap.get(view.getTag()+ "");
        }else{
            mLoadingAndRetryManager = new LoadingAndRetryManager(view, new OnLoadingAndRetryListener() {
                @Override
                public void setRetryEvent(View retryView) {
                    retryRefreashTextView(retryView, onRetryRefreashListener);
                }
            });
            mLamMap.put(view.getTag()+ "", mLoadingAndRetryManager);
        }
        return mLoadingAndRetryManager;
    }

    private void retryRefreashTextView(View retryView, final OnRetryRefreashListener onRetryRefreashListener)
    {
        View view = retryView.findViewById(R.id.id_btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onRetryRefreashListener != null){
                    onRetryRefreashListener.onClick(v);
                }
            }
        });
    }
    public interface OnRetryRefreashListener{
        void onClick(View view);
    }
}
