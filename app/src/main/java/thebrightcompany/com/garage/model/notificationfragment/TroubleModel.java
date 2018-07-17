package thebrightcompany.com.garage.model.notificationfragment;

import java.io.Serializable;

import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject(fieldDetectionPolicy =JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
public class TroubleModel implements Serializable{

    public String Id;
    public String Link;
    public String Code;
    public String Brand;
    public String Year;
    public String Model;
    public String Level;
    public String Name;
    public String ImportanceLevel;
    public String DifficultyLevel;
    public String PossibleCause;
    public String TechNotes;
    public String CodeDetected;
    public String PossibleSymptoms;
    public String Description;
    public String Status;
    public String Summary_En;
    public String Summary_Vi;
}
