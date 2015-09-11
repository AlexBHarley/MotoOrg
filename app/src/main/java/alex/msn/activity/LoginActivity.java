package alex.msn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alex on 10/09/15.
 */
public class LoginActivity extends Activity{
    @BindString(R.string.mobile_service_url) String mobile_service_url;
    @BindString(R.string.app_key) String app_key;
    DBService dbService;
    MobileServiceClient mClient;

    User mUser;
    MobileServiceTable<User> userTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        try {
            dbService = new DBService(this);
            mClient = dbService.getClient();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.google)
    void authenticateGoogle(){
        final ListenableFuture<MobileServiceUser> mLogin = mClient.login(MobileServiceAuthenticationProvider.Google);

        Futures.addCallback(mLogin, new FutureCallback<MobileServiceUser>() {
            @Override
            public void onSuccess(MobileServiceUser result) {
                Toast.makeText(getApplicationContext(), "You're in", Toast.LENGTH_LONG).show();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra("_userId", result.getUserId());
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "There was an error signing in", Toast.LENGTH_LONG).show();
            }
        });
    }




}
