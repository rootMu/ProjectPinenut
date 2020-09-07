package com.projects.rootmu.projectpinenut

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.login_details.*

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun onSignupClicked(v: View?) {
        val intent = Intent(this@LogInActivity, SignUpActivity::class.java)
        startActivity(intent)
    }

    fun onLoginClicked(v: View?) {
        var email = txtEmail.text.toString()
        var password = txtPassword.text.toString()
        email = email.trim { it <= ' ' }
        password = password.trim { it <= ' ' }
        if (email.isEmpty() || password.isEmpty()) {
            val builder = AlertDialog.Builder(this@LogInActivity)
            builder.setMessage(R.string.login_error_message)
                .setTitle(R.string.login_error_title)
                .setPositiveButton(android.R.string.ok, null)
            val dialog = builder.create()
            dialog.show()
        } else {
            //SUCCESSFULL
            val intent = Intent(this@LogInActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)


            //NOT SUCCESSFULL
/*            AlertDialog.Builder builder = new AlertDialog.Builder(LogInActivity.this);
            builder.setMessage("exception.getMessage()")
                    .setTitle(R.string.login_error_title)
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();*/
        }
    }
}