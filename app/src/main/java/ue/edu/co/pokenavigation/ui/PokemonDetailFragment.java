package ue.edu.co.pokenavigation.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import ue.edu.co.pokenavigation.R;
import ue.edu.co.pokenavigation.data.model.Pokemon;
import ue.edu.co.pokenavigation.data.model.PokemonDetail;
import ue.edu.co.pokenavigation.data.model.TypeSlot;
import ue.edu.co.pokenavigation.data.repository.FavoritesManager;
import ue.edu.co.pokenavigation.data.repository.PokemonRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonDetailFragment extends Fragment {

    private static final String ARG_NAME = "pokemon_name";
    private static final String ARG_URL = "pokemon_url";

    private ImageView imgPokemon;
    private TextView tvName;
    private TextView tvTypes;
    private TextView tvHeight;
    private TextView tvWeight;
    private TextView tvExperience;
    private MaterialButton btnGuardarFavorito;
    private CircularProgressIndicator progressDetail;

    private PokemonRepository repository;
    private Call<PokemonDetail> currentCall;

    private String pokemonName;
    private String pokemonUrl;

    public PokemonDetailFragment() {
        super(R.layout.fragment_detail);
    }

    public static PokemonDetailFragment newInstance(String name, String url) {
        PokemonDetailFragment fragment = new PokemonDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            pokemonName = getArguments().getString(ARG_NAME);
            pokemonUrl = getArguments().getString(ARG_URL);
        }

        imgPokemon = view.findViewById(R.id.imgPokemon);
        tvName = view.findViewById(R.id.tvName);
        tvTypes = view.findViewById(R.id.tvTypes);
        tvHeight = view.findViewById(R.id.tvHeight);
        tvWeight = view.findViewById(R.id.tvWeight);
        tvExperience = view.findViewById(R.id.tvExperience);
        btnGuardarFavorito = view.findViewById(R.id.btnGuardarFavorito);
        progressDetail = view.findViewById(R.id.progressDetail);

        repository = new PokemonRepository();

        btnGuardarFavorito.setOnClickListener(v -> guardarFavorito());

        cargarDetalle();
    }

    private void cargarDetalle() {
        progressDetail.setVisibility(View.VISIBLE);

        currentCall = repository.obtenerDetallePokemon(pokemonName);

        currentCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(
                    @NonNull Call<PokemonDetail> call,
                    @NonNull Response<PokemonDetail> response
            ) {
                if (!isAdded()) {
                    return;
                }

                progressDetail.setVisibility(View.GONE);

                PokemonDetail detail = response.body();

                if (response.isSuccessful() && detail != null) {
                    mostrarDetalle(detail);
                } else {
                    Toast.makeText(
                            requireContext(),
                            "No fue posible obtener el detalle.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<PokemonDetail> call,
                    @NonNull Throwable throwable
            ) {
                if (call.isCanceled() || !isAdded()) {
                    return;
                }

                progressDetail.setVisibility(View.GONE);

                Toast.makeText(
                        requireContext(),
                        "Error de conexión. Verifique internet.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void mostrarDetalle(PokemonDetail detail) {
        tvName.setText(detail.getName());
        tvHeight.setText("Altura: " + detail.getHeight());
        tvWeight.setText("Peso: " + detail.getWeight());
        tvExperience.setText("Experiencia base: " + detail.getBaseExperience());

        StringBuilder tipos = new StringBuilder("Tipos: ");
        if (detail.getTypes() != null) {
            for (int i = 0; i < detail.getTypes().size(); i++) {
                TypeSlot slot = detail.getTypes().get(i);
                tipos.append(slot.getType().getName());
                if (i < detail.getTypes().size() - 1) {
                    tipos.append(", ");
                }
            }
        }
        tvTypes.setText(tipos.toString());

        if (detail.getSprites() != null
                && detail.getSprites().getOther() != null
                && detail.getSprites().getOther().getOfficialArtwork() != null) {

            String imageUrl = detail.getSprites()
                    .getOther()
                    .getOfficialArtwork()
                    .getFrontDefault();

            Glide.with(this)
                    .load(imageUrl)
                    .into(imgPokemon);
        }
    }

    private void guardarFavorito() {
        Pokemon pokemon = new Pokemon(pokemonName, pokemonUrl);
        FavoritesManager.agregar(pokemon);

        Toast.makeText(
                requireContext(),
                pokemonName + " guardado en Favoritos",
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    public void onDestroyView() {
        if (currentCall != null) {
            currentCall.cancel();
        }
        super.onDestroyView();
    }
}