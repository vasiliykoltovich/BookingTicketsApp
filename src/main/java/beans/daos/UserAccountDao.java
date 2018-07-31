package beans.daos;

import beans.models.soap.UserAccount;

public interface UserAccountDao  {
    UserAccount update(UserAccount account);
    UserAccount create(UserAccount account);
}
