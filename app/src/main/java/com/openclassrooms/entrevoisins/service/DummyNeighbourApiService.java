package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        ArrayList<Neighbour> favoriteList = new ArrayList();
        for (Neighbour neighbour : getNeighbours()) {
            if (neighbour.isFavorite()) {
                favoriteList.add(neighbour);
            }
        }
        return favoriteList;
    }

    /**
     * Add a favorite neighbour
     * {@param neighbour}
     */
    @Override
    public void addFavoriteNeighbour(Neighbour neighbour) {
        for (Neighbour favNeighbour : getNeighbours()) {
            if (neighbour.getId() == favNeighbour.getId()) {
                favNeighbour.setFavorite(true);
            }
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteFavoriteNeighbour(Neighbour neighbour) {
        neighbour.setFavorite(false);
    }


    /**
     * Check if neighbour is favorite
     *
     * @param neighbour
     */
    @Override
    public Boolean isFavorite(Neighbour neighbour) {
        return neighbour.isFavorite();
    }
}
