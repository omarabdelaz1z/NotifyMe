package Entities;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.bson.types.ObjectId;



@JsonIgnoreProperties(ignoreUnknown = true)
public class Template {
    private ObjectId _id;
    @NotNull
    @Size(min = 5, max = 50, message = "Template's type should be 5:50 letters.")
    private String type;

    @NotNull
    @Size(min = 10, max = 1000, message = "Template's content should be 10:1000 letters.")
    private String content;

    public Template(){}

    public Template(ObjectId id, String type, String content){
        this._id = id;
        this.type = type;
        this.content = content;
    }

    @JsonSerialize(using = ToStringSerializer.class)
    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> fetchParameters(){
        ArrayList<String> parameters = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\{(.*?)}");
        Matcher matcher = pattern.matcher(this.content);

        while(matcher.find())
            parameters.add(matcher.group(1));

        return parameters;
    }

    @Override
    public String toString() {
        return "Template{" +
                "_id=" + _id +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}