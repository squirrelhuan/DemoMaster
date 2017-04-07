package com.huan.demomaster.constances;

/**
 * 进行网络请求的url常量
 */
public class NetConstants {
	//	public static final String SERVER_URL = "http://192.168.0.204:8091";
	public static final String SERVER_URL = "http://192.168.0.103:8080/freedom";/*60.205.230.187 //192.168.1.111*/
	//public static final String SERVER_URL = "http://zhuoerruanjian.oicp.net:1001";

	public static final String BASE_URL = SERVER_URL + "/client/";

	// 登录
	public static final String LOGIN = BASE_URL + "user/login";
	public static final String Update = BASE_URL + "user/update";
	public static final String DemoNews = BASE_URL+"news/getNews";
	public static final String AddNews = BASE_URL+"news/addNews";
	public static final String getTopic = BASE_URL+"topic/getTopic";
	public static final String getTopicById = BASE_URL+"topic/getTopicById";
	public static final String getDemoPlate = BASE_URL+"plate/getPlate";
	public static final String getDemoListByPlateid = BASE_URL+"demo/getDemoListByPlateid";
	public static final String getDemoListByName = BASE_URL+"demo/getDemoListByName";
	public static final String getDemoById = BASE_URL+"demo/getDemoById";

	//上传文件
	public static final String UPLOAD_File = BASE_URL + "user/springUpload";

	/// 检查更新 
	public static final String GET_VERSION = BASE_URL + "/GetVersion";

	//获取人员列表
	public static final String Get_Admin_List_By_Dep = BASE_URL + "/GetAdminListByDep";
	//获取部门列表
	public static final String Get_Depart_List = BASE_URL + "/GetDepartList";
	//获取事件类型列表
	public static final String Get_Event_Type_List = BASE_URL + "/GetEnentTypeList";

	//获取部件类型列表
	/// <param name="ParentId">大类节点id,不传入则列出大类列表</param>
	/// <param name="CusID">客户id</param>
	public static final String Get_Parts_Type_List = BASE_URL + "/GetPartsTypeList";

	//获取区域列表
	public static final String Get_Area_List = BASE_URL + "/GetAreaList";

	//获取未处理事件列表
	public static final String Get_Pending_Event_List = BASE_URL + "/GetPendingEventList";

	//上传事件
	public static final String Up_Event = BASE_URL + "/UpEvent";
	
	//上传部件
	public static final String Up_Parts = BASE_URL + "/UpParts";


	//获取人员列表
	/// <param name="CusID">客户id</param>
	/// <param name="DepartId">部门id</param>
	public static final String Get_Admin_Gps_List = BASE_URL + "/GetAdminGpsList";

	/// 获取人员、事件、部件列表
	/// <param name="CusID">客户id</param>
	/// <param name="Type">查询类型：0人员1事件2部件</param>
	/// <param name="DepartId">部门id</param>
	//(string CusID, string Type, string DepartId, string BagTypeId, string SmallTypeId)
	public static final String Get_Admin_Event_Parts_List = BASE_URL + "/GetAdminEventPartsList";

	/// 上传坐标
    /// <param name="AdminID">操作人id</param>
    /// <param name="CusID">客户id</param>
    /// <param name="Gps">原始坐标</param>
    /// <param name="Google">谷歌坐标</param>
    /// <param name="Baidu">百度坐标</param>
    /// <param name="Gaode">高德坐标</param>
    /// <param name="Address">位置</param>
    /// <param name="Date">时间</param>
    /// <param name="isOut">是否越界（0否1是）</param>
	public static final String Up_Coordinate = BASE_URL + "/UpCoordinate1";

	/// 获取流程列表
	/// <param name="CusID">客户id</param>
	public static final String Get_Flow_List = BASE_URL + "/GetFlowList";

	/// 事件撤回到某一步
	//	AdminID: 操作人id
	//  id: 事件id
	//  flowid: 流程id
	//  CusID: 客户id
	//  State: 撤消到第几步
	//  Memo: 撤消原因
	//  Statename: 最新流程名称
	public static final String UndoEvent = BASE_URL + "/UndoEvent";

	/// 事件转交
	//  AdminID: 操作人id
	//  id: 事件id
	//  flowid: 流程id
	//  CusID:客户id
	public static final String Transfer = BASE_URL + "/Transfer";

	/// 事件作废
	//	AdminID: 操作人id
	//  id: 事件id
	//  flowid: 流程id
	public static final String Cancel = BASE_URL + "/Cancel";

	/// 事件完成
	//	AdminID: 操作人id
	//  id: 事件id
	//  flowid: 流程id
	public static final String Comlpete = BASE_URL + "/Comlpete";

	//<param name="CusID">客户Id</param>
	// <param name="AdminID">分配人id</param>
	// <param name="DepartOrAdminId">执行任务的部门或者人员id</param>
	//<param name="TaskContent">任务内容</param>
	// <param name="EventId">事件id</param>
	// <param name="State">事件流程id</param> 
	public static final String Add_Task = BASE_URL + "/AddTask";

