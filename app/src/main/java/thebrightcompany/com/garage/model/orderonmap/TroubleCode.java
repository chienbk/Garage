package thebrightcompany.com.garage.model.orderonmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TroubleCode implements Serializable{

    @SerializedName("Id")
    private int Id;
    @SerializedName("Link")
    private String Link;
    @SerializedName("Code")
    private String Code;
    @SerializedName("Brand")
    private String Brand;
    @SerializedName("Year")
    private String Year;
    @SerializedName("Model")
    private String Model;
    @SerializedName("Level")
    private String Level;
    @SerializedName("Name")
    private String Name;
    @SerializedName("ImportanceLevel")
    private String ImportanceLevel;
    @SerializedName("DifficultyLevel")
    private String DifficultyLevel;
    @SerializedName("PossibleCause")
    private String PossibleCause;
    @SerializedName("TechNotes")
    private String TechNotes;
    @SerializedName("CodeDetected")
    private String CodeDetected;
    @SerializedName("PossibleSymptoms")
    private String PossibleSymptoms;
    @SerializedName("Description")
    private String Description;
    @SerializedName("Status")
    private int Status;
    @SerializedName("Summary_En")
    private String Summary_En;
    @SerializedName("Summary_Vi")
    private String Summary_Vi;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImportanceLevel() {
        return ImportanceLevel;
    }

    public void setImportanceLevel(String importanceLevel) {
        ImportanceLevel = importanceLevel;
    }

    public String getDifficultyLevel() {
        return DifficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        DifficultyLevel = difficultyLevel;
    }

    public String getPossibleCause() {
        return PossibleCause;
    }

    public void setPossibleCause(String possibleCause) {
        PossibleCause = possibleCause;
    }

    public String getTechNotes() {
        return TechNotes;
    }

    public void setTechNotes(String techNotes) {
        TechNotes = techNotes;
    }

    public String getCodeDetected() {
        return CodeDetected;
    }

    public void setCodeDetected(String codeDetected) {
        CodeDetected = codeDetected;
    }

    public String getPossibleSymptoms() {
        return PossibleSymptoms;
    }

    public void setPossibleSymptoms(String possibleSymptoms) {
        PossibleSymptoms = possibleSymptoms;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getSummary_En() {
        return Summary_En;
    }

    public void setSummary_En(String summary_En) {
        Summary_En = summary_En;
    }

    public String getSummary_Vi() {
        return Summary_Vi;
    }

    public void setSummary_Vi(String summary_Vi) {
        Summary_Vi = summary_Vi;
    }
}
