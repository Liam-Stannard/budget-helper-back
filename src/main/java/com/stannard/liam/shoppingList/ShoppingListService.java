package com.stannard.liam.shoppingList;

import com.stannard.liam.shoppingItem.ShoppingItem;
import com.stannard.liam.shoppingItem.ShoppingItemRepository;
import com.stannard.liam.user.User;
import com.stannard.liam.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingListService
{

    @Autowired
    private final ShoppingListRepository shoppingListRepository;

    @Autowired
    private final ShoppingItemRepository shoppingItemRepository;

    @Autowired
    private final UserRepository userRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository, ShoppingItemRepository shoppingItemRepository, UserRepository userRepository)
    {
        this.shoppingListRepository = shoppingListRepository;
        this.shoppingItemRepository = shoppingItemRepository;
        this.userRepository = userRepository;
    }

    public List<ShoppingList> getShoppingLists()
    {
        return shoppingListRepository.findAll();
    }

    public Optional<ShoppingList> getShoppingListById(Long id)
    {
        return shoppingListRepository.findById(id);
    }

    public void addNewShoppingList(ShoppingList shoppingList)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> user = userRepository.findByUsername(username);

        if(!user.isPresent())
        {
            throw new IllegalStateException("User  doesn't exist with username: " + username);
        }

        shoppingList.setUser(user.get());
        List<ShoppingItem> items = shoppingList.getItems();
        if(!items.isEmpty())
        {
         System.out.println("Items are the following: "+ items.toString());
         //shoppingItemRepository.saveAll(items);
        }
        shoppingListRepository.save(shoppingList);
    }

    public void updateShoppingList(Long id, ShoppingList shoppingListUpdate)
    {
        ShoppingList shoppingListToUpdate = shoppingListRepository.findById(id)
                .map(shoppingList -> {
                    shoppingList.setName(shoppingListUpdate.getName());
                    shoppingList.setItems(shoppingListUpdate.getItems());
                    return shoppingListRepository.save(shoppingList);
                })
                .orElseGet(() -> {
                    shoppingListUpdate.setId(id);
                    return shoppingListRepository.save(shoppingListUpdate);
                });
    }

    public void deleteShoppingList(Long id)
    {
        if(!shoppingListRepository.existsById(id))
        {
            throw new IllegalStateException("Shopping List doesn't exist with id - [" + id + "]");
        }

        shoppingListRepository.deleteById(id);
    }
}
