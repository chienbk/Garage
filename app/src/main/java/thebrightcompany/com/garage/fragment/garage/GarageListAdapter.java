package thebrightcompany.com.garage.fragment.garage;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.model.notificationfragment.OrderModel;
import thebrightcompany.com.garage.utils.Constant;

public class GarageListAdapter extends ArrayAdapter<OrderModel>{

    private static final String TAG = GarageListAdapter.class.getSimpleName();

    public List<OrderModel> orderModels;
    public GaraListAdapterDelegate delegate;
    public GarageListAdapter(@NonNull Context context, int resource) {
        super(context, resource);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_garage_fixing, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final OrderModel orderModel = getItem(position);
        viewHolder.txtGarageCode.setText(orderModel.code);
        viewHolder.txtCustomerName.setText(orderModel.customer_name);
        String description =  orderModel.customer_info;
        description = description.replaceAll("/n", " \n");
        viewHolder.txtFordRangerCode.setText(description);
        viewHolder.txtTrouble.setText(orderModel.getTroubleListString());

        if(orderModel.status.equals(Integer.toString(Constant.GARAGE_STATE_FIXED))){
            viewHolder.lnrTotalMoney.setVisibility(View.VISIBLE);
            viewHolder.txtMoney.setText("100.000 VNĐ");
            viewHolder.lnrAction.setVisibility(View.GONE);
        }else {
            viewHolder.lnrAction.setVisibility(View.VISIBLE);

            viewHolder.lnrTotalMoney.setVisibility(View.GONE);
            if (orderModel.end_time.equals(Constant.endTimeDefault)) {

                viewHolder.txtChoseDate.setVisibility(View.VISIBLE);
                viewHolder.txtChoseDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        delegate.choseTime(orderModel);
                    }
                });
                viewHolder.txtDateString.setVisibility(View.GONE);
                viewHolder.lnrComplete.setVisibility(View.GONE);
            } else {
                viewHolder.txtDateString.setText(orderModel.end_time);
                viewHolder.txtDateString.setVisibility(View.VISIBLE);
                viewHolder.txtChoseDate.setVisibility(View.GONE);
                viewHolder.lnrComplete.setVisibility(View.VISIBLE);
                viewHolder.lnrComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        delegate.completeOrder(orderModel);
                    }
                });

            }

            viewHolder.lnrCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delegate.callCustomer(orderModel.phone);
                }
            });
        }
        return convertView;
    }

    public class ViewHolder {
        TextView txtGarageCode;
        TextView txtCustomerName;
        TextView txtFordRangerCode;
        TextView txtTrouble;
        LinearLayout lnrCall;
        TextView txtChoseDate;
        TextView txtDateString;
        LinearLayout lnrComplete;

        LinearLayout lnrTotalMoney;
        LinearLayout lnrAction;
        TextView txtMoney;

        public ViewHolder(View view){
            txtGarageCode = (TextView) view.findViewById(R.id.txt_id_garage);
            txtCustomerName = (TextView) view.findViewById(R.id.txt_customer_name);
            txtFordRangerCode = (TextView) view.findViewById(R.id.txt_ford_ranger);
            txtTrouble = (TextView) view.findViewById(R.id.txt_trouble_id);
            txtChoseDate = (TextView) view.findViewById(R.id.txt_dat_hen);
            txtDateString = (TextView) view.findViewById(R.id.txt_date);
            lnrCall = (LinearLayout) view.findViewById(R.id.lnr_call);
            lnrComplete = (LinearLayout) view.findViewById(R.id.lnr_complete);

            txtMoney = (TextView) view.findViewById(R.id.txt_total_money);
            lnrTotalMoney = (LinearLayout) view.findViewById(R.id.lnr_total_money);
            lnrAction = (LinearLayout) view.findViewById(R.id.lnr_action);
        }

    }

    @Override
    public int getCount() {
        return this.orderModels.size();
    }

    @Nullable
    @Override
    public OrderModel getItem(int position) {
        return this.orderModels.get(position);
    }

    public interface GaraListAdapterDelegate{
        public void choseTime(OrderModel order);
        public void completeOrder(OrderModel order);
        public void callCustomer(String phoneNumer);
    }
}
