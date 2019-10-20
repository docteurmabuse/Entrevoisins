package com.openclassrooms.entrevoisins.service;


import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

public abstract class FavouriteNeighbourGenerator {
    public static List<Neighbour> FAVOURITE_NEIGHBOURS = new ArrayList<Neighbour>();

    static List<Neighbour> generateFavoriteNeighbour() {
        return new ArrayList<>(FAVOURITE_NEIGHBOURS);
    }
}

