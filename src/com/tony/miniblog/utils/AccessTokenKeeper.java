/*
 * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tony.miniblog.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.tony.miniblog.model.User;

/**
 * 该类定义了微博授权时所需要的参数。
 *
 * @author SINA
 * @since 2013-10-07
 */
public class AccessTokenKeeper {

    private static final String KEY_UID = "uid";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_EXPIRES_IN = "expires_in";

    /**
     * 保存 Token 对象到 SharedPreferences。
     *
     * @param context 应用程序上下文环境
     * @param token   Token 对象
     */
    public static void writeAccessToken(Context context, Oauth2AccessToken token) {
        if (null == context || null == token) {
            return;
        }

        SharedPreferences pref = context.getSharedPreferences(BlogConstants.AppStoredDB.SP_NAME, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putString(KEY_UID, token.getUid());
        editor.putString(KEY_ACCESS_TOKEN, token.getToken());
        editor.putLong(KEY_EXPIRES_IN, token.getExpiresTime());
        editor.commit();
    }

    /**
     * 从 SharedPreferences 读取 Token 信息。
     *
     * @param context 应用程序上下文环境
     * @return 返回 Token 对象
     */
    public static Oauth2AccessToken readAccessToken(Context context) {
        if (null == context) {
            return null;
        }

        Oauth2AccessToken token = new Oauth2AccessToken();
        SharedPreferences pref = context.getSharedPreferences(BlogConstants.AppStoredDB.SP_NAME, Context.MODE_APPEND);
        token.setUid(pref.getString(KEY_UID, ""));
        token.setToken(pref.getString(KEY_ACCESS_TOKEN, ""));
        token.setExpiresTime(pref.getLong(KEY_EXPIRES_IN, 0));
        return token;
    }

    /**
     * 清空 SharedPreferences 中 Token信息。
     *
     * @param context 应用程序上下文环境
     */
    public static void clear(Context context) {
        if (null == context) {
            return;
        }

        SharedPreferences pref = context.getSharedPreferences(BlogConstants.AppStoredDB.SP_NAME, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 将获取到的用户信息保存到SharedPreferences中.
     *
     * @param ctx   应用程序上下文
     * @param uInfo 待写入用户对象
     */
    public static void writeUserInfo2Disk(Context ctx, User uInfo) {
        if (null == ctx) {
            Log.e("Main","ctx is null");
            return;
        }
        Log.e("Main","uInfo is null?>>" + (null == uInfo)+"uInfo screen_name="+uInfo.screen_name);
        SharedPreferences pref = ctx.getSharedPreferences(BlogConstants.AppStoredDB.SP_NAME, ctx.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putString("id", uInfo.id);
        editor.putString("gender", uInfo.gender);
        editor.putString("profile_url", uInfo.profile_url);
        editor.putString("profile_image_url", uInfo.profile_image_url);
        editor.putString("avatar_hd", uInfo.avatar_hd);
        editor.putString("avatar_large", uInfo.avatar_large);
        editor.putString("block_word", uInfo.block_word);
        editor.putString("created_at", uInfo.created_at);
        editor.putString("description", uInfo.description);
        editor.putString("domain", uInfo.domain);
        editor.putString("location", uInfo.location);
        editor.putString("mbrank", uInfo.mbrank);
        editor.putString("screen_name", uInfo.screen_name);
        editor.putString("url", uInfo.url);
        editor.putString("name", uInfo.name);
        //editor.putString("province",uInfo.province);
        //editor.putString("",uInfo.city);
        editor.putInt("online_status", uInfo.online_status);
        editor.putBoolean("verified", uInfo.verified);

        //commit to database.
        editor.commit();
    }

    /**
     * 写入标志
     * @param ctx
     * @param key
     * @param value
     */
    public static void writeFlags(Context ctx,String key, String value, String defaultValue) {
        SharedPreferences pf = ctx.getSharedPreferences(BlogConstants.AppStoredDB.SP_NAME,ctx.MODE_APPEND);
        Editor editor = pf.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 读取标志
     * @param ctx
     * @param key
     * @return
     */
    public static Object readFlags(Context ctx,String key) {
        SharedPreferences pf = ctx.getSharedPreferences(BlogConstants.AppStoredDB.SP_NAME,ctx.MODE_APPEND);
        return pf.getString(key,"");
    }
}
