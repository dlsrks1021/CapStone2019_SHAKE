package com.example.user.shake.Request;

import com.example.user.shake.PhpConnect;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PhpRequest {

    public static ArrayList<String> connect(String url){
        PhpConnect task2 = new PhpConnect();
        ArrayList<String> reviewList = new ArrayList<>();
        try {
            reviewList= task2.execute(url).get();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e) {
            e.printStackTrace();
        }
        return reviewList;
    }

    public static int getReviewCount(String bikecode){
        int count = 0;
        ArrayList<String> reviewList = connect("http://13.125.229.179/getReviewCount.php?bikecode="+bikecode);
        count = reviewList.size();
        return count;
    }

    public static float getBikeRating(String bikecode){
        ArrayList<String> sBikeRating = connect("http://13.125.229.179/getRating.php?bikecode="+bikecode);
        if (sBikeRating.size() == 0)
            return 0;
        float rating = Float.parseFloat(sBikeRating.get(0));
        return rating;
    }

    public static String getBikecodeByRentnumber(int rentnumber){
        ArrayList<String> bikecode= connect("http://13.125.229.179/getBikecodeByRentnumber.php?rentnumber="+rentnumber);
        return bikecode.get(0);
    }

    public static void updateBikeRating(String bikecode, float rating){
        connect("http://13.125.229.179/updateBikeRating.php?bikecode="+bikecode+"?rating="+rating);
    }
}
