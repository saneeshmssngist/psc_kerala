package com.saneesh.psc_kerala.Model;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by saNeesH on 2018-05-31.
 */

public class ResponseResult<T> {

    String code;

    String status;

    T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
