package amigoinn.example.v4sales;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import amigoinn.db_model.ProductInfo;

public class AddProductItemActivity extends BaseActivity {
    EditText edtProduct, edtQty, edtTotal;
    Button btnSubmit;
    int PRODUCT_SELECT = 111;
    int button_submit = 112;
    String product_id;
    String pid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_product_item);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        edtProduct = (EditText) findViewById(R.id.edtProduct);
        edtQty = (EditText) findViewById(R.id.edtQty);
        edtTotal = (EditText) findViewById(R.id.edtTotal);
        edtTotal.setEnabled(false);

//        edtTotal.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                if(s.length()>0){
//                    int qty = Integer.parseInt(s.toString());
//
//
//
//                }
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        edtProduct.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent item = new Intent(getApplicationContext(),
                            AddProductActivity.class);
                    startActivityForResult(item, PRODUCT_SELECT);

                }
                return true;
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qty = edtQty.getText().toString();
                String total = edtTotal.getText().toString();
                String Product = edtTotal.getText().toString();
                boolean flag = true;
                int qty_send = 0;
                String msg = "";
                if (Product != null && Product.length() == 0) {
                    flag = false;
                    msg = "Please select product first.";
                } else if (qty != null && qty.length() == 0) {
                    flag = false;
                    msg = "Please add qty first.";
                }

                if (qty != null && qty.length() > 0) {
                    qty_send = Integer.parseInt(qty);
                }

                if (flag) {
                    Intent start = new Intent();
                    start.putExtra("product_id", pid);
                    start.putExtra("qty", qty_send);
                    setResult(RESULT_OK, start);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PRODUCT_SELECT) {
                if (data != null) {
                    String str = data.getStringExtra("product_id");

                    if (str != null && str.length() > 0) {
                        pid = str;
                        ProductInfo p_info = ProductInfo.getProductInfoById(pid);
                        product_id = p_info.StockNo;
                        if (p_info != null) {
                            edtProduct.setText(p_info.ItemDesc);
                        } else {

                        }
                    } else {
                        String strr = AccountApplication.getFilter_product_id();
                        if (strr != null && strr.length() > 0) {
                            edtProduct.setText(strr);
                            ProductInfo pro = ProductInfo.getProductInfoByItemName(strr);
                            if (pro != null) {
                                product_id = pro.StockNo;
                                pid = product_id;
                            }
                        }
                    }
                }
            }

        }
    }
}
