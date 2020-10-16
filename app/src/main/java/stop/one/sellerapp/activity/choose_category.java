package stop.one.sellerapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import stop.one.sellerapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class choose_category extends AppCompatActivity {

    RadioGroup radio_grp;
    RadioButton electronic,mobile,pet,books,cloth,footwear,sport,handicraft;

    TextView next;
    String doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        radio_grp=findViewById(R.id.radio_grp);
        electronic=radio_grp.findViewById(R.id.electronic);
        mobile=radio_grp.findViewById(R.id.mobile);
        pet=radio_grp.findViewById(R.id.pet);
        sport=radio_grp.findViewById(R.id.sport);
        books=radio_grp.findViewById(R.id.books);
        handicraft=radio_grp.findViewById(R.id.handicraft);
        cloth=radio_grp.findViewById(R.id.cloth);
        footwear=radio_grp.findViewById(R.id.footwear);

        next=findViewById(R.id.next);

        radio_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    // Changes the textview's text to "Checked: example radiobutton text"
//                    tv.setText("Checked:" + checkedRadioButton.getText());
                    doc=checkedRadioButton.getText().toString();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(doc.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Select a category",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent=new Intent(choose_category.this,choose_item.class);
                    intent.putExtra("Doc",doc);
                    startActivity(intent);
                }
            }
        });

    }
}
