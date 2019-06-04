package com.example.user.shake.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetTokenRequest extends StringRequest {
    final static private String URL = "http://13.125.229.179/getToken.php";
    private Map<String, String> parameters;

    public GetTokenRequest(String userID,Response.Listener<String> listener){
        super(Request.Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
