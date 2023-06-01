package com.stannard.liam.shoppingList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long>
{
    @PostFilter("filterObject.user.getId() == principal.id")
    @Override
    List<ShoppingList> findAll();

}
