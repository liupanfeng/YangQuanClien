package com.meishe.yangquan.inter;

public interface OnResponseListener {

    void  onSuccess(Object object);

    void  onSuccess(int type,Object object);

    void  onError(Object obj);

    void  onError(int type,Object obj);

}
