package Entities;

public class PreparedNotification {
    String text;
    String target;

    public PreparedNotification(String text, String target){
        this.text = text;
        this.target = target;
    }

    @Override
    public String toString() {
        return "PreparedNotification{" +
                "text='" + text + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}
