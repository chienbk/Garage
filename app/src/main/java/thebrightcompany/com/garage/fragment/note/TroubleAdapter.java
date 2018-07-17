package thebrightcompany.com.garage.fragment.note;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.fragment.note.model.NoteModel;
import thebrightcompany.com.garage.model.notificationfragment.TroubleModel;

public class TroubleAdapter extends ArrayAdapter<TroubleModel> {

    public List<TroubleModel> notes;
    public TroubleAdapter(@NonNull Context context, int resource, List<TroubleModel> troubleModels) {
        super(context, resource);
        this.notes = new ArrayList<>();
        this.notes.addAll(troubleModels);
        this.notes.addAll(troubleModels);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_trouble, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.txt_trouble_code);
            viewHolder.txtContent = (TextView) convertView.findViewById(R.id.txt_trouble_content);
            viewHolder.imgArrow = (ImageView) convertView.findViewById(R.id.img_trouble_arrow);
            viewHolder.lnrTitle = (LinearLayout)convertView.findViewById(R.id.lnr_trouble_title);
            viewHolder.lnrContent = (LinearLayout)convertView.findViewById(R.id.lnr_trouble_content);

            viewHolder.isshow = false;
            convertView.setTag(viewHolder);

            viewHolder.lnrTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewHolder.isshow = !viewHolder.isshow;
                    if(viewHolder.isshow){
                        viewHolder.lnrContent.setVisibility(View.VISIBLE);
                        viewHolder.imgArrow.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                    }else {
                        viewHolder.lnrContent.setVisibility(View.GONE);
                        viewHolder.imgArrow.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                    }

                    notifyDataSetChanged();

                }
            });

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final TroubleModel note = getItem(position);
        viewHolder.txtTitle.setText(note.Code);
        viewHolder.txtContent.setText(note.Name);
        if(viewHolder.isshow){
            viewHolder.lnrContent.setVisibility(View.VISIBLE);
            viewHolder.imgArrow.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
        }else {
            viewHolder.lnrContent.setVisibility(View.GONE);
            viewHolder.imgArrow.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        }

        return convertView;
    }

    public class ViewHolder {
        public TextView txtTitle, txtContent;
        public ImageView imgArrow;
        public LinearLayout lnrTitle, lnrContent;
        public boolean isshow;

    }

    @Override
    public int getCount() {
        if(this.notes == null) return 0;
        return this.notes.size();
    }

    @Nullable
    @Override
    public TroubleModel getItem(int position) {
        return this.notes.get(position);
    }
}

