package duc.thanhhoa.truyenhay24h.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import duc.thanhhoa.truyenhay24h.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding
private lateinit var auth: FirebaseAuth
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()

        binding?.btnSignOut?.setOnClickListener {
            if(auth.currentUser!=null){
                auth.signOut()
                startActivity(Intent(this, SplashActivity::class.java))
                finish()
            }

        }

    }
}