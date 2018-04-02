package com.Fee.system.user.service;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fee.common.db.CommonDao;
import com.Fee.common.enums.StatusType;
import com.Fee.common.nutz.NutzType;
import com.Fee.common.security.Digests;
import com.Fee.common.security.Encodes;
import com.Fee.common.service.BaseService;
import com.Fee.common.time.TimeUtils;
import com.Fee.system.user.domain.User;
import com.Fee.system.user.domain.UserRole;

@Service
public class UserServiceImpl implements UserService {
	
	/**加密方法*/
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;	//盐长度

	@Autowired
	private CommonDao commonDao;
	@Autowired
	private BaseService baseService;
	
	
	@Override
	public User addUser(User user) {
		user.setStatus(StatusType.NORMAL.opt);
		entryptPassword(user);
		return commonDao.insert(user);
	}
	
	@Override
	public List<Integer> getUserRoleIds(Object obj) {
		List<UserRole> userRole = commonDao.query(UserRole.class, Cnd.where("userId", NutzType.IN.opt, obj));
		List<Integer> roleIds = new ArrayList<Integer>();
		for(UserRole role : userRole){
			roleIds.add(role.getRoleId());
		}
		return roleIds;
	}

	@Override
	public void updateUser(User user, boolean isPawd) {
		if(isPawd){
			entryptPassword(user);
			commonDao.update(User.class, Chain.make("salt", user.getSalt())
					.add("password", user.getPassword()), Cnd.where("id", NutzType.EQ.opt, user.getId()));
		}else{
			commonDao.update(User.class, Chain.make("name", user.getName())
					.add("email", user.getEmail())
					.add("phone", user.getPhone())
					.add("status", user.getStatus()), Cnd.where("id", NutzType.EQ.opt, user.getId()));
		}
	}

	@Override
	public User getUser(String loginName, boolean isValid) {
		Cnd cnd = Cnd.where("account", NutzType.EQ.opt, loginName);
		if(isValid){
			cnd.and("status", NutzType.EQ.opt, 1);
		}
		User user = commonDao.fetch(User.class, cnd);
		return user;
	}
	
	@Override
	public void updateUserYangcongEventId(String loginName, String eventId) {
		commonDao.update(User.class,Chain.make("yangEventId", eventId),Cnd.where("account", NutzType.EQ.opt, loginName));
	}
	
	@Override
	public User getUserByYangcongEventId(String eventId) {
		return commonDao.fetch(User.class,Cnd.where("yangEventId",NutzType.EQ.opt,eventId));
	}
	
	@Override
	public int updateUserYangcongId(String yangId, int uid) {
		return commonDao.update(User.class,Chain.make("yangId", yangId).add("bindStatus", 1)
				,Cnd.where("id", NutzType.EQ.opt, uid));
	}

	@Override
	public boolean checkPawd(User user, String oldPaw) {
		return checkPassword(user, oldPaw);
	}
	
	
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(),salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 验证原密码是否正确
	 * @param user
	 * @param oldPwd
	 * @return
	 */
	private boolean checkPassword(User user,String oldPassword){
		byte[] salt =Encodes.decodeHex(user.getSalt()) ;
		byte[] hashPassword = Digests.sha1(oldPassword.getBytes(),salt, HASH_INTERATIONS);
		if(user.getPassword().equals(Encodes.encodeHex(hashPassword))){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void addUserRole(int userId,int roleId) {
		UserRole ur=new UserRole(userId,roleId,TimeUtils.getTimeStamp());
		commonDao.insert(ur);
	}

	@Override
	public void updateUserRole(final int userId,final List<Integer> newRoleList) {
		Trans.exec(new Atom() {
			
			@Override
			public void run() {
				//清除该用户的角色
				commonDao.clear(UserRole.class, Cnd.where("userId", NutzType.EQ.opt, userId));
				//插入新的权限
				for (Integer id : newRoleList) {
					UserRole ur=new UserRole(userId, id, TimeUtils.getTimeStamp());
					commonDao.fastInsert(ur);
				}
			}
		});
		
	}
	
	@Override
	public void deleteUserRole(int userId) {
		baseService.delete(UserRole.class, Cnd.where("userId", NutzType.EQ.opt, userId));
		
	}

}
