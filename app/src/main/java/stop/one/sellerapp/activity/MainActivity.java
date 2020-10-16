package stop.one.sellerapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import stop.one.sellerapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView add_new_product,order,products,banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        products=findViewById(R.id.products);
        order=findViewById(R.id.order);
        banner=findViewById(R.id.banner);
        add_new_product=findViewById(R.id.add_new_product);

//        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
//        String token= FirebaseInstanceId.getInstance().getToken();
//        firebaseFirestore.collection("Shop")
//                .document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
//                                if (document.get("token") != null) {
//                                    Log.d(TAG, "your field exist");
//                                } else {
//                                    HashMap<String,Object> map=new HashMap<String,Object>();
//                                    map.put("token",token);
//                                    FirebaseFirestore.getInstance().collection("Shop")
//                                            .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                            .update(map);
//                                }
//                            }
//                        }
//                    }
//                });

        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),chooseItem.class));
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Orders_list.class));
            }
        });

        add_new_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),choose_category.class));
            }
        });

        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
