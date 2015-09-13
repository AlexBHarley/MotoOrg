package alex.msn.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    public static final String SHAREDPREFFILE = "temp";
    public static final String USERIDPREF = "uid";
    public static final String TOKENPREF = "tkn";

    @BindString(R.string.mobile_service_url) String mobile_service_url;
    @BindString(R.string.app_key) String app_key;
    DBService dbService;
    MobileServiceClient mClient;
    ListenableFuture<MobileServiceUser> mLogin;

    User mUser;
    MobileServiceTable<User> userTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        if(loadUserTokenCache(mClient)) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.putExtra("_userId", mClient.getCurrentUser().getUserId());
        }

    }

    @OnClick(R.id.google)
    void authenticateGoogle(){
        mLogin = mClient.login(MobileServiceAuthenticationProvider.Google);

        Futures.addCallback(mLogin, new FutureCallback<MobileServiceUser>() {
            @Override
            public void onSuccess(MobileServiceUser result) {
                cacheUserToken(result);
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra("_userId", result.getUserId());
                startActivity(i);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "There was an error signing in", Toast.LENGTH_LONG).show();
            }
        });
    }



    private void cacheUserToken(MobileServiceUser user)
    {
        SharedPreferences prefs = getSharedPreferences(SHAREDPREFFILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USERIDPREF, user.getUserId());
        editor.putString(TOKENPREF, user.getAuthenticationToken());
        editor.commit();
    }

    private boolean loadUserTokenCache(MobileServiceClient client)
    {
        SharedPreferences prefs = getSharedPreferences(SHAREDPREFFILE, Context.MODE_PRIVATE);
        String userId = prefs.getString(USERIDPREF, "undefined");
        if (userId == "undefined")
            return false;
        String token = prefs.getString(TOKENPREF, "undefined");
        if (token == "undefined")
            return false;

        MobileServiceUser user = new MobileServiceUser(userId);
        user.setAuthenticationToken(token);
        client.setCurrentUser(user);

        return true;
    }




}
