package com.example.user.shake.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidtimeRequest extends StringRequest {
    final static private String URL = "http://13.125.229.179/insertValidtime.php";
    private Map<String, String> parameters;

    public ValidtimeRequest(String bikecode,int start_time,int end_time,String day,Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("bikecode",bikecode);
        parameters.put("start_time",start_time+"");
        parameters.put("end_time",end_time+"");
        parameters.put("day",day);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