	/// 根据事件id获取任务列表
	/// <param name="EventId">事件id</param>
	public static final String Get_Task_List_By_Event_Id = BASE_URL + "/GetTaskListByEventId";

	/// 获取我的任务列表
	/// <param name="AdminID">当前用户id</param>
	/// <param name="CusID">客户id</param>
	/// <param name="DepartId">部门Id</param>
	public static final String Get_Task_List = BASE_URL + "/GetTaskList";

	/// 删除任务
	//AdminID: 操作人id
	// id: 任务id
	//memo: 取消原因
	public static final String Delete = BASE_URL + "/Delete";

	///获取通知公告列表
	/// <param name="CusID">客户id</param>
	public static final String Get_In_Mail_List = BASE_URL + "/GetInMailList";
	
	///获取工作日报列表
	/// <param name="AdminID">用户ID</param>
	public static final String Get_WorkDaily_List = BASE_URL + "/GetWorkDailyList";
	
	///添加工作日报
	/// <param name="CusID">客户Id</param>
    /// <param name="UserID">当前登录人id</param>
    /// <param name="Title">标题</param>
    /// <param name="WorkContent">内容</param>
    /// <param name="ImageUrl">图片</param>
	public static final String Add_WorkDaily = BASE_URL + "/AddWorkDaily";

	/// 签到签退
	/// <param name="AdminID">用户ID</param>
	/// <param name="SignType">类型:签到/签退</param>
	/// <param name="CusId">客户id</param>
	/// <param name="Gps">原始坐标</param>
	/// <param name="Google">谷歌坐标</param>
	/// <param name="Baidu">百度坐标</param>
	/// <param name="Gaode">高德坐标</param>
	/// <param name="Address">位置</param>
	public static final String Admin_Sign = BASE_URL + "/AdminSign";

	/// 获取人员跟部门列表
	/// <param name="CusID">客户id</param>
	/// <param name="DepartId">部门id（不传值则默认取全部部门）</param>
	public static final String Get_Admin_Depart_List = BASE_URL + "/GetAdminDepartList";

	/// 人员统计（根据角色统计）
	/// <param name="CusID">客户id</param>
	public static final String Get_Admin_Statistics = BASE_URL + "/GetAdminStatistics";
	
	/// 获取签到签退列表
	/// <param name="CusID">客户id</param>
    /// <param name="DepartId">当前登录人的部门id</param>
	public static final String Get_Admin_Sign_List = BASE_URL + "/GetAdminSignList";
	
	/// 获取未签到未签退列表
	/// <param name="CusID">客户id</param>
    /// <param name="DepartId">当前登录人的部门id</param>
    /// <param name="SignType">类型：签到、签退</param>
	public static final String Get_No_Admin_Sign_List = BASE_URL + "/GetNoAdminSignList";

	/// 根据角色获取人员列表
	/// <param name="CusID">客户id</param>
	/// <param name="RoleId">角色id</param>
	public static final String Get_Admin_List_By_Role = BASE_URL + "/GetAdminListByRole";

	/// 开始任务
	/// <param name="AdminID">用户ID</param>
	/// <param name="id">任务id</param>
	/// <param name="memo">描述</param>
	///  <param name="image">图片</param>
	///  <param name="AudioUrl">音频</param>
	///  <param name="VideoUrl">视频</param>
	public static final String Start_Work = BASE_URL + "/StartWork";
	
	/// 更改密码
	/// <param name="AdminID">用户ID</param>
	/// <param name="OldPass ">旧密码</param>
	///  <param name="NewPass ">新密码</param>
	///  <param name="CusId">客户id</param>
	public static final String Change_PassWord = BASE_URL + "/ChangePassWord";
	
	/// 更改密码
	/// <param name="AdminID">用户ID</param>
    /// <param name="ImageUrl">头像地址（没有分号）</param>
    /// <param name="CusId">客户id</param>
	public static final String Update_Image = BASE_URL + "/UpdateImage";

	/// 获取人员详情
	/// <param name="AdminID">用户id</param>
	public static final String Get_Admin_Info = BASE_URL + "/GetAdminInfo";

	/// 获取事件详情
	/// <param name="id">id</param>
	public static final String Get_Event_Info = BASE_URL + "/GetEventInfo";

	/// 获取部件详情
	/// <param name="id">id</param>
	public static final String Get_Parts_Info = BASE_URL + "/GetPartsInfo";

	// 根据部门取考核项目
	/// <param name="DepartId">部门id</param>
	public static final String Get_Assess_Item_List = BASE_URL + "/GetAssessItemList";

	//	/ 添加考核
	/// <param name="CusID">客户Id</param>
	/// <param name="UserID">被考核人员id</param>
	/// <param name="AssessItemId">考核项id</param>
	/// <param name="Score">分数</param>
	/// <param name="AdminId">添加人id</param>
	/// <param name="AdminName">添加人姓名</param>
	/// <param name="Memo">备注</param>
	public static final String Add_Assess_Record = BASE_URL + "/AddAssessRecord";

}