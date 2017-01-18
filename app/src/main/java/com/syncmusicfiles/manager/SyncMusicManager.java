package com.syncmusicfiles.manager;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

/**
 * Created by tarun on 18/1/17.
 * This class sync all music library/files from your android device
 */
public class SyncMusicManager {
    private static SyncMusicManager ourInstance = new SyncMusicManager();

    /**
     * Factory method of {@link SyncMusicManager}
     * @return
     */
    public static SyncMusicManager getInstance() {
        return ourInstance;
    }

    private SyncMusicManager() {
    }

    public String[] getMusic(Context context) {
        final Cursor mCursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Audio.Media.DISPLAY_NAME }, null, null,
                "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");

        int count = mCursor.getCount();

        String[] songs = new String[count];
        int i = 0;
        if (mCursor.moveToFirst()) {
            do {
                songs[i] = mCursor.getString(0);
                i++;
            } while (mCursor.moveToNext());
        }

        mCursor.close();

        return songs;
    }



}
