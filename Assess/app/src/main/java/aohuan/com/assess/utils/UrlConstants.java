package aohuan.com.assess.utils;


import android.content.Context;


public class UrlConstants {
	private static UrlConstants mConstants;
	private Context mContext;
	public   static  boolean NEED_FRESH_USER_INFO=true;
	// TODO 单例模式
	private UrlConstants(Context context) {
		this.mContext = context;
	}
	public static UrlConstants getInstance(Context context) {
		if(mConstants == null){
			mConstants = new UrlConstants(context);
		}
		return mConstants;
	}
	/** 图片地址前缀 */
	public String getImgUrl(){
		return "http://7xpkfp.com1.z0.glb.clouddn.com/";

	}
}
