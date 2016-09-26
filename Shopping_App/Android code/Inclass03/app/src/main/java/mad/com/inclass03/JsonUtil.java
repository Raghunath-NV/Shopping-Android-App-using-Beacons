package mad.com.inclass03;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sridevi on 9/8/2016.
 */
public class JsonUtil {
    public static class ProductsJsonParser{
        public static ArrayList<Products> getAppJson(String jsonData){
            ArrayList<Products> appsList=new ArrayList<Products>();
            try {
                //JSONObject obj=new JSONObject(jsonData);
               // JSONArray appStoreArray=obj.getJSONArray("results");
                JSONArray appStoreArray=new JSONArray(jsonData);
                for(int i=0;i<appStoreArray.length();i++){
                    appsList.add(Products.createApp(appStoreArray.getJSONObject(i)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return appsList;
        }
    }
}
