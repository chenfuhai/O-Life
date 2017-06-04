package com.olife.test;

import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Created by chenfuhai on 2017/6/4 0004.
 * 共有两个方法 上传文件 上传JSON
 * 封装http请求
 */

public class HttpUntils {
    private static HttpUntils httpUntils;

    private Handler handler = new Handler();
    private String result = "";
    private  int code;
    private int readTimeOut = 10 * 1000; // 读取超时
    private int connectTimeout = 10 * 1000; // 超时时间

    interface SuccessListener {
        void onSuccessResponse(String result);
    }
    interface FailedListener{
        void onFialed(int connectCode);
    }

    private HttpUntils(){

    }

    public static HttpUntils getInstance(){
        if (httpUntils==null){
            synchronized (HttpUntils.class){
                if (httpUntils == null){
                    httpUntils = new HttpUntils();
                }
            }
        }

        return httpUntils;
    }
    /**
     *  上传JSON给服务器
     * @param RequestURL 地址
     * @param json jsonString
     * @param successListener 成功的回调接口
     * @param failedListener 失败的回调接口
     */
    public void postwithJSON(String RequestURL, String json ,
                             SuccessListener successListener,
                             FailedListener failedListener){

        if (json==null || json.equals("")){
            failedListener.onFialed(-1);
            Log.i("fuhai", "com.olife.test>>HttpUntils>>postwithJSON: 空json");
            return;
        }
        uploadJson(RequestURL,json,successListener,failedListener);

    }

    /**
     * 上传文件
     * @param RequestURL 地址
     * @param fileKey 在服务器那边所需要的名字 <input name="xxx"> XXX 这个
     * @param file 文件
     * @param params 需要附带的参数 也可以为空
     * @param successListener 成功回调的接口
     * @param failedListener 失败回调的接口
     */
    public void postwithFile( String RequestURL, String fileKey,File file,
                              Map<String,String> params , SuccessListener successListener,
                              FailedListener failedListener){


        if (file == null || !file.exists()) {
            Log.i("fuhai", "com.olife.test>>HttpUntils>>postwithFile: 文件不存在");
            failedListener.onFialed(-1);
            return;
        }
        try {

            uploadFile(file, fileKey, RequestURL, params,successListener,failedListener);
        } catch (Exception e) {
            Log.i("fuhai", "com.olife.test>>HttpUntils>>postwithFile: 文件出错，上传出错");
            failedListener.onFialed(-1);
            e.printStackTrace();
            return;
        }


    }
    private void uploadJson(final String RequestURL, final String json ,
                            final SuccessListener successListener,
                            final FailedListener failedListener){

        final String CONTENT_TYPE = "application/json"; // 内容类型
        final String CHARSET = "utf-8";


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(RequestURL);
                    HttpURLConnection conn = (HttpURLConnection) url.getContent();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(connectTimeout);
                    conn.setReadTimeout(readTimeOut);

                    conn.setRequestProperty("Charset", CHARSET); // 设置编码
                    conn.setRequestProperty("connection", "keep-alive");
                    conn.setRequestProperty("Content-Type", CONTENT_TYPE);

                    conn.connect();
                    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                    dos.write(json.getBytes());
                    dos.flush();

                    code = conn.getResponseCode();
                    if (code == 200){
                        //成功连接 接收
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String lineString;
                        while ((lineString=bufferedReader.readLine())!=null){
                            result+=lineString;
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                successListener.onSuccessResponse(result);
                            }
                        });
                    }else{
                        Log.i("fuhai", "com.olife.test>>HttpUntils>>run: 失败"+code);
                        failedListener.onFialed(code);

                    }
                    conn.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    private void uploadFile(final File file, final String fileKey, final String requestUrl,
                            final Map<String,String> params , final SuccessListener successListener,
                            final FailedListener failedListener){
        final String LINEEND ="\r\n";
        final String PREFIX ="--";
        final String BOUNDARY =UUID.randomUUID().toString();
        final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        final String CHARSET = "utf-8";

        if (file == null || !file.exists()){
            Log.i("fuhai", "com.olife.test>>HttpUntils>>uploadFile: 文件为空或者不存在");
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(requestUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.getContent();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(connectTimeout);
                    conn.setReadTimeout(readTimeOut);


                    conn.setRequestProperty("Charset", CHARSET); // 设置编码
                    conn.setRequestProperty("connection", "keep-alive");
                    conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

                    conn.connect();


                    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                    StringBuffer sb;
                    //上传参数
                    if (params != null && params.size() > 0) {
                        Iterator<String> it = params.keySet().iterator();
                        while (it.hasNext()) {
                            sb = null;
                            sb = new StringBuffer();
                            String key = it.next();
                            String value = params.get(key);
                            sb.append(PREFIX).append(BOUNDARY).append(LINEEND);
                            sb.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(LINEEND);
                            sb.append(LINEEND);
                            sb.append(value).append(LINEEND);


                            dos.write(sb.toString().getBytes());
                        }
                    }
                    //上传文件
                    sb = null;
                    sb = new StringBuffer();
                    sb.append(PREFIX).append(BOUNDARY).append(LINEEND);
                    sb.append("Content-Disposition: form-data; name=\""+fileKey+"\"; filename=\""+file.getName()+"\""+LINEEND);
                    sb.append("Content-Type: image/jpeg"+LINEEND);
                    sb.append(LINEEND);

                    dos.write(sb.toString().getBytes());
                    InputStream is = new FileInputStream(file);
                    byte[] bytes = new byte[1024];
                    int len = 0;

                    while ((len = is.read(bytes)) != -1) {

                        dos.write(bytes, 0, len);
                    }
                    is.close();

                    dos.write(LINEEND.getBytes());
                    byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEEND).getBytes();
                    dos.write(end_data);

                    dos.flush();
                    code = conn.getResponseCode();
                    if (code == 200){
                        //成功连接 接收
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String lineString;
                        while ((lineString=bufferedReader.readLine())!=null){
                            result+=lineString;
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                successListener.onSuccessResponse(result);
                            }
                        });
                    }else{
                        Log.i("fuhai", "com.olife.test>>HttpUntils>>run: 失败"+code);
                        failedListener.onFialed(code);
                    }
                    conn.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
