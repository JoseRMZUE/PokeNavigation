package ue.edu.co.pokenavigation.data.repository;

import ue.edu.co.pokenavigation.data.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class FavoritesManager {

    private static final List<Pokemon> favoritos = new ArrayList<>();

    private FavoritesManager() {
    }

    public static void agregar(Pokemon pokemon) {
        for (Pokemon p : favoritos) {
            if (p.getName().equals(pokemon.getName())) {
                return; // ya está en favoritos
            }
        }
        favoritos.add(pokemon);
    }

    public static List<Pokemon> obtenerFavoritos() {
        return favoritos;
    }

    public static boolean esFavorito(String name) {
        for (Pokemon p : favoritos) {
            if (p.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}