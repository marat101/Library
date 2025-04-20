package ru.marat.library

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import ru.marat.core_di.InjectUtils
import ru.marat.library.di.DaggerAppComponent
import ru.marat.core_data.api.R as CoreDataRes

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
        InjectUtils.provideAppComponent(DaggerAppComponent.factory().create(this))
    }

    fun createNotificationChannels() {
        val channelId = getString(CoreDataRes.string.notification_channel_id_download_files)
        val channelName =
            getString(CoreDataRes.string.notification_channel_name_download_files)
        val channelDescription =
            getString(CoreDataRes.string.notification_channel_description_download_files)
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = channelDescription
            enableVibration(false)
            setSound(null, null)
        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}