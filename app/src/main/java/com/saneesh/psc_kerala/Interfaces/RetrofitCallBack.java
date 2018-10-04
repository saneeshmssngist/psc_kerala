package com.saneesh.psc_kerala.Interfaces;

/**
 * Created by saNeesH on 2018-05-31.
 */

public interface RetrofitCallBack<T> {

    public void Success(T status);

    public void Failure(String error);


}
