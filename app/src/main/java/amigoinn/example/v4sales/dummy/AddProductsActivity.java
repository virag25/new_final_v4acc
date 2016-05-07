package amigoinn.example.v4sales.dummy;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.v4sales.R;

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
