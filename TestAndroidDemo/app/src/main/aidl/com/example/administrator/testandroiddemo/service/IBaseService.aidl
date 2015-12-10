// IBaseService.aidl
package com.example.administrator.testandroiddemo.service;

// Declare any non-default types here with import statements
import com.example.administrator.testandroiddemo.service.IBaseServiceCallback;
import com.example.administrator.data.Person;
import com.example.administrator.data.AppInfo;
//import android.graphics.Bitmap;

interface IBaseService
{
    String test(String test);
    void testPerson(in Person per);
    Person getPersonTest();
    void downloadImage(String url, IBaseServiceCallback cb);
    Person testPersonIn(in Person per);
    Person testPersonOut(out Person per);
    Person testPersonInOut(inout Person per);
    List<AppInfo> getAppInfos();
    String SaveBitmapToFile(in Bitmap bitmap, String pathName);

}
