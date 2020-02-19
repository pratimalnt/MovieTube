package com.pratima.movietube.view.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    companion object {
        @JvmStatic
        var PRIVATE_MODE = 0
        const val PREF_NAME = "com.pratima.movietube.user.login"
    }

    private lateinit var mEmailText: EditText
    private lateinit var mPasswordText: EditText
    private lateinit var mLoginButton: Button
    private lateinit var gSignInButton: SignInButton
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private val MOVIE_TUBE_GOOGLE_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
    }

    private fun initViews() {
        mEmailText = findViewById(R.id.username)
        mPasswordText = findViewById(R.id.password)
        mLoginButton = findViewById(R.id.login)
        gSignInButton = findViewById(R.id.google_signin)
        configureGoogleClient()
        mAuth = FirebaseAuth.getInstance()

        mLoginButton.setOnClickListener {
            login_progress.visibility = View.VISIBLE
            loginUser()
        }

        gSignInButton.setOnClickListener {
            gSignIn()
        }
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
        if (!validateLoginCredential()) {
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

    private fun validateLoginCredential(): Boolean {
        var valid = true

        val email = mEmailText.text.toString()
        val password = mPasswordText.text.toString()

        if (!isUserNameValid(email)) {
            mEmailText.error = "enter a valid email address"
            valid = false
        } else {
            mEmailText.error = null
        }

        if (password.isEmpty() || !isPasswordValid(password)) {
            mPasswordText.error = "more than 5 alphanumeric characters"
            valid = false
        } else {
            mPasswordText.error = null
        }

        return valid
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        var isValid = false

        if (!username.contains('@')) {
            isValid = false
        } else if (username.contains('@')) {
            isValid = Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            isValid = username.isNotBlank()
        }

        return isValid
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
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

