package com.example.betterkrishi

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class AuthViewModel : ViewModel() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var activity: Activity

    private val _otpState = MutableLiveData<OTPState>()
    val otpState: LiveData<OTPState> get() = _otpState

    fun setActivity(activity: Activity) {
        this.activity = activity
    }

    fun sendOtp(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    _otpState.value = OTPState.Error(e.localizedMessage ?: "Verification failed")
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    _otpState.value = OTPState.CodeSent(verificationId, token)
                }
            }).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyOtp(verificationId: String, otp: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, otp)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _otpState.value = OTPState.VerificationSuccess
                } else {
                    _otpState.value = OTPState.Error(task.exception?.localizedMessage ?: "Sign-in failed")
                }
            }
    }
}

sealed class OTPState {
    object VerificationSuccess : OTPState()
    data class CodeSent(val verificationId: String, val token: PhoneAuthProvider.ForceResendingToken) : OTPState()
    data class Error(val message: String) : OTPState()
}