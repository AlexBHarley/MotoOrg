package alex.msn;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alex on 13/09/15.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<PostMaster> postList;

    public PostAdapter(List<PostMaster> postList){
        this.postList = postList;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                parent.getContext()).
                inflate(R.layout.view_post, parent, false);

        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        PostMaster postMaster = postList.get(position);
        holder.postText.setText(postMaster.getText());
        holder.posterName.setText(postMaster.getCreatedBy());
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class PostViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.poster_name) TextView posterName;
        @Bind(R.id.poster_image) CircleImageView posterImage;
        @Bind(R.id.post_text) TextView postText;

        public PostViewHolder(View itemView) {
            super(itemView);
        }
    }
}
