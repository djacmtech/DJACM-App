package com.imbuegen.alumniapp.Service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.imbuegen.alumniapp.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CommitteePhotoDownloader extends AsyncTask<String,Void, Bitmap>
{
    private ImageView imageView; //The image view to be used for displaying the photo

    public CommitteePhotoDownloader(ImageView imageView)
    {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... uris)
    {
        try
        {
            URL urlConnection = new URL(uris[0]);
            HttpURLConnection httpConnection = (HttpURLConnection)urlConnection.openConnection();
            httpConnection.setDoInput(true);
            httpConnection.connect();

            InputStream stream = httpConnection.getInputStream();
            Bitmap downloadedImage = BitmapFactory.decodeStream(stream);
            return downloadedImage;
        }
        catch(Exception e)
        {
            Log.e("IMG_DWLD", e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result)
    {
        if(result != null)
        {
            //Displaying the image
            imageView.setImageBitmap(result);
        }
        else
            imageView.setImageResource(R.drawable.default_committee_profile_pic); //Setting the default profile pic
    }
}
