package thebrightcompany.com.garage.utils;

public class Constant {
    /**
     * Link api
     */
    public static final String ROOT_URL = "http://18.191.213.77/";
    public static final String URL_LOGIN = ROOT_URL + "api/v1/gara/login";
    public static final String URL__CUSTOMER_ON_MAP = ROOT_URL + "api/v1/gara/orders?token=%s";
    public static final String URL_UPDATE_STATE = ROOT_URL + "api/v1/gara/order/%s/update_state";
    public static final String URL_SEARCH_USER = ROOT_URL + "api/v1/gara/search_user?phone=%s&token=%s";
    public static final String URL_CREATE_ORDER = ROOT_URL + "api/v1/gara/add_order";
    public static final String URL_GET_CUSTOMER = ROOT_URL + "api/v1/gara/get-customers/{idOfGara}/{state}";
    public static final String URL_GET_LIST_NOTIFICATION = ROOT_URL + "api/v1/gara/notification/{idOfGara}";
    public static final String URL_UPDATE_TIME = ROOT_URL + "api/v1/gara/update-time-finish";
    public static final String URL_PAYMENT = ROOT_URL + "api/v1/gara/update-payment/{idOfOrder}";
    public static final String URL_ORDER_DETAIL = ROOT_URL + "api/v1/gara/order-detail/{idOfOrder}";
    public static final String URL_PROFILE = ROOT_URL + "api/v1/gara/profile/{idOfUser}";

    /**
     * Defind key
     */

    public static final String TOKEN = "TOKEN_APP";
    public static final String REF_EMAIL = "EMAIL_LOGIN";
    public static final String REF_PASSWORD = "PASS_LOGIN";
    public static final String GARAGE_ID = "GARAGE_ID";

    public static String GPS_FILTER = "android.intent.action.BOOT_COMPLETED";
    public static final String PREF_LAT = "PREF_LAT";
    public static final String PREF_LNG = "PREF_LNG";
    public static final String PREF_DEVICE_TOKEN = "PREF_DEVICE_TOKEN";
}
