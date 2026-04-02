package com.divanstrawberry.musicquizz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    // Статические константы для передачи данных
    public static final String EXTRA_CORRECT = "correct";
    public static final String EXTRA_TOTAL = "total";
    public static final String EXTRA_SKIPPED = "skipped";

    private TextView resultTextView;
    private Button viewStatsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setStatusBarColor(Color.parseColor("#9E7E41"));
        getWindow().setNavigationBarColor(Color.parseColor("#9E7E41"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultTextView = findViewById(R.id.text_result);
        viewStatsButton = findViewById(R.id.button_view_stats);
        Button mainMenuButton = findViewById(R.id.button_main_menu);

        // Получаем результаты из Intent
        int correct = getIntent().getIntExtra(EXTRA_CORRECT, 0);
        int total = getIntent().getIntExtra(EXTRA_TOTAL, 0);
        int skipped = getIntent().getIntExtra(ResultActivity.EXTRA_SKIPPED, 0);

        // Выводим результат
        resultTextView.setText("Вы ответили правильно: " + correct + " из " + total + "\n" +
                "Пропущено вопросов: " + skipped);

        // Обновляем статистику в SharedPreferences
        SharedPreferences prefs = getSharedPreferences("quiz_stats", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Получаем текущие значения или 0, если их еще нет
        int currentCorrect = prefs.getInt("correct", 0);
        int currentWrong = prefs.getInt("wrong", 0);
        int currentSkipped = prefs.getInt("skipped", 0);

        // Обновляем с учетом текущих результатов
        editor.putInt("correct", currentCorrect + correct);
        editor.putInt("wrong", currentWrong); // в вашем коде не подсчитывается wrong, можно добавить при необходимости
        editor.putInt("skipped", currentSkipped + skipped);
        editor.apply();

        // Обработка кнопки "Посмотреть статистику"
        viewStatsButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, StatisticsActivity.class);
            startActivity(intent);
        });

        // Обработка кнопки "Главное меню"
        mainMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}