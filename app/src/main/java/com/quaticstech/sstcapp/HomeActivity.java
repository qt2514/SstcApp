package com.quaticstech.sstcapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
ListView LVsublist;
String Ssubcateg_uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
LVsublist=findViewById(R.id.subcateg_list);
        final Intent intent = getIntent();
        Bundle Bintent = intent.getExtras();
        if(Bintent != null)
        {
            Ssubcateg_uid=(String) Bintent.get("serviceuid");

        }
        new SubcategAsyncList().execute(GlobalUrl.Home_Activity+"?serv_uid="+Ssubcateg_uid);

    }
    public class Schedule_class extends ArrayAdapter {
        private List<Subcateg_list> SubcategModeList;
        private int resource;
        Context context;
        private LayoutInflater inflater;

        Schedule_class(Context context, int resource, List<Subcateg_list> objects) {
            super(context, resource, objects);
            SubcategModeList = objects;
            this.context = context;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }


        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(resource, null);
                holder = new ViewHolder();

                holder.TVCateg_name = convertView.findViewById(R.id.categ_name);
                holder.TVSubcateg_name=convertView.findViewById(R.id.subcateg_name);
                holder.TVSubcateg_price=convertView.findViewById(R.id.subcateg_price);


                convertView.setTag(holder);
            }//ino
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            Subcateg_list supl = SubcategModeList.get(position);
            holder.TVCateg_name.setText(supl.getService_categ_name());
            holder.TVSubcateg_name.setText(supl.getServ_selection_name());
            holder.TVSubcateg_price.setText(supl.getServ_amt());

            return convertView;
        }

        class ViewHolder {
            private TextView TVCateg_name,TVSubcateg_name,TVSubcateg_price;



        }
    }



    @SuppressLint("StaticFieldLeak")
    public class SubcategAsyncList extends AsyncTask<String, String, List<Subcateg_list>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  avi.show();
        }

        @Override
        protected List<Subcateg_list> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("result");
                List<Subcateg_list> milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    Subcateg_list catego = gson.fromJson(finalObject.toString(), Subcateg_list.class);
                    milokilo.add(catego);
                }

                return milokilo;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final List<Subcateg_list> SubcategMode) {
            super.onPostExecute(SubcategMode);

            if ((SubcategMode != null) && (SubcategMode.size()>0))
            {

                Schedule_class adapter = new Schedule_class(HomeActivity.this, R.layout.subcateg_list_view, SubcategMode);
                LVsublist.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                LVsublist.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        Subcateg_list item = SubcategMode.get(position);
                        Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                    //    intent.putExtra("serviceuid",item.getServ_uid());
                      //  intent.putExtra("servicename",item.getServ_name());
                        startActivity(intent);
                    }
                });

            }



        }
    }

}
