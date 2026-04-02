package com.divanstrawberry.musicquizz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StatisticsActivity extends AppCompatActivity {

    private TextView statisticsText;
    private Button resetButton;
    private Button backButton;
    private SharedPreferences prefs;

    // Объявляем переменную как поле класса
    private int skippedCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setStatusBarColor(Color.parseColor("#9E7E41"));
        getWindow().setNavigationBarColor(Color.parseColor("#9E7E41"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        statisticsText = findViewById(R.id.text_statistics);
        resetButton = findViewById(R.id.button_reset_stats);
        backButton = findViewById(R.id.button_back_main);

        prefs = getSharedPreferences("quiz_stats", MODE_PRIVATE);

        // Обновляем статистику при запуске, не зависит от Intent
        loadStatistics();

        resetButton.setOnClickListener(v -> {
            prefs.edit().clear().apply();
            loadStatistics(); // Обновляем статистику после сброса
        });

        backButton.setOnClickListener(v -> {
            Intent intentBack = new Intent(this, MainActivity.class);
            intentBack.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intentBack);
            finish();
        });
    }

    private void loadStatistics() {
        int correct = prefs.getInt("correct", 0);
        int wrong = prefs.getInt("wrong", 0);
        int skipped = prefs.getInt("skipped", 0);

        int totalAttempts = correct + wrong + skipped;

        String stats = "Пробных попыток: " + totalAttempts + "\n"
                + "Правильных ответов: " + correct + "\n"
                + "Неправильных ответов: " + wrong + "\n"
                + "Пропущенных вопросов: " + skipped;

        statisticsText.setText(stats);
    }

    private void loadStatistics(int skipped) {
        int correct = prefs.getInt("correct", 0);
        int wrong = prefs.getInt("wrong", 0);
        int totalAttempts = correct + wrong + skipped;

        String stats = "Пробных попыток: " + totalAttempts + "\n"
                + "Правильных ответов: " + correct + "\n"
                + "Неправильных ответов: " + wrong + "\n"
                + "Пропущенных вопросов: " + skipped;

        statisticsText.setText(stats);
    }
}