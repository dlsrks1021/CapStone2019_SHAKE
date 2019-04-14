package com.example.user.shake;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest{
    final static private String URL = "http://13.125.229.179/Register.php";
    private Map<String, String> parameters;
    Context context;

    public RegisterRequest(String userID, String userPassword, String userName, String userEmail,String phonenumber,Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);
        parameters.put("userName",userName);
        parameters.put("userEmail",userEmail);
        parameters.put("phonenumber",phonenumber);
        /*parameters.put("grade",userGrade+"");
        parameters.put("report_count",report_count+"");
        parameters.put("point",point+"");
        parameters.put("share_account",share_count+"");
        parameters.put("account",account+"");*/
    }
//, String phonenumber, String userEmail,float userGrade, int report_count, int point, int share_count, int account
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
