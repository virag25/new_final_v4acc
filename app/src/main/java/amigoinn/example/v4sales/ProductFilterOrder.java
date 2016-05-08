package amigoinn.example.v4sales;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;



import amigoinn.adapters.SectionedListActivityForFilters;
import amigoinn.walkietalkie.Constants;
import amigoinn.walkietalkie.DatabaseHandler1;

public class ProductFilterOrder extends Activity implements View.OnClickListener
{

    TextView txtItemGroup,txtRequesting,txtProduct,txtBrand;
    TextView txtAutobicycle,txtElectronics,txtFmcg,txtModel,txtSize,txtApply;
    boolean isAutoselected=false,isElectronicsSelected=false,isFmcgSelected=false;
    DatabaseHandler1 handler1;
    String isvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try
        {
            //99.1 67.7*8 .5mm

            setContentView(R.layout.activity_product_filter);
            txtAutobicycle=(TextView)findViewById(R.id.txtAutoBicycle);
            txtElectronics=(TextView)findViewById(R.id.txtState);
            txtFmcg=(TextView)findViewById(R.id.txtCity);
            txtItemGroup=(TextView)findViewById(R.id.txtItemGroup);
            txtRequesting=(TextView)findViewById(R.id.txtReportingGroupCode);
            txtProduct=(TextView)findViewById(R.id.txtProduct);
            txtBrand=(TextView)findViewById(R.id.txtBrand);
            txtModel=(TextView)findViewById(R.id.txtModel);
            txtSize=(TextView)findViewById(R.id.txtSize);
            txtProduct=(TextView)findViewById(R.id.txtProduct);
            handler1=new DatabaseHandler1(this);
            txtApply=(TextView)findViewById(R.id.txtfiltertitle);
            Constants.ItemgroupList.clear();
//            Constants.addItemGroups();
            Constants.Reportinggroupcodelist.clear();
//            Constants.addReportingCode();
            Constants.Productlist.clear();
//            Constants.addProducts();
            Constants.BrandList.clear();
//            Constants.addBrands();
            Constants.sizelist.clear();
//            Constants.addSizes();
            Constants.modellist.clear();
//            Constants.addModels();
//            ArrayAdapter<String> adapterZone=new ArrayAdapter<String>(ProductFilter.this,R.layout.sammplelistitem,R.id.tv,Constants.Zonelist.subList(0,5));
//                    listData.setAdapter(adapterZone);
//            ArrayAdapter<String> adapterState=new ArrayAdapter<String>(ProductFilter.this,R.layout.sammplelistitem,R.id.tv,Constants.Statelist.subList(0,5));
//            listStates.setAdapter(adapterState);
//            ArrayAdapter<String> adapterCity=new ArrayAdapter<String>(ProductFilter.this,R.layout.sammplelistitem,R.id.tv,Constants.CityList.subList(0,5));
//            listCity.setAdapter(adapterCity);

            txtItemGroup.setOnClickListener(this);
            txtRequesting.setOnClickListener(this);
            txtProduct.setOnClickListener(this);
            txtBrand.setOnClickListener(this);
            txtFmcg.setOnClickListener(this);
            txtAutobicycle.setOnClickListener(this);
            txtElectronics.setOnClickListener(this);
            txtModel.setOnClickListener(this);
            txtSize.setOnClickListener(this);
            txtApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent in=new Intent(ProductFilterOrder.this, LeftMenusActivity.class);
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

    @Override
    public void onClick(View v)
    {
        Intent in=new Intent(ProductFilterOrder.this,SectionedListActivityForFilters.class);
        switch(v.getId())
        {
            case R.id.txtItemGroup:
                Constants.countries=handler1.getItemgroups();
                in.putExtra("from","itemgroup");
                startActivity(in);
                finish();
                break;
            case R.id.txtModel:
                Constants.countries=handler1.getModels();
                in.putExtra("from","model");
                startActivity(in);
                finish();
                break;
            case R.id.txtSize:
                Constants.countries=handler1.getPACKINGSIZE();
                in.putExtra("from","size");
                startActivity(in);
                finish();
                break;

            case R.id.txtReportingGroupCode:
//                Constants.countries=Constants.Reportinggroupcodelist
                Constants.countries=handler1.getReportingGroupcode();
                in.putExtra("from","reporting");
                startActivity(in);
                finish();
                break;
            case R.id.txtProduct:
                Constants.countries=handler1.getProduct();
                in.putExtra("from","product");
//                Intent in=new Intent(ProductFilter.this,SectionedListActivityForFilters.class);
                startActivity(in);
                finish();
                break;
            case R.id.txtBrand:
                Constants.countries=handler1.getBrands();
                in.putExtra("from","brand");
                startActivity(in);
                finish();
                break;
            case R.id.txtAutoBicycle:
                        if(isAutoselected)
                        {
                            isAutoselected=false;
                            txtAutobicycle.setBackgroundResource(R.drawable.buttonshapeempty);
                            txtAutobicycle.setTextColor(getResources().getColor(R.color.main_color_500));
                        }
                        else
                        {
                            isAutoselected=true;
                            txtAutobicycle.setBackgroundResource(R.drawable.buttonshape);
                            txtAutobicycle.setTextColor(getResources().getColor(R.color.white));
                        }
                break;
            case R.id.txtCity:
                if(isFmcgSelected)
                {
                    isFmcgSelected=false;
                    txtFmcg.setBackgroundResource(R.drawable.buttonshapeempty);
                    txtFmcg.setTextColor(getResources().getColor(R.color.main_color_500));
                }
                else
                {
                    isFmcgSelected=true;
                    txtFmcg.setBackgroundResource(R.drawable.buttonshape);
                    txtFmcg.setTextColor(getResources().getColor(R.color.white));
                }
                break;
            case R.id.txtState:
                if(isElectronicsSelected)
                {
                    isElectronicsSelected=false;
                    txtElectronics.setBackgroundResource(R.drawable.buttonshapeempty);
                    txtElectronics.setTextColor(getResources().getColor(R.color.main_color_500));
                }
                else
                {
                    isElectronicsSelected=true;
                    txtElectronics.setBackgroundResource(R.drawable.buttonshape);
                    txtElectronics.setTextColor(getResources().getColor(R.color.white));
                }
                break;
        }
    }
}
