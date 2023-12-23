insert into user_role (role_id, user_id)
select ue.id, re.id
from user_entity ue
join role_entity re on ue.login = 'user' and re.name = 'USER';

insert into user_role (role_id, user_id)
select ue.id, re.id
from user_entity ue
join role_entity re on ue.login = 'moder' and re.name = 'MODER';

insert into user_role (role_id, user_id)
select ue.id, re.id
from user_entity ue
join role_entity re on ue.login = 'admin' and re.name = 'ADMIN';