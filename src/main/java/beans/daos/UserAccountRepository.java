package beans.daos;

import beans.models.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

    UserAccount findByUser_email(String email);
    UserAccount findByUser_name(String name);
    UserAccount findByUser_id(Long id);

}
