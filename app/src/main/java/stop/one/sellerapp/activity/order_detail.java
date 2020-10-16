package stop.one.sellerapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import stop.one.sellerapp.R;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class order_detail extends AppCompatActivity {

    TextView name,city,state,phone,area,order_date,delivery_status,payment_type,
            cancel_order,prod_name,prod_brand,price,pincode,house_no;
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    String order_id,product_id,address_id,userid,user_id;
    ImageView prod_image;

    AlertDialog myAlertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        order_id=getIntent().getStringExtra("Order_id");
        product_id=getIntent().getStringExtra("Product_id");
        address_id=getIntent().getStringExtra("Address_id");
        user_id=getIntent().getStringExtra("User_id");
        userid=FirebaseAuth.getInstance().getCurrentUser().getUid();

        name=findViewById(R.id.name);
        city=findViewById(R.id.city);
        state=findViewById(R.id.state);
        phone=findViewById(R.id.phone);
        area=findViewById(R.id.area);
        order_date=findViewById(R.id.order_date);
        delivery_status=findViewById(R.id.delivery_status);
        payment_type=findViewById(R.id.payment_type);
        cancel_order=findViewById(R.id.cancel_order);
        prod_name=findViewById(R.id.prod_name);
        prod_brand=findViewById(R.id.prod_brand);
        price=findViewById(R.id.price);
        pincode=findViewById(R.id.pincode);
        house_no=findViewById(R.id.house_no);
        prod_image=findViewById(R.id.prod_image);

        firebaseFirestore.collection("Products").document(product_id).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        prod_brand.setText(documentSnapshot.getString("Brand"));
                        prod_name.setText(documentSnapshot.getString("Name"));
                        Glide.with(getApplicationContext()).load(documentSnapshot.getString("Image"))
                                .into(prod_image);
                    }
                });

        firebaseFirestore.collection("Users").document(user_id).collection("Address")
                .document(address_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                name.setText(documentSnapshot.getString("Name"));
                phone.setText(documentSnapshot.getString("Phone"));
                area.setText(documentSnapshot.getString("Area"));
                state.setText(documentSnapshot.getString("State"));
                house_no.setText(documentSnapshot.getString("House_no"));
                city.setText(documentSnapshot.getString("City"));
                pincode.setText(documentSnapshot.getString("Pincode"));
            }
        });

        firebaseFirestore.collection("Orders").document(order_id).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        order_date.setText(documentSnapshot.getString("Date"));
                        payment_type.setText(documentSnapshot.getString("Payment_type"));
                        delivery_status.setText(documentSnapshot.getString("Status"));
                        price.setText(documentSnapshot.getString("Price"));
                        if(documentSnapshot.getString("Status").equalsIgnoreCase("Delivered")||documentSnapshot.getString("Status").equalsIgnoreCase("Canceled"))
                            cancel_order.setVisibility(View.GONE);
                    }
                });

        cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> update = new HashMap<String, Object>();
                update.put("Status", "Canceled");
                update.put("Payment_type", "Refund in process");
                firebaseFirestore.collection("Orders").document(order_id)
                        .update(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Order Cancel",Toast.LENGTH_SHORT).show();
                        cancel_order.setVisibility(View.GONE);
                    }
                });
            }
        });

        delivery_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] colors = {"Accepted", "Pending", "Rejected", "Canceled","Delivered"};

                AlertDialog.Builder builders = new AlertDialog.Builder(order_detail.this, R.style.MyDialogTheme);
                builders.setTitle("Status");
                builders.setSingleChoiceItems(colors, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                firebaseFirestore.collection("Orders").document(order_id)
                                        .update("Status",colors[item]).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        delivery_status.setText(colors[item]);
                                        myAlertDialog.dismiss();
                                    }
                                });
                            }

                        });
                myAlertDialog = builders.create();
                myAlertDialog.show();
            }
        });


    }
}
