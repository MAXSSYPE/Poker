package my.project.poker.game

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.ionbit.ionalert.IonAlert
import it.sephiroth.android.library.numberpicker.NumberPicker
import it.sephiroth.android.library.numberpicker.doOnProgressChanged
import my.project.poker.MenuActivity
import my.project.poker.R
import my.project.poker.calculatePoints
import my.project.poker.scores.ScoreActivity4
import my.project.poker.scores.ScoreActivity5
import my.project.poker.scores.ScoreActivity6
import java.util.*


class Activity6 : AppCompatActivity() {

    private lateinit var bribes: NumberPicker
    private lateinit var bribes2: NumberPicker
    private lateinit var bribes3: NumberPicker
    private lateinit var bribes4: NumberPicker
    private lateinit var bribes5: NumberPicker
    private lateinit var bribes6: NumberPicker
    private lateinit var realBribes: NumberPicker
    private lateinit var realBribes2: NumberPicker
    private lateinit var realBribes3: NumberPicker
    private lateinit var realBribes4: NumberPicker
    private lateinit var realBribes5: NumberPicker
    private lateinit var realBribes6: NumberPicker
    private lateinit var result: TextView
    private lateinit var result2: TextView
    private lateinit var result3: TextView
    private lateinit var result4: TextView
    private lateinit var result5: TextView
    private lateinit var result6: TextView
    private lateinit var numberOfCards: TextView
    private lateinit var numberOfBribes: TextView
    private lateinit var name1: EditText
    private lateinit var name2: EditText
    private lateinit var name3: EditText
    private lateinit var name4: EditText
    private lateinit var name5: EditText
    private lateinit var name6: EditText
    lateinit var numberOfCardsInt: Array<Int>
    private var position = 0
    private var numberOfGames = 0
    private lateinit var pointsPlayer1: Array<Int>
    private lateinit var pointsPlayer2: Array<Int>
    private lateinit var pointsPlayer3: Array<Int>
    private lateinit var pointsPlayer4: Array<Int>
    private lateinit var pointsPlayer5: Array<Int>
    private lateinit var pointsPlayer6: Array<Int>
    private lateinit var sPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_6)
        sPref = getSharedPreferences("Poker.txt", Context.MODE_PRIVATE)
        numberOfGames = sPref.getInt("numberOfGames", 0)
        numberOfCardsIntInit()
        val linearLayout: LinearLayout = findViewById(R.id.main)
        linearLayout.setBackgroundColor(getColor(R.color.colorAccent))
        position = sPref.getInt("position", 0)
        if (position >= numberOfCardsInt.size) {
            gameOverDialog()
            initResultsAndNamesAndNumberOfCards()
            numberOfCards.text = getString(R.string.game_over)
        } else if (position < numberOfCardsInt.size && position != 0) {
            initResultsAndNamesAndNumberOfCards()
        } else {
            init()
        }
    }

    private fun init() {
        pickersInit()
        position = 0
        bribes.maxValue = numberOfCardsInt[position]
        bribes2.maxValue = numberOfCardsInt[position]
        bribes3.maxValue = numberOfCardsInt[position]
        bribes4.maxValue = numberOfCardsInt[position]
        bribes5.maxValue = numberOfCardsInt[position]
        bribes6.maxValue = numberOfCardsInt[position]
        realBribes.maxValue = numberOfCardsInt[position]
        realBribes2.maxValue = numberOfCardsInt[position]
        realBribes3.maxValue = numberOfCardsInt[position]
        realBribes4.maxValue = numberOfCardsInt[position]
        realBribes5.maxValue = numberOfCardsInt[position]
        realBribes6.maxValue = numberOfCardsInt[position]
        initResultsAndNamesAndNumberOfCards()
        val supp = getString(R.string.num_of_cards) + numberOfCardsInt[position]
        numberOfCards.text = supp
        changeMainPlayer()
    }

    private fun initResultsAndNamesAndNumberOfCards() {
        result = findViewById(R.id.result1)
        result2 = findViewById(R.id.result2)
        result3 = findViewById(R.id.result3)
        result4 = findViewById(R.id.result4)
        result5 = findViewById(R.id.result5)
        result6 = findViewById(R.id.result6)
        name1 = findViewById(R.id.name1)
        name2 = findViewById(R.id.name2)
        name3 = findViewById(R.id.name3)
        name4 = findViewById(R.id.name4)
        name5 = findViewById(R.id.name5)
        name6 = findViewById(R.id.name6)
        numberOfCards = findViewById(R.id.number_of_cards)
        numberOfBribes = findViewById(R.id.numberBribesCouldTake)
        var supp = getString(R.string.left_bribes) + numberOfCardsInt[position]
        numberOfBribes.text = supp
        supp = getString(R.string.num_of_cards) + numberOfCardsInt[position]
        numberOfCards.text = supp
        changeMainPlayer()
        pickersInit()
        updatePickersMax(numberOfCardsInt[position])
    }

    private fun pickersInit() {
        bribes = findViewById(R.id.numberOfBribe)
        bribes2 = findViewById(R.id.numberOfBribe2)
        bribes3 = findViewById(R.id.numberOfBribe3)
        bribes4 = findViewById(R.id.numberOfBribe4)
        bribes5 = findViewById(R.id.numberOfBribe5)
        bribes6 = findViewById(R.id.numberOfBribe6)
        realBribes = findViewById(R.id.numberOfRealBribe)
        realBribes2 = findViewById(R.id.numberOfRealBribe2)
        realBribes3 = findViewById(R.id.numberOfRealBribe3)
        realBribes4 = findViewById(R.id.numberOfRealBribe4)
        realBribes5 = findViewById(R.id.numberOfRealBribe5)
        realBribes6 = findViewById(R.id.numberOfRealBribe6)
        bribes.setBribesListener()
        bribes2.setBribesListener()
        bribes3.setBribesListener()
        bribes4.setBribesListener()
        bribes5.setBribesListener()
        bribes6.setBribesListener()
    }

    private fun NumberPicker.setBribesListener() {
        this.doOnProgressChanged { _, _, _ ->
            val int =
                numberOfCardsInt[position] - bribes.progress - bribes2.progress - bribes3.progress - bribes4.progress - bribes5.progress - bribes6.progress
            val supp: String
            supp = if (int < 0)
                getString(R.string.more_bribes)
            else
                getString(R.string.left_bribes) + int
            println(supp)
            numberOfBribes.text = supp
        }
    }

    private fun numberOfCardsIntInit() {
        when (numberOfGames) {
            5 -> {
                numberOfCardsInt =
                    arrayOf(1, 2, 3, 4, 5, 5, 5, 5, 5, 5, 4, 3, 2, 1)
                pointsPlayer1 = Array(14) { 0 }
                pointsPlayer2 = Array(14) { 0 }
                pointsPlayer3 = Array(14) { 0 }
                pointsPlayer4 = Array(14) { 0 }
                pointsPlayer5 = Array(14) { 0 }
                pointsPlayer6 = Array(14) { 0 }
            }
        }
    }

    fun onAddClick(view: View) {
        if (position < numberOfCardsInt.size) {
            if (realBribes.progress + realBribes2.progress + realBribes3.progress + realBribes4.progress + realBribes5.progress + realBribes5.progress == numberOfCardsInt[position])
                IonAlert(this, IonAlert.SUCCESS_TYPE)
                    .setTitleText(getString(R.string.sure))
                    .setContentText(getString(R.string.add))
                    .setCancelText(getString(R.string.no))
                    .setConfirmText(getString(R.string.yes))
                    .showCancelButton(true)
                    .setConfirmClickListener { sDialog: IonAlert ->
                        addAll()
                        sDialog.cancel()
                        if (position == numberOfCardsInt.size - 1)
                            gameOverDialog()
                    }
                    .show()
            else
                IonAlert(this, IonAlert.ERROR_TYPE)
                    .setTitleText(getString(R.string.not_equal))
                    .setConfirmText(getString(R.string.ok))
                    .showCancelButton(false)
                    .setConfirmClickListener { sDialog: IonAlert ->
                        sDialog.cancel()
                    }
                    .show()
        } else
            gameOverDialog()
    }

    private fun addAll() {
        pointsPlayer1[position] = calculatePoints(
            bribes.progress,
            realBribes.progress
        )
        pointsPlayer2[position] = calculatePoints(
            bribes2.progress,
            realBribes2.progress
        )
        pointsPlayer3[position] = calculatePoints(
            bribes3.progress,
            realBribes3.progress
        )
        pointsPlayer4[position] = calculatePoints(
            bribes4.progress,
            realBribes4.progress
        )
        pointsPlayer5[position] = calculatePoints(
            bribes5.progress,
            realBribes5.progress
        )
        pointsPlayer6[position] = calculatePoints(
            bribes6.progress,
            realBribes6.progress
        )
        val editor = sPref.edit()
        val gson = Gson()
        val listStr1 = gson.toJson(pointsPlayer1)
        val listStr2 = gson.toJson(pointsPlayer2)
        val listStr3 = gson.toJson(pointsPlayer3)
        val listStr4 = gson.toJson(pointsPlayer4)
        val listStr5 = gson.toJson(pointsPlayer5)
        val listStr6 = gson.toJson(pointsPlayer6)
        editor.putString("pl1", listStr1)
        editor.putString("pl2", listStr2)
        editor.putString("pl3", listStr3)
        editor.putString("pl4", listStr4)
        editor.putString("pl5", listStr5)
        editor.putString("pl6", listStr6)
        editor.apply()
        result.text = (result.text.toString().toInt() + calculatePoints(
            bribes.progress,
            realBribes.progress
        )).toString()
        result2.text = (result2.text.toString().toInt() + calculatePoints(
            bribes2.progress,
            realBribes2.progress
        )).toString()
        result3.text = (result3.text.toString().toInt() + calculatePoints(
            bribes3.progress,
            realBribes3.progress
        )).toString()
        result4.text = (result4.text.toString().toInt() + calculatePoints(
            bribes4.progress,
            realBribes4.progress
        )).toString()
        result5.text = (result5.text.toString().toInt() + calculatePoints(
            bribes5.progress,
            realBribes5.progress
        )).toString()
        result6.text = (result6.text.toString().toInt() + calculatePoints(
            bribes6.progress,
            realBribes6.progress
        )).toString()

        position++
        var supp = getString(R.string.game_over)
        var supp2 = getString(R.string.game_over)
        if (position < numberOfCardsInt.size) {
            supp = getString(R.string.num_of_cards) + numberOfCardsInt[position]
            supp2 = getString(R.string.left_bribes) + numberOfCardsInt[position]
            updatePickersMax(numberOfCardsInt[position])
            changeMainPlayer()
        }
        numberOfCards.text = supp
        numberOfBribes.text = supp2
        zeroAllPickers()
    }

    private fun zeroAllPickers() {
        bribes.progress = 0
        bribes2.progress = 0
        bribes3.progress = 0
        bribes4.progress = 0
        bribes5.progress = 0
        bribes6.progress = 0
        realBribes.progress = 0
        realBribes2.progress = 0
        realBribes3.progress = 0
        realBribes4.progress = 0
        realBribes5.progress = 0
        realBribes6.progress = 0
    }

    private fun updatePickersMax(value: Int) {
        bribes.maxValue = value
        bribes2.maxValue = value
        bribes3.maxValue = value
        bribes4.maxValue = value
        bribes5.maxValue = value
        bribes6.maxValue = value
        realBribes.maxValue = value
        realBribes2.maxValue = value
        realBribes3.maxValue = value
        realBribes4.maxValue = value
        realBribes5.maxValue = value
        realBribes6.maxValue = value
    }

    private fun save() {
        val ed = sPref.edit()
        ed.putString("res1", result.text.toString())
        ed.putString("res2", result2.text.toString())
        ed.putString("res3", result3.text.toString())
        ed.putString("res4", result4.text.toString())
        ed.putString("res5", result5.text.toString())
        ed.putString("res6", result6.text.toString())
        ed.putString("name1", name1.text.toString())
        ed.putString("name2", name2.text.toString())
        ed.putString("name3", name3.text.toString())
        ed.putString("name4", name4.text.toString())
        ed.putString("name5", name5.text.toString())
        ed.putString("name6", name6.text.toString())
        ed.putInt("position", position)
        ed.putInt("numberOfGames", numberOfGames)
        val gson = Gson()
        val listStr1 = gson.toJson(pointsPlayer1)
        val listStr2 = gson.toJson(pointsPlayer2)
        val listStr3 = gson.toJson(pointsPlayer3)
        val listStr4 = gson.toJson(pointsPlayer4)
        val listStr5 = gson.toJson(pointsPlayer5)
        val listStr6 = gson.toJson(pointsPlayer6)
        ed.putString("pl1", listStr1)
        ed.putString("pl2", listStr2)
        ed.putString("pl3", listStr3)
        ed.putString("pl4", listStr4)
        ed.putString("pl5", listStr5)
        ed.putString("pl6", listStr6)
        ed.apply()
    }

    private fun load(context: Context) {
        name1.setText(sPref.getString("name1", ""))
        name2.setText(sPref.getString("name2", ""))
        name3.setText(sPref.getString("name3", ""))
        name4.setText(sPref.getString("name4", ""))
        name5.setText(sPref.getString("name5", ""))
        name6.setText(sPref.getString("name6", ""))
        if (sPref.getString(
                "res1",
                "0"
            ) == ""
        ) result.text = "0" else result.text = sPref.getString("res1", "0")
        if (sPref.getString(
                "res2",
                "0"
            ) == ""
        ) result2.text = "0" else result2.text = sPref.getString("res2", "0")
        if (sPref.getString(
                "res3",
                "0"
            ) == ""
        ) result3.text = "0" else result3.text = sPref.getString("res3", "0")
        if (sPref.getString(
                "res4",
                "0"
            ) == ""
        ) result4.text = "0" else result4.text = sPref.getString("res4", "0")
        if (sPref.getString(
                "res5",
                "0"
            ) == ""
        ) result5.text = "0" else result5.text = sPref.getString("res5", "0")
        if (sPref.getString(
                "res6",
                "0"
            ) == ""
        ) result6.text = "0" else result6.text = sPref.getString("res6", "0")
        position = sPref.getInt("position", 0)
        numberOfGames = sPref.getInt("numberOfGames", 0)
        if (sPref.getString("pl1", "")?.isNotEmpty()!!) {
            pointsPlayer1 = Gson().fromJson(
                sPref.getString("pl1", ""),
                object : TypeToken<Array<Int?>?>() {}.type
            )
            pointsPlayer2 = Gson().fromJson(
                sPref.getString("pl2", ""),
                object : TypeToken<Array<Int?>?>() {}.type
            )
            pointsPlayer3 = Gson().fromJson(
                sPref.getString("pl3", ""),
                object : TypeToken<Array<Int?>?>() {}.type
            )
            pointsPlayer4 = Gson().fromJson(
                sPref.getString("pl4", ""),
                object : TypeToken<Array<Int?>?>() {}.type
            )
            pointsPlayer5 = Gson().fromJson(
                sPref.getString("pl5", ""),
                object : TypeToken<Array<Int?>?>() {}.type
            )
            pointsPlayer6 = Gson().fromJson(
                sPref.getString("pl6", ""),
                object : TypeToken<Array<Int?>?>() {}.type
            )
        }
    }

    fun onScoreClick(view: View) {
        val intent = Intent(this@Activity6, ScoreActivity6::class.java)
        intent.putIntegerArrayListExtra("nums", numberOfCardsInt.toList() as ArrayList<Int>?)
        startActivity(intent)
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_right
        )
    }

    private fun changeMainPlayer() {
        when (position % 6) {
            0 -> {
                name1.background = getDrawable(R.drawable.main_player)
                name2.background = getDrawable(R.drawable.player)
                name3.background = getDrawable(R.drawable.player)
                name4.background = getDrawable(R.drawable.player)
                name5.background = getDrawable(R.drawable.player)
                name6.background = getDrawable(R.drawable.player)
            }
            1 -> {
                name1.background = getDrawable(R.drawable.player)
                name2.background = getDrawable(R.drawable.main_player)
                name3.background = getDrawable(R.drawable.player)
                name4.background = getDrawable(R.drawable.player)
                name5.background = getDrawable(R.drawable.player)
                name6.background = getDrawable(R.drawable.player)
            }
            2 -> {
                name1.background = getDrawable(R.drawable.player)
                name2.background = getDrawable(R.drawable.player)
                name3.background = getDrawable(R.drawable.main_player)
                name4.background = getDrawable(R.drawable.player)
                name5.background = getDrawable(R.drawable.player)
                name6.background = getDrawable(R.drawable.player)
            }
            3 -> {
                name1.background = getDrawable(R.drawable.player)
                name2.background = getDrawable(R.drawable.player)
                name3.background = getDrawable(R.drawable.player)
                name4.background = getDrawable(R.drawable.main_player)
                name5.background = getDrawable(R.drawable.player)
                name6.background = getDrawable(R.drawable.player)
            }
            4 -> {
                name1.background = getDrawable(R.drawable.player)
                name2.background = getDrawable(R.drawable.player)
                name3.background = getDrawable(R.drawable.player)
                name4.background = getDrawable(R.drawable.player)
                name5.background = getDrawable(R.drawable.main_player)
                name6.background = getDrawable(R.drawable.player)
            }
            5 -> {
                name1.background = getDrawable(R.drawable.player)
                name2.background = getDrawable(R.drawable.player)
                name3.background = getDrawable(R.drawable.player)
                name4.background = getDrawable(R.drawable.player)
                name5.background = getDrawable(R.drawable.player)
                name6.background = getDrawable(R.drawable.main_player)
            }
        }
    }

    private fun gameOverDialog() {
        IonAlert(this, IonAlert.SUCCESS_TYPE)
            .setTitleText(getString(R.string.game_over))
            .setCancelText(getString(R.string.no))
            .setConfirmText(getString(R.string.new_game))
            .showCancelButton(true)
            .setConfirmClickListener { sDialog: IonAlert ->
                val editor = sPref.edit()
                editor.clear()
                position = 0
                result.text = "0"
                result2.text = "0"
                result3.text = "0"
                result4.text = "0"
                result5.text = "0"
                result6.text = "0"
                pickersInit()
                zeroAllPickers()
                updatePickersMax(numberOfCardsInt[position])
                editor.apply()
                sDialog.cancel()
                numberOfCardsIntInit()
                save()
            }
            .show()
    }

    private fun showMenuDialog() {
        IonAlert(this, IonAlert.NORMAL_TYPE)
            .setTitleText(getString(R.string.go_to_menu))
            .setContentText(getString(R.string.progress_save))
            .setCancelText(getString(R.string.no))
            .setConfirmText(getString(R.string.yes))
            .showCancelButton(true)
            .setConfirmClickListener { sDialog: IonAlert ->
                val intent = Intent(this@Activity6, MenuActivity::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
                sDialog.cancel()
            }
            .show()
    }

    fun onMenuClick(view: View) {
        showMenuDialog()
    }

    override fun onBackPressed() {
        showMenuDialog()
    }

    override fun onPause() {
        super.onPause()
        save()
    }

    override fun onStart() {
        super.onStart()
        load(this)
    }
}