package com.example.button_2fragment_app

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action") {
                        // Codi del OnClickListener
                        Log.e("DEBUG", "Ara s'ha apretat a 'Action'")
                    }.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Aquí es carrega els elements del menú que estan dins del layout indicat
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Cada cop que es clica un element del menú, s'executa això:

        return when (item.itemId) {
            R.id.action_settings -> true // S'ha clicat els settings
            else -> super.onOptionsItemSelected(item) // si no es cap dels nostres, ho passem al super
        }
    }
}