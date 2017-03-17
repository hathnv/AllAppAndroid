package com.hust.thanglv.lunchlist;

import android.app.TabActivity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends TabActivity {

    List<Restaurant> model = new ArrayList<Restaurant>();
    List<Restaurant> model2 = new ArrayList<Restaurant>();
    RestaurantAdapter adapter = null;
    RestaurantAdapter2 adapter2 = null;
    EditText name=null;
    EditText address=null;
    RadioGroup types=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView list=(ListView)findViewById(R.id.lvRes);

        ListView list2 = (ListView)findViewById(R.id.sitdown);

        name=(EditText) findViewById(R. id. name);
        address=(EditText) findViewById(R. id. addr);
        types=(RadioGroup) findViewById(R. id. types);

        adapter=new RestaurantAdapter();
        list.setAdapter(adapter);

        adapter2=new RestaurantAdapter2();
        list2.setAdapter(adapter2);

        TabHost.TabSpec spec=getTabHost().newTabSpec("tag1");
        spec.setContent(R.id.lvRes);
        spec.setIndicator("List", getResources().getDrawable(R.drawable.restaurant));
        getTabHost(). addTab(spec);

        spec=getTabHost().newTabSpec("tag2");
        spec.setContent(R.id.details);
        spec.setIndicator("Details", getResources().getDrawable(R.drawable.apple));
        getTabHost().addTab(spec);

        spec=getTabHost().newTabSpec("tag3");
        spec.setContent(R.id.sitdown);
        spec.setIndicator("Sitdown", getResources().getDrawable(R.drawable.doughnut));
        getTabHost().addTab(spec);

        list. setOnItemClickListener(onListClick);
    }

    /**
     * Xử ls sự kiện onClick cho btn Save
     * @param v
     */
    public void btSaveClick(View v) {
        Restaurant r = new Restaurant();
        EditText name = (EditText) findViewById(R.id.name);
        EditText address = (EditText) findViewById(R.id.addr);

        r.setName(name.getText().toString());
        r.setAddress(address.getText().toString());

        RadioGroup types = (RadioGroup) findViewById(R.id.types);

        switch (types.getCheckedRadioButtonId()) {
            case R.id.sit_down:
                r.setType("sit_down");
                adapter2.add(r);
                break;

            case R.id.take_out:
                r.setType("take_out");
                break;

            case R.id.delivery:
                r.setType("delivery");
                break;
        }

        adapter.add(r);

    }

    private AdapterView. OnItemClickListener onListClick=new
            AdapterView. OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent,View view, int position, long id) {
                    Restaurant r=model. get(position);
                    name.setText(r. getName());
                    address. setText(r. getAddress());
                    if (r. getType(). equals("sit_down")) {
                        types. check(R. id. sit_down);
                    }
                    else if (r. getType(). equals("take_out")) {
                        types. check(R. id. take_out);
                    }
                    else {
                        types. check(R. id. delivery);
                    }
                    getTabHost(). setCurrentTab(1);
                }
            };

    class RestaurantAdapter extends ArrayAdapter<Restaurant> {
        RestaurantAdapter() {
            super(MainActivity.this, R.layout.item_restaurant, model);
        }

        public View getView(int position, View convertView,ViewGroup parent) {
            View row=convertView;
            RestaurantHolder holder=null;

            if (row==null) {
                LayoutInflater inflater=getLayoutInflater();

                row=inflater.inflate(R.layout.item_restaurant, parent, false);
                holder=new RestaurantHolder(row);
                row.setTag(holder);
            }
            else {
                holder=(RestaurantHolder)row.getTag();
            }

            holder.populateFrom(model.get(position));

            return(row);
        }
    }

    /**
     * Adapter cho listview
     */
    class RestaurantAdapter2 extends ArrayAdapter<Restaurant> {
        RestaurantAdapter2() {
            super(MainActivity.this, R.layout.item_restaurant, model2);
        }

        public View getView(int position, View convertView,ViewGroup parent) {
            View row=convertView;
            RestaurantHolder holder=null;

            if (row==null) {
                LayoutInflater inflater=getLayoutInflater();

                row=inflater.inflate(R.layout.item_restaurant, parent, false);
                holder=new RestaurantHolder(row);
                row.setTag(holder);
            }
            else {
                holder=(RestaurantHolder)row.getTag();
            }

            holder.populateFrom(model2.get(position));
            if (position % 2 == 0) {
                row.setBackgroundColor(Color.parseColor("#80D8FF"));
                holder.name.setTextColor(Color.parseColor("#FF5722"));
                holder.address.setTextColor(Color.parseColor("#FF5722"));
            }
            if (position % 2 == 1) {
                row.setBackgroundColor(Color.parseColor("#009688"));
                holder.name.setTextColor(Color.parseColor("#FFFFFF"));
                holder.address.setTextColor(Color.parseColor("#FFFFFF"));
            }

            if(model.get(position).getDiscount().equals("0%")){
                holder.icontype.setVisibility(View.GONE);
            }
            if(model.get(position).getDiscount().equals("25%")){
                holder.icontype.setImageResource(R.drawable.apple);
            }
            if(model.get(position).getDiscount().equals("50%")){
                holder.icontype.setImageResource(R.drawable.champagne);
            }
            if(model.get(position).getDiscount().equals("75%")){
                holder.icontype.setImageResource(R.drawable.doughnut);
            }

            return(row);
        }
    }

    class RestaurantHolder {
        private TextView name = null;
        private TextView address = null;
        private ImageView icontype = null;

        RestaurantHolder(View row) {
            name = (TextView) row.findViewById(R.id.tvName);
            address = (TextView) row.findViewById(R.id.tvAddr);
            icontype = (ImageView) row.findViewById(R.id.imDiscount);

        }
        void populateFrom(Restaurant r) {
            name.setText(r.getName());
            address.setText(r.getAddress());
            if (r.getType().equals("sit_down")) {
                icontype.setImageResource(R.drawable.apple);
            }
            else if (r.getType().equals("take_out")) {
                icontype.setImageResource(R.drawable.doughnut);
            }
            else {
                icontype.setImageResource(R.drawable.champagne);
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.option, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.toast) {
            String message="";
            int countSitDown = 0, countTakeOut = 0, Delivery = 0;

            for(int i = 0; i < adapter.getCount(); i++){
                if(adapter.getItem(i).getType().contains("sit_down")){
                    countSitDown++;
                } else if(adapter.getItem(i).getType().contains("take_out")){
                    countTakeOut++;
                } else if(adapter.getItem(i).getType().contains("delivery")){
                    Delivery++;
                }
            }
            message += "Nhà hàng kiểu Sit Down: " + countSitDown + "\n";
            message += "Nhà hàng kiểu Take Out: " + countTakeOut + "\n";
            message += "Nhà hàng kiểu Delivery: " + Delivery;
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return(true);
        }
        return(super.onOptionsItemSelected(item));
    }
}