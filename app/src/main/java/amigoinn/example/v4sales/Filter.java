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


import amigoinn.adapters.SectionedListActivityForFilters;
import amigoinn.walkietalkie.Constants;
import amigoinn.walkietalkie.DatabaseHandler1;

public class Filter extends Activity {

    ListView listData,listStates,listCity;
    TextView txtZone,txtState,txtCity,txtApply;
    String isvalue;
    DatabaseHandler1 handler1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try
        {
            setContentView(R.layout.activity_filter);
            handler1=new DatabaseHandler1(this);
            txtZone=(TextView)findViewById(R.id.txtZone);
            txtState=(TextView)findViewById(R.id.txtState);
            txtCity=(TextView)findViewById(R.id.txtCity);
            listData=(ListView)findViewById(R.id.listFiltered);
            listStates=(ListView)findViewById(R.id.listState);
            listCity=(ListView)findViewById(R.id.listCity);
            txtApply=(TextView)findViewById(R.id.txtfiltertitle);

            Constants.Zonelist.clear();
            Constants.Zonelist=handler1.getClienZone();
//            Constants.addzones();
            Constants.CityList.clear();
            Constants.CityList=handler1.getClienCity();
//            Constants.addCity();
            Constants.Statelist.clear();
            Constants.Statelist=handler1.getClienState();
//            Constants.addStates();
            ArrayAdapter<String> adapterZone=new ArrayAdapter<String>(Filter.this,R.layout.sammplelistitem,R.id.tv,Constants.Zonelist);
                    listData.setAdapter(adapterZone);
            ArrayAdapter<String> adapterState=new ArrayAdapter<String>(Filter.this,R.layout.sammplelistitem,R.id.tv,Constants.Statelist);
            listStates.setAdapter(adapterState);
            ArrayAdapter<String> adapterCity=new ArrayAdapter<String>(Filter.this,R.layout.sammplelistitem,R.id.tv,Constants.CityList);
            listCity.setAdapter(adapterCity);


            listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    SectionedListActivityForFilters filter=new SectionedListActivityForFilters();
                    Constants.countries = Constants.Zonelist;
                    Intent in = new Intent(Filter.this, SectionedListActivityForFilters.class);
                    startActivity(in);

                }
            });
            listStates.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
//                    SectionedListActivityForFilters filter=new SectionedListActivityForFilters();
                    Constants.countries=Constants.Statelist;
                    Intent in=new Intent(Filter.this,SectionedListActivityForFilters.class);
                    startActivity(in);

                }
            });
            listCity.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
//                    SectionedListActivityForFilters filter=new SectionedListActivityForFilters();
                    Constants.countries=Constants.CityList;
                    Intent in=new Intent(Filter.this,SectionedListActivityForFilters.class);
                    startActivity(in);

                }
            });
            txtZone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constants.countries = Constants.Zonelist;
                    Intent in = new Intent(Filter.this, SectionedListActivityForFilters.class);
                    startActivity(in);
//                    Constants.Zonelist.clear();
//                    Constants.addzones();
//                    Constants.countries=Constants.Zonelist;
//                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(Filter.this,R.layout.sammplelistitem,R.id.tv,Constants.Zonelist);
//                    listData.setAdapter(adapter);
                }
            });

            txtState.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
//                    Constants.Statelist.clear();
//                    Constants.addStates();
//                    Constants.countries=Constants.Statelist;
//                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(Filter.this,R.layout.sammplelistitem,R.id.tv,Constants.Statelist);
//                    listData.setAdapter(adapter);
                    Constants.countries=Constants.Statelist;
                    Intent in=new Intent(Filter.this,SectionedListActivityForFilters.class);
                    startActivity(in);
                }
            });

            txtCity.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
//                    Constants.CityList.clear();
//                    Constants.addCity();
//                    Constants.countries=Constants.CityList;
//                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(Filter.this,R.layout.sammplelistitem,R.id.tv,Constants.CityList);
//                    listData.setAdapter(adapter);
                    Constants.countries=Constants.CityList;
                    Intent in=new Intent(Filter.this,SectionedListActivityForFilters.class);
                    startActivity(in);
                }
            });
            txtApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent in=new Intent(Filter.this, LeftMenusActivity.class);
                    in.putExtra("for","order");
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);
                    finish();
                }
            });
        }catch (Exception ex)
        {
            Log.e("Error",ex.toString());
        }
    }
}
