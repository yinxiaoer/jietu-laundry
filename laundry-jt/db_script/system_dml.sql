-- 用户
INSERT INTO `sys_user`(`id`, `username`, `realname`, `password`, `email`, `phone`, `available`, `role_id`, `openid`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES (1, 'admin', '管理员', '0f8e52434a578bc6428cfcac6df4ef1e', '123456@meiyusc.com', '123456789', b'1', 'ADMIN', '', NULL, NULL, '管理员', '2018-04-08 10:10:26');

-- 角色
INSERT INTO `sys_role`(`id`, `name`, `description`, `available`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('ADMIN', 'ADMIN', '管理员', b'1', NULL, NULL, NULL, NULL);
INSERT INTO `sys_role`(`id`, `name`, `description`, `available`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('MERCHANT', '商户', '商户', b'1', '管理员', '2018-12-23 12:56:30', '', NULL);

-- 资源
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('dashboard', 'home', '首页', 'menu', '', '', 'dashboard', '0', 1, '管理员', '2018-01-11 22:13:20', '管理员', '2018-02-23 14:43:06');
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('dashboard1', 'home', 'HOME', 'menu', '', 'dashboard', 'dashboard', 'dashboard', 1, '管理员', '2018-01-11 23:14:07', '管理员', '2018-02-23 14:42:51');
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('goods', 'goods', '商品管理', 'menu', '/goods', '/goods', 'goods', '0', 10, '管理员', '2018-12-19 21:20:12', '管理员', '2018-12-19 21:21:39');
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('goodsList', 'goods', '商品列表', 'menu', '/goods/list', 'goodsList', 'goods:list', 'goods', 1, '管理员', '2018-12-19 21:21:25', '管理员', '2018-12-19 21:24:02');
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('goods_delete', '', '删除', 'button', NULL, NULL, 'goods:delete', 'goodsList', 3, '管理员', '2018-12-23 12:55:54', '', NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('goods_save', '', '添加', 'button', NULL, NULL, 'goods:save', 'goodsList', 1, '管理员', '2018-12-22 14:21:13', '', NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('goods_update', '', '更新', 'button', NULL, NULL, 'goods:update', 'goodsList', 2, '管理员', '2018-12-23 12:55:13', '', NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('icon', 'picture', '图标', 'menu', NULL, 'icon', 'icon', 'icons', 1, '管理员', '2018-01-11 23:44:00', '管理员', '2018-01-11 23:46:15');
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('icons', 'picture', '图标', 'menu', '', '/icons', 'icons', '0', 200, '管理员', '2018-01-11 23:43:28', '管理员', '2018-01-12 00:19:43');
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('merchant', '', '商户管理', 'menu', 'merchant', '/merchant', 'merchant:list', '0', 50, '管理员', '2018-12-21 22:42:06', '管理员', '2018-12-21 23:06:15');
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('merchantList', 'merchant', '商户列表', 'menu', '/merchant/merchantList', 'merchantList', 'merchant:list', 'merchant', 1, '管理员', '2018-12-21 22:48:27', '管理员', '2018-12-22 13:49:28');
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('merchant_delete', '', '删除', 'button', NULL, NULL, 'merchant:delete', 'merchantList', 3, '管理员', '2018-12-22 08:46:02', '', NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('merchant_save', '', '添加', 'button', NULL, NULL, 'merchant:save', 'merchantList', 1, '管理员', '2018-12-22 08:45:18', '', NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('merchant_update', '', '更新', 'button', NULL, NULL, 'merchant:update', 'merchantList', 2, '管理员', '2018-12-22 08:45:41', '', NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('resource', 'resource', '资源管理', 'menu', '/resource/list', 'resource', 'resource:list', 'system', 3, NULL, NULL, '管理员', '2018-01-11 22:59:00');
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('resource_delete', NULL, '删除', 'button', NULL, NULL, 'resource:delete', 'resource', 3, '管理员', '2018-01-10 17:44:41', NULL, NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('resource_save', NULL, '添加', 'button', NULL, NULL, 'resource:save', 'resource', 1, '管理员', '2018-01-10 17:41:45', NULL, NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('resource_update', NULL, '更新', 'button', NULL, NULL, 'resource:update', 'resource', 2, '管理员', '2018-01-10 17:42:06', NULL, NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('role', 'role', '角色管理', 'menu', '/role/list', 'role', 'role:list', 'system', 2, NULL, NULL, '管理员', '2018-01-11 23:00:02');
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('role_configRole', NULL, '配置权限', 'button', NULL, NULL, 'role:configRole', 'role', 5, '管理员', '2018-01-10 16:28:32', '管理员', '2018-01-10 16:33:28');
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('role_delete', NULL, '删除', 'button', NULL, NULL, 'role:delete', 'role', 3, '管理员', '2018-01-10 10:44:44', NULL, NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('role_save', NULL, '添加', 'button', NULL, NULL, 'role:save', 'role', 1, '管理员', '2018-01-10 10:43:26', NULL, NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('role_update', NULL, '更新', 'button', NULL, NULL, 'role:update', 'role', 2, '管理员', '2018-01-10 10:43:45', NULL, NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('role_updateAvailable', NULL, '更新可用状态', 'button', NULL, NULL, 'role:updateAvailable', 'role', 3, '管理员', '2018-01-10 10:45:34', '管理员', '2018-01-10 10:46:33');
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('system', 'system', '系统管理', 'menu', '/system', '/system', 'system', '0', 100, NULL, NULL, '管理员', '2018-01-11 23:47:44');
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('systemParameter', 'parameter', '系统参数配置', 'menu', '/system/systemParameter', 'systemParameter', 'systemParameter:list', 'system', 4, '管理员', '2018-01-07 12:21:35', '管理员', '2018-01-16 17:19:16');
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('systemParameterType', 'parameter', '系统参数类型', 'menu', '/system/systemParameterType/list', 'systemParameterType', 'systemParameterType:list', 'system', 5, '管理员', '2018-01-07 12:21:59', '管理员', '2018-01-16 17:13:17');
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('systemParameterType_delete', NULL, '删除系统参数类型', 'button', NULL, NULL, 'systemParameterType:delete', 'systemParameterType', 3, '管理员', '2018-01-10 18:03:57', NULL, NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('systemParameterType_save', NULL, '保存系统参数类型', 'button', NULL, NULL, 'systemParameterType:save', 'systemParameterType', 1, '管理员', '2018-01-10 18:05:04', NULL, NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('systemParameterType_update', '', '更新系统参数类型', 'button', NULL, NULL, 'systemParameterType:update', 'systemParameterType', 2, '管理员', '2018-01-10 18:06:52', NULL, NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('systemParameter_delete', NULL, '删除系统参数', 'button', NULL, NULL, 'systemParameter:delete', 'systemParameter', 3, '管理员', '2018-01-10 18:02:54', NULL, NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('systemParameter_save', NULL, '保存系统参数', 'button', NULL, NULL, 'systemParameter:save', 'systemParameter', 1, '管理员', '2018-01-10 18:01:34', NULL, NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('systemParameter_update', NULL, '更新系统参数', 'button', NULL, NULL, 'systemParameter:update', 'systemParameter', 2, '管理员', '2018-01-10 18:02:00', NULL, NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('user', 'user', '用户管理', 'menu', '/user/list', 'user', 'user:list', 'system', 1, NULL, NULL, '管理员', '2018-01-11 23:00:07');
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('user_delete', NULL, '删除', 'button', '/user/delete', NULL, 'user:delete', 'user', 3, NULL, NULL, NULL, NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('user_resetPassword', '', '重置密码', 'button', NULL, NULL, 'user:resetPassword', 'user', 4, '管理员', '2018-04-09 19:52:19', NULL, NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('user_save', NULL, '添加', 'button', '/user/save', NULL, 'user:save', 'user', 1, NULL, NULL, NULL, NULL);
INSERT INTO `laundry`.`sys_resource`(`id`, `icon`, `name`, `resource_type`, `url`, `path`, `permission`, `parent_id`, `seq`, `create_name`, `create_date`, `update_name`, `update_date`) VALUES ('user_update', NULL, '更新', 'button', '/user/update', NULL, 'user:update', 'user', 2, NULL, NULL, NULL, NULL);

-- 权限资源对应关系
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'resource_save', 'button');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'resource_update', 'button');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'resource_delete', 'button');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'systemParameter_save', 'button');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'systemParameter_update', 'button');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'systemParameter_delete', 'button');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'systemParameterType_save', 'button');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'systemParameterType_update', 'button');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'systemParameterType_delete', 'button');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'role_save', 'button');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'role_update', 'button');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'role_updateAvailable', 'button');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'role_delete', 'button');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'role_configRole', 'button');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'user_save', 'button');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'user_update', 'button');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'user_delete', 'button');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'user_resetPassword', 'button');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'dashboard', 'menu');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'dashboard1', 'menu');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'goods', 'menu');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'goodsList', 'menu');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'system', 'menu');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'user', 'menu');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'role', 'menu');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'resource', 'menu');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'systemParameter', 'menu');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'systemParameterType', 'menu');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'icons', 'menu');
INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `resource_type`) VALUES ('ADMIN', 'icon', 'menu');
