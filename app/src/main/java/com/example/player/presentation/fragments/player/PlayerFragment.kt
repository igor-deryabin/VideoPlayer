package com.example.player.presentation.fragments.player

import android.annotation.SuppressLint
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.player.R
import com.example.player.databinding.FragmentPlayerBinding
import com.example.player.domain.data.Channel
import com.example.player.domain.data.Quality
import com.example.player.presentation.activity.MainActivity
import com.example.player.presentation.fragments.player.adapter.QualityItemsAdapter
import com.example.player.presentation.fragments.player.adapter.QualityItemsListener
import com.example.player.utils.RecyclerViewItemDecoration
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.TimelineChangeReason
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.source.hls.HlsManifest
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.hls.playlist.HlsMultivariantPlaylist
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.material.internal.ViewUtils.dpToPx
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayerFragment: Fragment(), QualityItemsListener {

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!

    private val vm: PlayerViewModel by viewModel()

    private var currentChannel: Channel? = null

    private val variantsQuality = mutableListOf<Quality>()
    private var selectedQuality: Int = 0
    private var exoPlayer: ExoPlayer? = null

    private var imageSettings: ImageView? = null
    private var popupWindowQuality: PopupWindow? = null
    private var adapterQuality: QualityItemsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        val root = binding.root

        arguments?.let { b ->
            currentChannel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                b.getSerializable("channel", Channel::class.java)
            } else {
                b.getSerializable("channel") as Channel
            }
        }

        return root
    }

    @SuppressLint("RestrictedApi", "NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnBack.setOnClickListener { findNavController().navigateUp() }
            imageSettings = playerControlView.findViewById<ImageView>(R.id.image_settings)

            imageSettings!!.setOnClickListener { showPopup() }
        }
        selectedQuality = 0
        variantsQuality.clear()
        variantsQuality.add(Quality(isAuto = true, selected = true))
        createPopup()

        if (currentChannel != null) {
            initUI()
            setupPlayer()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {
        binding.apply {
            Glide.with(requireContext())
                .load(currentChannel!!.image)
                .into(imageView)

            tvTitle.text = "${currentChannel!!.descShort} ${currentChannel!!.descFull}"
            tvSubtitle.text = currentChannel!!.name
        }
    }

    private fun setupPlayer() {
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        val mediaSource = HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse(currentChannel!!.url)))

        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        exoPlayer!!.setMediaSource(mediaSource)

        binding.apply {
            videoPlayer.player = exoPlayer!!
            videoPlayer.keepScreenOn = true
            playerControlView.player = exoPlayer!!
        }

        exoPlayer!!.addListener(
            object : Player.Listener {
                override fun onTimelineChanged(
                    timeline: Timeline, reason: @TimelineChangeReason Int
                ) {
                    val manifest = exoPlayer!!.currentManifest
                    if (manifest != null && variantsQuality.size == 1) {
                        val hlsManifest = manifest as HlsManifest
                        val variants = hlsManifest.multivariantPlaylist.variants

                        if (variants.isNotEmpty()) {
                            variantsQuality.clear()

                            val newList = mutableListOf<HlsMultivariantPlaylist.Variant>()
                            newList.addAll(variants)
                            newList.sortBy { it.format.height }

                            newList.forEachIndexed { i, v ->
                                variantsQuality.add(Quality(
                                    bitrate = v.format.bitrate,
                                    width = v.format.width,
                                    height = v.format.height,
                                    index = i
                                ))
                            }
                            variantsQuality.add(
                                Quality(
                                    isAuto = true,
                                    index = variantsQuality.size,
                                    selected = true
                                )
                            )
                            createPopup()
                        }
                    }
                }
                @Deprecated("Deprecated in Java")
                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    when (playbackState) {
                        ExoPlayer.STATE_BUFFERING ->
                            binding.videoProgress.visibility = View.VISIBLE
                        ExoPlayer.STATE_IDLE -> {}
                        ExoPlayer.STATE_READY ->
                            binding.videoProgress.visibility = View.GONE
                        ExoPlayer.STATE_ENDED -> {}
                    }
                }
            })

        exoPlayer!!.prepare()
    }

    private fun setAutoQuality() {
        val trackSelectionParameters = TrackSelectionParameters.Builder(requireContext())
            .build()

        exoPlayer!!.trackSelector!!.parameters = trackSelectionParameters
    }

    private fun setNewQuality(quality: Quality) {
        val trackSelectionParameters = TrackSelectionParameters.Builder(requireContext())
            .setMaxVideoSize(quality.width, quality.height)
            .setMaxVideoBitrate(quality.bitrate)
            .build()

        exoPlayer!!.trackSelector!!.parameters = trackSelectionParameters
    }

    override fun onResume() {
        super.onResume()
        if (exoPlayer != null) {
            exoPlayer!!.playWhenReady = true
            if (selectedQuality == 0) {
                setAutoQuality()
            } else {
                setNewQuality(variantsQuality.first{ it.height == selectedQuality })
            }
        }
        (requireActivity() as MainActivity).hideSystemUI()
    }

    override fun onPause() {
        exoPlayer!!.playWhenReady = false
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createPopup() {
        val inflater =
            requireContext().getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(
            R.layout.popup_quality,
            null
        )

        val rvItems = popupView.findViewById<RecyclerView>(R.id.rv_items)

        adapterQuality = QualityItemsAdapter(
            requireContext(),
            this@PlayerFragment,
            variantsQuality
        )

        rvItems.apply {
            isNestedScrollingEnabled = false
        }.adapter = adapterQuality

        val divider = RecyclerViewItemDecoration(requireContext(), R.drawable.divider_quality)
        rvItems.addItemDecoration(divider)

        val width = ViewGroup.LayoutParams.WRAP_CONTENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        val focusable = true

        popupWindowQuality = PopupWindow(popupView, width, height, focusable)
        popupWindowQuality!!.setOnDismissListener {
            (requireActivity() as MainActivity).hideSystemUI()
        }
    }

    @SuppressLint("RestrictedApi", "NotifyDataSetChanged")
    private fun showPopup() {
        if (popupWindowQuality != null && adapterQuality != null && imageSettings != null) {
            adapterQuality!!.notifyDataSetChanged()

            popupWindowQuality!!.showAsDropDown(
                imageSettings!!,
                -(dpToPx(requireContext(), 128).toInt() - imageSettings!!.width),
                -((variantsQuality.size * dpToPx(requireContext(), 40)  +
                        imageSettings!!.height) + dpToPx(requireContext(), 10)).toInt()
            )
        }
    }

    override fun changeQuality(item: Quality) {
        if (item.isAuto) {
            setAutoQuality()
        } else {
            setNewQuality(item)
        }
        selectedQuality = item.height
        variantsQuality.forEach {
            it.selected = it.height == selectedQuality
        }
        if (popupWindowQuality != null) {
            popupWindowQuality!!.dismiss()
        }
    }

    override fun onDestroy() {
        exoPlayer!!.stop()
        exoPlayer!!.release()
        exoPlayer = null
        super.onDestroy()
    }
}