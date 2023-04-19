package com.stannard.liam.shoppingList;

class ShoppingListNotFoundException extends RuntimeException
{
    ShoppingListNotFoundException(Long id)
    {
        super("\nCould not find shopping list with id -  [" + id + "]");
    }
}