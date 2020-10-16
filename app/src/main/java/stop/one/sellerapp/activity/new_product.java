package stop.one.sellerapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import stop.one.sellerapp.R;

public class new_product extends AppCompatActivity {

    private static final int SELECT_IMAGE = 234;
    int click_count;
    String item, category,user_id,document_id;
    EditText name, brand, price, mrp, extra, description;
    TextView submit;
    ImageView image0, image1, image2, image3, image4;
    String names, brands, prices, mrps, extras, descriptions,stock;
    RadioGroup radioGroup;
    List<ImageView> image_list = new ArrayList<>();
    List<Uri> image_url = new ArrayList<>();
    List<String> image_link = new ArrayList<>();
    RadioButton radio0,radio1;
    FirebaseStorage storage=FirebaseStorage.getInstance();
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        item = getIntent().getStringExtra("item");
        category = getIntent().getStringExtra("category");

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
        radioGroup=findViewById(R.id.radioGroup);
        radio0=radioGroup.findViewById(R.id.radio0);
        radio1=radioGroup.findViewById(R.id.radio1);

        image_list.add(image0);
        image_list.add(image1);
        image_list.add(image2);
        image_list.add(image3);
        image_list.add(image4);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    if(checkedRadioButton.getText()=="In stock")
                        stock="1";
                    else
                        stock="0";
                }
            }
        });

        image0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click_count=0;
                selectImageFromGallery();
            }
        });

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click_count=1;
                if (image_url.size() > 0)
                    selectImageFromGallery();
                else
                    Toast.makeText(getApplicationContext(), "Choose main image first", Toast.LENGTH_SHORT).show();
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click_count=2;
                if (image_url.size() > 0)
                    selectImageFromGallery();
                else
                    Toast.makeText(getApplicationContext(), "Choose main image first", Toast.LENGTH_SHORT).show();
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click_count=3;
                if (image_url.size() > 0)
                    selectImageFromGallery();
                else
                    Toast.makeText(getApplicationContext(), "Choose main image first", Toast.LENGTH_SHORT).show();
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click_count=4;
                if (image_url.size() > 0)
                    selectImageFromGallery();
                else
                    Toast.makeText(getApplicationContext(), "Choose main image first", Toast.LENGTH_SHORT).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadDatca();
            }
        });


    }

    protected void selectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            image_url.add(click_count,data.getData());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image_url.get(click_count));
                image_list.get(click_count).setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadDatca() {
        names = name.getText().toString();
        brands=brand.getText().toString();
        descriptions=description.getText().toString();
        prices=price.getText().toString();
        mrps=mrp.getText().toString();
        extras=extra.getText().toString();

        if(names.isEmpty())
            name.setError("Empty");
        else if(brands.isEmpty())
            brand.setError("Empty");
        else if(brands.isEmpty())
            brand.setError("Empty");
        else if(prices.isEmpty())
            price.setError("Empty");
        else if(mrps.isEmpty())
            mrp.setError("Empty");
        else if(extras.isEmpty())
            extra.setError("Empty");
        else if(descriptions.isEmpty())
            description.setError("Empty");
        else if(image_url.size()<5)
            Toast.makeText(getApplicationContext(),"Empty image",Toast.LENGTH_SHORT).show();
        else if(stock.isEmpty())
            Toast.makeText(getApplicationContext(),"Choose Stock",Toast.LENGTH_SHORT).show();
        else
        {
            uploadFile();
        }

    }

    private void uploadFile() {
        if (image_url.size()==5) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference riversRef = storage.getReference().child(user_id);

            for(int i =0;i<image_url.size();i++)
            {
                int finalI = i;
                riversRef.putFile(image_url.get(i))
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                int z= finalI;

                                taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                        new OnCompleteListener<Uri>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                image_link.add(task.getResult().toString());
                                                if(finalI ==4)
                                                {
                                                    progressDialog.dismiss();
                                                    setData();
                                                }
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NotNull UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                            }
                        });



            }
        }
    }

    private void setData() {
        document_id=firebaseFirestore.collection("Products").document().getId();

        List<String> lst=new ArrayList<>();
        lst.add(image_link.get(1));
        lst.add(image_link.get(2));
        lst.add(image_link.get(3));
        lst.add(image_link.get(4));

        Map<String,Object> detail=new HashMap<>();
        detail.put("Name",names);
        detail.put("Brand",brands);
        detail.put("Description",descriptions);
        detail.put("Price",prices);
        detail.put("Mrp",mrps);
        detail.put("Shop_id",user_id);
        detail.put("Category",category);
        detail.put("Item",item);
        detail.put("product_id",document_id);
        detail.put("Extra",extras);
        detail.put("Image",image_link.get(0));
        detail.put("list_image",lst);
        detail.put("Stock",stock);

        firebaseFirestore.collection("Products").document(document_id)
                .set(detail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
