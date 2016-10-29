package com.shineoxygen.work.admin.model;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shineoxygen.work.admin.service.AdminUserRolePermissionMgr;
import com.shineoxygen.work.admin.service.AdminUserService;

/**
 * 
 * @author 王辉阳
 * @date 2016年10月29日 下午3:55:00
 * @Description 管理后台shiro的realm，提供数据源给shiro
 */
@SuppressWarnings("unchecked")
public class AdminRealm extends AuthorizingRealm {
	private static final Logger log = LogManager.getLogger(AdminRealm.class);
	@Autowired
	private AdminUserService adminUserSrv;
	@Autowired
	private AdminUserRolePermissionMgr userRolePermissionMgr;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 根据用户配置用户与权限
		if (principals == null) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}

		AdminUser user = adminUserSrv.findByUserName((String) principals.getPrimaryPrincipal());
		if (null == user) {
			return null;
		}

		log.debug("用户：{}，获取权限", (String) principals.getPrimaryPrincipal());

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		if (user.isBuildin()) {// 内置超管
			authorizationInfo.addRoles((List<String>) CollectionUtils.collect(userRolePermissionMgr.findAllRoles(), new Transformer() {
				@Override
				public Object transform(Object input) {
					return ((Role) input).getName();
				}
			}));
			authorizationInfo.addStringPermissions((List<String>) CollectionUtils.collect(userRolePermissionMgr.findAllPermissions(), new Transformer() {
				@Override
				public Object transform(Object input) {
					return ((Permission) input).getUrl();
				}
			}));
			return authorizationInfo;
		}
		if (!user.isBuildin()) {
			List<Role> roles = userRolePermissionMgr.findRolesByUserId(user.getId());
			// 设置角色
			authorizationInfo.addRoles((List<String>) CollectionUtils.collect(roles, new Transformer() {
				@Override
				public Object transform(Object input) {
					return ((Role) input).getName();
				}
			}));

			// 设置权限
			authorizationInfo.addStringPermissions((List<String>) CollectionUtils.collect(userRolePermissionMgr.findPermissionsByRoles(roles), new Transformer() {
				@Override
				public Object transform(Object input) {
					return ((Permission) input).getUrl();
				}
			}));
			return authorizationInfo;
		}
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authToken;
		AdminUser user = adminUserSrv.findByUserNameAndPwd(token.getUsername(), new String(token.getPassword()));
		if (null != user) {
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(token.getUsername(), user.getPwd(), getName());
			return info;
		}
		return null;
	}
}
