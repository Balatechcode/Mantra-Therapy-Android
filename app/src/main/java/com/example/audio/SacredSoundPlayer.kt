package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.exp
import kotlin.math.sin

class SacredSoundPlayer(private val context: Context) {

    private val vibrator: Vibrator? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vibratorManager?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
    }

    private var chantAudioJob: Job? = null
    var isPlayingChant: Boolean = false
        private set

    /**
     * Subtle bead haptic tick for each rosary bead tapped
     */
    fun triggerBeadHaptic() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator?.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(20L)
            }
        } catch (_: Exception) {}
    }

    /**
     * Celebratory chime vibration when completing 108 Japa
     */
    fun triggerCompletionHaptic() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val timings = longArrayOf(0, 150, 100, 250)
                val amplitudes = intArrayOf(0, 180, 0, 255)
                vibrator?.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(longArrayOf(0, 150, 100, 250), -1)
            }
        } catch (_: Exception) {}
    }

    /**
     * Synthesize a gentle wooden bead tap click tone
     */
    fun playBeadClick() {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val sampleRate = 22050
                val durationMs = 45
                val numSamples = (sampleRate * durationMs / 1000)
                val buffer = ShortArray(numSamples)
                val freq = 432.0 // A4 tuning
                for (i in 0 until numSamples) {
                    val t = i.toDouble() / sampleRate
                    // Fast decay percussive envelope
                    val envelope = exp(-t * 90.0)
                    val sample = (sin(2.0 * Math.PI * freq * t) * envelope * Short.MAX_VALUE * 0.4).toInt()
                    buffer[i] = sample.toShort()
                }

                val audioTrack = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(buffer.size * 2)
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .build()

                audioTrack.write(buffer, 0, buffer.size)
                audioTrack.play()
                delay(durationMs.toLong() + 50)
                audioTrack.release()
            } catch (_: Exception) {}
        }
    }

    /**
     * Synthesize a singing bowl / temple bell harmonic chime
     */
    fun playSacredChime() {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val sampleRate = 44100
                val durationMs = 2600
                val numSamples = (sampleRate * durationMs / 1000)
                val buffer = ShortArray(numSamples)
                // Fundamental 528 Hz (Love/Healing Solfeggio frequency) + subtle harmonics
                val fundamental = 528.0
                val harmonic1 = 1056.0
                val harmonic2 = 1584.0

                for (i in 0 until numSamples) {
                    val t = i.toDouble() / sampleRate
                    val decay = exp(-t * 1.6)
                    val s1 = sin(2.0 * Math.PI * fundamental * t) * 0.65
                    val s2 = sin(2.0 * Math.PI * harmonic1 * t) * 0.25
                    val s3 = sin(2.0 * Math.PI * harmonic2 * t) * 0.10
                    val mixed = (s1 + s2 + s3) * decay
                    buffer[i] = (mixed * Short.MAX_VALUE * 0.55).toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
                }

                val audioTrack = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(buffer.size * 2)
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .build()

                audioTrack.write(buffer, 0, buffer.size)
                audioTrack.play()
                delay(durationMs.toLong() + 100)
                audioTrack.release()
            } catch (_: Exception) {}
        }
    }

    /**
     * Meditative Tanpura drone simulation for "Listen Audio"
     */
    fun toggleChantDrone(onStateChange: (Boolean) -> Unit) {
        if (isPlayingChant) {
            stopChantDrone()
            onStateChange(false)
        } else {
            isPlayingChant = true
            onStateChange(true)
            chantAudioJob = CoroutineScope(Dispatchers.Default).launch {
                try {
                    playSacredChime()
                    val sampleRate = 22050
                    val loopMs = 1200
                    val numSamples = (sampleRate * loopMs / 1000)
                    val buffer = ShortArray(numSamples)
                    // Tanpura base Pa (G) and Sa (C) drone chords
                    val freq1 = 196.0 // G3
                    val freq2 = 261.63 // C4
                    val freq3 = 392.0 // G4

                    for (i in 0 until numSamples) {
                        val t = i.toDouble() / sampleRate
                        // Soft breathing wave
                        val wave = (sin(2.0 * Math.PI * 0.8 * t) + 1.0) * 0.5
                        val s1 = sin(2.0 * Math.PI * freq1 * t) * 0.4
                        val s2 = sin(2.0 * Math.PI * freq2 * t) * 0.35
                        val s3 = sin(2.0 * Math.PI * freq3 * t) * 0.25
                        val sample = ((s1 + s2 + s3) * (0.2 + 0.3 * wave) * Short.MAX_VALUE * 0.35).toInt()
                        buffer[i] = sample.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
                    }

                    val track = AudioTrack.Builder()
                        .setAudioAttributes(
                            AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .build()
                        )
                        .setAudioFormat(
                            AudioFormat.Builder()
                                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                                .setSampleRate(sampleRate)
                                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                                .build()
                        )
                        .setBufferSizeInBytes(buffer.size * 2)
                        .setTransferMode(AudioTrack.MODE_STREAM)
                        .build()

                    track.play()
                    while (isActive && isPlayingChant) {
                        track.write(buffer, 0, buffer.size)
                    }
                    track.stop()
                    track.release()
                } catch (_: Exception) {} finally {
                    isPlayingChant = false
                    onStateChange(false)
                }
            }
        }
    }

    fun stopChantDrone() {
        isPlayingChant = false
        chantAudioJob?.cancel()
        chantAudioJob = null
    }
}
