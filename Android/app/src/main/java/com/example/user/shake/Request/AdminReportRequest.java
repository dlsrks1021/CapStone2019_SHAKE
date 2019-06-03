package com.example.user.shake.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AdminReportRequest extends StringRequest {
    final static private String URL = "http://13.125.229.179/adminGetReport.php";
    private Map<String, String> parameters;

    public AdminReportRequest(Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
