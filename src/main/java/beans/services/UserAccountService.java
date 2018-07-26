package beans.services;

import beans.models.User;
import beans.models.UserAccount;

public interface UserAccountService {
    UserAccount getByUser(User user);
    double checkAccountBalance(UserAccount account);
    double checkAccountBalance(User user);
    boolean withDrawMoney(double ticketPrice,UserAccount account);
    UserAccount saveToDb(UserAccount account);

}
