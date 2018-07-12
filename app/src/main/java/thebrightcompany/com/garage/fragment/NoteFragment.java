package thebrightcompany.com.garage.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.fragment.note.NoteListAdapter;
import thebrightcompany.com.garage.fragment.note.model.NoteModel;
import thebrightcompany.com.garage.view.MainActivity;

public class NoteFragment extends Fragment {
    public ListView lstView;
    public NoteListAdapter adapter;
    public List<NoteModel> notes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity)getContext()).setTittle(getResources().getString(R.string.str_note_title));
        lstView = (ListView) getView().findViewById(R.id.lst_note);
        adapter = new NoteListAdapter(getContext(), R.layout.item_notice_customer );
        adapter.notes = this.notes;
        lstView.setAdapter(adapter);

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // go to detail
            }
        });
    }



}

