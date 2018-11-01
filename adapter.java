package app.sample.app.android_project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {

    Context c;
        List<String> urls = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<Integer> rates = new ArrayList<>();

    public adapter(Context c, List<String> urls, List<String> names, List<Integer> rates) {
        this.c = c;
        this.urls = urls;
        this.names = names;
        this.rates = rates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View v = LayoutInflater.from(c).inflate(R.layout.menuitem , viewGroup , false);
       return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        holder.rate.setText("Rate :: " + rates.get(i));
        holder.name.setText("Name :: " + names.get(i));

        RequestOptions options  = new RequestOptions().placeholder(R.drawable.ic_launcher_foreground);

        Glide.with(c).load(urls.get(i)).apply(options).into(holder.image);

        final sql db = new sql(c);

        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 final int V =1;
                if( db.insertintocart(names.get(i), rates.get(i) ,V))
                {
                    Toast.makeText(c, "Item Added to cart!!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(c, "Item was not Added to cart!!", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name , rate , cart ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.menuitemimage);
            name = itemView.findViewById(R.id.menuitemname);
            rate = itemView.findViewById(R.id.menuitemrate);
            cart = itemView.findViewById(R.id.menuitemcart);

        }
    }
}
