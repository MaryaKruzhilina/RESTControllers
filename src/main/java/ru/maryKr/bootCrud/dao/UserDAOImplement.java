package ru.maryKr.bootCrud.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.maryKr.bootCrud.model.Role;
import ru.maryKr.bootCrud.model.User;
import ru.maryKr.bootCrud.model.UserRole;

import java.util.List;
@Repository
public class UserDAOImplement implements UserDAO{
    @PersistenceContext
    private EntityManager em;

    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public void removeUser(long id) {
        em.remove(em.find(User.class, id));
    }

    @Override
    public List<User> getUsers() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void updateUser(long id, User user) {
        User u = em.find(User.class, id);
        u.setName(user.getName());
        u.setEmail(user.getEmail());
        u.setLastname(user.getLastname());
        u.setAge(user.getAge());
        em.merge(u);
    }

    @Override
    public User getUser(long id) {
        return em.find(User.class, id);
    }
}
