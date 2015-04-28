package com.tony.miniblog.listener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.tony.miniblog.model.User;
import com.tony.miniblog.utils.AccessTokenKeeper;
import com.tony.miniblog.utils.BlogConstants;

/**
 * Get user info by oauth tokens.
 * Created by Tony on 2015/3/15 0015.
 */
public class PersonalCenterUserInfoRequestListener implements RequestListener {

    private User user;
    private SharedPreferences userInfoPreferences;
    private Context ctx;

    private String isUserInfoFinished;

    /**
     * Get parent context to build <b>SharedPreference</b>
     *
     * @param ctx
     */
    public PersonalCenterUserInfoRequestListener(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onComplete(String s) {
        Log.e("Main", "Oauth request have completed!");
        if (!TextUtils.isEmpty(s)) {
            user = User.parse(s);
            AccessTokenKeeper.writeUserInfo2Disk(ctx, user);
            Intent data = new Intent(BlogConstants.BroadCastActions.ACTION_NAME);
            data.putExtra("screen_name",user.screen_name);
            data.putExtra("profile_url",user.profile_url);
            data.putExtra("avatar_large",user.avatar_large);
            ctx.sendBroadcast(data);
        } else {
            Toast.makeText(ctx, "Oauth failed,please try it later!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onWeiboException(WeiboException e) {

    }
}
