package org.wit.foodchallengeplace.activities
// Please disregard this activity, it was a failed attempt at linking the app to firebase relative directory.
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.wit.foodchallengeplace.R
import org.wit.foodchallengeplace.databinding.ActivityRegisterBinding
import org.wit.foodchallengeplace.models.UserModel
import org.wit.foodchallengeplace.views.foodchallengeplacelist.FoodchallengePlaceListView


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_register)

        binding.registerBtn.setOnClickListener {

            val firstName = binding.firstName.text.toString()
            val lastName = binding.lastName.text.toString()
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Users")
            val userModel = UserModel(firstName,lastName, username, password)
            database.child(username).setValue(userModel).addOnSuccessListener {

                binding.firstName.text.clear()
                binding.lastName.text.clear()
                binding.username.text.clear()
                binding.password.text.clear()

                Toast.makeText(this,"Successfully Saved",Toast.LENGTH_SHORT).show()

            }.addOnFailureListener{

                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()


            }
            val welcome = getString(R.string.welcome)
            val intent = Intent(this@RegisterActivity, FoodchallengePlaceListView::class.java)
            startActivity(intent)
            Toast.makeText(
                applicationContext,
                "$welcome",
                Toast.LENGTH_LONG
            ).show()
        }
        }

    }