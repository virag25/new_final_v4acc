package amigoinn.example.v4accapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import amigoinn.db_model.ClientInfo;
import amigoinn.walkietalkie.Constants;

public class ClientDetails extends Activity
{
    TextView txtuser, txtZone, txtState,
            txtCity, txtAddress;
    TextView txtCall, txtMap;
    ClientInfo cinfo;
    Button btncall, btnlocation;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_contact_fragment);

        String c_code = AccountApplication.getClient_code();
        c_code= Constants.client_id;
        txtuser = (TextView) findViewById(R.id.txtuser);
        txtZone = (TextView) findViewById(R.id.txtZone);
        txtState = (TextView)findViewById(R.id.txtState);
        txtCity = (TextView) findViewById(R.id.txtCity);
        txtAddress = (TextView) findViewById(R.id.txtAddress);

        btncall = (Button) findViewById(R.id.btncall);
        btnlocation = (Button) findViewById(R.id.btnlocation);

        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberr = cinfo.mobile_number;
                if (numberr != null) {
                    Uri number = Uri.parse("tel:" + numberr);
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    startActivity(callIntent);
                }

            }
        });

        btnlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(ClientDetails.this, GoogleMapActivity.class);
                startActivity(start);
            }
        });


        txtCall = (TextView) findViewById(R.id.txtCall);
        txtMap = (TextView) findViewById(R.id.txtMap);

        txtCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberr = cinfo.mobile_number;
                if (numberr != null) {
                    Uri number = Uri.parse("tel:" + numberr);
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    startActivity(callIntent);
                }
            }
        });

        txtMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent start = new Intent(ClientDetails.this, GoogleMapActivity.class);
                startActivity(start);
            }
        });

        if (c_code != null)
        {
            cinfo = ClientInfo.getClintInfoById(c_code);
            if (cinfo != null)
            {
                txtuser.setText(cinfo.name);
                txtZone.setText(cinfo.Zone);
                txtState.setText(cinfo.client_state);
                txtCity.setText(cinfo.City);
                txtAddress.setText(cinfo.client_addr_one + "," + cinfo.client_addr_two + "," + cinfo.client_addr_three + "," + cinfo.client_addr_four);
            }
        }


    }
}
