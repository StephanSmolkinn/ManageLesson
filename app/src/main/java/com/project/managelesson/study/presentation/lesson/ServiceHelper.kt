package com.project.managelesson.study.presentation.lesson

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.project.managelesson.MainActivity
import com.project.managelesson.core.utils.Constants.REQUEST_CODE

object ServiceHelper {
    fun triggerService(context: Context, action: String) {
        Intent(context, LessonTimerService::class.java).also {
            it.action = action
            context.startService(it)
        }
    }

    fun clickIntent(context: Context): PendingIntent {
        val link = Intent(
            Intent.ACTION_VIEW,
            "managelesson://dashboard/lesson".toUri(),
            context,
            MainActivity::class.java
        )
        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(link)
            getPendingIntent(
                REQUEST_CODE,
                PendingIntent.FLAG_IMMUTABLE
            )
        }
    }
}