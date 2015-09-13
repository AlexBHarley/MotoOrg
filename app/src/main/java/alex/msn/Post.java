package alex.msn;

import java.math.BigInteger;

/**
 * Created by alex on 10/09/15.
 */
public class Post {
    private String createdBy;
    private String text;
    private String id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Post(String createdBy, String text){
        this.createdBy = createdBy;
        this.text = text;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
