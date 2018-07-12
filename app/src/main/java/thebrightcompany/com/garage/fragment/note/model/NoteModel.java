package thebrightcompany.com.garage.fragment.note.model;

import java.io.Serializable;

public class NoteModel implements Serializable{
    public String key;
    public String name;
    public String date;

    public String getContentText(){
        return name + "luc " + date;
    }
}
