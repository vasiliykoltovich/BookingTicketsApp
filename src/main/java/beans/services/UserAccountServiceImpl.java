package beans.services;

import beans.daos.BookingDAO;
import beans.daos.UserAccountRepository;
import beans.models.User;
import beans.models.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserAccountServiceImpl implements UserAccountService {
@Autowired
    private  EventService      eventService;
    @Autowired
    private  AuditoriumService auditoriumService;
    @Autowired
    private  UserService userService;
    @Autowired
    private  BookingDAO bookingDAO;
    @Autowired
    private  DiscountService   discountService;
    @Autowired
    private UserAccountRepository userAccountRepository;


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
        UserAccount account= userAccountRepository.findByUser_email(user.getEmail());
        return account.getPrepaidMoney();
    }

    @Override
    public boolean withDrawMoney(double ticketPrice, UserAccount account) {
        double balance=account.getPrepaidMoney()-ticketPrice;
        if(balance>0){
            account.setPrepaidMoney(balance);
            UserAccount userAccount=new UserAccount();
            userAccount.setId(account.getId());
            userAccount.setUser(account.getUser());
            userAccount.setPrepaidMoney(balance);
            userAccountRepository.save(userAccount);
//            userAccountRepository.flush();
            return true;
        }
        return false;
    }
}
