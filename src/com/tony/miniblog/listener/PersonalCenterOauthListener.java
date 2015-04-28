package com.tony.miniblog.listener;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tony.miniblog.oauth.UsersAPI;
import com.tony.miniblog.utils.AccessTokenKeeper;
import com.tony.miniblog.utils.BlogConstants;

/**
 * Created by Tony on 2015/3/15 0015.
 */
public class PersonalCenterOauthListener implements WeiboAuthListener {

    public Context ctx;
    /**
     * AccessToken
     */
    private Oauth2AccessToken accessToken;

    /**
     * LOG'S TAG
     */
    private static final String TAG = "OAuth";
    private String isAuthorized;

    /**
     * 传入应用程序上下文
     *
     * @param context
     */
    public PersonalCenterOauthListener(Context context) {
        this.ctx = context;
        //isAuthorizedFinished = this.isAuthorized;
    }

    @Override
    public void onComplete(Bundle bundle) {
        accessToken = Oauth2AccessToken.parseAccessToken(bundle);
        if (accessToken.isSessionValid()) {
            Log.e(TAG, "OAuth has completed!");
            AccessTokenKeeper.writeAccessToken(ctx, accessToken);

            isAuthorized = "true";
            Log.e(TAG, "UID=" + accessToken.getUid() + "//token=" + accessToken.getToken() + "ctx=" + ctx.getClass());

            /** **/
            Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(ctx);
            UsersAPI usersAPI = new UsersAPI(ctx, BlogConstants.APPInfo.APP_KEY, accessToken);
            usersAPI.show(Long.parseLong(accessToken.getUid()), new PersonalCenterUserInfoRequestListener(ctx));
        } else {
            isAuthorized = "false";
            Log.e(TAG, "Authorization failed! you get a code ->" + bundle.getString("code"));
            Toast.makeText(ctx, "认证失败，请重试！", Toast.LENGTH_SHORT).show();
        }
        AccessTokenKeeper.writeFlags(ctx, "isAuthorized", isAuthorized, "false");
        Log.e("Main", "Oauth Listener isAuthorized is " + isAuthorized);
//        SharedPreferences pf = ctx.getSharedPreferences(BlogConstants.AppStoredDB.SP_NAME, ctx.MODE_APPEND);
//        SharedPreferences.Editor editor = pf.edit();
//        editor.putString("isAuthorized",isAuthorized);
//        editor.commit();
    }

    @Override
    public void onCancel() {
        Log.e(TAG, "The user has canceled the authorization!");
        Toast.makeText(this.ctx, "认证取消！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWeiboException(WeiboException e) {
        Log.e(TAG, "An exception has occurred during the authorization!");
        Toast.makeText(this.ctx, "认证发生异常，请重试或联系客服!", Toast.LENGTH_SHORT).show();
    }
}
