package ue.edu.co.pokenavigation.data.model;

public class Sprites {
    private OtherSprites other;

    public OtherSprites getOther() { return other; }

    public static class OtherSprites {
        @com.google.gson.annotations.SerializedName("official-artwork")
        private OfficialArtwork officialArtwork;

        public OfficialArtwork getOfficialArtwork() { return officialArtwork; }
    }

    public static class OfficialArtwork {
        private String front_default;

        public String getFrontDefault() { return front_default; }
    }
}