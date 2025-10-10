package com.example.myapplication1.news.adapters

import android.os.Build
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication1.R
import com.example.myapplication1.news.models.Article
import java.time.Instant

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ArticlesViewHolder>() {

    private val differCallback = object: DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean  = oldItem.url == newItem.url

        override fun areContentsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean = oldItem == newItem

    }


    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticlesViewHolder {
        return ArticlesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(
        holder: ArticlesViewHolder,
        position: Int
    ) {
        val article = differ.currentList[position]

        val shortDescription = article.description
        val longDescription = article.content

        holder.readToggleButton.setOnClickListener {
            Log.i("check_toggle_text","$longDescription")
            if(holder.readToggleButton.text=="Read more..."){
                holder.articleContent.text = longDescription
                holder.readToggleButton.text = "Read less"
            }else{
                holder.readToggleButton.text = "Read more..."
                holder.articleContent.text = shortDescription
            }
        }

        holder.articleContent.text = if (holder.readToggleButton.text=="Read more...") shortDescription  else longDescription



        holder.itemView.apply{
            Glide.with(this).load(article.urlToImage)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                .into(holder.articleImage)

            holder.articleTitle.text = article.title

            val instant = Instant.parse(article.publishedAt)
            val timeInMillis = instant.toEpochMilli()

            val relativeTime = DateUtils.getRelativeTimeSpanString(
                timeInMillis,
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS
            )

            holder.articleTime.text = relativeTime
            holder.source.text = article.source?.name

            holder.authorName.text = article.author?:"Unknown"
        }




        holder.itemView.setOnClickListener {
            onItemClickListener?.let{
                it(article)
            }
        }

        holder.itemView.setOnLongClickListener {
            onLongItemClickListener?.let{
                it(article)
            }
            true
        }
    }

    override fun getItemCount(): Int = differ.currentList.size


    inner class ArticlesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val articleImage = itemView.findViewById<ImageView>(R.id.article_image)
        val articleTitle = itemView.findViewById<TextView>(R.id.article_title)
        val articleContent = itemView.findViewById<TextView>(R.id.article_content)
        val articleTime = itemView.findViewById<TextView>(R.id.time)
        val source = itemView.findViewById<TextView>(R.id.source)
        val authorName = itemView.findViewById<TextView>(R.id.author)

        val readToggleButton = itemView.findViewById<TextView>(R.id.read_toggle)
    }


    private var onItemClickListener:((Article)->Unit)?=null
    private var onLongItemClickListener:((Article)->Unit)?=null

    fun setOnItemClickListener(listener: (Article)->Unit){
        onItemClickListener = listener
    }

    fun setOnLongClick(listener: (Article)->Unit){
        onLongItemClickListener = listener
    }
}