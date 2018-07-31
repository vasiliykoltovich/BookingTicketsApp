package beans.services;

import beans.models.soap.User;
import beans.models.UserAccount;

public interface UserAccountService {
    UserAccount createAccount(User user,double initSumm);
    boolean fillInAccount(User user,double summ);
    UserAccount getByUser(User user);
    double checkAccountBalance(UserAccount account);
    double checkAccountBalance(User user);
    boolean withDrawMoney(double ticketPrice,UserAccount account);
    UserAccount saveToDb(UserAccount account);

}
