package com.projects.rootmu.projectpinenut

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class SignUpActivity : AppCompatActivity() {
    var txtEmail: TextInputEditText? = null
    var txtPassword: TextInputEditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }

    fun onSignupClicked(v: View?) {
        var password = txtPassword!!.text.toString()
        var email = txtEmail!!.text.toString()
        password = password.trim { it <= ' ' }
        email = email.trim { it <= ' ' }
        if (password.isEmpty() || email.isEmpty()) {
            val builder = AlertDialog.Builder(this@SignUpActivity)
            builder.setMessage(R.string.signup_error_message)
                .setTitle(R.string.signup_error_title)
                .setPositiveButton(android.R.string.ok, null)
            val dialog = builder.create()
            dialog.show()
        } else {
            //SUCCESSFUL
            val intent = Intent(this@SignUpActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)

            //NOT SUCCESSFUL
/*            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
            builder.setMessage("exception.getMessage()")
                    .setTitle(R.string.login_error_title)
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();*/
        }
    }
}