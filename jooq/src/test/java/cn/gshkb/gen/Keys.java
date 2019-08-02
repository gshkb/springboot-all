/*
 * This file is generated by jOOQ.
 */
package cn.gshkb.gen;


import cn.gshkb.gen.tables.*;
import cn.gshkb.gen.tables.records.*;
import org.jooq.*;
import org.jooq.impl.*;

import javax.annotation.*;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>test</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<EduWxUserInfoRecord, Integer> IDENTITY_EDU_WX_USER_INFO = Identities0.IDENTITY_EDU_WX_USER_INFO;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<EduWxUserInfoRecord> KEY_EDU_WX_USER_INFO_PRIMARY = UniqueKeys0.KEY_EDU_WX_USER_INFO_PRIMARY;
    public static final UniqueKey<EduWxUserInfoRecord> KEY_EDU_WX_USER_INFO_UN_OPEN_ID = UniqueKeys0.KEY_EDU_WX_USER_INFO_UN_OPEN_ID;
    public static final UniqueKey<EduWxUserInfoRecord> KEY_EDU_WX_USER_INFO_IDX_USER_ID = UniqueKeys0.KEY_EDU_WX_USER_INFO_IDX_USER_ID;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<EduWxUserInfoRecord, Integer> IDENTITY_EDU_WX_USER_INFO = Internal.createIdentity(EduWxUserInfo.EDU_WX_USER_INFO, EduWxUserInfo.EDU_WX_USER_INFO.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<EduWxUserInfoRecord> KEY_EDU_WX_USER_INFO_PRIMARY = Internal.createUniqueKey(EduWxUserInfo.EDU_WX_USER_INFO, "KEY_edu_wx_user_info_PRIMARY", EduWxUserInfo.EDU_WX_USER_INFO.ID);
        public static final UniqueKey<EduWxUserInfoRecord> KEY_EDU_WX_USER_INFO_UN_OPEN_ID = Internal.createUniqueKey(EduWxUserInfo.EDU_WX_USER_INFO, "KEY_edu_wx_user_info_un_open_id", EduWxUserInfo.EDU_WX_USER_INFO.OPEN_ID);
        public static final UniqueKey<EduWxUserInfoRecord> KEY_EDU_WX_USER_INFO_IDX_USER_ID = Internal.createUniqueKey(EduWxUserInfo.EDU_WX_USER_INFO, "KEY_edu_wx_user_info_idx_user_id", EduWxUserInfo.EDU_WX_USER_INFO.USER_ID);
    }
}