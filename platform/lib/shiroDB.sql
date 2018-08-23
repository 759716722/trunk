---用户、权限、资源表


-------------------用户表-----------------
CREATE TABLE sys_user(
  id bigint PRIMARY KEY identity(1,1),
  name varchar(64) NOT NULL ,
  password varchar(64) NOT NULL ,
  salt varchar(32) ,
	login_name varchar(64) NOT NULL ,
  sex tinyint ,
  birthday datetime ,
  phone varchar(20) ,
  email varchar(32) ,
  dept_id bigint    ,
  seq tinyint       ,
  state tinyint NOT NULL,
  create_id bigint,
  create_name varchar(64),
  create_date datetime,
  modify_id bigint,
  modify_name varchar(64),
  modify_date datetime
  );
create index sys_user_01 on sys_user(name);
create index sys_user_02 on sys_user(login_name);


--------------------角色表------------------
CREATE TABLE sys_role (
  id bigint PRIMARY KEY identity(1,1),
  name varchar(64) NOT NULL ,
  description varchar(255) ,
  remark varchar(255) ,
  seq tinyint ,
  create_id bigint,
  create_name varchar(64),
  create_date datetime,
  modify_id bigint,
  modify_name varchar(64),
  modify_date datetime
  );

create index sys_role_01 on sys_role(name);


-------------------部门表-------------------
CREATE TABLE sys_dept (
  id bigint PRIMARY KEY identity(1,1),
  name varchar(64) NOT NULL ,
  description varchar(255) ,
  remark varchar(255) ,
  seq tinyint ,
  parent_id bigint  ,
  create_id bigint,
  create_name varchar(64),
  create_date datetime
  ) ;

create index sys_dept_01 on sys_dept(name);


------------------ 菜单表 ---------------------
CREATE TABLE sys_menu (
	id bigint PRIMARY KEY identity(1,1),
	name varchar(64) NOT NULL ,
	description varchar(255),
	url varchar(100) NOT NULL,
  type tinyint NOT NULL,  ---1：目录   2：菜单   3：按钮
  icon varchar(32),
  seq tinyint,
  parent_id bigint,
  create_id bigint,
  create_name varchar(64),
  create_date datetime
  );

create index sys_menu_01 on sys_menu(name);


------------------- 用户与角色对应关系 ---------------------------

CREATE TABLE sys_user_role (
  id bigint PRIMARY KEY identity(1,1),
  user_id bigint NOT NULL ,
  role_id bigint NOT NULL
  );

create index sys_user_role_01 on sys_user_role(user_id,role_id);

------------------- 角色与菜单对应关系 ---------------------------

CREATE TABLE sys_role_menu (
  id bigint PRIMARY KEY identity(1,1),
  role_id bigint NOT NULL ,
  menu_id bigint NOT NULL
  );

create index sys_role_menu_01 on sys_role_menu(role_id,menu_id);

------------------ 角色与部门对应关系(暂不使用1对多关系，暂不生成) --------------------------

CREATE TABLE sys_role_dept (
  id bigint PRIMARY KEY identity(1,1),
  role_id bigint NOT NULL ,
  dept_id bigint NOT NULL
  );

create index sys_role_dept_01 on sys_role_dept(role_id,dept_id);


