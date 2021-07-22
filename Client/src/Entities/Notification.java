package Entities;

import java.util.HashMap;

public class Notification {
    String id;
    String templateId;
    String target;
    String type;
    HashMap<String, String> parameters;
    String state;

    public Notification(){

    }

    public String getId() {
        return id;
    }

    public Notification setId(String id) {
        this.id = id;
        return this;
    }

    public String getTemplateId() {
        return templateId;
    }

    public Notification setTemplateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public String getTarget() {
        return target;
    }

    public Notification setTarget(String target) {
        this.target = target;
        return this;
    }

    public String getType() {
        return type;
    }

    public Notification setType(String type) {
        this.type = type;
        return this;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public Notification setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
        return this;
    }

    public String getState() {
        return state;
    }

    public Notification setState(String state) {
        this.state = state;
        return this;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", templateId='" + templateId + '\'' +
                ", target='" + target + '\'' +
                ", type='" + type + '\'' +
                ", parameters=" + parameters +
                ", state='" + state + '\'' +
                '}';
    }
}
