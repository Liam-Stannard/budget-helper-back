package com.stannard.liam.shoppingList;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class ShoppingListAssembler implements RepresentationModelAssembler<ShoppingList, EntityModel<ShoppingList>>
{
    @Override
    public EntityModel<ShoppingList> toModel(ShoppingList shoppingList)
    {

        return EntityModel.of(shoppingList,
                linkTo(methodOn(ShoppingListController.class).one(shoppingList.getId())).withSelfRel(),
                linkTo(methodOn(ShoppingListController.class).all()).withRel("shoppingLists"));
    }
}