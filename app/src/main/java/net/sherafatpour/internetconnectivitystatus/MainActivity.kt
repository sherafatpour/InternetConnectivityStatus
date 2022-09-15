package net.sherafatpour.internetconnectivitystatus

import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import net.sherafatpour.internetconnectivitystatus.databinding.ActivityMainBinding
import net.sherafatpour.internetconnectivitystatus.util.ConnectivityObserver
import net.sherafatpour.internetconnectivitystatus.util.ConnectivityObserver.Status.*
import net.sherafatpour.internetconnectivitystatus.util.InternetConnectivityObserver

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    lateinit var connectivity: InternetConnectivityObserver

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        connectivity = InternetConnectivityObserver(applicationContext)

        binding.content.animationView.repeatCount= LottieDrawable.INFINITE
        binding.content.animationView.playAnimation()




        lifecycleScope.launchWhenStarted{
            connectivity.observe().collect() {


                when (it) {
                    Available -> {
                        binding.content.animationView.setAnimation("online.json")
                        binding.content.animationView.playAnimation()
                        binding.content.txtStatus.text = "Connectivity Available"

                    }

                    Unavailable -> {
                        binding.content.animationView.setAnimation("disconnect.json")
                        binding.content.animationView.playAnimation()
                        binding.content.txtStatus.text = "Connectivity Unavailable"


                    }

                    Losing -> {
                        binding.content.animationView.setAnimation("disconnect.json")
                        binding.content.animationView.playAnimation()
                        binding.content.txtStatus.text = "Connectivity is Losing"

                    }

                    Lost -> {
                        binding.content.animationView.setAnimation("disconnect.json")
                        binding.content.animationView.playAnimation()
                        binding.content.txtStatus.text = "Connectivity is Lost"

                    }

                }


            }

        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


}