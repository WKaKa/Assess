package aohuan.com.assess.utils;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import aohuan.com.assess.bean.GoodsAssessBean;
import aohuan.com.assess.bean.ReturnBean;
import aohuan.com.assess.utils.multi_image_selector.utils.BitmapUtils;


/**
 * Created by fml on 2016/8/27 0027.
 * Project_Name:
 * Project_Introduction:
 * Modified_By:
 */
public class AssessImgHelp {
    public ArrayList<GoodsAssessBean> mBeanList = new ArrayList<>();

    public AssessImgHelp(List<String> ids, List<String> icon){
        for(int i=0;i<ids.size();i++){
            mBeanList.add(new GoodsAssessBean(ids.get(i), icon.get(i)));
        }
    }

    /**
     *
     * @param index 对应item的位置
     * @param url  每个item里面的URL
     */
    public void save(int index,String url){
        if(mBeanList.size()>index){
            mBeanList.get(index).save(url);
        }
    }

    /**
     *
     * @return
     */
    public String getUrl(){
        String urlSum="";
        for(int i=0;i< mBeanList.size();i++){
            urlSum += mBeanList.get(i).getItemUrl();
        }
        return urlSum.substring(0,urlSum.length()-1);
    }

    public ArrayList<GoodsAssessBean> getBeanList(){
        return mBeanList;
    }

    /**
     *
     * @param outIndex item的位置
     * @param bitmap bitmap用于本地显士
     * @param url  七牛返回地址
     * @param inIndex  图片位置
     */
    public void doQiNiuDone(int outIndex, Bitmap bitmap, String url,int inIndex){
        mBeanList.get(outIndex).addOrSetImage(BitmapUtils.compressImage(bitmap), inIndex, url);
    }
    public void longDelete(int outIndex,int inIndex){
        mBeanList.get(outIndex).deleteImg(inIndex);
    }


    public String jsonSString(){
        String str="";
        ArrayList<ReturnBean> beanList = new ArrayList<>();
        for(int i=0; i< getBeanList().size(); i++){
            str += getBeanList().get(i).toSString();
            beanList.add(getBeanList().get(i).getReturnBean());
        }
        Log.e("123", "beanList" + JsonUtil.object2JSON(beanList));
        return JsonUtil.object2JSON(beanList);

    }


}
