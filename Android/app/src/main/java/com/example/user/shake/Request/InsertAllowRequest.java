package com.example.user.shake.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class InsertAllowRequest extends StringRequest {
    final static private String URL = "http://13.125.229.179/insertAllow.php";
    private Map<String, String> parameters;

    public InsertAllowRequest(int allow,String bikecode,Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("allow",allow+"");
        parameters.put("bikecode",bikecode);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
