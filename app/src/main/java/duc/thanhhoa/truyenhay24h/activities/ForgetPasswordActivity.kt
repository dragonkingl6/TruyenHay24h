package duc.thanhhoa.truyenhay24h.activities

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import duc.thanhhoa.truyenhay24h.databinding.ActivityForgetPasswordBinding

private lateinit var binding: ActivityForgetPasswordBinding
private lateinit var auth: FirebaseAuth
class ForgetPasswordActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= Firebase.auth

        binding?.btnForgotPasswordSubmit?.setOnClickListener {
            resetPassword()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun resetPassword(){
        val email= binding?.etForgotPasswordEmail?.text.toString()
        if(validateForm(email)){
            showProgressBar()
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this){task ->
                    if(task.isSuccessful){
                        hideProgressBar()
                        binding?.tilEmailForgetPassword?.visibility= View.GONE
                        binding?.tvSubmitMsg?.visibility= View.VISIBLE
                        binding?.btnForgotPasswordSubmit?.visibility= View.GONE
                    }
                    else{
                        hideProgressBar()
                        showToast(this,"Fail to send reset password email")
                    }
                }
        }


    }


    private fun validateForm(email:String):Boolean
    {
        return when {
            TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()->{
                binding?.tilEmailForgetPassword?.error = "Enter valid email address"
                false
            }
            else -> true
            }
        }
    }