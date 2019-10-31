package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Deletes a neighbour
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Neighbour> getFavoriteNeighbours();

    /**
     * Add a favorite neighbour
     * {@param neighbour}
     */
      void addFavoriteNeighbour(Neighbour neighbour);

    /**
     * Check if neighbour is favorite
     * @param neighbour
     */
     Boolean isFavorite (Neighbour neighbour);

    /**
     * Deletes a neighbour
     * @param neighbour
     */
    void deleteFavoriteNeighbour(Neighbour neighbour);

}
