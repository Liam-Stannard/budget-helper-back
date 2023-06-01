package com.stannard.liam.shoppingList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/shopping-lists")

public class ShoppingListController
{
    @Autowired
    private final ShoppingListRepository shoppingListRepository;
    @Autowired
    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListRepository shoppingListRepository, ShoppingListService shoppingListService)
    {
        this.shoppingListRepository = shoppingListRepository;
        this.shoppingListService = shoppingListService;
    }

    @GetMapping("/")
    public List<ShoppingList> getShoppingLists()
    {
        return shoppingListService.getShoppingLists();
    }

    @PostMapping("/")
    public void createShoppingList(@RequestBody ShoppingList shoppingList)
    {
        shoppingListService.addNewShoppingList(shoppingList);
    }

    @GetMapping("/{id}")
    public Optional<ShoppingList> getShoppingList(@PathVariable Long id)
    {
        return shoppingListService.getShoppingListById(id);
    }

    @PutMapping("/{id}")
    public void updateShoppingList(@RequestBody ShoppingList shoppingList, @PathVariable Long id)
    {
        shoppingListService.updateShoppingList(id, shoppingList);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id)
    {
        shoppingListService.deleteShoppingList(id);
    }
}


