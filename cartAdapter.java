package app.sample.app.android_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.ViewHolder> {

    private Context c;
    List<String> urls = new ArrayList<>();
    List<String> names = new ArrayList<>();
    List<Integer> rates = new ArrayList<>();


    public cartAdapter(Context c, List<String> urls, List<String> names, List<Integer> rates) {
        this.c = c;
        this.urls = urls;
        this.names = names;
        this.rates = rates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.cartitem , viewGroup ,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder,  int i) {


        RequestOptions options = new RequestOptions().placeholder(R.drawable.ic_launcher_foreground);
        Glide.with(c).load(urls.get(i)).apply(options).into(viewHolder.image);

        final int[] p = {1};
        final int k =i;
        final int rate = rates.get(i);
        viewHolder.name.setText("Name :: "+names.get(i));
        viewHolder.rate.setText("Rate ::"+rates.get(i)+"");
        viewHolder.total.setText("Total Price :: "+rate*p[0]+"");
        viewHolder.quantity.setText(p[0] +"");

        final sql db = new sql(c);
        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p[0] = p[0] +1;
                db.updatecart(names.get(k) ,p[0]);
                viewHolder.total.setText("Total Price :: "+rate*p[0]+"");
                viewHolder.quantity.setText(p[0] +"");

            }
        });

        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(p[0] > 1 ) {
                    p[0] = p[0] - 1;
                    db.updatecart(names.get(k) ,p[0]);
                    viewHolder.quantity.setText(p[0] +"");
                    viewHolder.total.setText("Total Price ::"+rate*p[0]+"");
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name ,rate ,total , quantity;
        Button plus, minus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.cartimage);
            name = itemView.findViewById(R.id.cartitemname);
            rate = itemView.findViewById(R.id.cartitemrate);
            quantity = itemView.findViewById(R.id.cartitemquantity);
            total = itemView.findViewById(R.id.carttotalprice);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
        }
    }
}
