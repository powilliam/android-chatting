package com.powilliam.android.chatting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.powilliam.android.chatting.ui.ChattingTheme
import com.powilliam.android.chatting.ui.viewmodels.AuthenticationViewModel
import com.powilliam.android.chatting.ui.viewmodels.MessagesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val authenticationViewModel: AuthenticationViewModel by viewModel()
    private val messagesViewModel: MessagesViewModel by viewModel()
    private val firebaseAuth: FirebaseAuth = Firebase.auth
    private val firebaseDatabase: FirebaseDatabase = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseDatabase.setPersistenceEnabled(true)

        firebaseAuth.currentUser?.let {
            authenticationViewModel.authenticate(it)
        }

        setContent {
            ChattingTheme {
                Navigation(
                    authenticationViewModel = authenticationViewModel,
                    messagesViewModel = messagesViewModel,
                    signInWithGoogle = { signInWithGoogle() },
                    signOutFromGoogle = { signOutFromGoogle() })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GOOGLE_SIGNIN_REQUEST_CODE) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    authenticationViewModel.authenticationFailed(
                        GOOGLE_PLAY_SERVICES_AUTHENTICATION_FAILURE
                    )
                }
            }
        }
    }

    private fun signInWithGoogle() {
        authenticationViewModel.authenticating()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this@MainActivity, gso)
        startActivityForResult(googleSignInClient.signInIntent, GOOGLE_SIGNIN_REQUEST_CODE)
    }

    private fun signOutFromGoogle() {
        firebaseAuth.signOut()
        authenticationViewModel.unAuthenticate()
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credentials = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credentials).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseAuth.currentUser?.let {
                    authenticationViewModel.authenticate(it)
                }
            } else {
                authenticationViewModel.authenticationFailed(FIREBASE_AUTHENTICATION_FAILURE)
            }
        }
    }

    companion object {
        private const val GOOGLE_SIGNIN_REQUEST_CODE = 101
        private const val GOOGLE_PLAY_SERVICES_AUTHENTICATION_FAILURE =
            "Unable to interact with Google Play Services"
        private const val FIREBASE_AUTHENTICATION_FAILURE =
            "Unable to authenticate with Google Sign In"
    }
}