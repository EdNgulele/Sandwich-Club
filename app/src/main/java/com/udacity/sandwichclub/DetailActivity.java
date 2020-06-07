package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView originPlaceLable;
    private TextView originPlaceTv;
    private TextView alsoKnownLable;
    private TextView alsoKnownTv;
    private TextView descriptionTv;
    private TextView ingredientsTv;
    private ImageView sandwitchIv;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json, this);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);


        setTitle(sandwich.getMainName());
    }

    //initialize the view variables
    private void init() {
        originPlaceLable = findViewById(R.id.origin_place_lable);
        originPlaceTv = findViewById(R.id.origin_place_tv);
        alsoKnownLable = findViewById(R.id.also_known_lable);
        alsoKnownTv = findViewById(R.id.also_known_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        sandwitchIv = findViewById(R.id.image_iv);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //load the sandwich image
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(sandwitchIv);

        descriptionTv.setText(sandwich.getDescription());
        originPlaceTv.setText(sandwich.getPlaceOfOrigin());


        if (sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownTv.setVisibility(View.GONE);
            alsoKnownLable.setVisibility(View.GONE);
        } else {
            List<String> alsonKnownList = sandwich.getAlsoKnownAs();
            alsoKnownTv.setText(splitter(alsonKnownList));
        }

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            originPlaceTv.setVisibility(View.GONE);
            originPlaceLable.setVisibility(View.GONE);
        } else {
            originPlaceTv.setText(sandwich.getPlaceOfOrigin());
        }

        List<String> ingredientList = sandwich.getIngredients();
        ingredientsTv.setText(splitter(ingredientList));

    }

    private static String splitter(List<String> list) {
        String str = TextUtils.join("\n", list);
        return str;
    }
}
