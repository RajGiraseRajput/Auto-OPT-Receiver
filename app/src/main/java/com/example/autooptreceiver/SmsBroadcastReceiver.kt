package com.example.autooptreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.phone.SmsRetriever
import java.util.regex.Pattern

class SmsBroadcastReceiver : BroadcastReceiver() {

    interface OTPListener {
        fun onOTPReceived(otp: String)
    }

    var otpListener: OTPListener? = null

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle["pdus"] as Array<*>
                for (pdu in pdus) {
                    val sms = SmsMessage.createFromPdu(pdu as ByteArray)
                    val messageBody = sms.messageBody
                    Log.d("OTP", " Received SMS: $messageBody")

                    val otpPattern = Pattern.compile("\\b\\d{6}\\b")
                    val matcher = otpPattern.matcher(messageBody)
                    if (matcher.find()) {
                        val otp = matcher.group()
                        Log.d("OTP", " Extracted OTP: $otp")
                        Toast.makeText(context, "OTP: $otp", Toast.LENGTH_SHORT).show()
                        otpListener?.onOTPReceived(otp)
                    }
                }
            }
        }
    }
}


//class SmsBroadcastReceiver : BroadcastReceiver() {
//
//    var otpListener: OTPListener? = null
//
//    override fun onReceive(context: Context?, intent: Intent?) {
//        Log.d("OTP", "📡 Broadcast received: ${intent?.action}")
//
//        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
//            val extras = intent.extras
//            val status = extras?.get(SmsRetriever.EXTRA_STATUS) as? com.google.android.gms.common.api.Status
//
//            when (status?.statusCode) {
//                com.google.android.gms.common.api.CommonStatusCodes.SUCCESS -> {
//                    val message = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as? String
//                    Log.d("OTP", "📩 Received SMS: $message")
//
//                    if (!message.isNullOrEmpty()) {
//                        val otpPattern = Pattern.compile("\\b\\d{6}\\b")
//                        val matcher = otpPattern.matcher(message)
//                        if (matcher.find()) {
//                            val otp = matcher.group()
//                            Log.d("OTP", "🔢 Extracted OTP: $otp")
//                            Toast.makeText(context, "OTP: $otp", Toast.LENGTH_SHORT).show()
//                            otpListener?.onOTPReceived(otp)
//                        } else {
//                            Log.e("OTP", "🚨 No OTP found in message")
//                        }
//                    } else {
//                        Log.e("OTP", "🚨 Received SMS is NULL")
//                    }
//                }
//
//                com.google.android.gms.common.api.CommonStatusCodes.TIMEOUT -> {
//                    Log.e("OTP", "⏳ OTP retrieval timed out")
//                }
//            }
//        }
//    }
//
//    interface OTPListener {
//        fun onOTPReceived(otp: String)
//    }
//}
