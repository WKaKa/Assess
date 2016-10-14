package aohuan.com.assess.bean;

/**
 * Created by fml on 2016/8/29 0029.
 * Project_Name:
 * Project_Introduction:
 * Modified_By:
 */
public class ReturnBean {
    String comment_img;
    String goods_num;
    String ordergoods_id;
    String content;

    public String getComment_img() {
        return comment_img;
    }

    public void setComment_img(String comment_img) {
        this.comment_img = comment_img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

    public String getOrdergoods_id() {
        return ordergoods_id;
    }

    public void setOrdergoods_id(String ordergoods_id) {
        this.ordergoods_id = ordergoods_id;
    }
}
