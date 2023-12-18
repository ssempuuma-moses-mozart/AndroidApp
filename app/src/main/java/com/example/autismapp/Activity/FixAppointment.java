package com.example.autismapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.autismapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class FixAppointment extends AppCompatActivity {

    private EditText nameEditText, specializationEditText, cityEditText, addressEditText;
    private TextView date, time;
    private String docid, name, addr, city, spl;
    private ProgressDialog progressDialog;
    private int d = 0, m = 0, y = 0, min = 0, h = 0;
    private HashMap<String, String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_appointment);

        setTitle("Get Appointment");

        nameEditText = findViewById(R.id.nameEditText);
        specializationEditText = findViewById(R.id.specializationEditText);
        cityEditText = findViewById(R.id.cityEditText);
        addressEditText = findViewById(R.id.addressEditText);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);

        progressDialog = new ProgressDialog(FixAppointment.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        AppCompatButton datebtn = findViewById(R.id.setdate);
        datebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(FixAppointment.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        AppCompatButton timebtn = findViewById(R.id.settime);
        timebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(FixAppointment.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
// Inside your onCreate method after setting click listeners for date and time buttons

        AppCompatButton setapp = findViewById(R.id.setappoinment);
        setapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user inputs
                name = nameEditText.getText().toString().trim();
                spl = specializationEditText.getText().toString().trim();
                city = cityEditText.getText().toString().trim();
                addr = addressEditText.getText().toString().trim();

                // Get the selected date and time from TextViews
                String selectedDate = date.getText().toString().trim();
                String selectedTime = time.getText().toString().trim();

                if (!name.isEmpty() && !spl.isEmpty() && !city.isEmpty() && !addr.isEmpty() && !selectedDate.isEmpty() && !selectedTime.isEmpty()) {
                    progressDialog.show();
                    // Prepare data for submission
                    hashMap = new HashMap<>();
                    hashMap.put("Address", addr);
                    hashMap.put("City", city);
                    hashMap.put("Name", name);
                    hashMap.put("Specialization", spl);
                    hashMap.put("DateAndTime", selectedDate + " " + selectedTime);

                    // Push data to Firebase Realtime Database
                    String key = FirebaseDatabase.getInstance().getReference().child("PendingPatientAppointments").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().getKey();
                    hashMap.put("PatientAppointKey", key);

                    FirebaseDatabase.getInstance().getReference().child("PendingPatientAppointments").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(key).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Success
                                progressDialog.dismiss();
                                showMessageAlert(FixAppointment.this, "Completed", "Appointment has been requested to Dr. " + name + " on " + selectedDate + " at " + selectedTime + ". Check status in pending appointments.", "OK", (byte) 1);
                            } else {
                                // Failure
                                progressDialog.dismiss();
                                showMessageAlert(FixAppointment.this, "Network Error", "Make sure you are connected to the internet.", "OK", (byte) 0);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            showMessageAlert(FixAppointment.this, "Network Error", "Make sure you are connected to the internet.", "OK", (byte) 0);
                        }
                    });
                } else {
                    // Display an alert if any field is empty
                    showMessageAlert(FixAppointment.this, "Error", "Please fill in all the details and select date/time.", "OK", (byte) 0);
                }
            }
        });

    }

    // Rest of your code for showMessageAlert method...



    public static void showMessageAlert(Context context, String title, String message, String buttonstring, byte type) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        if (type == 1) {
            alertDialog.setIcon(android.R.drawable.ic_dialog_info);
        } else {
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        }
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, buttonstring, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
