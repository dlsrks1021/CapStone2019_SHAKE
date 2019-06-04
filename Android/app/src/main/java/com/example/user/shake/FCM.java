package com.example.user.shake;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FCM {


    final static private String FCM_URL = "https://fcm.googleapis.com/fcm/send";


    static void send_FCM_Notification(String tokenId, String server_key, String message) {


        try {

            URL url = new URL(FCM_URL);

            HttpURLConnection conn;

            conn = (HttpURLConnection) url.openConnection();

            conn.setUseCaches(false);

            conn.setDoInput(true);

            conn.setDoOutput(true);

            conn.setRequestMethod("POST");

            conn.setRequestProperty("Authorization", "key=" + server_key);

            conn.setRequestProperty("Content-Type", "application/json");



            JSONObject infoJson = new JSONObject();

            infoJson.put("title", "Here is your notification.");

            infoJson.put("body", message);

            JSONObject json = new JSONObject();

            json.put("to", tokenId.trim());

            json.put("notification", infoJson);


            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(json.toString());

            wr.flush();



            int status = 0;

            if (null != conn) {

                status = conn.getResponseCode();

            }


            if (status != 0) {

                if (status == 200) {

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    System.out.println("Android Notification Response : " + reader.readLine());


                } else if (status == 401) {

                    System.out.println("Notification Response : TokenId : " + tokenId + " Error occurred :");


                } else if (status == 501) {

                    System.out.println("Notification Response : [ errorCode=ServerError ] TokenId : " + tokenId);


                } else if (status == 503) {

                    System.out.println("Notification Response : FCM Service is Unavailable  TokenId : " + tokenId);

                }

            }

        } catch (MalformedURLException mlfexception) {

            System.out.println("Error occurred while sending push Notification!.." + mlfexception.getMessage());

        } catch (IOException mlfexception) {

            System.out.println(

                    "Reading URL, Error occurred while sending push Notification!.." + mlfexception.getMessage());

        } catch (JSONException jsonexception) {

            System.out.println(

                    "Message Format, Error occurred while sending push Notification!.." + jsonexception.getMessage());

        } catch (Exception exception) {

            System.out.println("Error occurred while sending push Notification!.." + exception.getMessage());

        }

    }

}