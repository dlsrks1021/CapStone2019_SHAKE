package com.example.user.shake.Request;

import android.content.Context;

import com.example.user.shake.CategoryItem;
import com.example.user.shake.GpsInfo;
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
        count = Integer.parseInt(reviewList.get(0));
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

    public static void updateBikeReviewCount(String bikecode){
        connect("http://13.125.229.179/updateBikeReviewCount.php?bikecode="+bikecode);
    }

    public static ArrayList<CategoryItem> getCategoryItem(Context context){
        ArrayList<CategoryItem> itemList = new ArrayList<>();
        ArrayList<String> rowItemList= connect("http://13.125.229.179/getBikeListCategory.php");
        for (int i = 0; i < rowItemList.size(); i += 8){
            CategoryItem item = new CategoryItem();
            Double latitude = Double.parseDouble(rowItemList.get(i));
            Double longitude = Double.parseDouble(rowItemList.get(i + 1));
            int cost = Integer.parseInt(rowItemList.get(i + 2));
            String image = rowItemList.get(i + 3);
            String name = rowItemList.get(i + 4);
            String type = rowItemList.get(i + 5);
            float rating = Float.parseFloat(rowItemList.get(i + 6));
            String bikecode = rowItemList.get(i + 7);
            Double distance, gapLatitude, gapLongitude;
            GpsInfo gpsInfo = new GpsInfo(context);
            gapLatitude = latitude - gpsInfo.getLatitude();
            gapLongitude = longitude - gpsInfo.getLongitude();
            //위도 경도 km로 단위 변경
            gapLatitude *= 110;
            gapLongitude *= 88.74;
            distance = Math.sqrt(Math.pow(gapLatitude, 2) + Math.pow(gapLongitude, 2));

            item.setName(name);
            item.setType(type);
            item.setDistance(distance);
            item.setImageUrl(image);
            item.setPrice(cost);
            item.setRating(rating);
            item.setBikecode(bikecode);

            itemList.add(item);
        }

        return itemList;
    }
}
