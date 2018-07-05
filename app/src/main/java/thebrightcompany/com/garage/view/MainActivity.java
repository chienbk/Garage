package thebrightcompany.com.garage.view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.fragment.CustomerFragment;
import thebrightcompany.com.garage.fragment.GarageFragment;
import thebrightcompany.com.garage.fragment.NoteFragment;
import thebrightcompany.com.garage.fragment.SettingFragment;

public class MainActivity extends Activity {
    public LinearLayout lnrCustomer;
    public LinearLayout lnrGarage;
    public LinearLayout lnrNote;
    public LinearLayout lnrSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        lnrCustomer = (LinearLayout) findViewById(R.id.customer_lnr);
        lnrCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new CustomerFragment());
                updateTabbar(0);

            }
        });

        lnrGarage = (LinearLayout) findViewById(R.id.garage_lnr);
        lnrGarage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new GarageFragment());
                updateTabbar(1);

            }
        });

        lnrNote = (LinearLayout) findViewById(R.id.note_lnr);
        lnrNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new NoteFragment());
                updateTabbar(2);

            }
        });

        lnrSetting = (LinearLayout) findViewById(R.id.setting_lnr);
        lnrSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new SettingFragment());
                updateTabbar(3);

            }
        });

        addFragment(new CustomerFragment());
        updateTabbar(0);


       }

       public void updateTabbar(int tapSelect){
           lnrCustomer.setAlpha(0.5f);
           lnrGarage.setAlpha(0.5f);
           lnrNote.setAlpha(0.5f);
           lnrSetting.setAlpha(0.5f);
           switch (tapSelect){
               case 0:
                   lnrCustomer.setAlpha(1f);
                   break;
               case 1:
                   lnrGarage.setAlpha(1f);
                   break;
               case 2:
                   lnrNote.setAlpha(1f);
                   break;
               default:
                   lnrSetting.setAlpha(1f);
                   break;
           }
       }


    public void addFragment(Fragment fragment) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layoutchange, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


//    public String encodeFileToBase64Binary(ImageView imageView) {
//        Bitmap bmp =((BitmapDrawable)imageView.getDrawable()).getBitmap();
//        ByteArrayOutputStream bos = null;
//        byte[] bt = null;
//        String encodeString = null;
////        try {
//            bos = new ByteArrayOutputStream();
//            bmp.compress(Bitmap.CompressFormat.JPEG, 40, bos);
//            bt = bos.toByteArray();
//            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        return encodeString;
//    }
//
//    public Bitmap decodeBase64AndSetImage(String completeImageData) {
//
//        // Incase you're storing into aws or other places where we have extension stored in the starting.
//        String imageDataBytes = completeImageData.substring(completeImageData.indexOf(",") + 1);
//
//        InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));
//
//        Bitmap bitmap = BitmapFactory.decodeStream(stream);
//
//        return bitmap;
//    }


}
