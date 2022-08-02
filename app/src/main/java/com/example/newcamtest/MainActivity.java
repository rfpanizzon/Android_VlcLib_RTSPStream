package com.example.newcamtest;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.util.VLCVideoLayout;

public class MainActivity extends AppCompatActivity {

    //motec Cam possible links
    private static final String url = "rtsp://192.168.0.75:8554/MCDE3000";
    //private static final String url = "http://192.168.0.75:1001/stream.mjpg";

    //working RTSP link
    //private static final String url = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mp4";
    private LibVLC libVlc;
    private MediaPlayer mediaPlayer;
    private VLCVideoLayout videoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        libVlc = new LibVLC(this);
        mediaPlayer = new MediaPlayer(libVlc);
        videoLayout = findViewById(R.id.videoLayout);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        mediaPlayer.attachViews(videoLayout, null, false, false);

        Media media = new Media(libVlc, Uri.parse(url));
        media.setHWDecoderEnabled(true, false);
        media.addOption(":network-caching=0");

        mediaPlayer.setMedia(media);
        media.release();
        mediaPlayer.play();
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        mediaPlayer.stop();
        mediaPlayer.detachViews();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        mediaPlayer.release();
        libVlc.release();
    }
}