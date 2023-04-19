package com.stannard.liam.shoppingList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long>
{

}
