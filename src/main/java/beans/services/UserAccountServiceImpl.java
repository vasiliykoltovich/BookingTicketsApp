package beans.services;

import beans.daos.UserAccountDao;
import beans.daos.UserAccountRepository;
import beans.models.soap.User;
import beans.models.soap.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional(rollbackFor = Exception.class)
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserAccountDao userAccountDao;

    @Override
    @Transactional
    public UserAccount createAccount(User user, double initSumm) {
        UserAccount newAccount=new UserAccount(-1,initSumm,user);

        return userAccountDao.create(newAccount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation=Propagation.REQUIRED)
    public boolean fillInAccount(User user, double summ) {
        UserAccount account=userAccountRepository.findByUser_email(user.getEmail());
        if(account!=null && summ>=0){
            double balance=account.getPrepaidMoney();
            account.setPrepaidMoney(summ+balance);
            UserAccount newOne=saveToDb(account);
            return true;
        }else
            return false;
    }

    @Override
    public UserAccount getByUser(User user) {
        return userAccountRepository.findByUser_email(user.getEmail());
    }

    @Override
    public double checkAccountBalance(UserAccount account) {
        return 0;
    }

    @Override
    public double checkAccountBalance(User user) {
        UserAccount account = userAccountRepository.findByUser_email(user.getEmail());
        return account.getPrepaidMoney();
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation=Propagation.REQUIRED)
    public boolean withDrawMoney(double ticketPrice, UserAccount account) {
        double initialBalance=account.getPrepaidMoney();
        double balance = initialBalance - ticketPrice;
        if (balance > 0) {
            account.setPrepaidMoney(balance);
            UserAccount newOne=saveToDb(account);
            if(newOne.getPrepaidMoney()<initialBalance) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation=Propagation.REQUIRED)
    public UserAccount saveToDb(UserAccount account) {
          UserAccount updated=null;
        try{
            updated=userAccountDao.update(account);

        } catch (Exception e) {
            throw new RuntimeException("Saving is not completed");
        }

        return updated;
    }
}
