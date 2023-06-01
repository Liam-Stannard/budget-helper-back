package com.stannard.liam.shoppingItem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stannard.liam.shoppingList.ShoppingList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "shopping_list_item")
public class ShoppingItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "shopping_list_id", referencedColumnName = "id", nullable = false) //
    private ShoppingList shoppingList;

    public ShoppingItem()
    {

    }

    public ShoppingItem(String name, ShoppingList shoppingList)
    {
        this.name = name;
        this.shoppingList = shoppingList;
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
    @Override
    public String toString()
    {
        return "Id: " + id + "\tname: " + this.name;
    }
}
