package com.content.common.constant;

public interface SecurityConstants {
    
    String AUTHENTICATION_HEADER = "Authorization";
    
    String AUTHENTICATION_SCHEME = "Bearer ";
    
    String AUTHENTICATION_TOKEN = "token";
    
    String AUTHENTICATION_USERNAME = "username";
    
    String AUTHENTICATION_PASSWORD = "password";
    
    String AUTHENTICATION_CODE = "code";
    
    String AUTHENTICATION_UUID = "uuid";
    
    String AUTHENTICATION_REMEMBER = "remember";
    
    String AUTHENTICATION_GRANT_TYPE = "grant_type";
    
    String AUTHENTICATION_SCOPE = "scope";
    
    String AUTHENTICATION_CLIENT_ID = "client_id";
    
    String AUTHENTICATION_CLIENT_SECRET = "client_secret";
    
    String AUTHENTICATION_REFRESH_TOKEN = "refresh_token";
    
    String AUTHENTICATION_ACCESS_TOKEN = "access_token";
    
    String AUTHENTICATION_EXPIRES_IN = "expires_in";
    
    String AUTHENTICATION_REFRESH_EXPIRES_IN = "refresh_expires_in";
    
    String AUTHENTICATION_TOKEN_TYPE = "token_type";
    
    String AUTHENTICATION_SCOPE_SEPARATOR = " ";
    
    String LOGIN_URL = "/auth/login";
    
    String LOGOUT_URL = "/auth/logout";
    
    String REGISTER_URL = "/auth/register";
    
    String REFRESH_URL = "/auth/refresh";
    
    String VERIFY_URL = "/auth/verify";
    
    String CAPTCHA_URL = "/auth/captcha";
    
    String RESET_PASSWORD_URL = "/auth/reset-password";
    
    String CHANGE_PASSWORD_URL = "/auth/change-password";
    
    String PROFILE_URL = "/auth/profile";
    
    String LOGIN_USER_KEY = "loginUser";
    
    String CURRENT_USER_KEY = "currentUser";
    
    String CURRENT_USERNAME_KEY = "currentUsername";
    
    String CURRENT_USER_ID_KEY = "currentUserId";
    
    String CURRENT_ROLES_KEY = "currentRoles";
    
    String CURRENT_PERMISSIONS_KEY = "currentPermissions";
    
    String ANONYMOUS_USER = "anonymousUser";
    
    String ANONYMOUS_ROLE = "ROLE_ANONYMOUS";
    
    String ADMIN_ROLE = "ROLE_ADMIN";
    
    String USER_ROLE = "ROLE_USER";
    
    String SUPER_ADMIN_ROLE = "ROLE_SUPER_ADMIN";
    
    String PERMISSION_PREFIX = "ROLE_";
    
    String PERMISSION_SEPARATOR = ":";
    
    String PERMISSION_ALL = "*";
    
    String PERMISSION_READ = "read";
    
    String PERMISSION_WRITE = "write";
    
    String PERMISSION_UPDATE = "update";
    
    String PERMISSION_DELETE = "delete";
    
    String PERMISSION_CREATE = "create";
    
    String PERMISSION_EXPORT = "export";
    
    String PERMISSION_IMPORT = "import";
    
    String PERMISSION_GRANT = "grant";
    
    String PERMISSION_RESET = "reset";
    
    String PERMISSION_ENABLE = "enable";
    
    String PERMISSION_DISABLE = "disable";
    
    String PERMISSION_VIEW = "view";
    
    String PERMISSION_EDIT = "edit";
    
    String PERMISSION_ADD = "add";
    
    String PERMISSION_REMOVE = "remove";
    
    String PERMISSION_QUERY = "query";
    
    String PERMISSION_LIST = "list";
    
    String PERMISSION_DETAIL = "detail";
    
    String PERMISSION_STATISTICS = "statistics";
    
    String PERMISSION_ANALYSIS = "analysis";
    
    String PERMISSION_DASHBOARD = "dashboard";
    
    String PERMISSION_CONFIG = "config";
    
    String PERMISSION_SETTING = "setting";
    
    String PERMISSION_MANAGE = "manage";
    
    String PERMISSION_OPERATE = "operate";
    
    String PERMISSION_EXECUTE = "execute";
    
    String PERMISSION_TEST = "test";
    
    String PERMISSION_DEBUG = "debug";
    
    String PERMISSION_MONITOR = "monitor";
    
    String PERMISSION_LOG = "log";
    
    String PERMISSION_AUDIT = "audit";
    
    String PERMISSION_REPORT = "report";
    
    String PERMISSION_ALARM = "alarm";
    
    String PERMISSION_NOTIFY = "notify";
    
