package aohuan.com.asyhttp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import aohuan.com.asyhttp.empty.LoadingAndRetryManager;
import cz.msebera.android.httpclient.Header;


public class AsyHttpClicenUtils<T> {

	private static AsyncHttpClient client = new AsyncHttpClient();

	private static boolean isPrintDetail = false;
	private final int POST_REQUEST= 0;//post请求方式
	private final int GET_REQUEST= 1;//get请求方式
	private int mRequestType;//判断时那种请求方式
	private String mUrl="";//url地址
	private boolean mIsRequest =false; //判断当前是否正在请求
	private RequestParams mParams= new RequestParams();//请求参数
	private boolean mIsShowDailog= false;//是否显示加载框
	private static AsyHttpClicenUtils mAsyHttpClicenUtils = null;
	private static Map<String, AsyHttpClicenUtils> mAsyMap = new HashMap<>();

	LoadingAndRetryManager mLoadingAndRetryManager;
	private static void printAsyUtil(String msg){
		if(isPrintDetail){
			Log.e("asyClient", "printAsyUtil:  " + msg);
		}
	}
	static{
		client.setTimeout(5000);//15秒超时  默认10秒
	}
	private Activity mContext;
	private IUpdateUI<T> mCallback;
	private Class<T> clazz;
	private T mT;
	private boolean isShowNoNet = true;
//	ProgressUtils pu;
	public static AsyHttpClicenUtils getNewInstance(View view){
		if(view.getTag()== null){
			view.setTag(view.toString());
		}
		if (view == null){
			Log.e("AsyHttpClicenUtils:", "view==null");
			return null;
		}
		if(mAsyMap.containsKey(view.getTag())){
			mAsyHttpClicenUtils = mAsyMap.get(view.getTag()+"");
		}else{
			mAsyHttpClicenUtils = new AsyHttpClicenUtils();
			mAsyMap.put(view.getTag()+ "", mAsyHttpClicenUtils);
		}
		return mAsyHttpClicenUtils;
	}
	public AsyHttpClicenUtils asyHttpClicenUtils(Activity context, Class<T> clazz,View mParent, IUpdateUI<T> callback){
		mContext = context;
		this.mCallback = callback;
		this.clazz = clazz;
//		pu = new ProgressUtils();
		printAsyUtil("initMethod");
		mLoadingAndRetryManager = LoadUtils.getNewInstance().getmLoadingAndRetryManager(mParent, new LoadUtils.OnRetryRefreashListener() {
			@Override
			public void onClick(View retryView) {
				if (!isConnected(mContext)){
					if(isShowNoNet){
						Toast.makeText(mContext, ExceptionType.NoNetworkException.getDetail(), Toast.LENGTH_SHORT).show();
					}
					printAsyUtil("NoNetworkException");
					mLoadingAndRetryManager.closeLoading();
					mCallback.sendFail(ExceptionType.NoNetworkException, mLoadingAndRetryManager);
					return;
				}
				if(mRequestType== POST_REQUEST){
					postRequest();
				}
				if(mRequestType== GET_REQUEST){
					getRequest();
				}
			}
		});
		return mAsyMap.get(mParent.getTag()+ "");
	}
	public AsyHttpClicenUtils asyHttpClicenUtils(Activity context, Class<T> clazz,View mParent,boolean isRequest ,IUpdateUI<T> callback){
		mContext = context;
		mIsRequest = isRequest;
		this.mCallback = callback;
		this.clazz = clazz;
//		pu = new ProgressUtils();
		printAsyUtil("initMethod");
		mLoadingAndRetryManager = LoadUtils.getNewInstance().getmLoadingAndRetryManager(mParent, new LoadUtils.OnRetryRefreashListener() {
			@Override
			public void onClick(View retryView) {
				if (!isConnected(mContext)){
					if(isShowNoNet){
						Toast.makeText(mContext, ExceptionType.NoNetworkException.getDetail(), Toast.LENGTH_SHORT).show();
					}
					printAsyUtil("NoNetworkException");
					mLoadingAndRetryManager.closeLoading();
					mCallback.sendFail(ExceptionType.NoNetworkException, mLoadingAndRetryManager);
					return;
				}
				if(mRequestType== POST_REQUEST){
					postRequest();
				}
				if(mRequestType== GET_REQUEST){
					getRequest();
				}
			}
		});
		return mAsyMap.get(mParent.getTag()+ "");
	}

