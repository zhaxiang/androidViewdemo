package com.example.administrator.volley;

import java.util.List;

/**
 * Created by Administrator on 2016/1/20.
 */
public class ApiResultCustom<T>
{
    public final static String SUCCESS_CODE = "0";//0--无错误; 1--无数据; 2--查询失败  3--优惠券不可用
    public final static String FAIL_CUSTOMER = "3";
    public final static String CODE = "errorNum";
    public final static String MESSAGE = "errorInfo";

    private T object;//拿到解析出来的object
    private List<T> list;//拿到解析出来的list
    private String source;//拿到所有的数据

    private String errorNum = "-1";//没有解析结果
    private String errorInfo = MESSAGE;

    public ApiResultCustom()
    {

    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(String errorNum) {
        this.errorNum = errorNum;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
