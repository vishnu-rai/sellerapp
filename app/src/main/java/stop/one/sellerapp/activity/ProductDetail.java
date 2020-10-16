package stop.one.sellerapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import stop.one.sellerapp.R;

public class ProductDetail extends AppCompatActivity {

    String product_id;
    EditText price, mrp, extra, description;
    TextView submit, name, brand, stocktv;
    ImageView image0, image1, image2, image3, image4;
    String  prices, mrps, extras, descriptions, stock;
    RadioGroup radioGroup;
    RadioButton radio0, radio1;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String a;
    List<Object> image_=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        product_id = getIntent().getStringExtra("Product_id");

        submit = findViewById(R.id.submit);
        name = findViewById(R.id.name);
        brand = findViewById(R.id.brand);
        price = findViewById(R.id.price);
        mrp = findViewById(R.id.mrp);
        extra = findViewById(R.id.extra);
        description = findViewById(R.id.description);
        image0 = findViewById(R.id.image0);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        radioGroup = findViewById(R.id.radioGroup);
        radio0 = radioGroup.findViewById(R.id.radio0);
        radio1 = radioGroup.findViewById(R.id.radio1);
        stocktv = findViewById(R.id.stocktv);

        firebaseFirestore.collection("Products").document(product_id).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        name.setText(documentSnapshot.getString("Name"));
                        brand.setText(documentSnapshot.getString("Brand"));
                        price.setText(documentSnapshot.getString("Price"));
                        mrp.setText(documentSnapshot.getString("Mrp"));
                        description.setText(documentSnapshot.getString("Description"));
                        extra.setText(documentSnapshot.getString("Extra"));
                        if (documentSnapshot.getString("Stock") == "1")
                            stocktv.setText("In stock");
                        else
                            stocktv.setText("Not in stock");
                        a=documentSnapshot.getString("Image");
                        image_= (List<Object>) documentSnapshot.get("list_image");

                        Glide.with(getApplicationContext()).load(a).into(image0);
                        Glide.with(getApplicationContext()).load(image_.get(0)).into(image1);
                        Glide.with(getApplicationContext()).load(image_.get(1)).into(image2);
                        Glide.with(getApplicationContext()).load(image_.get(2)).into(image3);
                        Glide.with(getApplicationContext()).load(image_.get(3)).into(image4);


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    if (checkedRadioButton.getText().equals("In stock"))
                        stock = "1";
                    else
                        stock = "0";
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadDatca();
            }
        });

    }

    private void uploadDatca() {
        descriptions = description.getText().toString();
        prices = price.getText().toString();
        mrps = mrp.getText().toString();
        extras = extra.getText().toString();


        if (prices.isEmpty())
            price.setError("Empty");
        else if (mrps.isEmpty())
            mrp.setError("Empty");
        else if (extras.isEmpty())
            extra.setError("Empty");
        else if (descriptions.isEmpty())
            description.setError("Empty");
        else if (stock.isEmpty())
            Toast.makeText(getApplicationContext(), "Choose Stock", Toast.LENGTH_SHORT).show();
        else {
            setData();
        }

    }

    private void setData() {

        Map<String, Object> detail = new HashMap<>();
        detail.put("Description", descriptions);
        detail.put("Price", prices);
        detail.put("Mrp", mrps);
        detail.put("Extra", extras);
        detail.put("Stock", stock);

        firebaseFirestore.collection("Products").document(product_id)
                .update(detail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