	private AsyHttpClicenUtils() {
	}

	/** 判断是否要显示网络提示 */
	public void setIsShowNoNet(boolean isShowNoNet){
		this.isShowNoNet = isShowNoNet;
	}
	/** POST请求 */
	public  void post(String url,RequestParams params,final boolean isShowDailog){
		mRequestType = POST_REQUEST;
		mUrl = url;
		mParams= params;
		mIsShowDailog= isShowDailog;
		if(mCallback == null){
			printAsyUtil("mCallback == null");
			return;
		}
		if(clazz == null){
			printAsyUtil("clazz == null");
			mCallback.sendFail(ExceptionType.ParamsException, mLoadingAndRetryManager);
			return;
		}
		if (!isConnected(mContext)){
			if(isShowNoNet){
				Toast.makeText(mContext, ExceptionType.NoNetworkException.getDetail(), Toast.LENGTH_SHORT).show();
			}
			printAsyUtil("NoNetworkException");
			mLoadingAndRetryManager.closeLoading();
			mCallback.sendFail(ExceptionType.NoNetworkException, mLoadingAndRetryManager);
			return;
		}
		postRequest();
	}
	/**post真正请求*/
	private void postRequest(){
		if(mIsRequest){
			return;
		}
		mIsRequest = true;
		client.post(mContext, mUrl, mParams, new TextHttpResponseHandler() {
			@Override
			public void onStart() {
				// TODO 加载框显示
				super.onStart();
				if (mIsShowDailog) {
					//显示加载框
//					pu.showDialog(mContext);
					mLoadingAndRetryManager.showLoading();
				}
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
								  String responseString) {
				Log.e("TAG", "post-->" + decode(responseString));
				printAsyUtil("onSuccess");
				if (statusCode == 200 && responseString != null) {
					printAsyUtil("onSuccess 200");
					try {
						if (clazz == String.class) {
							printAsyUtil("onSuccess 200 String "+ responseString);
							mT = (T) responseString;
						} else {
							printAsyUtil("onSuccess 200 Class before toObject");
							mT = JsonUtil.toObjectByJson(responseString, clazz);
							printAsyUtil("onSuccess 200 Class after toObject");
						}
					} catch (JsonParseException e) {
						printAsyUtil("onSuccess 200 Class JsonParseException");
						mLoadingAndRetryManager.closeLoading();
						mCallback.sendFail(ExceptionType.JsonParseException, mLoadingAndRetryManager);
					} catch (JsonMappingException e) {
						printAsyUtil("onSuccess 200 Class JsonMappingException");
						mLoadingAndRetryManager.closeLoading();
						mCallback.sendFail(ExceptionType.JsonMappingException, mLoadingAndRetryManager);
					} catch (IOException e) {
						printAsyUtil("onSuccess 200 Class IOException");
						mLoadingAndRetryManager.closeLoading();
						mCallback.sendFail(ExceptionType.IOException, mLoadingAndRetryManager);
					} catch (Exception e) {
						printAsyUtil("onSuccess 200 Class Exception");
						mLoadingAndRetryManager.closeLoading();
						mCallback.sendFail(ExceptionType.Exception, mLoadingAndRetryManager);
					}

					if (mT != null) {
						mCallback.update(mT, mLoadingAndRetryManager);
					}else{
						mCallback.sendFail(ExceptionType.Exception, mLoadingAndRetryManager);
					}
//					if (mT == null) {
//						mCallback.sendFail(ExceptionType.Exception);
//					} else {
//						mCallback.update(mT);
//					}
				} else {
					printAsyUtil("onSuccess not 200");
					mCallback.sendFail(ExceptionType.RequestFailException, mLoadingAndRetryManager);
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
								  String responseString, Throwable throwable) {
				if(responseString != null){
					Log.e("AsyHttpClicenUtils",responseString);
				}
				mCallback.sendFail(ExceptionType.RequestFailException, mLoadingAndRetryManager);
			}

			@Override
			public void onFinish() {
				// TODO 加载结束
				super.onFinish();
					mIsRequest = false;
					//显示数据，， 变相关闭加载框
					mLoadingAndRetryManager.closeLoading();
			}
		});
	}

	/**
	 * 判断网络是否连接
	 *
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context) {

		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (null != connectivity) {

			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (null != info && info.isConnected()) {
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}
	public  void get(String url,final boolean isShowDailog){
		get(url, null, isShowDailog);
	}

	/** GET请求 */
	public  void get(String url,RequestParams params,final boolean isShowDailog){
		mRequestType = GET_REQUEST;
		mUrl = url;
		mParams= params;
		mIsShowDailog= isShowDailog;
		if(mCallback == null){
			return;
		}
		if(clazz == null){
			mCallback.sendFail(ExceptionType.ParamsException, mLoadingAndRetryManager);
			return;
		}
		if (!isConnected(mContext)){
			if(isShowNoNet){
				Toast.makeText(mContext, ExceptionType.NoNetworkException.getDetail(), Toast.LENGTH_SHORT).show();
			}
			mLoadingAndRetryManager.closeLoading();
			mCallback.sendFail(ExceptionType.NoNetworkException, mLoadingAndRetryManager);
			return;
		}
		getRequest();
	}
	private  void getRequest() {
		client.get(mContext, mUrl, mParams, new TextHttpResponseHandler() {
			@Override
			public void onStart() {
				// TODO 加载框显示
				super.onStart();
				if(mIsRequest){
					return;
				}
				mIsRequest = true;
				if (mIsShowDailog) {
					//显示加载框
//					pu.showDialog(mContext);
					mLoadingAndRetryManager.showLoading();
				}
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
								  String responseString) {
				Log.e("---TAG", "get-->" + decode(responseString));

				System.out.println("-----s" + responseString);
				if (statusCode == 200 && responseString != null) {
					try {
						if (clazz == String.class) {
							mT = (T) responseString;
//							mCallback.update(mT);
						} else {
							mT = JsonUtil.toObjectByJson(responseString, clazz);
						}
					} catch (JsonParseException e) {
						mLoadingAndRetryManager.closeLoading();
						mCallback.sendFail(ExceptionType.JsonParseException, mLoadingAndRetryManager);
					} catch (JsonMappingException e) {
						mLoadingAndRetryManager.closeLoading();
						mCallback.sendFail(ExceptionType.JsonMappingException, mLoadingAndRetryManager);
					} catch (IOException e) {
						mLoadingAndRetryManager.closeLoading();
						mCallback.sendFail(ExceptionType.IOException, mLoadingAndRetryManager);
					} catch (Exception e) {
						mLoadingAndRetryManager.closeLoading();
						mCallback.sendFail(ExceptionType.Exception, mLoadingAndRetryManager);
					}

					if (mT != null) {
						mCallback.update(mT, mLoadingAndRetryManager);
					}else{
						mCallback.sendFail(ExceptionType.Exception, mLoadingAndRetryManager);
					}
//					if (mT == null) {
//						mCallback.sendFail(ExceptionType.Exception);
//					} else {
//						mCallback.update(mT);
//					}
				} else {
					System.out.println("----" + statusCode);
					mCallback.sendFail(ExceptionType.RequestFailException, mLoadingAndRetryManager);
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
								  String responseString, Throwable throwable) {

				System.out.println("-----fa" + responseString + "---" + statusCode);
				mCallback.sendFail(ExceptionType.RequestFailException, mLoadingAndRetryManager);
			}

			@Override
			public void onFinish() {
				// TODO 加载结束
				super.onFinish();
					//显示数据，， 变相关闭加载框
					mLoadingAndRetryManager.closeLoading();
					mIsRequest = false;

			}
		});
	}

	/** unicode 的decode操作 */
	public static String decode(String unicodeStr) {
		if (unicodeStr == null) {
			return null;
		}
		StringBuffer retBuf = new StringBuffer();
		int maxLoop = unicodeStr.length();
		for (int i = 0; i < maxLoop; i++) {
			if (unicodeStr.charAt(i) == '\\') {
				if ((i < maxLoop - 5)
						&& ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
						.charAt(i + 1) == 'U')))
					try {
						retBuf.append((char) Integer.parseInt(
								unicodeStr.substring(i + 2, i + 6), 16));
						i += 5;
					} catch (NumberFormatException localNumberFormatException) {
						retBuf.append(unicodeStr.charAt(i));
					}
				else
					retBuf.append(unicodeStr.charAt(i));
			} else {
				retBuf.append(unicodeStr.charAt(i));
			}
		}
		return retBuf.toString();
	}

}
