package my.project.poker.scores

import android.content.Context
import android.os.Bundle
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.r0adkll.slidr.Slidr
import ir.androidexception.datatable.DataTable
import ir.androidexception.datatable.model.DataTableHeader
import ir.androidexception.datatable.model.DataTableRow
import my.project.poker.R
import java.util.*

class ScoreActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        val scrollView: ScrollView = findViewById(R.id.main)
        scrollView.setBackgroundColor(getColor(R.color.colorAccent))
        Slidr.attach(this)
        loadText(this)
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
    }

    private fun loadText(context: Context) {
        val dataTable: DataTable = findViewById(R.id.data_table)
        val sPref =
            context.getSharedPreferences("Poker.txt", Context.MODE_PRIVATE)
        if (sPref.getString("pl1", "")?.isNotEmpty()!!) {
            val pl1: ArrayList<String> = Gson().fromJson(
                sPref.getString("pl1", ""),
                object : TypeToken<ArrayList<String?>?>() {}.type
            )
            val pl2: ArrayList<String> = Gson().fromJson(
                sPref.getString("pl2", ""),
                object : TypeToken<ArrayList<String?>?>() {}.type
            )
            val pl3: ArrayList<String> = Gson().fromJson(
                sPref.getString("pl3", ""),
                object : TypeToken<ArrayList<String?>?>() {}.type
            )
            val list = intent.getIntegerArrayListExtra("nums")
            val header = DataTableHeader.Builder()
                .item(getString(R.string.number), 1)
                .item(getString(R.string.gamer1), 2)
                .item(getString(R.string.gamer2), 2)
                .item(getString(R.string.gamer3), 2).build()
            val rows = ArrayList<DataTableRow>()
            if (pl1.isNotEmpty() && pl2.isNotEmpty() && pl3.isNotEmpty()) {
                for (i in pl1.indices) {
                    val row =
                        if (pl1[i] == "0" && pl2[i] == "0" && pl3[i] == "0") {
                            DataTableRow.Builder()
                                .value(list[i].toString())
                                .value("")
                                .value("")
                                .value("").build()
                        } else {
                            DataTableRow.Builder()
                                .value(list[i].toString())
                                .value(pl1[i])
                                .value(pl2[i])
                                .value(pl3[i]).build()
                        }
                    rows.add(row)
                }
            }
            dataTable.header = header
            dataTable.rows = rows
            dataTable.inflate(context)
        } else {
            val header = DataTableHeader.Builder()
                .item(getString(R.string.number), 1)
                .item(getString(R.string.gamer1), 2)
                .item(getString(R.string.gamer2), 2)
                .item(getString(R.string.gamer3), 2).build()
            dataTable.header = header
            dataTable.inflate(context)
        }
    }
}