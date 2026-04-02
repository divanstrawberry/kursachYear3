package com.divanstrawberry.musicquizz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class KpopQuizActivity extends AppCompatActivity {

    private TextView questionNumberText;
    private TextView questionText;
    private Button yesButton;
    private Button noButton;
    private Button nextButton;
    private Button skipQuestionButton;

    private int skippedCount = 0;
    private int currentQuestionIndex = 0;

    // Вопросы для классической музыки
    private String[] questions = {
            "TWICE-самая награждаемая женская группа?",
            "В ENHYPEN 7 участников?",
            "ASTRO были созданы через реалити-шоу?",
            "Большинство кей-поп групп дебютируют более,чем с 10 участникам?",
            "Монбэлль - это сокращенное название для всей коллекции наград, полученных в жанре кей-поп?",
            "All-Kill означает, что песня заняла первое место одновременно на всех крупных онлайн-чартах Кореи?",
            "Группа ATEEZ впервые дебютировала в 2018 году?",
            "В 2015 году EXO дебютировали с альбомом Love Yourself?",
            "Группа Monsta X использует японский язык в большинстве своих песен?",
            "В группе SEVENTEEN есть 13 участников, разделенных на три подпоставки: вокал, хип-хоп и производственный?"
    };

    private boolean[] correctAnswers = {
            true,
            true,
            false,
            false,
            false,
            true,
            true,
            false,
            false,
            true
    };

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setStatusBarColor(Color.parseColor("#9E7E41"));
        getWindow().setNavigationBarColor(Color.parseColor("#9E7E41"));

        SharedPreferences globalPrefs = getSharedPreferences("global_stats", MODE_PRIVATE);
        int totalAttempts = globalPrefs.getInt("total_attempts", 0);
        globalPrefs.edit().putInt("total_attempts", totalAttempts + 1).apply();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionNumberText = findViewById(R.id.text_question_number);
        questionText = findViewById(R.id.text_question);
        yesButton = findViewById(R.id.button_yes);
        noButton = findViewById(R.id.button_no);
        nextButton = findViewById(R.id.button_next);
        skipQuestionButton = findViewById(R.id.button_skip_question);

        prefs = getSharedPreferences("classical_quiz_stats", MODE_PRIVATE);

        prefs.edit().putInt("correct", 0).putInt("wrong", 0).apply();
        skippedCount = 0;

        loadQuestion();

        yesButton.setOnClickListener(v -> verifyAnswer(true));
        noButton.setOnClickListener(v -> verifyAnswer(false));
        nextButton.setOnClickListener(v -> goToNextQuestion());
        skipQuestionButton.setOnClickListener(v -> skipQuestion());

        hideNextButton();
    }

    private void loadQuestion() {
        questionNumberText.setText("Вопрос " + (currentQuestionIndex + 1));
        questionText.setText(questions[currentQuestionIndex]);

        yesButton.setEnabled(true);
        noButton.setEnabled(true);
    }

    private void verifyAnswer(boolean answer) {
        boolean correct = correctAnswers[currentQuestionIndex];
        boolean isCorrect = (answer == correct);

        saveResult(isCorrect);

        String msg = isCorrect ? "Ответ верный" : "Ответ не верный";

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 300);
        toast.show();

        showNextButton();

        yesButton.setEnabled(false);
        noButton.setEnabled(false);
    }

    private void saveResult(boolean isCorrect) {
        int correctCount = prefs.getInt("correct", 0);
        int wrongCount = prefs.getInt("wrong", 0);

        SharedPreferences.Editor editor = prefs.edit();
        if (isCorrect) {
            editor.putInt("correct", correctCount + 1);
        } else {
            editor.putInt("wrong", wrongCount + 1);
        }
        editor.apply();
    }

    private void showNextButton() {
        nextButton.setVisibility(Button.VISIBLE);
    }

    private void hideNextButton() {
        nextButton.setVisibility(Button.INVISIBLE);
    }

    private void goToNextQuestion() {
        if (currentQuestionIndex >= questions.length - 1) {
            endQuiz();
        } else {
            currentQuestionIndex++;
            loadQuestion();
            hideNextButton();
        }
    }

    private void skipQuestion() {
        skippedCount++;

        if (currentQuestionIndex >= questions.length - 1) {
            endQuiz();
        } else {
            currentQuestionIndex++;
            loadQuestion();
            hideNextButton();

            yesButton.setEnabled(true);
            noButton.setEnabled(true);
        }
    }

    private void endQuiz() {
        int correctAnswersCount = prefs.getInt("correct", 0);
        int wrongAnswersCount = prefs.getInt("wrong", 0);

        // Обновляем глобальную статистику
        SharedPreferences globalPrefs = getSharedPreferences("global_quiz_stats", MODE_PRIVATE);
        SharedPreferences.Editor globalEditor = globalPrefs.edit();

        int totalCorrect = globalPrefs.getInt("total_correct", 0);
        int totalWrong = globalPrefs.getInt("total_wrong", 0);
        int totalSkipped = globalPrefs.getInt("total_skipped", 0);
        int totalAttempts = globalPrefs.getInt("total_attempts", 0);

        globalEditor.putInt("total_correct", totalCorrect + correctAnswersCount);
        globalEditor.putInt("total_wrong", totalWrong + wrongAnswersCount);
        globalEditor.putInt("total_skipped", totalSkipped + skippedCount);
        globalEditor.putInt("total_attempts", totalAttempts); // уже увеличено в onCreate

        globalEditor.apply();

        // Переход к итогам
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultActivity.EXTRA_CORRECT, correctAnswersCount);
        intent.putExtra(ResultActivity.EXTRA_TOTAL, questions.length);
        intent.putExtra(ResultActivity.EXTRA_SKIPPED, skippedCount);
        startActivity(intent);
        finish();
    }
}