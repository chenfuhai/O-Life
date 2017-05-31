package com.olife.theserver;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


/**
 * Created by chenfuhai on 2017/4/11 0011.
 */

public class HttpUploadUtil {


    private static String read(InputStream in) throws IOException {
        byte[] data;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[]buf = new byte[1024];
        int len = 0;
        Log.i("fuhai", "parseJSON: 0.3");
        while((len = in.read(buf))!=-1){
            Log.i("fuhai", "parseJSON: 0.4");
            bout.write(buf, 0, len);
        }
        Log.i("fuhai", "parseJSON: 0.5");
        data = bout.toByteArray();
        Log.i("fuhai", "parseJSON: 0.6");
        return new String(data,"UTF-8");
    }

    private static ArrayList<Student> parseJSON(InputStream in)throws Exception{
        ArrayList<Student> students = new ArrayList<Student>();
        Student student = null;
        Log.i("fuhai", "parseJSON: 0.1");
        String str = read(in);
        Log.i("fuhai", "parseJSON: 0.2"+str);
        JSONArray array = new JSONArray(str);
        Log.i("fuhai", "parseJSON: 1");
        int length = array.length();
        for(int i=0;i<length;i++){
            Log.i("fuhai", "parseJSON: 2");
            JSONObject object = array.getJSONObject(i);
            Log.i("fuhai", "parseJSON: 3");
            student = new Student(object.getString("sno"),
                    object.getString("sname"),
                    object.getString("sclass"),
                    object.getString("scollege"),
                    object.getString("sgrade"),
                    object.getString("ssex"),
                    object.getInt("sstatus"));
            students.add(student);
        }
        return students;
    }


    public static Student getStudentByID(String surl,String sno){

        Log.i("fuhai", "getALLStudent:surl "+surl);
        URL url = null;
        ArrayList<Student> students = null;
        try {
            url = new URL(surl);


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            ArrayList<BasicNameValue> params = new ArrayList<>();
            params.add(new BasicNameValue("stuSno",sno));
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(params));
            writer.flush();
            writer.close();
            os.close();


            int code = conn.getResponseCode();




            if (code== HttpURLConnection.HTTP_OK){
                students = parseJSON(conn.getInputStream());
            }

            Log.i("fuhai", "getALLStudent: "+code);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        }catch (ConnectException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return students.get(0);


    }


    public static ArrayList<Student> getALLStudent(String surl){

        Log.i("fuhai", "getALLStudent:surl "+surl);
        URL url = null;
        ArrayList<Student> students = null;
        try {
            url = new URL(surl);


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();




            int code = conn.getResponseCode();




            if (code== HttpURLConnection.HTTP_OK){
                students = parseJSON(conn.getInputStream());
            }

            Log.i("fuhai", "getALLStudent: "+code);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        }catch (ConnectException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return students;


    }



    public static String postWithParams(String surl,ArrayList<BasicNameValue> params)  {
        String line = "failer";
        String msg = "";
        URL url = null;
        try {
            url = new URL(surl);


        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.connect();

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(getQuery(params));
        writer.flush();
        writer.close();
        os.close();

        int code = conn.getResponseCode();
        System.out.println(code);
        if (code== HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((line=in.readLine())!=null){
                System.out.println(line);
                msg = "success";
            }
        }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        }catch (ConnectException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        return msg;
    }

    private static String getQuery(ArrayList<BasicNameValue> params)
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (BasicNameValue pair : params)
        {
            if (first)
                first = false;//第一次不做
            else
                result.append("&");

            try {
                result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            result.append("=");
            try {
                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return result.toString();
    }

}
