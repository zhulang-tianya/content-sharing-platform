package com.content.common.constant;

public interface RedisConstants {
    
    String LOGIN_TOKEN_KEY = "login_tokens:";
    
    String CAPTCHA_CODE_KEY = "captcha_codes:";
    
    String SYS_CONFIG_KEY = "sys_config:";
    
    String SYS_DICT_KEY = "sys_dict:";
    
    String SYS_DICT_DATA_KEY = "sys_dict_data:";
    
    String SYS_MENU_KEY = "sys_menu:";
    
    String SYS_ROLE_KEY = "sys_role:";
    
    String SYS_USER_KEY = "sys_user:";
    
    String SYS_DEPT_KEY = "sys_dept:";
    
    String SYS_POST_KEY = "sys_post:";
    
    String SYS_JOB_KEY = "sys_job:";
    
    String SYS_LOG_KEY = "sys_log:";
    
    String SYS_ONLINE_KEY = "sys_online:";
    
    String SYS_NOTICE_KEY = "sys_notice:";
    
    String CONTENT_ARTICLE_KEY = "content_article:";
    
    String CONTENT_CATEGORY_KEY = "content_category:";
    
    String CONTENT_TAG_KEY = "content_tag:";
    
    String CONTENT_COMMENT_KEY = "content_comment:";
    
    String CONTENT_LIKE_KEY = "content_like:";
    
    String CONTENT_FAVORITE_KEY = "content_favorite:";
    
    String CONTENT_VIEW_KEY = "content_view:";
    
    String CONTENT_SHARE_KEY = "content_share:";
    
    String RATE_LIMIT_KEY = "rate_limit:";
    
    String BLACKLIST_KEY = "blacklist:";
    
    String WHITELIST_KEY = "whitelist:";
    
    String HOT_ARTICLE_KEY = "hot_article:";
    
    String RECOMMEND_ARTICLE_KEY = "recommend_article:";
    
    String SEARCH_HISTORY_KEY = "search_history:";
    
    String USER_SESSION_KEY = "user_session:";
    
    String ONLINE_USER_KEY = "online_user:";
    
    String LOCK_USER_KEY = "lock_user:";
    
    String DEFAULT_EXPIRE_SECONDS = "7200";
    
    String CAPTCHA_EXPIRE_SECONDS = "300";
    
    String ONLINE_USER_EXPIRE_SECONDS = "1800";
    
    String RATE_LIMIT_EXPIRE_SECONDS = "60";
}
