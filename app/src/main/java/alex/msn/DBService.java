package alex.msn;

import android.content.Context;
import android.os.AsyncTask;

import com.google.common.io.Resources;
import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceAuthenticationProvider;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceUser;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;

import butterknife.BindString;

/**
 * Created by alex on 10/09/15.
 */
public class DBService{
    @BindString(R.string.mobile_service_url) String mobile_service_url;
    @BindString(R.string.app_key) String app_key;
    MobileServiceClient mClient;
    MobileServiceTable<PostComment> commentTable;
    MobileServiceTable<PostMaster> masterPostTable;
    MobileServiceTable<User> userTable;


    public DBService(Context context) throws MalformedURLException {
        mClient = new MobileServiceClient(
                mobile_service_url,
                app_key,
                context
        );

    }

    public void initialiseTables(){
        commentTable = mClient.getTable("comments", PostComment.class);
        masterPostTable = mClient.getTable("posts", PostMaster.class);
    }

    public MobileServiceTable initialiseUserTable(){
        userTable = mClient.getTable("users", User.class);
        return userTable;
    }

    public boolean post(final PostMaster master){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try{
                    masterPostTable.insert(master);
                } catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        };
        return true;
    }

    public ListenableFuture<MobileServiceUser> login(MobileServiceAuthenticationProvider type){
        return mClient.login(type);
    }






}