    String PERMISSION_MESSAGE = "message";
    
    String PERMISSION_CHAT = "chat";
    
    String PERMISSION_CALL = "call";
    
    String PERMISSION_MEETING = "meeting";
    
    String PERMISSION_FILE = "file";
    
    String PERMISSION_UPLOAD = "upload";
    
    String PERMISSION_DOWNLOAD = "download";
    
    String PERMISSION_SHARE = "share";
    
    String PERMISSION_PRINT = "print";
    
    String PERMISSION_EXPORT_EXCEL = "exportExcel";
    
    String PERMISSION_EXPORT_PDF = "exportPdf";
    
    String PERMISSION_IMPORT_EXCEL = "importExcel";
    
    String PERMISSION_SYNCHRONIZE = "synchronize";
    
    String PERMISSION_BACKUP = "backup";
    
    String PERMISSION_RESTORE = "restore";
    
    String PERMISSION_CLEAN = "clean";
    
    String PERMISSION_CLEAR = "clear";
    
    String PERMISSION_INITIALIZE = "initialize";
    
    String PERMISSION_DEPLOY = "deploy";
    
    String PERMISSION_PUBLISH = "publish";
    
    String PERMISSION_ROLLBACK = "rollback";
    
    String PERMISSION_STOP = "stop";
    
    String PERMISSION_START = "start";
    
    String PERMISSION_RESTART = "restart";
    
    String PERMISSION_PAUSE = "pause";
    
    String PERMISSION_RESUME = "resume";
    
    String PERMISSION_EXPAND = "expand";
    
    String PERMISSION_SHRINK = "shrink";
    
    String PERMISSION_MIGRATE = "migrate";
    
    String PERMISSION_UPGRADE = "upgrade";
    
    String PERMISSION_DOWNGRADE = "downgrade";
    
    String PERMISSION_ACTIVATE = "activate";
    
    String PERMISSION_DEACTIVATE = "deactivate";
    
    String PERMISSION_BLOCK = "block";
    
    String PERMISSION_UNBLOCK = "unblock";
    
    String PERMISSION_LOCK = "lock";
    
    String PERMISSION_UNLOCK = "unlock";
    
    String PERMISSION_SUSPEND = "suspend";
    
    String PERMISSION_RESUME_SUSPENDED = "resumeSuspended";
    
    String PERMISSION_REVOKE = "revoke";
    
    String PERMISSION_GRANT_PERMISSION = "grantPermission";
    
    String PERMISSION_REVOKE_PERMISSION = "revokePermission";
    
    String PERMISSION_ASSIGN_ROLE = "assignRole";
    
    String PERMISSION_REMOVE_ROLE = "removeRole";
    
    String PERMISSION_ADD_MEMBER = "addMember";
    
    String PERMISSION_REMOVE_MEMBER = "removeMember";
    
    String PERMISSION_INVITE_MEMBER = "inviteMember";
    
    String PERMISSION_KICK_MEMBER = "kickMember";
    
    String PERMISSION_PROMOTE_MEMBER = "promoteMember";
    
    String PERMISSION_DEMOTE_MEMBER = "demoteMember";
    
    String PERMISSION_TRANSFER_OWNERSHIP = "transferOwnership";
    
    String PERMISSION_CREATE_TEAM = "createTeam";
    
    String PERMISSION_DELETE_TEAM = "deleteTeam";
    
    String PERMISSION_UPDATE_TEAM = "updateTeam";
    
    String PERMISSION_MANAGE_TEAM = "manageTeam";
    
    String PERMISSION_JOIN_TEAM = "joinTeam";
    
    String PERMISSION_LEAVE_TEAM = "leaveTeam";
    
    String PERMISSION_INVITE_TO_TEAM = "inviteToTeam";
    
    String PERMISSION_KICK_FROM_TEAM = "kickFromTeam";
    
    String PERMISSION_PROMOTE_IN_TEAM = "promoteInTeam";
    
    String PERMISSION_DEMOTE_IN_TEAM = "demoteInTeam";
    
    String PERMISSION_TRANSFER_TEAM_OWNERSHIP = "transferTeamOwnership";
    /**
     * 用户权限缓存键
     */
    String USER_PERMISSION_CACHE_KEY = "user:permission:";

    /**
     * 用户角色缓存键
     */
    String USER_ROLE_CACHE_KEY = "user:role:";

    /**
     * Token过期时间（秒），默认2小时
     */
    long TOKEN_EXPIRE_TIME = 7200L;

    /**
     * 刷新Token过期时间（秒），默认7天
     */
    long REFRESH_TOKEN_EXPIRE_TIME = 604800L;
}
