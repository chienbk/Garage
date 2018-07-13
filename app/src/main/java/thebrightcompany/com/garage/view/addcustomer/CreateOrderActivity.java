package thebrightcompany.com.garage.view.addcustomer;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.adapter.searchcustomer.SearchCustomerAdapter;
import thebrightcompany.com.garage.model.searchcustomer.Customer;

public class CreateOrderActivity extends AppCompatActivity implements CreateOrderView{

    public static final String TAG = CreateOrderActivity.class.getSimpleName();


    @BindView(R.id.edit_search)
    EditText edit_search;
    @BindView(R.id.img_search)
    ImageView img_search;
    @BindView(R.id.rc_search_customer)
    RecyclerView rc_search_customer;
    @BindView(R.id.edit_name)
    EditText edit_name;
    @BindView(R.id.edit_phone)
    EditText edit_phone;
    @BindView(R.id.edit_typeOfCar)
    EditText edit_typeOfCar;
    @BindView(R.id.edit_licenceOfCar)
    EditText edit_licenceOfCar;
    @BindView(R.id.edit_address)
    EditText edit_address;
    @BindView(R.id.edit_troubleCode)
    EditText edit_troubleCode;

    @BindView(R.id.progress)
    ProgressBar progress;

    private List<Customer> mCustomers = new ArrayList<>();
    private SearchCustomerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("Tạo đơn hàng mới");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle("Tạo đơn hàng mới");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    /**
     * The method used to hide the keyboard
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onSearchCustomerSuccess(String token, List<Customer> customers) {

    }

    @Override
    public void onAddOrderSuccess(String msg) {
        showMessage(msg);
    }

    @Override
    public void onAddOrderError(String msg) {
        showMessage(msg);
    }

    @Override
    public void onSearchCustomerError(String msg) {
        showMessage(msg);
    }

    @Override
    public void onNetWorkError(String msg) {
        showMessage(msg);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
