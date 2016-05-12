package amigoinn.example.v4sales;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import amigoinn.adapters.SectionedListActivityForFilters;
import amigoinn.db_model.ClientInfo;
import amigoinn.walkietalkie.Constants;
import amigoinn.walkietalkie.DatabaseHandler1;

public class Filter extends Activity {

    ListView listData, listStates, listCity;
    TextView txtZone, txtState, txtCity, txtApply;
    String isvalue;
    DatabaseHandler1 handler1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_filter);
            handler1 = new DatabaseHandler1(this);
            txtZone = (TextView) findViewById(R.id.txtZone);
            txtState = (TextView) findViewById(R.id.txtState);
            txtCity = (TextView) findViewById(R.id.txtCity);
            listData = (ListView) findViewById(R.id.listFiltered);
            listStates = (ListView) findViewById(R.id.listState);
            listCity = (ListView) findViewById(R.id.listCity);
            txtApply = (TextView) findViewById(R.id.txtfiltertitle);

            Constants.Zonelist.clear();
            Constants.Zonelist = ClientInfo.getAllClintZone();//handler1.getClienZone();
//            Constants.addzones();
            Constants.CityList.clear();
            Constants.CityList = ClientInfo.getAllClintCity();//handler1.getClienCity();
//            Constants.addCity();
            Constants.Statelist.clear();
            Constants.Statelist = ClientInfo.getAllClintState();//handler1.getClienState();
//            Constants.addStates();
            ArrayAdapter<String> adapterZone = new ArrayAdapter<String>(Filter.this, R.layout.sammplelistitem, R.id.tv, Constants.Zonelist);
            listData.setAdapter(adapterZone);
            ArrayAdapter<String> adapterState = new ArrayAdapter<String>(Filter.this, R.layout.sammplelistitem, R.id.tv, Constants.Statelist);
            listStates.setAdapter(adapterState);
            ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(Filter.this, R.layout.sammplelistitem, R.id.tv, Constants.CityList);
            listCity.setAdapter(adapterCity);


            listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    SectionedListActivityForFilters filter=new SectionedListActivityForFilters();
                    String selected = Constants.Zonelist.get(position);
                    ArrayList<ClientInfo> clist = ClientInfo.getClintInfoByZone(selected);
                    ArrayList<String> selectedzone = new ArrayList<String>();
                    if (clist != null) {
                        for (int i = 0; i < clist.size(); i++) {
                            ClientInfo ci = clist.get(i);
                            selectedzone.add(ci.name);
                        }
                    }
                    Constants.countries = selectedzone;//Constants.Zonelist;
                    Intent in = new Intent(Filter.this, SectionedListActivityForFilters.class);
                    startActivityForResult(in, 1111);

                }
            });
            listStates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    SectionedListActivityForFilters filter=new SectionedListActivityForFilters();
                    String selected = Constants.Statelist.get(position);
                    ArrayList<ClientInfo> clist = ClientInfo.getClintInfoByState(selected);
                    ArrayList<String> selectedzone = new ArrayList<String>();
                    if (clist != null) {
                        for (int i = 0; i < clist.size(); i++) {
                            ClientInfo ci = clist.get(i);
                            selectedzone.add(ci.name);
                        }
                    }
                    Constants.countries = selectedzone;// Constants.Statelist;
                    Intent in = new Intent(Filter.this, SectionedListActivityForFilters.class);
                    startActivityForResult(in, 1111);

                }
            });
            listCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    SectionedListActivityForFilters filter=new SectionedListActivityForFilters();
                    String selected = Constants.CityList.get(position);
                    ArrayList<ClientInfo> clist = ClientInfo.getClintInfoByCity(selected);
                    ArrayList<String> selectedzone = new ArrayList<String>();
                    if (clist != null) {
                        for (int i = 0; i < clist.size(); i++) {
                            ClientInfo ci = clist.get(i);
                            selectedzone.add(ci.name);
                        }
                    }
                    Constants.countries = selectedzone;//Constants.CityList;
                    Intent in = new Intent(Filter.this, SectionedListActivityForFilters.class);
                    startActivityForResult(in, 1111);

                }
            });
            txtZone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constants.countries = Constants.Zonelist;
                    Intent in = new Intent(Filter.this, SectionedListActivityForFilters.class);
                    startActivityForResult(in, 1111);
//                    Constants.Zonelist.clear();
//                    Constants.addzones();
//                    Constants.countries=Constants.Zonelist;
//                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(Filter.this,R.layout.sammplelistitem,R.id.tv,Constants.Zonelist);
//                    listData.setAdapter(adapter);
                }
            });

            txtState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Constants.Statelist.clear();
//                    Constants.addStates();
//                    Constants.countries=Constants.Statelist;
//                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(Filter.this,R.layout.sammplelistitem,R.id.tv,Constants.Statelist);
//                    listData.setAdapter(adapter);
                    Constants.countries = Constants.Statelist;
                    Intent in = new Intent(Filter.this, SectionedListActivityForFilters.class);
                    startActivityForResult(in, 1111);
                }
            });

            txtCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Constants.CityList.clear();
//                    Constants.addCity();
//                    Constants.countries=Constants.CityList;
//                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(Filter.this,R.layout.sammplelistitem,R.id.tv,Constants.CityList);
//                    listData.setAdapter(adapter);
                    Constants.countries = Constants.CityList;
                    Intent in = new Intent(Filter.this, SectionedListActivityForFilters.class);
                    startActivityForResult(in, 1111);
                }
            });
            txtApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(Filter.this, LeftMenusActivity.class);
                    in.putExtra("for", "order");
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);
                    finish();
                }
            });
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1111) {
                Intent start = new Intent();
                setResult(RESULT_OK, start);
                finish();
            }

        } else {

        }
    }
}
