package com.firstfaceme.firstface.view.activity.twilioVoice

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.twilio.video.*

class SettingsActivity : AppCompatActivity() {
    companion object {
        const val PREF_AUDIO_CODEC = "audio_codec"
        const val PREF_AUDIO_CODEC_DEFAULT = OpusCodec.NAME
        const val PREF_VIDEO_CODEC = "video_codec"
        const val PREF_VIDEO_CODEC_DEFAULT = Vp8Codec.NAME
        const val PREF_SENDER_MAX_AUDIO_BITRATE = "sender_max_audio_bitrate"
        const val PREF_SENDER_MAX_AUDIO_BITRATE_DEFAULT = "0"
        const val PREF_SENDER_MAX_VIDEO_BITRATE = "sender_max_video_bitrate"
        const val PREF_SENDER_MAX_VIDEO_BITRATE_DEFAULT = "0"
        const val PREF_VP8_SIMULCAST = "vp8_simulcast"
        const val PREF_VP8_SIMULCAST_DEFAULT = false
        const val PREF_ENABLE_AUTOMATIC_SUBSCRIPTION = "enable_automatic_subscription"
        const val PREF_ENABLE_AUTOMATIC_SUBCRIPTION_DEFAULT = true

        val VIDEO_CODEC_NAMES = arrayOf(Vp8Codec.NAME, H264Codec.NAME, Vp9Codec.NAME)

        val AUDIO_CODEC_NAMES = arrayOf(IsacCodec.NAME, OpusCodec.NAME, PcmaCodec.NAME,
                PcmuCodec.NAME, G722Codec.NAME)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}
