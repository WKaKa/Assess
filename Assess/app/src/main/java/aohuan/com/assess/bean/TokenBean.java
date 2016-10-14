package aohuan.com.assess.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/8 0008.
 */
public class TokenBean {

    /**
     * status : success
     * success : true
     * data : [{"token":"4VFupmQot3IaTc0NSPclXIFaVz7ElGWNz87tkymc:tZBlcDXbURQGCn1ZwDg_FglaTuM=:eyJzY29wZSI6InpoaW1nIiwiZGVhZGxpbmUiOjE0NzIwMTA2Nzd9"}]
     * msg :
     */

    private String status;
    private boolean success;
    private String msg;
    /**
     * token : 4VFupmQot3IaTc0NSPclXIFaVz7ElGWNz87tkymc:tZBlcDXbURQGCn1ZwDg_FglaTuM=:eyJzY29wZSI6InpoaW1nIiwiZGVhZGxpbmUiOjE0NzIwMTA2Nzd9
     */

    private List<DataEntity> data;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String token;

        public void setToken(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}
