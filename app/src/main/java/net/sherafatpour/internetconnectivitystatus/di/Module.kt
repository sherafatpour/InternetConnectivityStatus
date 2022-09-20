package net.sherafatpour.internetconnectivitystatus.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import net.sherafatpour.internetconnectivitystatus.R
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {





    @Provides
    @Singleton
    fun connectivityManager(@ApplicationContext context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @Singleton
    @RequiresApi(Build.VERSION_CODES.N)
    fun observe(@ApplicationContext context: Context,connectivityManager:ConnectivityManager): Flow<String>{

        return callbackFlow {

            //initial connectivity status
            launch { send("Unavailable") }

            val callback = object :ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(context.getString(R.string.availabe)) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { context.getString(R.string.losing) }

                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(context.getString(R.string.lost))  }

                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(context.getString(R.string.unavailabe) ) }

                }


            }


            connectivityManager.registerDefaultNetworkCallback(callback)

            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)

            }
        }.distinctUntilChanged()
    }

}