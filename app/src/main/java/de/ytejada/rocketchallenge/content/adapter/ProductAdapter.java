package de.ytejada.rocketchallenge.content.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import de.ytejada.rocketchallenge.R;

/**
 * Adapter providing {@link de.ytejada.rocketchallenge.content.adapter.provider.Data} elements and Views representing this elements. Views provided by
 * this adapter are pretty simple, consisting on a image in the left side of the layout and on the
 * right side, some textViews displaying the info of the product. If any of the Data´s text fields it´s missing,
 * it´s view won´t display empty content, instead the view will be gone.
 * In case of missing image or if any error occurs when loading the image, default android ic will be displayed.
 */
public class ProductAdapter extends BaseAdapter implements AbsListView.RecyclerListener {
    private final List<ClothesItem> mProducts = new ArrayList<ClothesItem>();

    //Setup ImageLoader
    DisplayImageOptions mDisplayOptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .resetViewBeforeLoading(true)
            .showImageOnFail(R.drawable.ic_launcher)
            .showImageForEmptyUri(R.drawable.ic_launcher)
            .build();

    public ProductAdapter(final Context context) {
        // Create global configuration and initialize ImageLoader with it. Half of available processors and 2M of cache
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(Math.max(Runtime.getRuntime().availableProcessors() / 2, 1))
                .memoryCacheSize(2 * 1024 * 1024)
                .build();
        ImageLoader.getInstance().init(config);
    }


    public void setContent(List<ClothesItem> content) {
        mProducts.clear();
        mProducts.addAll(content);
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return mProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View v;
        final ViewHolder holder;

        //No convertView to be recycled, so create a new one
        if (convertView == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, null);
            holder = new ViewHolder();
            holder.brand = ((TextView) v.findViewById(R.id.product_brand));
            holder.name = ((TextView) v.findViewById(R.id.product_name));
            holder.price = ((TextView) v.findViewById(R.id.product_price));
            holder.image = (ImageView) v.findViewById(R.id.product_image);
            v.setTag(holder);
        } else {
            v = convertView;
            holder = (ViewHolder) v.getTag();
        }

        //Populate View with current information
        final ClothesItem currentProduct = mProducts.get(position);
        holder.name.setText(currentProduct.getName());
        holder.brand.setText(currentProduct.getBrand());
        holder.price.setText(currentProduct.getPrice());
        Log.d("ProductAdapter", "getView: " + "Building product " + currentProduct.getName() + "" + currentProduct.getBrand() + "" + currentProduct.getPrice());

        //Get all available images, if not empty get the first one
        final List<Image> images = (List<Image>) currentProduct.getImages();
        if (!images.isEmpty()) {
            ImageLoader.getInstance().displayImage(currentProduct.getImages().get(0).getPath(), holder.image, mDisplayOptions);
        }
        return v;
    }


    /**
     * ViewHolder Pattern for View recycling in the Adapter.
     */
    private static class ViewHolder {
        TextView name;
        TextView price;
        TextView brand;
        ImageView image;
    }


    @Override
    public void onMovedToScrapHeap(View view) {
        final ImageView imageView;
        if (view.getTag() != null) {
            ViewHolder holder = (ViewHolder) view.getTag();
            imageView = holder.image;
        } else {
            imageView = (ImageView) view.findViewById(R.id.product_image);
        }
        ImageLoader.getInstance().cancelDisplayTask(imageView);
    }
}