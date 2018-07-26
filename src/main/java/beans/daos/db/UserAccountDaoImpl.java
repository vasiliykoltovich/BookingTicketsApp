package beans.daos.db;

import beans.daos.AbstractDAO;
import beans.daos.UserAccountDao;
import beans.models.UserAccount;
import org.springframework.stereotype.Repository;

@Repository
public class UserAccountDaoImpl extends AbstractDAO implements UserAccountDao {
    @Override
    public UserAccount update(UserAccount account) {
        return ((UserAccount) getCurrentSession().merge(account));
    }
}
