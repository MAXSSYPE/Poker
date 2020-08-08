package my.project.poker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import id.ionbit.ionalert.IonAlert
import it.sephiroth.android.library.numberpicker.NumberPicker
import it.sephiroth.android.library.numberpicker.doOnProgressChanged
import my.project.poker.game.*

class NewActivity : AppCompatActivity() {

    private lateinit var maxNumberOfBribes: NumberPicker
    private lateinit var numberOfPlayers: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
        val linearLayout: LinearLayout = findViewById(R.id.main)
        linearLayout.setBackgroundColor(getColor(R.color.colorAccent))
        init()
    }

    private fun init() {
        maxNumberOfBribes = findViewById(R.id.maxNumberOfBribes)
        numberOfPlayers = findViewById(R.id.numberOfGamers)
        numberOfPlayers.doOnProgressChanged { _, progress, _ ->
            when (progress) {
                2 -> maxNumberOfBribes.maxValue = 16
                3 -> {
                    maxNumberOfBribes.maxValue = 10
                    if (maxNumberOfBribes.progress > 10)
                        maxNumberOfBribes.progress = 10
                }
                4 -> {
                    maxNumberOfBribes.maxValue = 8
                    if (maxNumberOfBribes.progress > 8)
                        maxNumberOfBribes.progress = 8
                }
                5 -> {
                    maxNumberOfBribes.maxValue = 6
                    if (maxNumberOfBribes.progress > 6)
                        maxNumberOfBribes.progress = 6
                }
                6 -> {
                    maxNumberOfBribes.maxValue = 5
                    if (maxNumberOfBribes.progress > 5)
                        maxNumberOfBribes.progress = 5
                }
            }
        }
    }

    fun onMenuClick(view: View) {
        val intent = Intent(this@NewActivity, MenuActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
    }

    fun onNewClick(view: View) {
        IonAlert(this, IonAlert.NORMAL_TYPE)
            .setTitleText(getString(R.string.start_new_game))
            .setCancelText(getString(R.string.no))
            .setConfirmText(getString(R.string.yes))
            .showCancelButton(true)
            .setConfirmClickListener { sDialog: IonAlert ->
                startNewGame()
                sDialog.cancel()
            }
            .show()
    }

    private fun startNewGame() {
        val sPref =
            getSharedPreferences("Poker.txt", Context.MODE_PRIVATE)
        val ed = sPref.edit()
        ed.clear()
        ed.putInt("numberOfGames", maxNumberOfBribes.progress)
        ed.putInt("numberOfPlayers", numberOfPlayers.progress)
        ed.apply()

        when (numberOfPlayers.progress) {
            2 -> {
                val intent = Intent(this@NewActivity, Activity2::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            }
            3 -> {
                val intent = Intent(this@NewActivity, Activity3::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            }
            4 -> {
                val intent = Intent(this@NewActivity, Activity4::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            }
            5 -> {
                val intent = Intent(this@NewActivity, Activity5::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            }
            6 -> {
                val intent = Intent(this@NewActivity, Activity6::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@NewActivity, MenuActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
    }
}