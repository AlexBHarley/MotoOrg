package alex.msn;

/**
 * Created by alex on 10/09/15.
 */
public class PostMaster{
    /*
    public PostMaster(String createdBy, String text){
        super(createdBy, text);
    }
    */

    private String postername;
    private String text;
    private String id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedBy() {
        return postername;
    }

    public void setCreatedBy(String createdBy) {
        this.postername = createdBy;
    }

    public PostMaster(String createdBy, String text){
        this.postername = createdBy;
        this.text = text;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
