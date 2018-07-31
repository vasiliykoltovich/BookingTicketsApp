package beans.daos.db;

import beans.daos.AbstractDAO;
import beans.daos.UserAccountDao;
import beans.models.soap.UserAccount;
import org.springframework.stereotype.Repository;

@Repository
public class UserAccountDaoImpl extends AbstractDAO implements UserAccountDao {
    @Override
    public UserAccount update(UserAccount account) {
        return ((UserAccount) getCurrentSession().merge(account));
    }

    @Override
    public UserAccount create(UserAccount account) {
        Long id= (Long) getCurrentSession().save(account);
        account.setId(id);
        return account;
    }
}


//    @Override
//    public Ticket create(User user, Ticket ticket) {
//        BookingDAO.validateTicket(ticket);
//        BookingDAO.validateUser(user);
//
//        Long ticketId = (Long) getCurrentSession().save(ticket);
//        Ticket storedTicket = ticket.withId(ticketId);
//        Booking booking = new Booking(user, storedTicket);
//        getCurrentSession().save(booking);
//        return storedTicket;
//    }