package amigoinn.example.v4sales;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import org.w3c.dom.Text;

import amigoinn.db_model.ClientInfo;

public class ClientContactFragment extends Fragment {
    TextView txtuser, txtZone, txtState,
            txtCity, txtAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.client_contact_fragment, container, false);
        String c_code = AccountApplication.getClient_code();
        txtuser = (TextView) v.findViewById(R.id.txtuser);
        txtZone = (TextView) v.findViewById(R.id.txtZone);
        txtState = (TextView) v.findViewById(R.id.txtState);
        txtCity = (TextView) v.findViewById(R.id.txtCity);
        txtAddress = (TextView) v.findViewById(R.id.txtAddress);

        if (c_code != null) {
            ClientInfo cinfo = ClientInfo.getClintInfoById(c_code);
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
