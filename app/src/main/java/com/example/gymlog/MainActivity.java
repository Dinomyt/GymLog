package com.example.gymlog;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymlog.database.GymLogRepository;
import com.example.gymlog.database.entities.GymLog;
import com.example.gymlog.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private GymLogRepository repository;
    public static final String TAG = "DAC_GYMLOG";
    String mExercise = "";
    double mWeight = 0.0;
    int mReps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = GymLogRepository.getRepository(getApplication());

        binding.LogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationDisplay();
                insertGymLogRecord();
                updateDisplay();

            }
        });
    }
    private void insertGymLogRecord(){
        GymLog log = new GymLog(mExercise, mWeight, mReps);
        repository.insertGymLog(log);
    }
    private void updateDisplay(){
        String currentInfo = binding.LogDisplayTextView.getText().toString();
        Log.d(TAG, "Current Info: " + currentInfo);
        String newDisplay = String.format(Locale.US, "Exercise:%s%nWeight:%.2f%nReps:%d%n", mExercise, mWeight, mReps);
        binding.LogDisplayTextView.setText(newDisplay);
        Log.i(TAG, repository.getAllLogs().toString());
    }
    private void getInformationDisplay(){
        mExercise = binding.ExerciseInputEditText.getText().toString();
        try {
            mWeight = Double.parseDouble(binding.WeightInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from weight edit text");
        }
        try {
            mReps = Integer.parseInt(binding.RepInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from rep edit text");
        }

    }
}