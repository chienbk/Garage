package thebrightcompany.com.garage.view;

import android.content.Context;

/**
 * @author ChienBK
 */
public interface BaseView {
    Context getContext();

    void showProgress();

    void hideProgress();

    void showMessage(String message);
}
