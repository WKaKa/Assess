package aohuan.com.asyhttp;


import aohuan.com.asyhttp.empty.LoadingAndRetryManager;

public interface IUpdateUI<T> {
	void update(T allData, LoadingAndRetryManager viewManage);

	void sendFail(ExceptionType s,LoadingAndRetryManager viewManage);
}
