package com.stannard.liam.shoppingList;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.stannard.liam.shoppingItem.ShoppingItem;
import com.stannard.liam.user.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import java.util.List;

@Entity
@Table(name = "shopping_list")

public class ShoppingList
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "shoppingList")
    private List<ShoppingItem> items;

    public ShoppingList()
    {

    }

    ShoppingList(String name, List<ShoppingItem> items)
    {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShoppingItem> getItems() {
        return List.copyOf(items);
    }

    public void setItems(List<ShoppingItem> items) {
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}


