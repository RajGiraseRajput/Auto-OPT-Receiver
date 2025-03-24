package com.example.autooptreceiver

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.auth.api.phone.SmsRetriever


class MainActivity : AppCompatActivity(), SmsBroadcastReceiver.OTPListener {

    private lateinit var smsReceiver: SmsBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Request SMS permissions
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS), 1)

        // Register SMS Receiver
        smsReceiver = SmsBroadcastReceiver()
        smsReceiver.otpListener = this
        val filter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        registerReceiver(smsReceiver, filter)
    }

    override fun onOTPReceived(otp: String) {
        Toast.makeText(this, "Received OTP: $otp", Toast.LENGTH_LONG).show()
        Log.d("OTP", "Received OTP: $otp ✅")
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(smsReceiver)
    }
}


//class MainActivity : Activity(), SmsBroadcastReceiver.OTPListener {
//
//    private lateinit var smsReceiver: SmsBroadcastReceiver
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        startSmsRetriever()
//    }
//
//    private fun startSmsRetriever() {
//        val client = SmsRetriever.getClient(this)
//        val task = client.startSmsRetriever()
//
//        task.addOnSuccessListener {
//            Toast.makeText(this, "Waiting for OTP...", Toast.LENGTH_SHORT).show()
//            Log.d("OTP", "SmsRetriever started successfully ✅")
//        }
//
//        task.addOnFailureListener {
//            Toast.makeText(this, "Failed to start SMS Retriever", Toast.LENGTH_SHORT).show()
//            Log.e("OTP", "SmsRetriever failed to start ❌")
//        }
//
//        // Register BroadcastReceiver
//        smsReceiver = SmsBroadcastReceiver()
//        smsReceiver.otpListener = this
//        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            registerReceiver(smsReceiver, intentFilter, RECEIVER_EXPORTED)
//        } else {
//            registerReceiver(smsReceiver, intentFilter)
//        }
//
//    }
//
//    override fun onOTPReceived(otp: String) {
//        Toast.makeText(this, "Received OTP: $otp", Toast.LENGTH_LONG).show()
//        Log.d("OTP", "Received OTP: $otp ✅")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        unregisterReceiver(smsReceiver)
//    }
//}
//
