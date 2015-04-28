package com.tony.miniblog.utils;

/**
 * Created by Tony on 2015/2/18 0018.
 */
public final class BlogConstants {

    public final static class MainBlogTitles{
        public final static int hotBlog = 0;
        public final static int friendsBlogs = 1;
        public final static int relatedMe = 2;
        public final static int personalCenter = 3;
    }

    /**
     * App Information on the Sina open platforms
     */
    public static final class APPInfo {
		public static final String APP_KEY = "384368582";
		public static final String APP_SECRET = "158f92929bd4925d8ef08385598c6e88";
		public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
		public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
				+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
				+ "follow_app_official_microblog," + "invitation_write";

	}

	/**
     * Oauth request urls.
	 * @author Tony
	 *
	 */
	public final class OAuthURLS {
		public static final String AUTHORIZE_URL = "https://api.weibo.com/oauth2/authorize?client_id="
				+ APPInfo.APP_KEY
				+ "&redirect_uri="
				+ APPInfo.REDIRECT_URL
				+ "&scope=all&display=web";
		public static final String ACCESSTOKEN_URL = "https://api.weibo.com/oauth2/access_token&client_id="
				+ APPInfo.APP_KEY
				+ "&client_secret="
				+ APPInfo.APP_KEY
				+ "&grant_type=authorization_code&redirect_uri="
				+ APPInfo.REDIRECT_URL + "&code=";
	}

    /**
     * define some request code.
     */
	public final class PersonalRequestCode {
		public static final String OAUTH_CODE = "com.tony.sina_oauth.GET_CODE";
		public static final int GET_CODE = 1;

		public static final String OAUTH_ACCESS_TOKEN = "com.tony.sina_oauth.ACCESS_TOKEN";
		public static final int ACCESS_TOKEN = 2;
	}

	/**
	 * App stored information
	 */
	public final class AppStoredDB{
		public static final String SP_NAME = "SP_MINIBLOG";
	}


	public final class BroadCastActions{
		public  static final String ACTION_NAME="UPDATE_USERINFO";
	}
}
