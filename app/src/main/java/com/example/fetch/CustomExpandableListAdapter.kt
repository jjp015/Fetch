package com.example.fetch

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import java.util.*

class CustomExpandableListAdapter internal constructor(
    private val context: Context,
    private val listId: ArrayList<Int>,
    private val modelList: TreeMap<Int, ArrayList<Int>>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return this.listId.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return this.modelList[this.listId[p0]]!!.size
    }

    override fun getGroup(p0: Int): Any {
        return this.listId[p0]
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return this.modelList[this.listId[p0]]!![p1]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        var p2 = p2
        val listId = getGroup(p0) as Int
        if (p2 == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            p2 = layoutInflater.inflate(R.layout.list_item, null)
        }
        val listTitleTextView = p2!!.findViewById<TextView>(R.id.list_item)
        listTitleTextView.text = context.getString(R.string.list_id_title, listId)
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        return p2
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        var p3 = p3
        val expandedListText = getChild(p0, p1) as Int
        if (p3 == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            p3 = layoutInflater.inflate(R.layout.list_item, null)
        }
        val expandedListTextView = p3?.findViewById<TextView>(R.id.list_item)
        expandedListTextView!!.text =
            context.getString(R.string.expanded_list_item, expandedListText)
        expandedListTextView.setTypeface(null, Typeface.ITALIC)
        return p3!!
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

}