package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class FavoriteNeighbourApiServiceTest {
    private NeighbourApiService service;
    private List<Neighbour> favoriteNeighbours;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
        favoriteNeighbours= service.getFavoriteNeighbours();
    }

    @Test
    public void getFavoriteNeighboursWithSuccess() {

    }

    @Test
    public void addFavoriteNeighbourWithSuccess() {
        service.getFavoriteNeighbours().clear();
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
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