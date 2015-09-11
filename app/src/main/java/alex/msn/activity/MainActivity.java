package alex.msn.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;

import alex.msn.DBService;
import alex.msn.PostComment;
import alex.msn.PostMaster;
import alex.msn.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends Activity{
    @Bind(R.id.submit_post_text) EditText newPostText;
    String mUserId;
    MobileServiceTable<PostComment> commentTable;
    MobileServiceTable<PostMaster> masterPostTable;
    DBService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        try {
            dbService = new DBService(this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mUserId = getIntent().getStringExtra("_userId");



    }

    @OnClick(R.id.submit_post_text)
    void uploadPost(){
        String text = newPostText.getText().toString();

    }


}
