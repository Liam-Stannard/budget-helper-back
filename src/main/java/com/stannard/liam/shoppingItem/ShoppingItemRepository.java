package com.stannard.liam.shoppingItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long>
{
    Page<ShoppingItem> findByShoppingListId(Long shoppingListId, Pageable pageable);
    Optional<ShoppingItem> findByIdAndShoppingListId(Long id, Long shoppingListId);
}
