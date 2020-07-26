package com.fluper.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.fluper.R;
import com.fluper.view_models.ProductModel;
import com.fluper.views.ProductDetails;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private ArrayList<ProductModel> productlist;
    Activity context;

    public ProductAdapter(Activity context, ArrayList<ProductModel> productlist) {
        this.productlist = productlist;
        this.context = context;
    }
    public interface InviteUser{
        public void inviteUser();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_adapter, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ProductModel model = productlist.get(position);
        holder.product_name.setText(model.getName());
        holder.product_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("prod_id",model.getId());
                context.startActivity(intent);
            }
        });
        // getting drawble from db.
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(model.getproduct_photo(), "drawable",
                context.getPackageName());
        Drawable drawable = resources.getDrawable(resourceId);
        holder.prod_image.setImageDrawable(drawable);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout product_lay;
        TextView product_name;
        ImageView prod_image;

        public MyViewHolder(View view) {
            super(view);
            product_lay=view.findViewById(R.id.product_lay);
            product_name=view.findViewById(R.id.product_name);
            prod_image=view.findViewById(R.id.prod_image);
        }
    }
    @Override
    public int getItemCount() {
        return productlist.size();
    }
    public ArrayList<ProductModel> getlst(){
        return  productlist;
    }
}