------------------菜单数据-----------------------------
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('系统管理', '系统管理目录', '/sys/', 1, NULL, NULL, 0, 1, '管理员', '2018-06-19 16:38:05.587');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('用户管理', '用户信息管理', '/**/user.html', 2, NULL, NULL, 1, 1, '管理员', '2018-06-19 17:29:22.780');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('角色管理', '角色信息管理', '/**/role.html', 2, NULL, NULL, 1, 1, '管理员', '2018-06-19 17:29:22.797');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('部门管理', '部门信息管理', '/**/dept.html', 2, NULL, NULL, 1, 1, '管理员', '2018-06-19 17:29:22.810');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('菜单管理', '菜单信息管理', '/**/menu.html', 2, NULL, NULL, 1, 1, '管理员', '2018-06-19 17:29:22.820');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('权限管理', '权限信息管理', '/**/permission.html', 2, NULL, NULL, 1, 1, '管理员', '2018-06-19 17:29:22.830');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('查询用户', '查询用户信息', '/sys/user/getUserByPage.do', 3, NULL, NULL, 2, 1, '管理员', '2018-06-20 08:53:00.563');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('增加用户', '增加新用户', '/sys/user/createUser.do', 3, NULL, NULL, 2, 1, '管理员', '2018-06-20 08:53:00.627');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('修改用户', '修改用户信息', '/sys/user/doModifyUser.do', 3, NULL, NULL, 2, 1, '管理员', '2018-06-20 08:53:00.647');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('删除用户', '删除用户信息', '/sys/user/doDeleteUser.do', 3, NULL, NULL, 2, 1, '管理员', '2018-06-20 08:53:00.657');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('查询角色', '查询角色信息', '/sys/role/getRoleByPage.do', 3, NULL, NULL, 3, 1, '管理员', '2018-06-20 09:03:09.337');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('增加角色', '增加角色信息', '/sys/role/saveRole.do', 3, NULL, NULL, 3, 1, '管理员', '2018-06-20 09:03:09.350');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('修改角色', '修改角色信息', '/sys/role/getRoleById.do', 3, NULL, NULL, 3, 1, '管理员', '2018-06-20 09:03:09.363');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('删除角色', '删除角色信息', '/sys/role/doDeleteRole.do', 3, NULL, NULL, 3, 1, '管理员', '2018-06-20 09:03:09.377');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('查询部门', '查询部门信息', '/sys/dept/getAllDept.do', 3, NULL, NULL, 4, 1, '管理员', '2018-06-20 09:08:03.433');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('增加部门', '增加部门信息', '/sys/dept/createDept.do', 3, NULL, NULL, 4, 1, '管理员', '2018-06-20 09:08:03.447');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('删除部门', '删除部门信息', '/sys/dept/doDeleteDept.do', 3, NULL, NULL, 4, 1, '管理员', '2018-06-20 09:08:03.563');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('查询菜单', '查询菜单信息', '/sys/menu/getMenuByPid.do', 3, NULL, NULL, 5, 1, '管理员', '2018-06-20 09:11:43.527');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('增加菜单', '增加菜单信息', '/sys/menu/createMenu.do', 3, NULL, NULL, 5, 1, '管理员', '2018-06-20 09:11:43.543');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('删除菜单', '删除菜单信息', '/sys/menu/doDeleteMenu.do', 3, NULL, NULL, 5, 1, '管理员', '2018-06-20 09:11:43.633');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('查询角色', '查询角色信息', '/sys/role/getRoleById.do', 3, NULL, NULL, 6, 1, '管理员', '2018-06-20 09:27:36.770');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('查询角色用户', '查询该角色所有的用户', '/sys/userRole/getUserByRoleId.do', 3, NULL, NULL, 6, 1, '管理员', '2018-06-20 09:27:36.777');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('查询该角色菜单', '查询该角色拥有的菜单权限', '/sys/roleMenu/getMenuByRoleId.do', 3, NULL, NULL, 6, 1, '管理员', '2018-06-20 09:27:36.870');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('角色关联用户', '增加某用户拥有该角色', '/sys/userRole/createUserRole.do', 3, NULL, NULL, 6, 1, '管理员', '2018-06-20 09:27:36.883');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('删除该角色用户', '删除该角色的某个用户', '/sys/userRole/doDeleteUserRole.do', 3, NULL, NULL, 6, 1, '管理员', '2018-06-20 09:27:36.897');
INSERT INTO [sys_menu]([name], [description], [url], [type], [icon], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('角色关联菜单', '修改角色拥有的菜单信息', '/sys/roleMenu/createRoleMenu.do', 3, NULL, NULL, 6, 1, '管理员', '2018-06-20 09:27:36.910');

-------------------------部门数据-------------------------------------------------

INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('经纬科技', NULL, NULL, NULL, 0, NULL, NULL, NULL);
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('营销服务中心', '', '', NULL, 1, 1, '管理员', '2018-06-20 16:40:39.377');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('生产运营中心', '', '', NULL, 1, 1, '管理员', '2018-06-20 16:40:39.390');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('资源开发与供应中心', '', '', NULL, 1, 1, '管理员', '2018-06-20 16:40:39.400');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('研究院', '', '', NULL, 1, 1, '管理员', '2018-06-20 16:40:39.407');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('财务管理中心', '', '', NULL, 1, 1, '管理员', '2018-06-20 16:40:39.413');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('综合管理中心', '', '', NULL, 1, 1, '管理员', '2018-06-20 16:40:39.423');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('国内贸易部', '', '', NULL, 2, 1, '管理员', '2018-06-20 16:41:51.737');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('国际贸易部', '', '', NULL, 2, 1, '管理员', '2018-06-20 16:41:51.757');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('市场开发部', '', '', NULL, 2, 1, '管理员', '2018-06-20 16:41:51.807');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('贸易服务部', '', '', NULL, 2, 1, '管理员', '2018-06-20 16:41:51.813');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('售后技术部', '', '', NULL, 2, 1, '管理员', '2018-06-20 16:41:51.823');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('生产部', '', '', NULL, 3, 1, '管理员', '2018-06-20 16:42:45.557');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('生产技术部', '', '', NULL, 3, 1, '管理员', '2018-06-20 16:42:45.567');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('仓储部', '', '', NULL, 3, 1, '管理员', '2018-06-20 16:42:45.617');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('PMC部', '', '', NULL, 3, 1, '管理员', '2018-06-20 16:42:45.630');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('品质管理部', '', '', NULL, 3, 1, '管理员', '2018-06-20 16:42:45.653');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('资源开发部', '', '', NULL, 4, 1, '管理员', '2018-06-20 16:43:07.680');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('采购服务部', '', '', NULL, 4, 1, '管理员', '2018-06-20 16:43:07.693');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('研发中心', '', '', NULL, 5, 1, '管理员', '2018-06-20 16:43:23.427');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('研发技术部', '', '', NULL, 5, 1, '管理员', '2018-06-20 16:43:23.437');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('投资发展部', '', '', NULL, 6, 1, '管理员', '2018-06-20 16:44:05.060');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('内控审计部', '', '', NULL, 6, 1, '管理员', '2018-06-20 16:44:05.073');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('财务部', '', '', NULL, 6, 1, '管理员', '2018-06-20 16:44:05.080');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('总裁办', '', '', NULL, 7, 1, '管理员', '2018-06-20 16:44:35.217');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('人力资源部', '', '', NULL, 7, 1, '管理员', '2018-06-20 16:44:35.233');
INSERT INTO [sys_dept]([name], [description], [remark], [seq], [parent_id], [create_id], [create_name], [create_date]) VALUES ('信息技术部', '', '', NULL, 7, 1, '管理员', '2018-06-20 16:44:35.243');
