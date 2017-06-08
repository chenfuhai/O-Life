package com.olife.o_life.util;
/**
 * 网络服务器IP配置
 * @author chenfuhai
 *
 */
public class NetConfig {
	public static String IP="http://10.100.2.43";
	public static String PORT="1433";
	public static String DateBaseName="olife";
	public static String USERNAME="sa";
	public static String PASSWORD="fdc159";
	public static String HTTPPORT="8080";
	public static String ProjectName = "olifeManager";

	public static String PreUrl = IP+":"+HTTPPORT+"/"+ProjectName;

    //--------------------ACTION-----------------------------
	public static String TestAction = IP+":"+HTTPPORT+"/onmyoji/test.action";
	public static String SaveFeedBackAction = PreUrl+"/FeedBack_submitAction.action";
	public static String FindAllFeedBackAction = PreUrl+"/FeedBack_queryAction.action";
	public static String FindAllGoodsAction =PreUrl+ "/Goods_queryAction.action";
	public static String SaveonekeyResultRecordAction = PreUrl+"/TestResult_saveAction.action";
	public static String findOnkeyResultByUserIdAction = PreUrl+"/TestResult_queryByIdAction.action";
	public static String findLastOnkeyResultByUserIdAction =PreUrl+ "/TestLastResult_queryByIdAction.action";
	public static String reportDiscussionAction = PreUrl+"/Comment_submitAction.action";
	public static String findMessageAllDiscussions = PreUrl+"/Comment_queryAction.action";
	public static String findUserAllDiscussionsAction =PreUrl+ "/Comment_queryAction.action";
	public static String deleteDiscussionAction = PreUrl+"/Comment_deleteAction.action";
	public static String findOnkeySharedByUserIdAction=PreUrl+"/ShareMessage_queryByIdAction.action";
	public static String shareUserResultAction = PreUrl+"/ShareMessage_saveAction.action";
	public static String findOthersSharedByLatLngAction=PreUrl+"/ShareMessage_queryByLaInAction.action";
	public static String UploadUserImgAction=PreUrl+"/UserHead_updateAction.action";
	public static String UpdateUserAction=PreUrl+"/User_updateAction.action";

	public static String ChangeUserPwdAction=PreUrl+"/User_changePwdAction.action";
	public static String UserLoginAction = PreUrl+"/User_loginByPwdAction.action";
	public static String UserSignUpAction = PreUrl+"/User_resisterByPwdAction.action";
	//--------------------TableName-----------------

	public static String UserTableName="ouser";
}
