package clh.inspecciones.com.inspecciones_v2.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import clh.inspecciones.com.inspecciones_v2.R;

public class SplashActivity extends AppCompatActivity {

    private TextView tvMovemos;
    private Animation anim;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        tvMovemos = (TextView)findViewById(R.id.spalshactivitymovemos);

        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
        tvMovemos.startAnimation(anim);

        handler.postDelayed(mUpdateTimeTask, 3000);
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            // do what you need to do here after the delay
            Intent intent = new Intent();
            intent.setClass(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    };
}
