package com.stannard.liam.shoppingItem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stannard.liam.shoppingList.ShoppingList;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;



import java.util.List;

@Entity
@Table(name = "shopping_list_item")
public class ShoppingItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "shopping_list_id") //nullable = false
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private ShoppingList shoppingList;

    ShoppingItem()
    {

    }

    ShoppingItem(String name)
    {
        this.name = name;
    }

    public ShoppingList getShoppingList()
    {
        return shoppingList;
    }

    public void setShoppingList(ShoppingList shoppingList)
    {
        this.shoppingList = shoppingList;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}


