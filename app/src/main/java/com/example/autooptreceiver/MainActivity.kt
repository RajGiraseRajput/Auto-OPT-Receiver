package com.example.autooptreceiver

import android.Manifest
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.autooptreceiver.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), SmsBroadcastReceiver.OTPListener {

    private lateinit var smsReceiver: SmsBroadcastReceiver
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Request SMS permissions
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS), 1)

        // Register SMS Receiver
        smsReceiver = SmsBroadcastReceiver()
        smsReceiver.otpListener = this
        val filter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        registerReceiver(smsReceiver, filter)


        val clickListener = { view: android.view.View ->
            when (view) {
                binding.text1 -> Toast.makeText(this, "Text 1", Toast.LENGTH_SHORT).show()
                binding.text2 -> Toast.makeText(this, "Text 2", Toast.LENGTH_SHORT).show()
                binding.text3 -> Toast.makeText(this, "Text 3", Toast.LENGTH_SHORT).show()
                binding.text4 -> Toast.makeText(this, "Text 4", Toast.LENGTH_SHORT).show()
            }
        }

        // Assign the same listener to all views
        binding.text1.setOnClickListener(clickListener)
        binding.text2.setOnClickListener(clickListener)
        binding.text3.setOnClickListener(clickListener)
        binding.text4.setOnClickListener(clickListener)



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
