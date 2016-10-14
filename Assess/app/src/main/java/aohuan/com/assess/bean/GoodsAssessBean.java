package aohuan.com.assess.bean;

import android.graphics.Bitmap;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import aohuan.com.assess.utils.JsonUtil;


/**
 * Created by fml on 2016/8/26 0026.
 * Project_Name:
 * Project_Introduction:
 * Modified_By:
 */
public class GoodsAssessBean implements Serializable {
    private String id;//商品id
    private String content ="";//商品评价内容
    private ArrayList<String> imgList = new ArrayList<>();//七牛返回的图片url
    private ArrayList<Bitmap> bitmapList = new ArrayList<>();//本地的bitmap
    private String icon;//商品图片
    private String starts="0";//商品星级评定


    /**
     * 添加Bitmap
     * @param bitmap
     */
    public void addBitMap(Bitmap bitmap){
        if(bitmapList.size()<3){
            bitmapList.add(bitmap);
        }
    }

    public void addOrSetImage(Bitmap bitmap, int index, String url){
        if(index + 1 <= bitmapList.size()){
            imgList.set(index, url);
            bitmapList.set(index, bitmap);
        }else{
            addBitMap(bitmap);
            save(url);
        }
    }

    public int getBitMapListSize(){
        return bitmapList.size();
    }
    public int getImgListSize(){
        return imgList.size();
    }

    public ArrayList<Bitmap> getBitmapList(){
        return bitmapList;
    }





    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }



    public String getStarts() {
        return starts;
    }

    public void setStarts(String starts) {
        this.starts = starts;
    }



    public GoodsAssessBean(String id, String icon) {
        this.id = id;
        this.icon = icon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @param url  每个item里面的URL
     */
    public void save(String url){
        imgList.add(url);
    }
    /**
     *
     * @return
     */
    public String getItemUrl(){
        String urls="";
        if(imgList.size() > 0){
            for(int i=0;i<imgList.size();i++){
                urls+= imgList.get(i)+";";
            }
            return urls.substring(0,urls.length()-1);
        }else {
            return " ";
        }

    }

    /**
     *
     * @param index
     */
    public void deleteImg(int index){
        if(imgList.size()>index){
            imgList.remove(index);
            bitmapList.remove(index);
        }
    }


    public String toSString(){
        String str = "";
        ReturnBean rb = new ReturnBean();
        rb.setContent(getContent());
        rb.setComment_img(getItemUrl());
        rb.setGoods_num(getStarts());
        rb.setOrdergoods_id(getId());
        try {
            str = JsonUtil.toJsonString(rb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public ReturnBean getReturnBean(){
        ReturnBean rb = new ReturnBean();
        rb.setContent(getContent());
        rb.setComment_img(getItemUrl());
        rb.setGoods_num(getStarts());
        rb.setOrdergoods_id(getId());
        return rb;
    }

//    @JsonIgnoreProperties(value={"hibernateLazyInitializer"})


}
