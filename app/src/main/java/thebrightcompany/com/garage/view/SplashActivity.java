package thebrightcompany.com.garage.view;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.view.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    public static final String TAG = SplashActivity.class.getSimpleName();
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    private String type;
    private int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_splash);


        final Bundle b = getIntent().getExtras();
        if (b != null) {
            orderId = b.getInt("order_id");
            type = b.getString("type");
            Log.d(TAG, "fcm_notification: " + orderId + " - " + type);
        }

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                //Intent i = new Intent(SplashActivity.this, LoginScreenActivity.class);
                //startActivity(i);
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                if (b != null) {
                    intent.putExtra("orderId", orderId + "");
                    intent.putExtra("type", type);
                    Log.d(TAG, "fcm_notification: " + orderId + " - " + type);
                }
                startActivity(intent);
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
