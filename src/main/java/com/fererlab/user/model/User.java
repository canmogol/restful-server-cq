package com.fererlab.user.model;

import com.fererlab.core.model.BaseModel;

import javax.persistence.*;


@Entity
@Table(name = "USER_ID_INT")
public class User extends BaseModel<Integer> {

    private static final long serialVersionUID = -2573763017488910282L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_INT_SEQ")
    @SequenceGenerator(name = "USER_ID_INT_SEQ", sequenceName = "user_id_int_seq")
    @Column(name = "ID")
    private Integer id;

    @Column(name = "USR_PASSWORD", length = 100)
    private String password;

    @Column(name = "USR_USERNAME", length = 100)
    private String username;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
