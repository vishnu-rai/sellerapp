package stop.one.sellerapp.activity;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import stop.one.sellerapp.R;
import stop.one.sellerapp.activity.holder.product_frag_order_list_holder;

public class Orders_list extends AppCompatActivity {

    RecyclerView order_recycler;
    FirestoreRecyclerAdapter adapter;
    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);

        order_recycler = findViewById(R.id.order_recycler);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Query query = rootRef.collection("Orders").whereEqualTo("Type", "Product").whereEqualTo("Shop_id", userId);

        FirestoreRecyclerOptions<product_frag_order_list_holder> options = new FirestoreRecyclerOptions.Builder<product_frag_order_list_holder>()
                .setQuery(query, product_frag_order_list_holder.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<product_frag_order_list_holder, RecyclerAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RecyclerAdapter holder, final int position, @NonNull final product_frag_order_list_holder model) {

                holder.item_name.setText(model.getName());
                holder.brand_name.setText(model.getBrand());
                holder.price.setText("Price : " + model.getPrice());
                holder.status.setText(model.getStatus());

                FirebaseFirestore.getInstance().collection("Products").document(model.getProduct_id())
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        holder.item_name.setText(documentSnapshot.getString("Name"));
                        holder.brand_name.setText(documentSnapshot.getString("Brand"));
                        Glide.with(getApplicationContext()).load(documentSnapshot.getString("Image")).into(holder.product_image);
                    }
                });
                holder.product_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String order_id= getSnapshots().getSnapshot(position).getId();
                        Intent intent = new Intent(getApplicationContext(), order_detail.class);
                        intent.putExtra("Order_id", order_id);
                        intent.putExtra("Product_id",model.getProduct_id());
                        intent.putExtra("Address_id",model.getAddress_id());
                        intent.putExtra("User_id",model.getUser_id());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public RecyclerAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.frag_product_order_list_card, parent, false);
                return new RecyclerAdapter(view);
            }
        };

        order_recycler.setLayoutManager(new LinearLayoutManager(this));
        order_recycler.setAdapter(adapter);

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

    public class RecyclerAdapter extends RecyclerView.ViewHolder {
        TextView item_name, brand_name, price, status;
        LinearLayout product_layout;
        ImageView product_image;


        public RecyclerAdapter(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            brand_name = itemView.findViewById(R.id.brand_name);
            price = itemView.findViewById(R.id.price);
            status = itemView.findViewById(R.id.status);
            product_layout = itemView.findViewById(R.id.product_layout);
            product_image=itemView.findViewById(R.id.product_image);

        }
    }
}