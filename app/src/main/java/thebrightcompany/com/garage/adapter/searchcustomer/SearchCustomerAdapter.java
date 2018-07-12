package thebrightcompany.com.garage.adapter.searchcustomer;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.model.searchcustomer.Customer;


/**
 * Created by ChienNv9 on 1/24/2018.
 */

public class SearchCustomerAdapter extends RecyclerView.Adapter<SearchCustomerAdapter.MyViewHolder>{

    public static final String TAG = SearchCustomerAdapter.class.getSimpleName();

    private List<Customer> mList;
    private ItemSearchCustomerOnClickListener mListener;
    private LatLng latLng;

    public SearchCustomerAdapter(List<Customer> list, LatLng latLng, ItemSearchCustomerOnClickListener listener) {
        this.mList = list;
        this.latLng = latLng;
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_customer, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Customer customer = mList.get(position);

       /* try {
            holder.txt_nameOfGarage.setText(garageOnMap.getName());
            holder.txt_distance.setText(Utils.calculationByDistance(latLng, new LatLng(garageOnMap.getLat(), garageOnMap.getLng())) + " km");
            holder.txt_addressOfGarage.setText(garageOnMap.getAddress());
        }catch (NullPointerException e){
            Log.d(TAG, e.toString());
        }

        holder.layout_search_garage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClickListener(position, garageOnMap);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_nameOfGarage, txt_distance, txt_addressOfGarage;
        public LinearLayout layout_search_garage;

        public MyViewHolder(View view) {
            super(view);

            layout_search_garage = (LinearLayout) view.findViewById(R.id.layout_search_garage);

            txt_nameOfGarage = (TextView) view.findViewById(R.id.txt_nameOfGarage);
            txt_distance = (TextView) view.findViewById(R.id.txt_distance);
            txt_addressOfGarage = (TextView) view.findViewById(R.id.txt_addressOfGarage);
        }
    }

    public void notifyDataSetChanged(List<Customer> customers) {
        if(mList != null){
            mList.clear();
            this.mList.addAll(customers);
            this.notifyDataSetChanged();
        }
    }

    public void clearDataSetChanged() {
        this.mList.clear();
        this.notifyDataSetChanged();
    }

    public void notifyItemChange(int position, Customer customer){
        mList.remove(position);
        notifyItemRemoved(position);
        mList.add(position, customer);
        notifyItemInserted(position);
    }
}
