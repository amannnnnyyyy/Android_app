package com.example.myapplication1.view.adapters.recycler_view_adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.compose.ui.text.LinkAnnotation
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.core.model.advertisement.AdvertisementModel
import com.example.myapplication1.core.model.chat.Chat
import com.example.myapplication1.core.model.chat.ChatModel
import com.example.myapplication1.core.model.contact.Contact
import com.example.myapplication1.core.model.contact.ContactModel
import com.example.myapplication1.core.model.message.MessageModel
import com.example.myapplication1.core.model.message.ReadStatus
import com.google.android.material.imageview.ShapeableImageView
import androidx.core.net.toUri

class ChatListRecyclerViewAdapter(
    chatList:List<Chat>, private val onUpdateChange: (ListenerType)-> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val chats = chatList

    var onClickListener: OnItemClickListener? = null


    override fun getItemViewType(position: Int): Int {
        //return super.getItemViewType(position)
        if (position%5==0){
            return 0
        }
        return 1
    }

    fun interface OnItemClickListener{
        fun onClick(chatId:Int, contactId: Int)
    }

    fun setClickListener(listener: OnItemClickListener){
        this.onClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        var view: View

        if (viewType==0) {
            view = layoutInflater.inflate(R.layout.advertisment, parent, false)
            return viewHolderAd(view)
        }

        view = layoutInflater.inflate(R.layout.chats, parent, false)
        return viewHolderChat(view)

       // return ChatListRecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chats,parent,false))
    }



    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        positionNotFixed: Int
    ) {
        val position = holder.adapterPosition

        val adOptions = listOf<AdvertisementModel>(
            AdvertisementModel(R.drawable.ethiotel, "https://www.ethiotelecom.et".toUri()),
            AdvertisementModel(R.drawable.temu, "https://www.temu.com".toUri()),
            AdvertisementModel(R.drawable.coca, "https://www.coca-colacompany.com".toUri()),
            AdvertisementModel(R.drawable.colagate , "https://www.colgate.com".toUri())
        )



        if (position%5==0){
            val holderAd = holder as viewHolderAd

            val displayAd = adOptions.random()

            holderAd.seeMore.setOnClickListener {
                onUpdateChange.invoke(ListenerType.AdSeeMoreClicked(displayAd.redirectTo))
            }

            return holderAd.bindAd(displayAd)
        }


        val wholeSearch: LinearLayout = holder.itemView.findViewById<LinearLayout>(R.id.search)
        val searchField: EditText = holder.itemView.findViewById<EditText>(R.id.search_area)
        if (position == 0){
            wholeSearch.visibility = View.GONE
            searchField.addTextChangedListener(object: TextWatcher{
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {onUpdateChange.invoke(ListenerType.SearchClick(s))}

            })
            //{ edit->
//                Log.i("searching","Hey yo I'm tryna search")
//                onUpdateChange.invoke(ListenerType.SearchClick(edit as EditText))
//            }
        }else{
            wholeSearch.visibility = View.GONE
        }
        val chat = chats[position]
        val profilePic = holder.itemView.findViewById<ShapeableImageView>(R.id.userProfile)
        val name = holder.itemView.findViewById<TextView>(R.id.name)
        val message = holder.itemView.findViewById<TextView>(R.id.message)
        val notificationIconNumber = holder.itemView.findViewById<TextView>(R.id.notification)
        val chatsView = holder.itemView.findViewById<RelativeLayout>(R.id.chats)

        val contact = ContactModel.contacts.find {
            it.contactId == chat.sender
        }
        val lastMessage: String? = MessageModel.message.find { it.chatId == chat.id }?.message

        holder.itemView.let{
            profilePic.setImageURI(contact?.profilePic)
            name.text = contact?.name
            message.text = lastMessage
        }


        contact?.let { con->
            profilePic.setOnClickListener {
                onUpdateChange.invoke(ListenerType.ProfileClicked(con.contactId))
            }
            chatsView.setOnClickListener {
                onUpdateChange.invoke(ListenerType.ItemClick(chat.id,con.contactId))
            }
        }

        val unreadNumber = MessageModel.message.filter {
            (it.chatId == chat.id) && (it.readStatus == ReadStatus.NOT_READ) }.size

        if(unreadNumber>0)
            notificationIconNumber.text = unreadNumber.toString()
        else
            notificationIconNumber.visibility = View.GONE
    }

    override fun getItemCount(): Int = chats.size






    inner class ChatListRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)



    inner class viewHolderChat(itemView: View): RecyclerView.ViewHolder(itemView){
        val userProfilePic = itemView.findViewById<ImageView>(R.id.userProfile)
        val name = itemView.findViewById<TextView>(R.id.name)
        val message = itemView.findViewById<TextView>(R.id.message)
        val time = itemView.findViewById<TextView>(R.id.time)
        val notification = itemView.findViewById<TextView>(R.id.notification)
        fun bindChat(item: Chat){

        }
    }

    inner class viewHolderAd(itemView: View): RecyclerView.ViewHolder(itemView){
        val userProfilePic = itemView.findViewById<ImageView>(R.id.ad_pic)
        val seeMore = itemView.findViewById<TextView>(R.id.see_more)
        fun bindAd(item: AdvertisementModel){
            userProfilePic.setImageResource(item.adPic)
        }
    }

}

sealed class ListenerType{
    class ItemClick(val chatId:Int, val contactId: Int): ListenerType()
    class SearchClick(val searchString: CharSequence?): ListenerType()

    class ProfileClicked(val contactId: Int): ListenerType()

    class AdSeeMoreClicked(val redirectUrl: Uri): ListenerType()
}

//interface UpdateListener{
//    fun onUpdateClicked(type: ListenerType)
//}