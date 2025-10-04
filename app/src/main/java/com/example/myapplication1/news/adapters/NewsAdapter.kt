package com.example.myapplication1.news.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication1.R
import com.example.myapplication1.news.models.Article

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

    override fun onBindViewHolder(
        holder: ArticlesViewHolder,
        position: Int
    ) {
        val article = differ.currentList[position]

        holder.itemView.apply{
            Glide.with(this).load(article.url).into(holder.articleImage)
            holder.articleTitle.text = article.title
                holder.articleContent.text = article.source.name
                //article.description
                //article.publishedAt

            setOnItemClickListener{
                onItemClickListener?.let{
                    it(article)
                }
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size


    inner class ArticlesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val articleImage = itemView.findViewById<ImageView>(R.id.article_image)
        val articleTitle = itemView.findViewById<TextView>(R.id.article_title)
        val articleContent = itemView.findViewById<TextView>(R.id.article_content)
    }


    private var onItemClickListener:((Article)->Unit)?=null

    fun setOnItemClickListener(listener: (Article)->Unit){
        onItemClickListener = listener
    }
}