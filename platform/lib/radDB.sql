---研发需求

-------------------自定义字典表-----------------
create table rad_wordbook
(
   id int PRIMARY KEY identity(1,1),
   type tinyint NOT NULL,
   name varchar(64) NOT NULL,
   category tinyint,
   seq int,
   create_id bigint,
   create_name varchar(64),
   create_date datetime,
   modify_id bigint,
   modify_name varchar(64),
   modify_date datetime
);
--create index rad_wordbook_1 on rad_wordbook(type);

-----------------类型参数表（定义大机器、小机器对应的配置参数）--------------------
create table rad_type_config
(
   id int PRIMARY KEY identity(1,1),
   type tinyint not null,
   config_id int not null,
   create_id bigint,
   create_name varchar(64),
   create_date datetime
);

--create index rad_type_config_1 on rad_type_config(type)

--------------------产品表------------------
create table rad_product
(
   id int PRIMARY KEY identity(1,1),
   type_id int NOT NULL,
   model varchar(64) NOT NULL,
   industry int NOT NULL,
   model_desc varchar(256),
   remark varchar(256),
   create_id bigint,
   create_name varchar(64),
   create_date datetime,
   modify_id bigint,
   modify_name varchar(64),
   modify_date datetime
);

--create index rad_product_1 on rad_product(model);


-------------------产品明细表-------------------
create table rad_product_detail
(
   id int PRIMARY KEY identity(1,1),
   product_id int NOT NULL,
   config_id int NOT NULL,
   config_value tinyint  NOT NULL
);
--create index rad_product_detail_1 on rad_product_detail(model_id);



create table wechat_user(
id int PRIMARY KEY identity(1,1),
openid: varchar(64),
nickname: varchar(64),
sex: tinyint,
language: varchar(32),
city: varchar(32),
province: varchar(32),
country: varchar(32),
headimgurl:varchar(512),
subscribe_time: datetime,
unionid: varchar(64)
remark: varchar(256),
groupid: varchar(2),
tagid_list:varchar(64),
subscribe_scene: varchar(32),
qr_scene: varchar(32),
qr_scene_str: varchar(256)
)


