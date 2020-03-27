package com.example.mysendsms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;

    EditText number, message;
    Button sendSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number = (EditText) findViewById(R.id.inputNumber);
        message = (EditText) findViewById(R.id.inputMessage);
        sendSMS = (Button) findViewById(R.id.buttonSend);

        sendSMS.setEnabled(false);
        if(checkPermission(Manifest.permission.SEND_SMS)) {
            sendSMS.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);

        }
    }

    public void onSend(View v) {
        String phoneNumber = number.getText().toString();
        String smsMessage = message.getText().toString();

        if(phoneNumber == null || phoneNumber.length() == 0 || sendSMS == null || sendSMS.length() ==0) {
            return;
        }

        if(checkPermission(Manifest.permission.SEND_SMS)) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber,null,smsMessage,null, null);
            Toast.makeText(this,"Message sent!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Message denied!", Toast.LENGTH_SHORT).show();
        }

        number.setText("");
        message.setText("");
    }

    public boolean checkPermission (String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}
