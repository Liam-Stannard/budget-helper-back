package com.stannard.liam.shoppingList;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ShoppingListController
{
    private final ShoppingListRepository repository;

    private final ShoppingListAssembler assembler;

    ShoppingListController(ShoppingListRepository repository, ShoppingListAssembler assembler)
    {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/shopping-lists")
    CollectionModel<EntityModel<ShoppingList>> all()
    {
        List<EntityModel<ShoppingList>> shoppingLists = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(shoppingLists, linkTo(methodOn(ShoppingListController.class).all()).withSelfRel());
    }

    @GetMapping("/shopping-lists/{id}")
    EntityModel<ShoppingList> one(@PathVariable Long id)
    {
        ShoppingList shoppingList = repository.findById(id)
                .orElseThrow(() -> new ShoppingListNotFoundException(id));

        return assembler.toModel(shoppingList);
    }

    @PostMapping("/shopping-lists")
    ResponseEntity<?> newShoppingList(@RequestBody ShoppingList newShoppingList)
    {
        EntityModel<ShoppingList> entityModel = assembler.toModel(repository.save(newShoppingList));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }


}
