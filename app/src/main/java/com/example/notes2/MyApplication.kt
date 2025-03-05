package com.example.notes2

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp // Эта аннотация генерирует Hilt-компоненты и должна быть применена к классу Application.
class MyApplication : Application()