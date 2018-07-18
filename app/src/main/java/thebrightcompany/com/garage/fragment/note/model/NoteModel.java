package thebrightcompany.com.garage.fragment.note.model;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.io.Serializable;

@JsonObject(fieldDetectionPolicy =JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
public class NoteModel implements Serializable {
    public int id;
    public String order_id;
    public String garage_id;
    public String type;
    public String content;
    public String create_date;
}
