package com.example.user.shake.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReportRequest extends StringRequest {
    final static private String URL = "http://13.125.229.179/insertReport.php";
    private Map<String, String> parameters;

    public ReportRequest(String borrower,String bikecode, String real_return_time,String return_image_url,String contents, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("borrower",borrower);
        parameters.put("bikecode",bikecode);
        parameters.put("rent_time",real_return_time);
        parameters.put("imageurl",return_image_url);
        parameters.put("report_content",contents);
    }



    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
