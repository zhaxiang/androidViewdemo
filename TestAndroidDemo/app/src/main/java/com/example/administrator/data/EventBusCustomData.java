package com.example.administrator.data;

/**
 * Created by Administrator on 2015/12/22.
 */
public class EventBusCustomData
{
    private String content = null;

    public EventBusCustomData()
    {

    }

    public EventBusCustomData(String string)
    {
        content = string;
    }

    public EventBusCustomData(EventBusCustomData data)
    {
        this.content = data.content;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
