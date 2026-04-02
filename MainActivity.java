package com.divanstrawberry.musicquizz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonClassical, buttonRock, buttonKpop, buttonStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(Color.parseColor("#9E7E41"));
        getWindow().setNavigationBarColor(Color.parseColor("#9E7E41"));
        setContentView(R.layout.activity_main);

        // Устанавливаем заголовок в ActionBar (если есть)
        setTitle("Музыкальный квиз");

        // Находим кнопки по id
        buttonClassical = findViewById(R.id.button_classical);
        buttonRock = findViewById(R.id.button_rock);
        buttonKpop = findViewById(R.id.button_kpop);
        buttonStatistics = findViewById(R.id.button_statistics);

        // Обработчик нажатия для кнопки "Классическая музыка"
        buttonClassical.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ClassicalQuizActivity.class);
            startActivity(intent);
        });

        // Обработчик нажатия для кнопки "Рок"
        buttonRock.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RockQuizActivity.class);
            startActivity(intent);
        });

        // Обработчик нажатия для кнопки "К-Поп"
        buttonKpop.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, KpopQuizActivity.class);
            startActivity(intent);
        });

        // Обработчик нажатия для кнопки "Статистика"
        buttonStatistics.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
            startActivity(intent);
        });
    }
}