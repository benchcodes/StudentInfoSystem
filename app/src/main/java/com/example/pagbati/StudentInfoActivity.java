package com.example.pagbati;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class StudentInfoActivity extends AppCompatActivity {

    private TextView tvWelcomeUser;
    private MaterialButton btnLogout;

    private TextInputLayout tilStudentNumber, tilLastName, tilFirstName, tilMiddleName, tilProgram, tilYearLevel;
    private TextInputEditText etStudentNumber, etLastName, etFirstName, etMiddleName;
    private AutoCompleteTextView actvProgram, actvYearLevel;
    private MaterialButton btnSave, btnClear;

    private MaterialCardView cardRecordSummary;
    private TextView tvDisplayFullName, tvDisplayStudentNumber, tvDisplayProgram, tvDisplayYearLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        initViews();
        setupDropdowns();
        setupListeners();

        // Get logged-in username
        String username = getIntent().getStringExtra(LoginActivity.EXTRA_USERNAME);
        if (!TextUtils.isEmpty(username)) {
            tvWelcomeUser.setText("Welcome, " + username);
        }
    }

    private void initViews() {
        tvWelcomeUser = findViewById(R.id.tvWelcomeUser);
        btnLogout = findViewById(R.id.btnLogout);

        tilStudentNumber = findViewById(R.id.tilStudentNumber);
        tilLastName = findViewById(R.id.tilLastName);
        tilFirstName = findViewById(R.id.tilFirstName);
        tilMiddleName = findViewById(R.id.tilMiddleName);
        tilProgram = findViewById(R.id.tilProgram);
        tilYearLevel = findViewById(R.id.tilYearLevel);

        etStudentNumber = findViewById(R.id.etStudentNumber);
        etLastName = findViewById(R.id.etLastName);
        etFirstName = findViewById(R.id.etFirstName);
        etMiddleName = findViewById(R.id.etMiddleName);

        actvProgram = findViewById(R.id.actvProgram);
        actvYearLevel = findViewById(R.id.actvYearLevel);

        btnSave = findViewById(R.id.btnSave);
        btnClear = findViewById(R.id.btnClear);

        cardRecordSummary = findViewById(R.id.cardRecordSummary);
        tvDisplayFullName = findViewById(R.id.tvDisplayFullName);
        tvDisplayStudentNumber = findViewById(R.id.tvDisplayStudentNumber);
        tvDisplayProgram = findViewById(R.id.tvDisplayProgram);
        tvDisplayYearLevel = findViewById(R.id.tvDisplayYearLevel);
    }

    private void setupDropdowns() {
        ArrayAdapter<CharSequence> programAdapter = ArrayAdapter.createFromResource(
                this, R.array.programs_array, android.R.layout.simple_dropdown_item_1line);
        actvProgram.setAdapter(programAdapter);
        if (programAdapter.getCount() > 0 && programAdapter.getItem(0) != null) {
            actvProgram.setText(programAdapter.getItem(0).toString(), false);
        }

        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(
                this, R.array.year_levels_array, android.R.layout.simple_dropdown_item_1line);
        actvYearLevel.setAdapter(yearAdapter);
        if (yearAdapter.getCount() > 0 && yearAdapter.getItem(0) != null) {
            actvYearLevel.setText(yearAdapter.getItem(0).toString(), false);
        }
    }

    private void setupListeners() {
        btnSave.setOnClickListener(v -> saveStudentRecord());
        btnClear.setOnClickListener(v -> clearFields());
        btnLogout.setOnClickListener(v -> {
            Toast.makeText(StudentInfoActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StudentInfoActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void saveStudentRecord() {
        clearErrors();

        String studentNum = etStudentNumber.getText() != null ? etStudentNumber.getText().toString().trim() : "";
        String lastName = etLastName.getText() != null ? etLastName.getText().toString().trim() : "";
        String firstName = etFirstName.getText() != null ? etFirstName.getText().toString().trim() : "";
        String middleName = etMiddleName.getText() != null ? etMiddleName.getText().toString().trim() : "";
        String program = actvProgram.getText() != null ? actvProgram.getText().toString().trim() : "";
        String yearLevel = actvYearLevel.getText() != null ? actvYearLevel.getText().toString().trim() : "";

        boolean isValid = true;

        if (TextUtils.isEmpty(studentNum)) {
            tilStudentNumber.setError(getString(R.string.empty_field_error));
            isValid = false;
        }
        if (TextUtils.isEmpty(lastName)) {
            tilLastName.setError(getString(R.string.empty_field_error));
            isValid = false;
        }
        if (TextUtils.isEmpty(firstName)) {
            tilFirstName.setError(getString(R.string.empty_field_error));
            isValid = false;
        }
        if (TextUtils.isEmpty(middleName)) {
            tilMiddleName.setError(getString(R.string.empty_field_error));
            isValid = false;
        }
        if (TextUtils.isEmpty(program)) {
            tilProgram.setError(getString(R.string.empty_field_error));
            isValid = false;
        }
        if (TextUtils.isEmpty(yearLevel)) {
            tilYearLevel.setError(getString(R.string.empty_field_error));
            isValid = false;
        }

        if (isValid) {
            String fullName = lastName + ", " + firstName + " " + middleName;
            tvDisplayFullName.setText("Name: " + fullName);
            tvDisplayStudentNumber.setText("Student ID: " + studentNum);
            tvDisplayProgram.setText("Program: " + program);
            tvDisplayYearLevel.setText("Year Level: " + yearLevel);

            cardRecordSummary.setVisibility(View.VISIBLE);
            Toast.makeText(this, getString(R.string.record_saved), Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etStudentNumber.setText("");
        etLastName.setText("");
        etFirstName.setText("");
        etMiddleName.setText("");

        setupDropdowns();
        clearErrors();
        cardRecordSummary.setVisibility(View.GONE);
    }

    private void clearErrors() {
        tilStudentNumber.setError(null);
        tilLastName.setError(null);
        tilFirstName.setError(null);
        tilMiddleName.setError(null);
        tilProgram.setError(null);
        tilYearLevel.setError(null);
    }
}
