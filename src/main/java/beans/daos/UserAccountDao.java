package beans.daos;

import beans.models.UserAccount;

public interface UserAccountDao  {
    UserAccount update(UserAccount account);
    UserAccount create(UserAccount account);
}
