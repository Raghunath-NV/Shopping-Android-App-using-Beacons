package mad.com.inclass03;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Nearable;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    static AppAdapter adapter;
    BeaconManager beaconManager;
    List<Region> lstRegion;
    String oldCategory="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        beaconManager = new BeaconManager(this);
// add this below:
        lstRegion=new ArrayList<Region>();
        lstRegion.add(new Region("grocery", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),15212,31506));
        lstRegion.add(new Region("lifestyle", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),48071,25324));
        lstRegion.add(new Region("produce", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 45153, 9209));


        beaconManager.setRangingListener(new BeaconManager.RangingListener() {

            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    Toast.makeText(MainActivity.this,"on beacon discover "+list.get(0).getMajor(),Toast.LENGTH_SHORT).show();

                    for(Region reg : lstRegion){
                        if(list.get(0).getMajor()==reg.getMajor()){
                            if(oldCategory.isEmpty() || !oldCategory.equals(reg.getIdentifier())) {
                                    Toast.makeText(MainActivity.this, reg.getIdentifier(), Toast.LENGTH_LONG).show();
                                    oldCategory=reg.getIdentifier();
                                    new AppAsynTask(MainActivity.this).execute(reg.getIdentifier());
                                }

                        }
                    }

             }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                Toast.makeText(MainActivity.this, "beacon connect", Toast.LENGTH_LONG).show();
                beaconManager.startRanging(new Region("this", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null));
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        beaconManager.stopRanging(new Region("this", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null));
        Toast.makeText(MainActivity.this,"beacon stop",Toast.LENGTH_LONG).show();

    }
    public void buildUI(final ArrayList<Products> appsList) {
        adapter=new AppAdapter(this,R.layout.activity_list_view,appsList);
        ListView lst=(ListView) findViewById(R.id.lstView);
        lst.setAdapter(adapter);
    }
    public class AppAsynTask extends AsyncTask<String,Void,ArrayList<Products>> {
        ProgressDialog pd;
        Context con;
        AppAsynTask(Context main){
            con=main;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected ArrayList<Products> doInBackground(String... params) {
            try {
                StringBuilder sr = new StringBuilder();
                String urlPath="http://52.42.214.119:3000/info?region="+params[0];
                URL url = new URL(urlPath);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int requestCode = con.getResponseCode();
                if (requestCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String line = "";
                    while ((line = br.readLine()) != null) {
                        sr.append(line);
                    }

                    return JsonUtil.ProductsJsonParser.getAppJson(sr.toString());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(ArrayList<Products> Prods) {
            super.onPostExecute(Prods);
            buildUI(Prods);

        }
    }
}


