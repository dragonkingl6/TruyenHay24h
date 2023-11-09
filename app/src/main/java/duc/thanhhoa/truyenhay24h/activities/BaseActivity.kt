package duc.thanhhoa.truyenhay24h.activities

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import duc.thanhhoa.truyenhay24h.R
import duc.thanhhoa.truyenhay24h.databinding.ActivityBaseBinding

private lateinit var binding: ActivityBaseBinding
private lateinit var pb: Dialog
open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun showProgressBar(){
        pb = Dialog(this)
        pb.setContentView(R.layout.progress_bar)
        pb.setCancelable(false)
        pb.show()
    }
    fun hideProgressBar(){
        pb.hide()
    }
    fun showToast(activity: AppCompatActivity, msg:String){
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }
}