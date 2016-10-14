package aohuan.com.asyhttp;

/**
 * Created by dodo in Administrator on 2015/8/13.
 * qq  2390183798
 */
public enum ExceptionType {
    JsonParseException("json解析错误"), JsonMappingException("jso映射错误"), IOException("io错误"), Exception("未知错误"), NoNetworkException("没有网络"),
    RequestFailException("请求失败"),ParamsException("参数错误"),;
    private String detail;

    ExceptionType(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }
}
