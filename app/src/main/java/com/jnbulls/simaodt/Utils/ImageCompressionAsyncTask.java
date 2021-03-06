package com.jnbulls.simaodt.Utils;

import android.os.AsyncTask;

public abstract class ImageCompressionAsyncTask extends AsyncTask<String, Void, byte[]> {
    @Override
    protected byte[] doInBackground(String... strings) {
        if(strings.length == 0 || strings[0] == null)
            return null;
        return ImageAdapter.compressImage(strings[0]);
    }

    protected abstract void onPostExecute(byte[] imageBytes) ;
}
