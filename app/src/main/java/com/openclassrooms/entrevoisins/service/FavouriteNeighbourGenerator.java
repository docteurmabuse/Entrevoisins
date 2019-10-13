package com.openclassrooms.entrevoisins.service;


import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

public abstract class FavouriteNeighbourGenerator {
    public static List<Neighbour> FAVOURITE_NEIGHBOURS = Arrays.asList(
            new Neighbour(1, "fgff", "http://i.pravatar.cc/150?u=a042581f4e29026704d"),
            new Neighbour(12, "bbnnb", "http://i.pravatar.cc/150?u=a042581f3e39026702d")
    );

    static List<Neighbour> generateFavoriteNeighbour() {
        return new ArrayList<>(FAVOURITE_NEIGHBOURS);
    }
}

