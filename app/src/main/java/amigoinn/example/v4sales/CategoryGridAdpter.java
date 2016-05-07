package amigoinn.example.v4sales;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.v4sales.R;

import java.util.List;

/**
 * Created by Manthan on 04/10/2015.
 */
public class CategoryGridAdpter extends BaseAdapter {
    Context context;
    List data;
    LayoutInflater mInflater;

    public CategoryGridAdpter(Context context, List data) {
        this.context = context;
        this.data = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            // Inflate the view since it does not exist
            convertView = mInflater.inflate(R.layout.row_item_category, parent, false);

            // Create and save off the holder in the tag so we get quick access to inner fields
            // This must be done for performance reasons
            holder = new ViewHolder();
            holder.tv_itemdetail = (TextView) convertView.findViewById(R.id.tv_itemdetail);
            holder.tv_itemprice = (TextView) convertView.findViewById(R.id.tv_itemprice);
            holder.tv_itemsellprice = (TextView) convertView.findViewById(R.id.tv_itemsellprice);
            holder.iv_product_image = (ImageView) convertView.findViewById(R.id.iv_product_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder
    {
        ImageView iv_product_image;
        TextView tv_itemdetail;
        TextView tv_itemprice;
        TextView tv_itemsellprice;
    }
}
