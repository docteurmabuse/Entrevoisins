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
     * Delete  a favorite neighbour
     * {@param neighbour}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * Add a favorite neighbour
     * {@param neighbour}
     */
    public  void addFavoriteNeighbour(Neighbour neighbour){neighbours.add(neighbour);}

    /**
     * Check if neighbour is favorite
     * @param neighbour
     */
    public Boolean favorite (Neighbour neighbour){
        return neighbours.contains(neighbour);
    }

}
