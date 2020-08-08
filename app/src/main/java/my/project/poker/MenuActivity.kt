package my.project.poker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import id.ionbit.ionalert.IonAlert
import my.project.poker.game.*

class MenuActivity : AppCompatActivity() {

    lateinit var sPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val linearLayout: LinearLayout = findViewById(R.id.main)
        linearLayout.setBackgroundColor(getColor(R.color.colorAccent))
        sPref = getSharedPreferences("Poker.txt", Context.MODE_PRIVATE)
        if (!sPref.contains("position") || sPref.getInt("position", 0) == 0) {
            val button: Button = findViewById(R.id.continue_game)
            markButtonDisable(button)
        }

    }

    private fun markButtonDisable(button: Button) {
        button.isEnabled = false
        button.setTextColor(ContextCompat.getColor(button.context, R.color.color_white))
        button.setBackgroundColor(
            ContextCompat.getColor(
                button.context,
                R.color.material_blue_grey_95
            )
        )
    }

    fun onNewGameClick(view: View) {
        val intent = Intent(this@MenuActivity, NewActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
    }

    fun onContinueClick(view: View) {
        sPref = getSharedPreferences("Poker.txt", Context.MODE_PRIVATE)
        when (sPref.getInt("numberOfPlayers", 0)) {
            2 -> {
                val intent = Intent(this@MenuActivity, Activity2::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            }
            3 -> {
                val intent = Intent(this@MenuActivity, Activity3::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            }
            4 -> {
                val intent = Intent(this@MenuActivity, Activity4::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            }
            5 -> {
                val intent = Intent(this@MenuActivity, Activity5::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            }
            6 -> {
                val intent = Intent(this@MenuActivity, Activity6::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            }
        }
    }



    fun onSettingsClick(view: View) {

    }

    override fun onBackPressed() {
        IonAlert(this, IonAlert.NORMAL_TYPE)
            .setTitleText(getString(R.string.exit))
            .setCancelText(getString(R.string.no))
            .setConfirmText(getString(R.string.yes))
            .showCancelButton(true)
            .setConfirmClickListener { sDialog: IonAlert ->
                finishAffinity()
                sDialog.cancel()
            }
            .show()
    }
}