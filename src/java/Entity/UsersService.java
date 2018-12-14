package Entity;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;

public class UsersService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public UsersService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public void addUsers(Users users) {
        mgr.persist(users);
    }

    public Users findUsersByCode(int code) {
        Users users = mgr.find(Users.class, code);
        return users;
    }

    public void deleteUsers(int code) {
        Users users = findUsersByCode(code);
        if (users != null) {
            mgr.remove(users);
        }
    }

    public List<Users> findAll() {
        List usersList = mgr.createNamedQuery("Users.findAll").getResultList();
        return usersList;
    }

    public void updateUsers(Users users) {
        mgr.merge(users);
    }
}
