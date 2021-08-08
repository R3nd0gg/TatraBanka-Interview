package sk.tatrabanka.masarykapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sk.tatrabanka.masarykapp.view.UserListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UserListFragment())
                .commitNow()
        }
    }
}