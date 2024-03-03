package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_of_role")
    private String nameOfRole;


    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    public Role() {}
    public Role(String nameOfRole) {
        this.nameOfRole = nameOfRole;
    }

    public Role(Long id, String nameOfRole, Collection<User> users) {
        this.id = id;
        this.nameOfRole = nameOfRole;
        this.users = users;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfRole() {
        return nameOfRole;
    }

    public void setNameOfRole(String nameOfRole) {
        this.nameOfRole = nameOfRole;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getNameOfRole();
    }

    @Override
    public String toString() {
        return nameOfRole.substring("ROLE_".length());
    }

}
