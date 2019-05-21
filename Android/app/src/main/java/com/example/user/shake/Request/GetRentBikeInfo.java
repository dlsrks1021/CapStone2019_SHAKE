package com.example.user.shake.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetRentBikeInfo extends StringRequest{

    static private String URL = "http://13.125.229.179/getRentBikeInfo.php";
    private Map<String, String> parameters;

    public GetRentBikeInfo(String bikecode, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("bikecode",bikecode);
    }

    //Use Later
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
