package thebrightcompany.com.garage.fragment.garage;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.fragment.note.model.NoteModel;
import thebrightcompany.com.garage.model.notificationfragment.OrderModel;

public class GarageListAdapter extends ArrayAdapter<OrderModel>{

    public List<OrderModel> notes;
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
        final OrderModel note = getItem(position);
//        viewHolder.txtTitle.setText(note.type);
//        viewHolder.txtContent.setText(note.content);
        viewHolder.txtGarageCode.setText(note.code);
        viewHolder.txtCustomerName.setText(note.customer_name);
        viewHolder.txtFordRangerCode.setText(note.customer_info);
        viewHolder.txtTrouble.setText(note.getTroubleListString());
        if(note.time_finish == null || note.time_finish.length()==0){

            viewHolder.txtChoseDate.setVisibility(View.VISIBLE);
            viewHolder.txtChoseDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delegate.choseTime(note);
                }
            });
            viewHolder.txtDateString.setVisibility(View.GONE);
            viewHolder.lnrComplete.setVisibility(View.GONE);
        }else {
            viewHolder.txtDateString.setText(note.time_finish);
            viewHolder.txtDateString.setVisibility(View.VISIBLE);
            viewHolder.txtChoseDate.setVisibility(View.GONE);
            viewHolder.lnrComplete.setVisibility(View.VISIBLE);
            viewHolder.lnrComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delegate.completeOrder(note.id);
                }
            });

        }

        viewHolder.lnrCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.callCustomer(note.phone);
            }
        });
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

        public ViewHolder(View view){
            txtGarageCode = (TextView) view.findViewById(R.id.txt_id_garage);
            txtCustomerName = (TextView) view.findViewById(R.id.txt_customer_name);
            txtFordRangerCode = (TextView) view.findViewById(R.id.txt_ford_ranger);
            txtTrouble = (TextView) view.findViewById(R.id.txt_trouble_id);
            txtChoseDate = (TextView) view.findViewById(R.id.txt_dat_hen);
            txtDateString = (TextView) view.findViewById(R.id.txt_date);
            lnrCall = (LinearLayout) view.findViewById(R.id.lnr_call);
            lnrComplete = (LinearLayout) view.findViewById(R.id.lnr_complete);
        }

    }

    @Override
    public int getCount() {
        return this.notes.size();
    }

    @Nullable
    @Override
    public OrderModel getItem(int position) {
        return this.notes.get(position);
    }

    public interface GaraListAdapterDelegate{
        public void choseTime(OrderModel order);
        public void completeOrder(String orderId);
        public void callCustomer(String phoneNumer);
    }
}
