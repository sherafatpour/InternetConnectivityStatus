package net.sherafatpour.internetconnectivitystatus

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieDrawable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import net.sherafatpour.internetconnectivitystatus.databinding.ActivityMainBinding

import net.sherafatpour.internetconnectivitystatus.util.Status
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding


    @Inject
    lateinit var observe : Flow<String>

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        binding.content.animationView.repeatCount= LottieDrawable.INFINITE
        binding.content.animationView.playAnimation()




        lifecycleScope.launchWhenStarted{
            observe.collect() {


                when (it) {
                    getString(R.string.availabe) -> {
                        binding.content.animationView.setAnimation("online.json")
                        binding.content.animationView.playAnimation()
                        binding.content.txtStatus.text = getString(R.string.availabe)

                    }

                    getString(R.string.unavailabe) -> {
                        binding.content.animationView.setAnimation("disconnect.json")
                        binding.content.animationView.playAnimation()
                        binding.content.txtStatus.text = getString(R.string.unavailabe)


                    }

                    getString(R.string.losing) -> {
                        binding.content.animationView.setAnimation("disconnect.json")
                        binding.content.animationView.playAnimation()
                        binding.content.txtStatus.text = getString(R.string.losing)

                    }

                    getString(R.string.lost) -> {
                        binding.content.animationView.setAnimation("disconnect.json")
                        binding.content.animationView.playAnimation()
                        binding.content.txtStatus.text = getString(R.string.lost)

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