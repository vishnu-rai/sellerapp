package stop.one.sellerapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import stop.one.sellerapp.R;
import stop.one.sellerapp.activity.holder.choose_item_holder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class choose_item extends AppCompatActivity {

    public String doc,item;
    private int mCheckedPostion=-1;
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;
    TextView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_item);

        doc=getIntent().getStringExtra("Doc");
        recyclerView=findViewById(R.id.recycler_view);
        next=findViewById(R.id.next);

        Query query= FirebaseFirestore.getInstance().collection("Items").document(doc).collection(doc);

        FirestoreRecyclerOptions<choose_item_holder> options=new FirestoreRecyclerOptions.Builder<choose_item_holder>()
                .setQuery(query,choose_item_holder.class).build();

        adapter= new FirestoreRecyclerAdapter<choose_item_holder, recyclerAdapter>(options) {
            @NonNull
            @Override
            public recyclerAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.choose_item_card, parent, false);
                return new recyclerAdapter(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull recyclerAdapter recyclerAdapter, int i, @NonNull choose_item_holder choose_item_holder) {

                recyclerAdapter.radio.setText(choose_item_holder.getName());

                recyclerAdapter.radio.setOnCheckedChangeListener(null);
                recyclerAdapter.radio.setChecked(i == mCheckedPostion);
                recyclerAdapter.radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        item=recyclerAdapter.radio.getText().toString();
                        mCheckedPostion = i;
                        notifyDataSetChanged();
                    }
                });

            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(choose_item.this,new_product.class);
                intent.putExtra("item",item);
                intent.putExtra("category",doc);
                startActivity(intent);

            }
        });

    }

    public class recyclerAdapter extends RecyclerView.ViewHolder {
        RadioButton radio;
        public recyclerAdapter(@NonNull View itemView) {
            super(itemView);

            radio=itemView.findViewById(R.id.radio);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
