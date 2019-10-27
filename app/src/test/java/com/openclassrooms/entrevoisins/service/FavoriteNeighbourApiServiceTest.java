package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class FavoriteNeighbourApiServiceTest {
    private NeighbourApiService service;
    private FavoriteNeighbourApiService favoriteService;


    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
        favoriteService=DI.getNewInstanceFavoriteApiService();

    }

    @Test
    public void getFavoriteNeighboursWithSuccess() {
    }

    @Test
    public void addFavoriteNeighbourWithSuccess(){
        List<Neighbour> neighbours = service.getNeighbours();
        Neighbour favoriteNeighbourToAdd= service.getNeighbours().get(0);
        favoriteService.addFavoriteNeighbour(favoriteNeighbourToAdd);
        Neighbour favoriteNeighbour = favoriteService.getNeighbours().get(0);
        assertEquals(favoriteNeighbourToAdd,favoriteNeighbour);
        assertEquals(1,favoriteService.getNeighbours().size());
        assertTrue(neighbours.stream().map(Neighbour::getId).collect(Collectors.toList()).contains(favoriteNeighbour.getId()));
        assertTrue(neighbours.stream().map(Neighbour::getName).collect(Collectors.toList()).contains(favoriteNeighbour.getName()));
        assertTrue(neighbours.stream().map(Neighbour::getAvatarUrl).collect(Collectors.toList()).contains(favoriteNeighbour.getAvatarUrl()));
    }
    @Test
    public void deleteFavoriteNeighboursWithSuccess() {
    }
}