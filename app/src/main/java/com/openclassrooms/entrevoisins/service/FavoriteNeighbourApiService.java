package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

/**
 * Dummy mock for the Api
 */
public class FavoriteNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = FavouriteNeighbourGenerator.generateFavoriteNeighbour();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    public  void addFavoriteNeighbour(Neighbour neighbour){neighbours.add(neighbour);}
}
