package com.pratima.movietube.view.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.pratima.movietube.view.main.MainActivity
import com.pratima.movietube.R
import com.pratima.movietube.api.ApiConstants
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    companion object {
        @JvmStatic
        var PRIVATE_MODE = 0
        const val PREF_NAME = "com.pratima.movietube.user.login"
    }
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private val MOVIE_TUBE_GOOGLE_SIGN_IN = 1
    private var valid = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
    }

    private fun initViews() {
        configureGoogleClient()
        mAuth = FirebaseAuth.getInstance()

        login.setOnClickListener {
            login_progress.visibility = View.VISIBLE
            loginUser()
        }
        google_signin.setOnClickListener {
            gSignIn()
        }
        username.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                val email = username.text.toString()
                if (!isUserNameValid(email)) {
                    username.error = "enter a valid email address"
                    valid = false
                } else {
                    username.error = null
                    valid = true
                }
            }

        }

        password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isEmpty() || !isPasswordValid(s.toString())) {
                    password.error = "more than 5 alphanumeric characters"
                    valid = false
                } else {
                    password.error = null
                    valid = true
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    private fun gSignIn() {
        val intent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(intent, MOVIE_TUBE_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MOVIE_TUBE_GOOGLE_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            firebaseGoogleAuth(account)
        } catch (e: ApiException) {
            Log.e("Login", "Signed In Failed")
        }
    }

    private fun firebaseGoogleAuth(account: GoogleSignInAccount?) {
        val authCredential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Signed In Successfully", Toast.LENGTH_LONG).show()
                updateUi(mAuth.currentUser)
            } else {
                Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun updateUi(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(this, "Welcome " + user.displayName, Toast.LENGTH_LONG).show()
            onLoginSuccess()
        }
    }

    private fun configureGoogleClient() {
        val options: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, options)
    }

    private fun loginUser() {
        if (!valid) {
            onLoginFailed()
            return
        }

        android.os.Handler().postDelayed(
            {
                // On complete call either onLoginSuccess or onLoginFailed
                onLoginSuccess()
            }, 1000
        )
    }


    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {

        return username.isNotBlank() && username.contains('@') && ApiConstants.emailList.contains(
            username
        )

    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return ApiConstants.passwordList.contains(password)
    }


    private fun onLoginSuccess() {
        login_progress.visibility = View.GONE
        saveUserSession()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun saveUserSession() {
        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val editor = sharedPref.edit()
        editor.putBoolean(PREF_NAME, true)
        editor.apply()

    }

    private fun onLoginFailed() {
        Toast.makeText(baseContext, "Login failed", Toast.LENGTH_LONG).show()
        login_progress.visibility = View.GONE
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(this, "Google Login failed" + p0.errorMessage, Toast.LENGTH_LONG).show()
    }
}

