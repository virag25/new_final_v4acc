package amigoinn.example.v4sales;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.v4sales.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;


/**
 * Created by Manthan on 28/09/2015.
 */
public class FragmentReportChart extends Fragment
{
    View view;
    public List<String> list = new ArrayList<String>();
    GridView gridView;
    Context context;
    BarChart chart;
    int count=0;
    CategoryGridAdpter adpter;
    TextView tv_pricesort,tv_filter;
    ArrayList<BarEntry> entries = new ArrayList<>();
    BarDataSet dataset;
    Spinner typespinner;
//    List<OverallPercentage> posts;
    ArrayList<String> labels = new ArrayList<String>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_report_chart, container, false);
        context=view.getContext();
        typespinner=(Spinner)view.findViewById(R.id.spinnerType);
//        typespinner.setBaseColor(context.getResources().getColor(R.color.main_color_500));

        chart = (BarChart) view.findViewById(R.id.barChart1);
        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("Report");
        chart.animateXY(2000, 2000);
        chart.invalidate();
        ArrayList<String> spinnerData=new ArrayList<>();
        spinnerData.add("Today's order collection");
        spinnerData.add("Product-wise order collection");
        spinnerData.add("Party-wise order collection");
        spinnerData.add("Pending route report");
        spinnerData.add("Pending order report");
        spinnerData.add("Target order report");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,spinnerData );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typespinner.setAdapter(adapter);

        typespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedValue = typespinner.getSelectedItem().toString();
//                if (!selectedValue.equalsIgnoreCase(typespinner.getHint().toString()))
//                {
                   // createchart();

                    BarData data = new BarData(getXAxisValues(), getDataSet());
                    chart.setData(data);
                    chart.setDescription("Report");
                    chart.animateXY(2000, 2000);
                    chart.invalidate();
                   // monthName = post.get(0).getMonth().get(i);
//                    new GetMarks12().execute();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //SharedPreferences preference=context.getSharedPreferences("url1",Context.MODE_PRIVATE);
        // int position=FragmentCategory.viewPager.getCurrentItem();
        // String url=String.valueOf(position); //preference.getString("url","0");//

        //chart.setDrawGridBackground(false);
        //chart.saveToGallery("chart.jpg",80);
//        FrameLayout parent = (FrameLayout) view.findViewById(R.id.parentLayout);
//
//        parent.addView(chart);

        //setMapping();
        // setListner();
//        for (int i=0;i<10;i++)
//        {
//            list.add("");
//        }
        //adpter =new CategoryGridAdpter(getActivity(),list);
        //gridView.setAdapter(adpter);
        return view;
    }
    public void createchart()
    {
        entries.clear();
            entries.add(new BarEntry(40f, 0));
            entries.add(new BarEntry(80f, 1));
            entries.add(new BarEntry(60f, 2));
            entries.add(new BarEntry(20f, 3));
            entries.add(new BarEntry(100f, 4));
            entries.add(new BarEntry(90f, 5));
            dataset = new BarDataSet(entries, "# of Calls");

            labels.clear();

            labels.add("January");
            labels.add("February");
            labels.add("March");
            labels.add("April");
            labels.add("May");
            labels.add("June");

        dataset = new BarDataSet(entries, "# of Calls");
        dataset.setColor(getActivity().getResources().getColor(R.color.main_color_400));
        chart.setDrawGridBackground(false);
        BarData data = new BarData(labels, dataset);
        data.setValueTextSize(10f);
        chart.zoom(1.1f, 1, 1, 1);
        chart.setData(data);
        //chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        //Float fl=Float.labels.size();
        //chart.setVisibleXRange(labels.size(),labels.size());
        chart.setDescription("% of ");
        chart.animateY(2000);
        //chart.zoom(1.4,1.4,100f,100f);
        chart.setVisibleXRangeMaximum(5);
        chart.setDescriptionTextSize(15f);
//        XAxis xa=chart.getXAxis();
//        xa.setTextSize(px);
        YAxis yAxis = chart.getAxisLeft();
        chart.setAlwaysDrawnWithCacheEnabled(true);
        chart.getAxisRight().setAxisMaxValue(100f);
        // chart.setRendererRightYAxis(null);
        // yAxis.setTextSize(px);

        yAxis.setAxisMaxValue(100f);
        FrameLayout parent = (FrameLayout) view.findViewById(R.id.parentLayout);

        parent.addView(chart);

    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
        valueSet1.add(v1e6);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
        valueSet2.add(v2e4);
        BarEntry v2e5 = new BarEntry(20.000f, 4); // May
        valueSet2.add(v2e5);
        BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
        valueSet2.add(v2e6);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        return xAxis;
    }
    public int dpToPx(int dp)
    {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int)((dp * displayMetrics.density) + 0.5);

    }

//    private void setMapping() {
//        //gridView = (GridView)view.findViewById(R.id.gridView);
//        //tv_pricesort =(TextView)view.findViewById(R.id.tv_pricesort);
//        //tv_filter = (TextView)view.findViewById(R.id.tv_filter);
//    }

//    private void setListner()
//    {
//        tv_pricesort.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openDialog();
//            }
//        });
//        tv_filter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),FilterProduct.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void openDialog() {
//        final Dialog dialog = new Dialog(getActivity());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.custom_dialog_categorylist);
//        ListView listView = (ListView) dialog.findViewById(R.id.listView);
//        dialog.setCancelable(true);
//        List<String> categorynames = new ArrayList<String>();
//        categorynames.add("Lowest Price");
//        categorynames.add("Highest Price");
//
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categorynames);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                tv_pricesort.setText(adapter.getItem(position));
//
//                Log.e("Price Filter", "" + adapter.getItem(position));
//
////                selected_event_category = Constants.categoryList.get(position).getIntId();
//
//                dialog.dismiss();
//
//            }
//        });
//
////        dialog.setTitle("Sort By");
//        dialog.show();
//    }

}
