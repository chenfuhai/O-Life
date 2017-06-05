package com.olife.o_life.util;
/**
 * ÍøÂç·þÎñÆ÷IPÅäÖÃ
 * @author chenfuhai
 *
 */
public class NetConfig {
	public static String IP="http://10.100.1.82";
	public static String PORT="1433";
	public static String DateBaseName="olife";
	public static String USERNAME="sa";
	public static String PASSWORD="fdc159";
	public static String HTTPPORT="8080";

	//--------------------ACTION-----------------------------
	public static String TestAction = IP+":"+HTTPPORT+"/onmyoji/test.action";
	public static String SaveFeedBackAction = "";
	public static String FindAllFeedBackAction = "";
	public static String FindAllGoodsAction = "";
	public static String SaveonekeyResultRecordAction = "";
	public static String findOnkeyResultByUserIdAction = "";
	public static String findLastOnkeyResultByUserIdAction = "";
	public static String reportDiscussionAction = "";
	public static String findMessageAllDiscussions = "";
	public static String findUserAllDiscussionsAction = "";
	public static String deleteDiscussionAction = "";
	public static String findOnkeySharedByUserIdAction="";
	public static String shareUserResultAction = "";
	public static String findOthersSharedByLatLngAction="";
	public static String UploadUserImgAction="";
	public static String UpdateUserAction="";

	public static String ChangeUserPwdAction="";
	//--------------------TableName-----------------

	public static String UserTableName="ouser";
}
