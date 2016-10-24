package com.vac.vmusic.callback;

import java.util.List;

/**
 * Created by vac on 16/10/17.
 * 网络请求回调
 */
public interface RequestCallback<T> {
    void beforeRequest();
    void requestCompleted();
    void requestSuccess(List<T> data);
    void requestError(String errorMsg);
}
