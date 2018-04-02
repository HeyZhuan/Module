package com.Fee.system.user.service;

import java.util.List;

import com.Fee.system.user.domain.User;

public interface UserService {

	/**
	 * 添加用户
	 * @param user
	 */
	public User addUser(User user);
	
	/**
	 * 获取用户业务角色
	 * @param obj
	 * @return
	 */
	public List<Integer> getUserRoleIds(Object obj);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @param isPawd 是否修改密码
	 */
	public void updateUser(User user,boolean isPawd);
	
	/**
	 * 更新用户洋葱ID
	 * @param loginName
	 * @param eventId
	 */
	public void updateUserYangcongEventId(String loginName,String eventId);
	
	/**
	 * 更新用户洋葱ID
	 * @param yangId
	 * @param uid
	 * @return
	 */
	public int updateUserYangcongId(String yangId,int uid);
	
	/**
	 * 查询用户
	 * @param loginName
	 * @return
	 */
	public User getUser(String loginName,boolean isValid);
	
	/**
	 * 根据eventId获取用户信息
	 * @param loginName
	 * @return
	 */
	public User getUserByYangcongEventId(String eventId);
	
	/**
	 * 校验旧密码是否正确
	 * @param user
	 * @param oldPaw
	 * @param newPaw
	 * @return
	 */
	public boolean checkPawd(User user,String oldPaw);
	
	/**
	 * 添加用户角色
	 */
	public void addUserRole(int userId,int roleId);
	
	/**
	 * 修改用户角色
	 */
	public void updateUserRole(final int userId,final List<Integer> newRoleList);
	
	/**
	 * 删除用户角色
	 */
	public void deleteUserRole(int userId);
	
}
