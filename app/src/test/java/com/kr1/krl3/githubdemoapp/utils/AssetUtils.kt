package com.kr1.krl3.githubdemoapp.utils

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

object AssetUtils {

    private const val ASSETS_BASE_PATH = "../app/src/main/assets/"

    fun readJsonFile(filename: String): String {
        val br = BufferedReader(InputStreamReader(FileInputStream(ASSETS_BASE_PATH + filename)))
        val sb = StringBuilder()
        var line: String? = br.readLine()

        while (line != null) {
            sb.append(line)
            line = br.readLine()
        }

        return sb.toString()
    }
}