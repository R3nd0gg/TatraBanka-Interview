package sk.tatrabanka.masarykapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sk.tatrabanka.masarykapp.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MainActivityBinding.inflate(layoutInflater).root)
    }
}