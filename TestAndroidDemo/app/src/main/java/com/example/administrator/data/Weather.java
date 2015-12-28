package com.example.administrator.data;

/**
 * Created by Administrator on 2015/12/23.
 */
public class Weather
{
    public String quName;
    public String pyName;
    public String cityName;
    public int state1;
    public int state2;
    public String stateDetailed;
    public int tem1;
    public int tem2;
    public String windState;

    public Weather(String quName, String pyName, String cityName, int state1,
                   int state2, String stateDetailed,
                   int tem1, int tem2, String windState)
    {
        this.quName = quName;
        this.pyName = pyName;
        this.cityName = cityName;
        this.state1 = state1;
        this.state2 = state2;
        this.stateDetailed = stateDetailed;
        this.tem1 = tem1;
        this.tem2 = tem2;
        this.windState = windState;
    }

    public String weatherToString()
    {
        StringBuffer stringBuffer = new StringBuffer("Weather = ");
        stringBuffer.append("quName =");
        stringBuffer.append(this.quName);
        stringBuffer.append("+ pyName =");
        stringBuffer.append(this.pyName);
        stringBuffer.append("+ cityName =");
        stringBuffer.append(this.cityName);
        stringBuffer.append("+ state1 =");
        stringBuffer.append(this.state1);
        stringBuffer.append("+ state2 =");
        stringBuffer.append(this.state2);
        stringBuffer.append("+ stateDetailed =");
        stringBuffer.append(this.stateDetailed);
        stringBuffer.append("+ tem1 =");
        stringBuffer.append(this.tem1);
        stringBuffer.append("+ tem2 =");
        stringBuffer.append(this.tem2);
        stringBuffer.append("+ windState =");
        stringBuffer.append(this.windState);

        return stringBuffer.toString();
    }
}
