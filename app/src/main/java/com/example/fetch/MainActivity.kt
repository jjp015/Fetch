package com.example.fetch

import android.content.Context
import android.os.Bundle
import android.widget.ExpandableListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var expandableListView: ExpandableListView
    lateinit var textView0: TextView
    lateinit var textView1: TextView
    lateinit var textView2: TextView
    lateinit var textView3: TextView
    lateinit var textView4: TextView
    lateinit var textView5: TextView
    var totalEmpty = 0
    var totalNull = 0

    var idListGroup: ArrayList<Int> = ArrayList()
    var idListGroupItems: TreeMap<Int, ArrayList<Int>> = TreeMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        expandableListView = findViewById(R.id.expandable_list)
        textView0 = findViewById(R.id.unique_listIds)
        textView1 = findViewById(R.id.total_fetched)
        textView2 = findViewById(R.id.total_empty)
        textView3 = findViewById(R.id.total_null)
        textView4 = findViewById(R.id.total_discarded)
        textView5 = findViewById(R.id.total_remaining)

        val jsonFileString = getJsonDataFromAsset(applicationContext)

        run(jsonFileString)
    }

    private fun getJsonDataFromAsset(context: Context): String {
        lateinit var jsonString: String

        try {
            jsonString = context.assets.open("fetchFile.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return jsonString
    }

    private fun run(fetchFile: String) {
        val jsonArray = JSONArray(fetchFile)
        val size: Int = jsonArray.length()

        for (i in 0 until size) {
            val jsonObject: JSONObject = jsonArray.getJSONObject(i)
            val model = Model()
            model.id = jsonObject.getString("id")
            model.listId = jsonObject.getString("listId")
            model.name = jsonObject.getString("name")

            if (model.name == "") {
                totalEmpty += 1
            } else if (model.name == "null") {
                totalNull += 1
            } else {
                val listId = model.listId.toInt()
                val idLength = model.name.length
                if (idListGroupItems.containsKey(model.listId.toInt())) {
                    val list: ArrayList<Int>? = idListGroupItems[listId]
                    list?.add(model.name.substring(5, idLength).toInt())
                    idListGroupItems[model.listId.toInt()] = list!!
                } else {
                    val list: ArrayList<Int> = ArrayList()
                    list.add(model.name.substring(5, idLength).toInt())
                    idListGroupItems[model.listId.toInt()] = list
                }
            }
        }

        for (i in idListGroupItems.keys) {
            idListGroup.add(i)
        }

        for (i in idListGroupItems.values) {
            i.sort()
        }

        runOnUiThread {
            Toast.makeText(applicationContext, "Fetched Success", Toast.LENGTH_SHORT).show()

            val adapter = CustomExpandableListAdapter(
                applicationContext, idListGroup, idListGroupItems
            )
            expandableListView.setAdapter(adapter)

            textView0.text = getString(R.string.unique_listIds, idListGroup.size)
            textView1.text = getString(R.string.total_fetched, jsonArray.length())
            textView2.text = getString(R.string.total_empty, totalEmpty)
            textView3.text = getString(R.string.total_null, totalNull)
            textView4.text = getString(R.string.total_discarded, totalEmpty + totalNull)
            textView5.text = getString(
                R.string.total_remaining, jsonArray.length() - totalEmpty - totalNull
            )
        }
    }
}