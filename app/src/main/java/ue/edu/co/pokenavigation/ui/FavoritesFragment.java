package ue.edu.co.pokenavigation.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ue.edu.co.pokenavigation.R;
import ue.edu.co.pokenavigation.data.model.Pokemon;
import ue.edu.co.pokenavigation.data.repository.FavoritesManager;
import ue.edu.co.pokenavigation.ui.adapter.PokemonAdapter;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerFavoritos;
    private TextView tvEmptyFavorites;
    private PokemonAdapter adapter;

    public FavoritesFragment() {
        super(R.layout.fragment_favorites);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        recyclerFavoritos = view.findViewById(R.id.recyclerFavoritos);
        tvEmptyFavorites = view.findViewById(R.id.tvEmptyFavorites);

        adapter = new PokemonAdapter(this::abrirDetalle);

        recyclerFavoritos.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerFavoritos.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        actualizarLista();
    }

    private void actualizarLista() {
        var favoritos = FavoritesManager.obtenerFavoritos();
        adapter.actualizarDatos(favoritos);

        if (favoritos.isEmpty()) {
            tvEmptyFavorites.setVisibility(View.VISIBLE);
            recyclerFavoritos.setVisibility(View.GONE);
        } else {
            tvEmptyFavorites.setVisibility(View.GONE);
            recyclerFavoritos.setVisibility(View.VISIBLE);
        }
    }

    private void abrirDetalle(Pokemon pokemon) {
        PokemonDetailFragment detailFragment =
                PokemonDetailFragment.newInstance(pokemon.getName(), pokemon.getUrl());

        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}