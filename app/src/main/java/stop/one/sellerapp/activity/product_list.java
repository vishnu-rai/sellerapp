package stop.one.sellerapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import stop.one.sellerapp.R;
import stop.one.sellerapp.activity.holder.product_holder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class product_list extends AppCompatActivity {

    String item_name,category_name,user_id;
    TextView txt_item_name;
    RecyclerView product_list_recycler;
    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        user_id= FirebaseAuth.getInstance().getCurrentUser().getUid();
        Intent startingIntent = getIntent();
        item_name = startingIntent.getStringExtra("item");

        product_list_recycler = findViewById(R.id.product_list_recycler);

        Query query = rootRef.collection("Products").whereEqualTo("Shop_id",user_id).whereEqualTo("Item",item_name);

        FirestoreRecyclerOptions<product_holder> options = new FirestoreRecyclerOptions.Builder<product_holder>()
                .setQuery(query, product_holder.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<product_holder, product_list_RecyclerAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final product_list_RecyclerAdapter holder, final int position, @NonNull final product_holder model) {

                holder.item_name.setText(model.getName().toUpperCase());
                Glide.with(getApplicationContext()).load(model.getImage()).into(holder.item_image);
                holder.brand_name.setText(model.getBrand());
                holder.mrp.setText(model.getMrp());
                holder.price.setText(model.getPrice());

                holder.product_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(product_list.this, ProductDetail.class);
                        intent.putExtra("Product_id", model.getProduct_id());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public product_list_RecyclerAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.product_list_card_layout, parent, false);
                return new product_list_RecyclerAdapter(view);
            }
        };
        product_list_recycler.setLayoutManager(new GridLayoutManager(this, 2));
        product_list_recycler.setAdapter(adapter);

    }


    public class product_list_RecyclerAdapter extends RecyclerView.ViewHolder {
        TextView item_name,brand_name,mrp,price;
        ImageView item_image;
        LinearLayout product_layout;


        public product_list_RecyclerAdapter(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            item_image = itemView.findViewById(R.id.item_image);
            product_layout = itemView.findViewById(R.id.product_layout);
            brand_name = itemView.findViewById(R.id.brand_name);
            mrp=itemView.findViewById(R.id.mrp);
            price=itemView.findViewById(R.id.price);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();

    }
}
