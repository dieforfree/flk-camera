package com.hzflk.camera;
import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.MediaController;
import android.widget.VideoView;
import android.content.pm.ActivityInfo;
 
public class VideoPlayer extends Activity implements MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener, OnClickListener {
    public static final String TAG = "flk-camera";
    private VideoView mVideoView;
    private Uri mUri;
    private int mPositionWhenPaused = -1;
 
    private MediaController mMediaController;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.video_player);
 
        mVideoView = (VideoView)findViewById(R.id.video_view);
        
        findViewById(R.id.btn_cancel).setOnClickListener(this);
 
        //Video file
        mUri = getIntent().getData();
 
        //Create media controller
        mMediaController = new MediaController(this);
        mVideoView.setMediaController(mMediaController);
    }
 
    @Override
    public void onStart() {
        // Play Video
        mVideoView.setVideoURI(mUri);
        mVideoView.start();
 
        super.onStart();
    }
 
    @Override
    public void onPause() {
        // Stop video when the activity is pause.
        mPositionWhenPaused = mVideoView.getCurrentPosition();
        mVideoView.stopPlayback();
        Log.d(TAG, "OnStop: mPositionWhenPaused = " + mPositionWhenPaused);
        Log.d(TAG, "OnStop: getDuration  = " + mVideoView.getDuration());
 
        super.onPause();
    }
 
    @Override
    public void onResume() {
        // Resume video player
        if(mPositionWhenPaused >= 0) {
            mVideoView.seekTo(mPositionWhenPaused);
            mPositionWhenPaused = -1;
        }
 
        super.onResume();
    }
 
    @Override
    public boolean onError(MediaPlayer player, int arg1, int arg2) {
        return false;
    }
 
    @Override
    public void onCompletion(MediaPlayer mp) {
        this.finish();
    }
    
    @Override
    public void onClick(View v) {
    	switch(v.getId()) {
    	case R.id.btn_cancel:
    		this.finish();
    		break;
    	}
    }
}