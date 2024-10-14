package com.swordhealth.thecat

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var connectivityManager: ConnectivityManager
    private var isConnectedToInternet: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        registerNetworkCallback()

        setContent {
            val isConnected = mainViewModel.internetStatus.collectAsState().value
            MainNavigation(mainViewModel)

            LaunchedEffect(isConnected) {
                if (isConnected) {
                    mainViewModel.fetchCats()
                    mainViewModel.getFavoritesCats()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("testInternet", "No internet")
    }

    override fun onStop() {
        super.onStop()
        unregisterNetworkCallback()
    }

    private fun registerNetworkCallback() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(
            networkRequest,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    isConnectedToInternet = true
                    updateInternetStatusInViewModel(true)
                }

                override fun onLost(network: Network) {
                    isConnectedToInternet = false
                    updateInternetStatusInViewModel(false)
                }
            })
    }

    private fun unregisterNetworkCallback() {
        connectivityManager.unregisterNetworkCallback(ConnectivityManager.NetworkCallback())
    }

    private fun updateInternetStatusInViewModel(isConnected: Boolean) {
        mainViewModel.viewModelScope.launch {
            mainViewModel.updateInternetStatus(isConnected)
        }
    }
}
