package com.example.mechu_project;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShowDetail extends AppCompatActivity {

    private ImageView menuImageView;
    private TextView menuTitleTextView, menuCalorieTextView, menuProteinTextView, menuFatTextView, menuCarbohydrateTextView;
    private ProgressBar proteinProgressBar, carbsProgressBar, fatProgressBar;
    private TextView proteinProgressText, carbsProgressText, fatProgressText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);

        menuImageView = findViewById(R.id.menuImage);
        menuTitleTextView = findViewById(R.id.menuTitle);
        menuCalorieTextView = findViewById(R.id.menuCal);
        menuProteinTextView = findViewById(R.id.menuProtein);
        menuFatTextView = findViewById(R.id.menuFat);
        menuCarbohydrateTextView = findViewById(R.id.menuCarbohydrate);
        proteinProgressBar = findViewById(R.id.proteinProgressBar);
        carbsProgressBar = findViewById(R.id.carbsProgressBar);
        fatProgressBar = findViewById(R.id.fatProgressBar);
        proteinProgressText = findViewById(R.id.proteinProgressText);
        carbsProgressText = findViewById(R.id.carbsProgressText);
        fatProgressText = findViewById(R.id.fatProgressText);

        String menuName = getIntent().getStringExtra("menuName");

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.getFoodDetails(menuName);

        if (cursor != null && cursor.moveToFirst()) {
            String foodName = cursor.getString(cursor.getColumnIndexOrThrow("food_name"));
            String foodImg = cursor.getString(cursor.getColumnIndexOrThrow("food_img"));
            double calorie = cursor.getDouble(cursor.getColumnIndexOrThrow("calorie"));
            double carbs = cursor.getDouble(cursor.getColumnIndexOrThrow("carbs"));
            double protein = cursor.getDouble(cursor.getColumnIndexOrThrow("protein"));
            double fat = cursor.getDouble(cursor.getColumnIndexOrThrow("fat"));

            menuTitleTextView.setText(foodName);
            menuCalorieTextView.setText(String.format("%s kcal", calorie));
            menuCarbohydrateTextView.setText(String.format("탄수화물: %s g", carbs));
            menuProteinTextView.setText(String.format("단백질: %s g", protein));
            menuFatTextView.setText(String.format("지방: %s g", fat));

            setCircularProgress(proteinProgressBar, protein, 50, proteinProgressText, "단백질");
            setCircularProgress(carbsProgressBar, carbs, 300, carbsProgressText, "탄수화물");
            setCircularProgress(fatProgressBar, fat, 70, fatProgressText, "지방");

            if (foodImg != null) {
                Bitmap bitmap = ImageUtils.loadBitmapFromFile(this, foodImg);
                if (bitmap != null) {
                    menuImageView.setImageBitmap(bitmap);
                } else {
                    menuImageView.setImageResource(R.drawable.characterlogo);
                }
            } else {
                menuImageView.setImageResource(R.drawable.characterlogo);
            }

            cursor.close();
        }

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setCircularProgress(ProgressBar progressBar, double value, double maxValue, TextView textView, String label) {
        int progress = (int) ((value / maxValue) * 100);
        progressBar.setProgress(progress);
        textView.setText(String.format("%.0fg/%.0fg", value, maxValue));
    }
}
