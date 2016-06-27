package amigoinn.example.v4accapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maulik on 28/09/2015.
 */
public class FragmentMessages extends Fragment
{
    View view;
   public List<String> list = new ArrayList<String>();

    Context context;
    EditText edtTitle,edtDescription;
    RobotoTextView txtSubmit;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.message_fragment, container, false);
        context=view.getContext();
        edtTitle=(EditText)view.findViewById(R.id.title);
        edtDescription=(EditText)view.findViewById(R.id.Description);
        txtSubmit=(RobotoTextView)view.findViewById(R.id.login);
        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTitle.setText("");
                edtDescription.setText("");
            }
        });
        return view;
    }








}
