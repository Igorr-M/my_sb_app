create table if not exists user_role (
    user_id bigint not null,
    roles varchar(255)
    ) ENGINE=InnoDB;

create table if not exists message (
    id bigint not null AUTO_INCREMENT,
    filename varchar(255),
    tag varchar(255),
    text varchar(2048) not null,
    user_id bigint,
    primary key (id)
    ) ENGINE=InnoDB;

create table if not exists usr (
    id bigint not null AUTO_INCREMENT,
    activation_code varchar(255),
    active bit not null,
    email varchar(255),
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
    ) ENGINE=InnoDB;

alter table message add constraint message_user_fk foreign key (user_id) references usr (id);

alter table user_role add constraint user_role_user_fk foreign key (user_id) references usr (id);