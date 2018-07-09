package thebrightcompany.com.garage.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import thebrightcompany.com.garage.R;

/**
 * Dialog utils
 * 
 * @author ChienNv9
 */
public class AlertDialogUtils {

	public static void showInfoDialog(Context mContext, String message,
                                      final IOnDialogClickListener mIOnDialogClickListener) {

		final Dialog dialog = new Dialog(mContext, R.style.Theme_Dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(true);
		dialog.setContentView(R.layout.fragment_dialog_alert);

		Button btnClose = (Button) dialog
				.findViewById(R.id.btn_alert_dialog_close);
		dialog.setCancelable(false);
		btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mIOnDialogClickListener != null) {
					mIOnDialogClickListener.onClickOk();
				}
				dialog.dismiss();
			}
		});

		TextView tvMessage = (TextView) dialog
				.findViewById(R.id.lbl_alert_dialog_text);
		tvMessage.setText(message);

		dialog.show();
	}

	public static void showInfoDialog(Context mContext, String title,
                                      String message, boolean hasNegativeBt,
                                      final IOnDialogClickListener mIOnDialogClickListener) {

		final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(true);
		dialog.setContentView(R.layout.fragment_dialog_info);

		Button btnOk = (Button) dialog.findViewById(R.id.btn_alert_dialog_ok);
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mIOnDialogClickListener != null) {
					mIOnDialogClickListener.onClickOk();
				}
				dialog.dismiss();
			}
		});

		Button btnCancel = (Button) dialog
				.findViewById(R.id.btn_alert_dialog_cancel);
		if (!hasNegativeBt) {
			btnCancel.setVisibility(View.GONE);
		} else {
			btnCancel.setVisibility(View.VISIBLE);
		}
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});

		TextView tvTitle = (TextView) dialog
				.findViewById(R.id.tv_alert_dialog_title);
		tvTitle.setText(title);
		TextView tvMessage = (TextView) dialog
				.findViewById(R.id.tv_alert_dialog_text);
		tvMessage.setText(message);

		dialog.show();
	}

	// public static void showInfoDialog(Context mContext, String tilte, String
	// message, String image,
	// final IOnDialogClickListener mOnDialogClickListener) {
	// final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Dialog);
	// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	// dialog.setCancelable(true);
	// dialog.getWindow().setBackgroundDrawable(new
	// ColorDrawable(android.graphics.Color.TRANSPARENT));
	// dialog.setContentView(R.layout.alertdialog_notifination);
	//
	// TextView btnClose = (TextView) dialog.findViewById(R.id.close);
	// TextView btnOpen = (TextView) dialog.findViewById(R.id.open);
	// TextView tvTitle = (TextView) dialog.findViewById(R.id.title);
	// TextView tvMessage = (TextView) dialog.findViewById(R.id.message);
	// NetworkImageView imvImage = (NetworkImageView)
	// dialog.findViewById(R.id.image);
	// btnClose.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// dialog.dismiss();
	// if (mOnDialogClickListener != null) {
	// mOnDialogClickListener.onClickCancel();
	// }
	// }
	// });
	// btnOpen.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// dialog.dismiss();
	// if (mOnDialogClickListener != null) {
	// mOnDialogClickListener.onClickOk();
	// }
	// //
	// }
	// });
	// if (image.isEmpty())
	// imvImage.setVisibility(View.GONE);
	// tvTitle.setText(tilte);
	// tvMessage.setText(message);
	// imvImage.setImageUrl(image,
	// ImageCacheManager.getInstance().getImageLoader());
	// ;
	// dialog.show();
	// }

	// public static void showInfoDialog(Context mContext, String title, String
	// message, boolean hasNegativeBt,
	// final IOnDialogClickListener mIOnDialogClickListener) {
	// AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
	// TextView mtitle = new TextView(mContext);
	// mtitle.setText(title);
	// mtitle.setGravity(Gravity.CENTER_HORIZONTAL);
	// mtitle.setTextSize(20);
	// mtitle.setPadding(20, 20, 20, 20);
	// builder.setCustomTitle(mtitle);
	//
	// builder.setMessage(message).setPositiveButton(mContext.getResources().getString(R.string.btn_ok),
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int id) {
	// // FIRE ZE MISSILES!
	// if (mIOnDialogClickListener != null) {
	// mIOnDialogClickListener.onClickOk();
	// }
	// }
	// });
	// if (hasNegativeBt) {
	// builder.setNegativeButton(mContext.getResources().getString(R.string.btn_cancel),
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int id) {
	// // User cancelled the dialog
	// if (mIOnDialogClickListener != null) {
	// mIOnDialogClickListener.onClickCancel();
	// }
	// dialog.dismiss();
	// }
	// });
	// }
	// // Create the AlertDialog object and return it
	// // builder.setCancelable(false);
	// AlertDialog dialog = builder.show();
	// dialog.setCanceledOnTouchOutside(true);
	//
	// TextView messageText = (TextView)
	// dialog.findViewById(android.R.id.message);
	// if (messageText != null) {
	// messageText.setGravity(Gravity.CENTER);
	// }
	// dialog.show();
	//
	// }

	@SuppressLint("NewApi")
	public static void showInfoDialogUpdate(Context mContext, String message,
                                            boolean hasNegativeBt,
                                            final IOnDialogClickListener mIOnDialogClickListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext,
				AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
		TextView mtitle = new TextView(mContext);
		mtitle.setText("Thông báo");
		mtitle.setTextAppearance(mContext, R.style.boldText);
		mtitle.setTextColor(mContext.getResources().getColor(Color.BLACK));
		mtitle.setGravity(Gravity.CENTER_HORIZONTAL);
		mtitle.setTextSize(20);
		mtitle.setPadding(20, 20, 20, 20);
		builder.setCustomTitle(mtitle);

		builder.setMessage(message).setPositiveButton("Cập nhật",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// FIRE ZE MISSILES!
						if (mIOnDialogClickListener != null) {
							mIOnDialogClickListener.onClickOk();
						}
					}
				});
		if (hasNegativeBt) {
			builder.setNegativeButton("Bỏ qua",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// User cancelled the dialog
							if (mIOnDialogClickListener != null) {
								mIOnDialogClickListener.onClickCancel();
							}
							dialog.dismiss();
						}
					});
		}
		// Create the AlertDialog object and return it
		// builder.setCancelable(false);
		AlertDialog dialog = builder.show();
		dialog.setCanceledOnTouchOutside(true);
		dialog.setCancelable(false);
		TextView messageText = (TextView) dialog
				.findViewById(android.R.id.message);
		if (messageText != null) {
			messageText.setGravity(Gravity.CENTER);
		}
		dialog.show();

	}

	@SuppressLint("NewApi")
	public static void showInfoDialogFacebook(Context mContext, String message,
                                              boolean hasNegativeBt,
                                              final IOnDialogClickListener mIOnDialogClickListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext,
				AlertDialog.THEME_HOLO_LIGHT);
		TextView mtitle = new TextView(mContext);
		mtitle.setText("Thông báo");
		mtitle.setTextAppearance(mContext, R.style.boldText);
		mtitle.setTextColor(mContext.getResources().getColor(Color.BLACK));
		mtitle.setGravity(Gravity.CENTER_HORIZONTAL);
		mtitle.setTextSize(20);
		mtitle.setPadding(20, 20, 20, 20);
		builder.setCustomTitle(mtitle);
		builder.setMessage(message).setPositiveButton("Thoát",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// FIRE ZE MISSILES!
						if (mIOnDialogClickListener != null) {
							mIOnDialogClickListener.onClickOk();
						}
					}
				});
		if (hasNegativeBt) {
			builder.setNegativeButton("Thoát",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// User cancelled the dialog
							if (mIOnDialogClickListener != null) {
								mIOnDialogClickListener.onClickCancel();
							}
							dialog.dismiss();
						}
					});
		}
		// Create the AlertDialog object and return it
		// builder.setCancelable(false);
		AlertDialog dialog = builder.show();
		dialog.setCanceledOnTouchOutside(true);
		dialog.setCancelable(false);
		TextView messageText = (TextView) dialog
				.findViewById(android.R.id.message);
		if (messageText != null) {
			messageText.setGravity(Gravity.CENTER);
		}
		dialog.show();

	}

	public static Dialog ShowDialog(Context context, String strTitle,
                                    String strMsg, String strBtnOk, boolean hasNegativeBt,
                                    String strBtnCancel,
                                    final IOnDialogClickListener mIOnDialogClickListener) {
		final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.dialog_turn_on_gps);
		dialog.setCancelable(true);
		TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
		TextView tvMsg = (TextView) dialog.findViewById(R.id.tv_msg);
		TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);
		TextView tvCancel = (TextView) dialog.findViewById(R.id.tv_cancel);
		View vLine = (View) dialog.findViewById(R.id.view_cancel_line);
		tvTitle.setText(strTitle);
		tvMsg.setText(strMsg);
		tvOk.setText(strBtnOk);
		if (hasNegativeBt) {
			tvCancel.setVisibility(View.VISIBLE);
			vLine.setVisibility(View.VISIBLE);
			tvCancel.setText(strBtnCancel);
		} else {
			tvCancel.setVisibility(View.GONE);
			vLine.setVisibility(View.GONE);
		}
		tvOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mIOnDialogClickListener != null) {
					mIOnDialogClickListener.onClickOk();
					dialog.dismiss();
				}
			}
		});
		tvCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mIOnDialogClickListener != null) {
					mIOnDialogClickListener.onClickCancel();
					dialog.dismiss();
				}
			}
		});
		dialog.show();
		return dialog;
	};

	public interface IOnDialogClickListener {

		public void onClickOk();

		public void onClickCancel();
	}

}
