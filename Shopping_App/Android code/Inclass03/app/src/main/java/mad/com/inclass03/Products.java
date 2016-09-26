package mad.com.inclass03;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sridevi on 9/8/2016.
 */
public class Products {
    String discount,name, photoURL,price, region;
    static public Products createApp(JSONObject js){
        Products prod=new Products();
        try {
            prod.setName(js.getString("pname"));
            prod.setDiscount(js.getString("discount"));
            prod.setPhotoURL(js.getString("photo"));
            prod.setPrice(js.getString("price"));
            prod.setRegion(js.getString("region"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return prod;
    }
    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
