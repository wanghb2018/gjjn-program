package com.mochen.utils;

public class Constant {
	public static final String CACHE_YEAR = "cache_year";
	public static final String CACHE_HOUR = "cache_hour";

	public static final String SESSION_USER_ID = "session_user_id";
	public static final String SESSION_ROLE_ID = "session_role_id";

	public static final int SUCCESS = 0;
	public static final int FAILED = -1;
	public static final int OTHER = 1;
	public static final int OTHER2 = 2;

	public static final String CACHE_ALL_ROLESJ = "'all_rolesj'";
	public static final String CACHE_ALL_GAMEMAP = "'all_gameMap'";
	public static final String CACHE_ALL_JIANNIANG = "'all_jianniang'";
	public static final String CACHE_ALL_JIANNIANG_MAP = "'all_jianniang_map'";
	public static final String CACHE_ALL_KEYANSJ = "'all_keyansj'";
	public static final String CACHE_ALL_JUNXIAN = "'all_junxian'";
	public static final String CACHE_ALL_NOTICE = "'all_notice'";
	public static final String CACHE_LATEST_NOTICE = "'latest_notice'";

	public static final int JN_ZBL = 1;
	public static final int JN_JBL = 2;

	public enum InitialJN {
		LAFEI("jnimg1", 19), BIAOQIANG("jnimg2", 101), Z23("jnimg3", 236);

		private String imgName;
		private Integer jnId;

		private InitialJN(String imgName, Integer jnId) {
			this.imgName = imgName;
			this.jnId = jnId;
		}

		public String getImgName() {
			return imgName;
		}

		public Integer getJnId() {
			return jnId;
		}

		public static Integer getIdByImg(String img) {
			for (InitialJN item : InitialJN.values()) {
				if (item.getImgName().equals(img)) {
					return item.getJnId();
				}
			}
			return LAFEI.getJnId();
		}
	}
}
