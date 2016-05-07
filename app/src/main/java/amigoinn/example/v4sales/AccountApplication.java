package amigoinn.example.v4sales;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import amigoinn.activerecordbase.ActiveRecordBase;
import amigoinn.activerecordbase.ActiveRecordException;
import amigoinn.activerecordbase.Database;
import amigoinn.activerecordbase.DatabaseBuilder;
import amigoinn.common.CommonUtils;
import amigoinn.common.Const;
import amigoinn.db_model.ClassCombInfo;
import amigoinn.db_model.ClientInfo;
import amigoinn.db_model.GenLookInfo;
import amigoinn.db_model.ProductInfo;
import amigoinn.db_model.UserInfo;
import amigoinn.modallist.Combo12;

/**
 * Created by Virag kuvadia on 24-04-2016.
 */
public class AccountApplication extends Application {
    private static AccountApplication _intance = null;
    private static ActiveRecordBase mDatabase;

    public static String getClient_code() {
        return client_code;
    }

    public static void setClient_code(String client_code) {
        AccountApplication.client_code = client_code;
    }

    private static String client_code;

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseBuilder builder = new DatabaseBuilder(Const.DATABASE_NAME);
        builder.addClass(UserInfo.class);
        builder.addClass(ClientInfo.class);
        builder.addClass(ProductInfo.class);

        builder.addClass(GenLookInfo.class);
        try {
            builder.addClass(ClassCombInfo.class);
        } catch (Exception ex) {
            Log.e("log_tag", ex.toString());
        }
        Database.setBuilder(builder);
        try {
            mDatabase = ActiveRecordBase.open(this, Const.DATABASE_NAME,
                    Const.DATABASE_VERSION);


        } catch (ActiveRecordException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    public AccountApplication() {
        _intance = this;
    }

    public static ActiveRecordBase Connection() {
        return mDatabase;
    }

    public static Context getContext() {
        return _intance;
    }

}
