package com.bnawan.gituwu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bnawan.gituwu.databinding.ActivityDetailBinding
import com.bnawan.gituwu.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.detailHeader.headerTitle.text = resources.getString(R.string.detail_title)

        user = intent.getParcelableExtra<User>(DATA_USER) as User
        setDetailInformation()
    }

    private fun setDetailInformation() {
        binding.apply {
            detailContent.detailImg.loadImage(user.avatar)
            detailContent.detailName.text = user.name
            detailContent.detailUsername.text = user.username
            detailContent.detailFollower.text = user.follower.toString()
            detailContent.detailFollowing.text = user.following.toString()
            detailContent.detailRepo.text = user.repository.toString()
            detailContent.detailCompany.text = user.company
            detailContent.detailLocation.text = user.location

            btnBack.setOnClickListener {
                super.onBackPressed()
            }
        }
    }

    private fun ImageView.loadImage(src: Int?) {
//        Glide.with(this.context).load(imageView).apply(RequestOptions().override(500, 500)).centerCrop()
//            .into(this)
        Glide.with(this.context).load(src)
            .circleCrop()
            .into(this)
    }

    companion object {
        const val DATA_USER = "data_user"
    }
}