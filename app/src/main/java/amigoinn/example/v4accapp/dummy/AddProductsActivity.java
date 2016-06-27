package amigoinn.example.v4accapp.dummy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import amigoinn.example.v4accapp.R;


public class AddProductsActivity extends Activity {
    EditText edtProduct,edtTotal,edtQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_products);
        edtProduct = (EditText) findViewById(R.id.edtProduct);
        edtTotal = (EditText) findViewById(R.id.edtTotal);
        edtQty = (EditText) findViewById(R.id.edtQty);
    }
}
