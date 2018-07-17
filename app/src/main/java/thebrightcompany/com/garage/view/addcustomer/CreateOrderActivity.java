package thebrightcompany.com.garage.view.addcustomer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import thebrightcompany.com.garage.App;
import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.adapter.searchcustomer.ItemSearchCustomerOnClickListener;
import thebrightcompany.com.garage.adapter.searchcustomer.SearchCustomerAdapter;
import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.api.addorder.AddOrderRequest;
import thebrightcompany.com.garage.api.searchorder.SearchCustomerRequest;
import thebrightcompany.com.garage.customview.VerticalSpaceItemDecoration;
import thebrightcompany.com.garage.model.addorder.AddOrderResponse;
import thebrightcompany.com.garage.model.searchcustomer.Customer;
import thebrightcompany.com.garage.model.searchcustomer.SearchCustomerResponse;
import thebrightcompany.com.garage.utils.Constant;
import thebrightcompany.com.garage.utils.SharedPreferencesUtils;
import thebrightcompany.com.garage.utils.Utils;

public class CreateOrderActivity extends AppCompatActivity implements CreateOrderView, ItemSearchCustomerOnClickListener{

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
    @BindView(R.id.edit_email)
    EditText edit_email;
    @BindView(R.id.layout_inputCustomer)
    LinearLayout layout_inputCustomer;

    @BindView(R.id.progress)
    ProgressBar progress;

    private SharedPreferencesUtils preferencesUtils;
    private List<Customer> mCustomers = new ArrayList<>();
    private SearchCustomerAdapter adapter;
    private String key = "";
    private Customer mCustomer;
    private String idOfCuatomer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        hideKeyboard();
        setTitle("Khách hàng mới");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(2);

        preferencesUtils = new SharedPreferencesUtils(this);

