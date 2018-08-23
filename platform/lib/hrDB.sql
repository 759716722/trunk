--人力资源

---------------------员工基本信息-----------------------

create table hr_employee
(
   id                   int           primary key identity(1,1),
   employee_no          varchar(64)   ,
   name                 varchar(64)   not null,
   sex                  tinyint       ,
   birthday             datetime      ,
   id_card              char(18)      not null,
   phone                varchar(32)   ,
   email                varchar(32)   ,
   native_place         varchar(32)   ,
   ethnic_group         varchar(32)   ,
   hukou_type           varchar(32)   ,
   family_linkman       varchar(32)   ,
   family_relation      varchar(32)   ,
   family_linkphone     varchar(32)   ,
   now_addr             varchar(128)  ,
   family_addr          varchar(128)  ,
   education            varchar(32)   ,
   university           varchar(32)   ,
   major                varchar(32)   ,
   graduate_date        datetime      ,
   join_date            datetime      ,
   contract_num         tinyint       ,
   contract_start_date  datetime      ,
   contract_end_date    datetime      ,
   dept_id              bigint        ,
   dept_name            varchar(32)   ,
   duty                 varchar(32)   ,
   professional_title   varchar(32)   ,
   own_company        	varchar(32)   ,
   social_security      varchar(32)   ,
   house_fund           varchar(32)   ,
   state                tinyint       not null,
   leave_date           datetime      ,
   remark               varchar(256)  ,
   create_id            bigint        ,
   create_name          varchar(64)   ,
   create_date          datetime      ,
   modify_id            bigint        ,
   modify_name          varchar(64)   ,
   modify_date          datetime
);

create table hr_employee_contract
(
   id                   int           primary key identity(1,1),
   employee_id          varchar(64)   ,
   start_date           datetime      ,
   end_date             datetime      ,
   contract_no					varchar(64)  	,
   remark               varchar(256)  ,
   create_id            bigint        ,
   create_name          varchar(64)   ,
   create_date          datetime      ,
   modify_id            bigint        ,
   modify_name          varchar(64)   ,
   modify_date          datetime
);
