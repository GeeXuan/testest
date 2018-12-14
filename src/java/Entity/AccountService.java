package Entity;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;

public class AccountService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public AccountService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public void addAccount(Account account) {
        mgr.persist(account);
    }

    public Account findAccountByCode(int code) {
        Account account = mgr.find(Account.class, code);
        return account;
    }

    public void deleteAccount(int code) {
        Account account = findAccountByCode(code);
        if (account != null) {
            mgr.remove(account);
        }
    }

    public List<Account> findAll() {
        List accountList = mgr.createNamedQuery("Account.findAll").getResultList();
        return accountList;
    }

    public void updateAccount(Account account) {
        mgr.merge(account);
    }
}
