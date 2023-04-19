package com.stannard.liam.shoppingList;

import com.stannard.liam.shoppingItem.ShoppingItem;
import jakarta.persistence.*;


import java.util.List;

@Entity
@Table(name = "shopping_list")
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ElementCollection
    @OneToMany(mappedBy = "shoppingList", cascade=CascadeType.ALL)
    private List<ShoppingItem> items;

    ShoppingList() {

    }

    ShoppingList(String name, List<ShoppingItem> items) {
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
}


