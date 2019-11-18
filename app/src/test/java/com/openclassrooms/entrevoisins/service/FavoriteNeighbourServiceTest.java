package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FavoriteNeighbourServiceTest {
    private NeighbourApiService service;
    private List<Neighbour> favoriteNeighbours;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
        favoriteNeighbours= service.getFavoriteNeighbours();
    }
    @Test
    public void getFavoriteNeighboursWithSuccess() {
        service.getFavoriteNeighbours().clear();
        service.getFavoriteNeighbours().addAll(DummyNeighbourGenerator.DUMMY_NEIGHBOURS);
        favoriteNeighbours = service.getFavoriteNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(favoriteNeighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void addFavoriteNeighbourWithSuccess() {
        service.getFavoriteNeighbours().clear();
        favoriteNeighbours = service.getFavoriteNeighbours();
        Neighbour favoriteNeighbourToAdd = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(0);
        service.addFavoriteNeighbour(favoriteNeighbourToAdd);
        assertTrue(service.getFavoriteNeighbours().contains(favoriteNeighbourToAdd));
        }

    @Test
    public void isFavoriteNeighboursWithSuccess() {
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
        Neighbour neighbourToCompare = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(0);
        service.getFavoriteNeighbours().clear();
        favoriteNeighbours.add(neighbourToCompare);
        assertTrue(service.isFavorite(neighbourToCompare));
    }

    @Test
    public void deleteFavoriteNeighboursWithSuccess() {
        Neighbour neighbourToDelete = DummyNeighbourGenerator.DUMMY_NEIGHBOURS.get(0);
        service.getFavoriteNeighbours().clear();
        favoriteNeighbours.add(neighbourToDelete);
        service.deleteFavoriteNeighbour(neighbourToDelete);
        assertFalse(service.getFavoriteNeighbours().contains(neighbourToDelete));
    }
}