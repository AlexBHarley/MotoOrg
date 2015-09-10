package alex.msn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceAuthenticationProvider;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceUser;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;

import alex.msn.DBService;
import alex.msn.R;
import alex.msn.User;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alex on 10/09/15.
 */
public class LoginActivity extends Activity{

    @Bind(R.id.username) EditText username;
    @Bind(R.id.password) EditText password;
    DBService mClient;
    User mUser;
    MobileServiceTable<User> userTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        try {
            mClient = new DBService(this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.button_login)
    public void login(){
        ListenableFuture<MobileServiceUser> mLogin = mClient.login(MobileServiceAuthenticationProvider.Google);

        Futures.addCallback(mLogin, new FutureCallback<MobileServiceUser>() {
            @Override
            public void onSuccess(MobileServiceUser result) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("user_id", result.getUserId());
                startActivity(intent);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "There was an error signing in", Toast.LENGTH_LONG).show();
            }
        });
    }

}
