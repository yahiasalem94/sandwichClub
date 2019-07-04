package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    /* Views */
    private ImageView ingredientsIv;
    private TextView originTv;
    private TextView descriptionTv;
    private TextView alsoKnownTv;
    private TextView ingredientsTv;

    /* Progress Bar */
    private ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        originTv = findViewById(R.id.origin_tv);
        descriptionTv = findViewById(R.id.description_tv);
        alsoKnownTv = findViewById(R.id.also_known_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);

        progress = findViewById(R.id.progressBar);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        setTitle(sandwich.getMainName());

        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.drawable.placeholder)
                .into(ingredientsIv,  new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (progress != null) {
                            progress .setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });

        String listToString = TextUtils.join(",  ", sandwich.getIngredients());
        if ( !listToString.isEmpty() ) {
            ingredientsTv.setText(listToString);
        }

        listToString = TextUtils.join(",  ", sandwich.getAlsoKnownAs());
        if ( !listToString.isEmpty() ) {
            alsoKnownTv.setText(listToString);
        }

        if ( !sandwich.getPlaceOfOrigin().isEmpty() ) {
            originTv.setText(sandwich.getPlaceOfOrigin());
        }

        if ( !sandwich.getDescription().isEmpty() ) {
            descriptionTv.setText(sandwich.getDescription());
        }

    }
}
