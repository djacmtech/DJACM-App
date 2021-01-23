package com.djacm.alumniapp.Service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import com.djacm.alumniapp.Models.CommitteeMember;
import com.djacm.alumniapp.R;
import com.djacm.alumniapp.Activity.CommiteeFragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CommitteePhotoDownloader extends AsyncTask<String,Void, Void>
{
    private ArrayList<CommitteeMember> members; //List of the members whose photos are to be updated
    private CommiteeFragment fragment; //The committee fragment
    private boolean isFaculty; //Tells whether the downloader is downloading faculty pics

    public CommitteePhotoDownloader(ArrayList<CommitteeMember> members, CommiteeFragment fragment, boolean isFaculty)
    {
        this.members = members;
        this.fragment = fragment;
        this.isFaculty = isFaculty;
    }

    @Override
    protected Void doInBackground(String... uris)
    {
        for(int a = 0; a < uris.length; ++a)
        {
            URL urlConnection = null;
            HttpURLConnection httpConnection = null;
            InputStream stream = null;
            try
            {
                urlConnection = new URL(uris[a]);
                httpConnection = (HttpURLConnection) urlConnection.openConnection();
                httpConnection.setDoInput(true);
                httpConnection.connect();

                stream = httpConnection.getInputStream();
                Bitmap downloadedImage = BitmapFactory.decodeStream(stream);
                members.get(a).setPhoto(downloadedImage);
            }
            catch (Exception e)
            {
                Log.e("IMG_DWLD", e.getMessage());
                members.get(a).setPhoto(BitmapFactory.decodeResource(fragment.getContext().getResources(), R.drawable.default_committee_profile_pic));
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
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        //fragment.RecyclerViewUpdated(isFaculty);
    }
}
