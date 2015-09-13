package alex.msn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceUser;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;

import alex.msn.DBService;
import alex.msn.PostAdapter;
import alex.msn.PostComment;
import alex.msn.PostMaster;
import alex.msn.R;
import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends Activity{
    @Bind(R.id.submit_post_text) EditText newPostText;
    @Bind(R.id.cardlist) RecyclerView mRecyclerView;
    String mUserId;
    DBService dbService;
    LinearLayoutManager mLinearLayoutManager;
    MobileServiceTable<PostComment> commentTable;
    MobileServiceTable<PostMaster> masterPostTable;
    MobileServiceClient mClient;
    ListenableFuture<MobileServiceUser> mLogin;
    @BindString(R.string.mobile_service_url) String mobile_service_url;
    @BindString(R.string.app_key) String app_key;
    String mCurrentUserId;
    PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentUserId = getIntent().getStringExtra("_userId");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        try {
            mClient = new MobileServiceClient(
                    mobile_service_url,
                    app_key,
                    this
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        commentTable = mClient.getTable("comments", PostComment.class);
        masterPostTable = mClient.getTable("posts", PostMaster.class);

        //PostAdapter adapter
        //mRecyclerView.setAdapter();

    }

    @OnClick(R.id.submit_post_text)
    public void submitPost(){
        final PostMaster postMaster = new PostMaster(newPostText.getText().toString(),
                                                mCurrentUserId);

        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                masterPostTable.insert(postMaster);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                return null;
            }
        }.execute();
    }


}