        initRecycleView();
        if (mCustomers.size() == 0){
            rc_search_customer.setVisibility(View.GONE);
        }

        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void afterTextChanged(Editable editable) {
                String data = editable.toString();
                if (TextUtils.isEmpty(data)){
                   // layout_inputCustomer.setVisibility(View.VISIBLE);
                    key = "";
                    rc_search_customer.setVisibility(View.GONE);
                    img_search.setBackground(getDrawable(R.drawable.ic_search_gara));
                    layout_inputCustomer.setVisibility(View.VISIBLE);
                }else {
                    //layout_detail.setVisibility(View.GONE);
                    img_search.setBackground(getDrawable(R.drawable.ic_close));
                    key = data;
                    //todo process search customer
                    if (!Utils.isNetworkAvailable(getContext())){
                        onNetWorkError(getString(R.string.str_msg_network_fail));
                        return;
                    }else {
                        if (key.length() >= 10 && key.length() <= 12){
                            showProgress();
                            processSearchCustomer(key, Utils.APP_TOKEN);
                        }
                    }
                }
            }
        });
    }

    /**
     * The method use to search customer
     * @param key
     * @param appToken
     */
    private void processSearchCustomer(String key, String appToken) {
        //todo search customer
        SearchCustomerListener listener = new SearchCustomerListener();
        SearchCustomerRequest request = new SearchCustomerRequest(listener, key, appToken);
        App.addRequest(request, "Search customer");
    }


    private class SearchCustomerListener extends OnResponseListener<SearchCustomerResponse>{
        @Override
        public void onErrorResponse(VolleyError error) {
            super.onErrorResponse(error);
            onSearchCustomerError("Đã có lỗi xảy ra, vui lòng thử lại.");
        }

        @Override
        public void onResponse(SearchCustomerResponse response) {
            super.onResponse(response);
            int status_code = response.getStatus_code();
            if (status_code == 0){
                onSearchCustomerSuccess(response.getDataSearchCustomer().getToken(), response.getDataSearchCustomer().getCustomers());
            }else {
                onSearchCustomerError(response.getMessage());
            }
        }
    }

    private void initRecycleView(){
        adapter = new SearchCustomerAdapter(mCustomers, this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rc_search_customer.setLayoutManager(mLayoutManager);
        rc_search_customer.setItemAnimator(new SlideInDownAnimator());
        rc_search_customer.setAdapter(new SlideInLeftAnimationAdapter(adapter));
        rc_search_customer.addItemDecoration(new VerticalSpaceItemDecoration(35));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle("Khách hàng mới");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
        updateToken(token);
        Utils.APP_TOKEN = token;
        if (customers != null){
            this.mCustomers.clear();
            this.mCustomers = customers;
            rc_search_customer.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged(mCustomers);
            layout_inputCustomer.setVisibility(View.INVISIBLE);
        }else {
            layout_inputCustomer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAddOrderSuccess(String msg) {
        hideProgress();
        showMessage(msg);
        finish();
    }

    @Override
    public void onAddOrderError(int status_code, String msg) {
        showMessage(msg);
        hideProgress();
    }

    @Override
    public void onSearchCustomerError(String msg) {
        showMessage(msg);
        hideProgress();
    }

    @Override
    public void onNetWorkError(String msg) {
        showMessage(msg);
        hideProgress();
    }

    @Override
    public void onNameError(String msg) {
        //showMessage(msg);
        edit_name.setError(msg);
        edit_name.requestFocus();
        hideProgress();
    }

    @Override
    public void onPhoneError(String msg) {
        edit_phone.setError(msg);
        edit_phone.requestFocus();
        hideProgress();
    }

    @Override
    public void onAddressError(String msg) {
        edit_address.setError(msg);
        edit_address.requestFocus();
        hideProgress();
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

    @Override
    public void onItemClickListener(int position, Customer customer) {
        //todo something
        this.mCustomer = customer;
        idOfCuatomer = customer.getCustomer_id() + "";
        layout_inputCustomer.setVisibility(View.VISIBLE);
        mCustomers.clear();
        rc_search_customer.setVisibility(View.GONE);
        try {
            edit_name.setText(mCustomer.getNameOfCustomer() + "");
        }catch (NullPointerException e){
            Log.d(TAG, e.toString());
        }

        try {
            edit_phone.setText(mCustomer.getPhone() + "");
        }catch (NullPointerException e){
            Log.d(TAG, e.toString());
        }

        try {
            edit_email.setText(mCustomer.getEmail() + "");
        }catch (NullPointerException e){
            Log.d(TAG, e.toString());
        }
    }

    /**
     * The method use to update token device
     *
     * @param token is token of device
     */
    public void updateToken(String token){
        //todo something
        if (preferencesUtils != null){
            preferencesUtils.writeStringPreference(Constant.PREF_DEVICE_TOKEN, token);
        }else {
            preferencesUtils = new SharedPreferencesUtils(this);
            preferencesUtils.writeStringPreference(Constant.PREF_DEVICE_TOKEN, token);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.btn_search)
    public void processSearch() {
        //todo something
        if (!TextUtils.isEmpty(key)) {
            edit_search.setText("");
            hideKeyboard();
            img_search.setBackground(getDrawable(R.drawable.ic_search_gara));
        }
    }

    /**
     * The method used to hide the keyboard
     * @param
     * @return
     */
    public void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.create_order:
                showProgress();
                processAddOrder();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Process add order
     */
    private void processAddOrder() {
        String name, phone, email, typeOfCar, licenseOfCar, address, troubleCode;

        name = edit_name.getText().toString() + "";
        phone = edit_phone.getText().toString() + "";
        email = edit_email.getText().toString() + "";
        typeOfCar = edit_typeOfCar.getText().toString() + "";
        licenseOfCar = edit_licenceOfCar.getText().toString() + "";
        address = edit_address.getText().toString() + "";
        troubleCode = edit_troubleCode.getText().toString() + "";

        if (TextUtils.isEmpty(name)){
            onNameError("Chưa nhập tên");
            return;
        }

        if (TextUtils.isEmpty(phone)){
            onPhoneError("Chưa nhập số điện thoại");
            return;
        }

        if (phone.length() < 10 || phone.length() > 12){
            onPhoneError("Số điện thoại chưa đúng");
            return;
        }

        if (TextUtils.isEmpty(address)){
            onAddressError("Chưa nhập địa chỉ");
            return;
        }

        if (!Utils.isNetworkAvailable(this)){
            onNetWorkError(getString(R.string.str_msg_network_fail));
            return;
        }else {
            processAddNewOrder(idOfCuatomer, name, phone, email, typeOfCar, licenseOfCar, address, troubleCode);
        }
    }

    /**
     * Use to add new forder
     * @param idOfCustomer
     * @param name
     * @param phone
     * @param email
     * @param typeOfCar
     * @param licenseOfCar
     * @param address
     * @param troubleCode
     */
    private void processAddNewOrder(String idOfCustomer, String name, String phone, String email, String typeOfCar, String licenseOfCar, String address, String troubleCode) {
        AddCustomerListener listener = new AddCustomerListener();
        AddOrderRequest request = new AddOrderRequest(listener);
        request.setIdOfCustomer(idOfCustomer);
        request.setToken(Utils.APP_TOKEN);
        request.setName(name);
        request.setPhone(phone);
        request.setEmail(email);
        request.setTypeOrCar(typeOfCar);
        request.setLisenceOfCar(licenseOfCar);
        request.setAddress(address);
        request.setTroubleCode(troubleCode);
        App.addRequest(request, "Create order");
    }

    private class AddCustomerListener extends OnResponseListener<AddOrderResponse>{
        @Override
        public void onErrorResponse(VolleyError error) {
            super.onErrorResponse(error);
            showMessage("Đã có lỗi xảy ra, vui lòng thử lại.");
        }

        @Override
        public void onResponse(AddOrderResponse response) {
            super.onResponse(response);
            int status_code = response.getStatus_code();

            if (status_code == 0){
                onAddOrderSuccess("Tạo đơn hàng thành công");
            } else {
                onAddOrderError(status_code, response.getMessage());
            }
        }
    }

}
