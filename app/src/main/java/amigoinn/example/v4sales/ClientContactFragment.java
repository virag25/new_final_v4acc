package amigoinn.example.v4sales;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import org.w3c.dom.Text;

import amigoinn.db_model.ClientInfo;

public class ClientContactFragment extends Fragment {
    TextView txtuser, txtZone, txtState,
            txtCity, txtAddress;
    TextView txtCall, txtMap;
    ClientInfo cinfo;
    Button btncall, btnlocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.client_contact_fragment, container, false);
        String c_code = AccountApplication.getClient_code();
        txtuser = (TextView) v.findViewById(R.id.txtuser);
        txtZone = (TextView) v.findViewById(R.id.txtZone);
        txtState = (TextView) v.findViewById(R.id.txtState);
        txtCity = (TextView) v.findViewById(R.id.txtCity);
        txtAddress = (TextView) v.findViewById(R.id.txtAddress);

        btncall = (Button) v.findViewById(R.id.btncall);
        btnlocation = (Button) v.findViewById(R.id.btnlocation);

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
                Intent start = new Intent(getActivity(), GoogleMapActivity.class);
                startActivity(start);
            }
        });


        txtCall = (TextView) v.findViewById(R.id.txtCall);
        txtMap = (TextView) v.findViewById(R.id.txtMap);

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

        txtMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(getActivity(), GoogleMapActivity.class);
                startActivity(start);
            }
        });

        if (c_code != null) {
            cinfo = ClientInfo.getClintInfoById(c_code);
            if (cinfo != null) {
                txtuser.setText(cinfo.name);
                txtZone.setText(cinfo.Zone);
                txtState.setText(cinfo.client_state);
                txtCity.setText(cinfo.City);
                txtAddress.setText(cinfo.client_addr_one + "," + cinfo.client_addr_two + "," + cinfo.client_addr_three + "," + cinfo.client_addr_four);
            }
        }


        return v;
    }
}
