package com.syncmusicfiles;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.syncmusicfiles.manager.SyncMusicManager;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void syncMusicFromDevice(View view) {
       callSyncManager();
    }

    private void callSyncManager() {
        // check if user has READ_EXTERNAL_STORAGE permission
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_EXTERNAL_STORAGE_PERMISSION);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        }
        String[] musicList = SyncMusicManager.getInstance().getMusic(this);
        Log.e("Count", musicList.length+"");
        for (String song : musicList) {
            Log.e("SongName", song);
        }
    }

    private void playSong(String path) throws IllegalArgumentException,
            IllegalStateException, IOException {
        String extStorageDirectory = Environment.getExternalStorageDirectory()
                .toString();

        path = extStorageDirectory + File.separator + path;

     /*   mMediaPlayer.reset();
        mMediaPlayer.setDataSource(path);
        mMediaPlayer.prepare();
        mMediaPlayer.start();*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_EXTERNAL_STORAGE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    callSyncManager();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
