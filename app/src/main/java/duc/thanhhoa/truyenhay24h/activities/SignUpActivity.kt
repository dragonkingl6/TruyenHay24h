package duc.thanhhoa.truyenhay24h.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import duc.thanhhoa.truyenhay24h.databinding.ActivitySignUpBinding


private lateinit var binding: ActivitySignUpBinding
private lateinit var auth: FirebaseAuth
private lateinit var fFirestore: FirebaseFirestore
class SignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        auth = Firebase.auth

        binding.isAdmin.setOnClickListener {
            if (binding.isAdmin.isChecked) {
                binding.isUser.isChecked = false
            }
        }

        binding.isUser.setOnClickListener {
            if (binding.isUser.isChecked) {
                binding.isAdmin.isChecked = false
            }
        }

        binding?.tvLoginPage?.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        binding?.btnSignUp?.setOnClickListener { registerUser() }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }

    private fun registerUser() {
        val name = binding?.etSinUpName?.text.toString()
        val email = binding?.etSinUpEmail?.text.toString()
        val password = binding?.etSinUpPassword?.text.toString()

        if (validateForm(name, email, password)) {
            showProgressBar()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Get the user ID of the newly created user
                        val userId = auth.currentUser?.uid

                        // Create a HashMap to store user data
                        val userMap = hashMapOf(
                            "name" to name,
                            "email" to email,
                            "isAdmin" to binding?.isAdmin?.isChecked, // true if checked, false otherwise
                            "isUser" to binding?.isUser?.isChecked    // true if checked, false otherwise
                            // Add more fields as needed
                        )

                        // Get reference to Firestore collection "users" and set the user data
                        fFirestore = FirebaseFirestore.getInstance()
                        fFirestore.collection("users").document(userId!!)
                            .set(userMap)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()

                                // Check the role and navigate accordingly
                                if (binding?.isAdmin?.isChecked == true) {
                                    // Navigate to admin screen
                                    startActivity(Intent(this, AdminActivity::class.java))
                                } else {
                                    // Navigate to user screen
                                    startActivity(Intent(this, MainActivity::class.java))
                                }

                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    this,
                                    "Oops! Something went wrong",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                    } else {
                        Toast.makeText(this, "Oops! Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                    hideProgressBar()
                }
        }
    }


    private fun validateForm(name:String, email:String,password:String):Boolean
    {
        return when {
            TextUtils.isEmpty(name) ->{
                binding?.tilName?.error = "Enter name"
                false
            }
            TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()->{
                binding?.tilEmail?.error = "Enter valid email address"
                false
            }
            TextUtils.isEmpty(password) ->{
                binding?.tilPassword?.error = "Enter password"
                false
            }
            else -> { true }
        }
    }
}