package thebrightcompany.com.garage.fragment.note;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.fragment.note.model.NoteModel;

public class NoteListAdapter extends ArrayAdapter<NoteModel>{

    public List<NoteModel> notes;
    public NoteListAdapter(@NonNull Context context, int resource) {
        super(context, resource);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_notice_customer, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.txt_title_note);
            viewHolder.txtContent = (TextView) convertView.findViewById(R.id.txt_content_note);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final NoteModel note = getItem(position);
        viewHolder.txtTitle.setText(note.name);
        viewHolder.txtContent.setText(note.getContentText());
        return convertView;
    }

    public class ViewHolder {
        TextView txtTitle, txtContent;

    }

    @Override
    public int getCount() {
        return this.notes.size();
    }

    @Nullable
    @Override
    public NoteModel getItem(int position) {
        return this.notes.get(position);
    }
}
