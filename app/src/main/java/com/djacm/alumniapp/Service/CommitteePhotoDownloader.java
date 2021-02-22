package com.djacm.alumniapp.Service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.djacm.alumniapp.Models.CommitteeMember;
import com.djacm.alumniapp.R;
import com.djacm.alumniapp.Activity.CommiteeFragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CommitteePhotoDownloader extends AsyncTask<String,Void, Bitmap>
{
    private Object[] downloadContext;

    public CommitteePhotoDownloader(Object[] downloadContext)
    {
        this.downloadContext = downloadContext;
    }

    @Override
    protected Bitmap doInBackground(String... uris)
    {

        URL urlConnection = null;
        HttpURLConnection httpConnection = null;
        InputStream stream = null;
        try
        {
            urlConnection = new URL(uris[0]);
            httpConnection = (HttpURLConnection) urlConnection.openConnection();
            httpConnection.setDoInput(true);
            httpConnection.connect();

            stream = httpConnection.getInputStream();
            Bitmap downloadedImage = BitmapFactory.decodeStream(stream);

            return downloadedImage;
        }
        catch (Exception e)
        {
            Log.e("IMG_DWLD", e.getMessage());
        }
        finally
        {
            try
            {
                stream.close();
            }
            catch(IOException exe)
            {

            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result)
    {

    }
}
