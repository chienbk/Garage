package thebrightcompany.com.garage.utils;

public class Constant {
    /**
     * Link api
     */
    public static final String ROOT_URL = "";
    public static final String URL_LOGIN = "v1/gara/login";
    public static final String URL__CUSTOMER_ON_MAP = "v1/gara/customer-on-map/{idOfGara}";
    public static final String URL_UPDATE_STATE = "v1/gara/update-state";
    public static final String URL_SEARCH_USER = "v1/gara/search-user";
    public static final String URL_CREATE_ORDER = "v1/gara/add-order";
    public static final String URL_GET_CUSTOMER = "v1/gara/get-customers/{idOfGara}/{state}";
    public static final String URL_GET_LIST_NOTIFICATION = "v1/gara/notification/{idOfGara}";
    public static final String URL_UPDATE_TIME = "v1/gara/update-time-finish";
    public static final String URL_PAYMENT = "v1/gara/update-payment/{idOfOrder}";
    public static final String URL_ORDER_DETAIL = "v1/gara/order-detail/{idOfOrder}";
    public static final String URL_PROFILE = "v1/gara/profile/{idOfUser}";

    /**
     * Defind key
     */

    public static final String TOKEN = "TOKEN_APP";
    public static final String REF_EMAIL = "EMAIL_LOGIN";
    public static final String REF_PASSWORD = "PASS_LOGIN";
}
