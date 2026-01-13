package ru.vsu.rogachev.entity.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "invites")
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invite_id")
    private Long id;

    @NotNull
    @ManyToMany(mappedBy = "invites")
    private List<User> users = new ArrayList<>();

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inviter_id", nullable = false)
    private User inviter;

    public Invite(@NotNull List<User> users, @NotNull User inviter) {
        this.users = users;
        this.inviter = inviter;
    }

}
