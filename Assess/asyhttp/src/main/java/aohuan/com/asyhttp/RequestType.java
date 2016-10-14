package aohuan.com.asyhttp;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public enum  RequestType {
    PostRequest("post请求"),GetRequest("get请求");

    private String requestType;
    RequestType(String type){
        requestType= type;
    }
    public String getRequestType(){
        return requestType;
    }
}
