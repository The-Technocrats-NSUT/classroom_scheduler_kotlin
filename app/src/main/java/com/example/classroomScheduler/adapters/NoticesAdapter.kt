package com.example.classroomScheduler.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.classroomScheduler.R
import com.example.classroomScheduler.Utils.MyUtils
import com.example.classroomScheduler.model.NoticeModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.item_notice.view.*
import org.w3c.dom.Text

class NoticesAdapter(options: FirestoreRecyclerOptions<NoticeModel>,
                    private val isAdmin: Boolean,
                    private val listener: INoticeListAdapter):
        FirestoreRecyclerAdapter<NoticeModel, NoticesAdapter.NoticeViewHolder>(options) {

        class NoticeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
        {
            val noticeText :TextView = itemView.findViewById(R.id.noticeText)
            val noticeAuthor: TextView = itemView.findViewById(R.id.noticeAuthor)
            val noticePostTime: TextView = itemView.findViewById(R.id.noticeTime)
            val noticePostDate: TextView = itemView.findViewById(R.id.noticeDate)
            val deleteNoticeBtn: Button = itemView.findViewById(R.id.deleteNoticeBtn)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {

        val noticeListView = LayoutInflater.from(parent.context).inflate(R.layout.item_notice, parent, false)
        val noticeListViewHolder = NoticeViewHolder(noticeListView)

        if (!isAdmin)
        {
            noticeListViewHolder.deleteNoticeBtn.visibility = GONE
        }

        // On clicking the delete button on a notice by the admin
        noticeListViewHolder.deleteNoticeBtn.setOnClickListener {
            val noticeSnapshot = snapshots.getSnapshot(noticeListViewHolder.adapterPosition)
            listener.deleteNoticeBtnListener(noticeSnapshot)
        }

        return noticeListViewHolder
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int, model: NoticeModel) {
        holder.noticeText.text = model.noticeText
        holder.noticeAuthor.text = model.noticeAuthor
        holder.noticePostDate.text = model.datePosted
        holder.noticePostTime.text = model.timePosted
    }
}

interface INoticeListAdapter
{
    fun deleteNoticeBtnListener(notice: DocumentSnapshot)
}