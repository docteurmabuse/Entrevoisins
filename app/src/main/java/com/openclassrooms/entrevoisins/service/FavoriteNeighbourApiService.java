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

    @Override
    public  void addFavoriteNeighbour(Neighbour neighbour){neighbours.add(new Neighbour(12, "bbnnb", "http://i.pravatar.cc/150?u=a042581f3e39026702d"));}
}
