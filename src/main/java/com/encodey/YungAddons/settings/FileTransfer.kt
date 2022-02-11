package com.encodey.YungAddons.settings

object FileTransfer {
    private fun loadData() {
            try {
                kotlin.io.path.createTempFile("null")
            }
            catch(e: Exception) {
                e.printStackTrace()
            }
    return
    }

    internal fun preload() {}
}