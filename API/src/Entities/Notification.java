package Entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import Validation.Custom.TargetConstraint;
import org.bson.types.ObjectId;

import java.util.HashMap;


@TargetConstraint
public class Notification {
    private ObjectId _id;

    @NotBlank
    private String templateId;

    @NotBlank
    private String target;

    @NotBlank
    @Pattern(regexp = "^(SMS|MAIL)$", message = "A notification type must be MAIL or SMS.")
    private String type;

    private String state;

    private HashMap<@NotBlank String, @NotBlank String> parameters;

    public Notification() {
        parameters = new HashMap<>();
        this.state = "PENDING";
    }

    public Notification(ObjectId _id, String templateId, HashMap<String, String> parameters, String target){
        this._id = _id;
        this.templateId = templateId;
        this.parameters = parameters;
        this.target = target;
        this.state = "PENDING";
    }

    public Notification setTarget(String target) {
        this.target = target;
        return this;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setType (String type) {
        this.type = type;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public String getTarget() {
        return target;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getType() {
        return type;
    }

    @JsonSerialize(using= ToStringSerializer.class)
    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }
}