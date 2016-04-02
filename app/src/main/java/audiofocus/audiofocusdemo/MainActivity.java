package audiofocus.audiofocusdemo;

import android.app.Activity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private final static String TAG = "AudioFocus";
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {

        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    Log.i(TAG, "AUDIOFOCUS_GAIN");
                    //Play or restart your sound
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    Log.e(TAG, "AUDIOFOCUS_LOSS");
                    //Loss of audio focus for a long time
                    //Mostly stop playing the sound
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    Log.e(TAG, "AUDIOFOCUS_LOSS_TRANSIENT");
                    //Loss of audio focus for a short time
                    //Mostly pause playing the sound
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    Log.e(TAG, "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                    //Loss of audio focus for a short time.
                    //But one can duck .Mostly lower the volume of playing the sound
                    break;

                default:
                    //
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRequestFocus = (Button)findViewById(R.id.btnRequestFocus);
        btnRequestFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean gotFocus = requestAudioFocusForMyApp(MainActivity.this);
                if(gotFocus) {
                    //play audio.
                }
            }
        });


        Button btnReleaseFocus = (Button)findViewById(R.id.btnReleaseFocus);
        btnReleaseFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Stop playing audio.
                releaseAudioFocusForMyApp(MainActivity.this);
            }
        });
    }

    private boolean requestAudioFocusForMyApp(final Context context) {
        AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        // Request audio focus for playback
        int result = am.requestAudioFocus(mOnAudioFocusChangeListener,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            Log.d(TAG, "Audio focus received");
            return true;
        } else {
            Log.d(TAG, "Audio focus NOT received");
            return false;
        }
    }

    void releaseAudioFocusForMyApp(final Context context) {
        AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        am.abandonAudioFocus(mOnAudioFocusChangeListener);
    }
}
