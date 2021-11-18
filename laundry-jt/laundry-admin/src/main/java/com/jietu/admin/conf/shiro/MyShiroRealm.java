package com.jietu.admin.conf.shiro;

import com.jietu.admin.entity.system.ResourceEntity;
import com.jietu.admin.entity.system.ResourceEntity;
import com.jietu.admin.entity.system.UserEntity;
import com.jietu.admin.service.system.IUserService;
import com.jietu.admin.entity.system.ResourceEntity;
import com.jietu.admin.entity.system.UserEntity;
import com.jietu.admin.service.system.IResourceService;
import com.jietu.admin.service.system.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;
    @Autowired
    private IResourceService resourceService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        UserEntity user = userService.findByUsername(username);
        if(user == null){
            throw new AccountException("帐号或密码不正确！");
        }else if(!user.getAvailable()){
            throw new LockedAccountException("帐号已经禁止登录！");
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(user.getUsername()),
                getName()
        );
        // 当验证都通过后，把用户信息放在session里
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("loginUser", user);
        session.setAttribute("loginUserId", user.getId());
        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserEntity user  = (UserEntity)principals.getPrimaryPrincipal();
        authorizationInfo.addRole(user.getRoleId());
        List<ResourceEntity> resourceList = resourceService.findByRoleId(user.getRoleId());
        for(ResourceEntity rousou : resourceList){
            authorizationInfo.addStringPermission(rousou.getPermission());
        }
        return authorizationInfo;
    }

}
