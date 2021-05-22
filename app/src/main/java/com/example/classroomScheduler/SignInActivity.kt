package com.example.classroomScheduler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SignInActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // creating the google sign in object
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth

        // On clicking the sign in button
        ll_btn_login.setOnClickListener{
            // calling the sign in method to handle the sign in
            signIn()
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun signIn() {
        // create a new intent
        // which will show all the current google accounts present in the phone
        val signInIntent = googleSignInClient.signInIntent

        //launch the activity
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN)
        {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try
            {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!

                // Log the success message
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()

                // Calling a function to authenticate the sign in using the token
                firebaseAuthWithGoogle(account.idToken!!)

            }
            catch (e: ApiException)
            {
                // Google Sign In failed
                // Log the fail message in
                Log.e(TAG, "Google Sign-In failed", e)
                Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun firebaseAuthWithGoogle(idToken: String)
    {
        // get credentials using the token received from
        // OnActivityResult method
        val credentials = GoogleAuthProvider.getCredential(idToken, null)

        // while the authentication process is going on
        // hide the sign in button and
        // show a progress bar
        ll_btn_login.visibility = View.GONE
        progressBar.visibility = View.VISIBLE

        // launching a coroutine
        // to authenticate the user
        GlobalScope.launch (Dispatchers.IO)
        {
            val auth = auth.signInWithCredential(credentials).await()
            val firebaseUser = auth.user
            withContext(Dispatchers.Main) {
                // if firebase user is not null: take to the next screen
                // otherwise bring back to the log in screen
                updateUI(firebaseUser)
            }
        }
    }

    private fun updateUI(firebaseUser: FirebaseUser?)
    {
        if (firebaseUser!=null) {
            val hubactivityintent = startActivity(Intent(this, HubListMainActivity::class.java))
            finish()
        }
        else {
            ll_btn_login.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val RC_SIGN_IN: Int = 123
        private const val TAG = "Sign In Activity Tag"
    }
}