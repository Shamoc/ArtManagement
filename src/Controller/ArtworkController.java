package Controller;

import Model.ArtWork;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class ArtworkController {
    private LinkedHashMap<String, ArtWork> artworkList;

    private static ArtworkController artworkListInstance = null;

    private ArtworkController (){

       /* artworkList = new LinkedHashMap<>();
        ArtWork monaLisa =new ArtWork("Mona Lisa","Mysterious painting of a woman", "Leonardo Da Vinci", 1948, "Realism");
        ArtWork sunRise = new ArtWork("Sunrise","Painting of boats and a sunrise", "Claude Monet", 1950, "Impressionism");

        this.artworkList.put("Mona Lisa", monaLisa);
        this.artworkList.put("Sunrise", sunRise);

        */
    }

    public void createArtwork(ArtWork name){
        String artName = name.getName();
        artworkList.put(artName, name);
    }

    public static ArtworkController getInstance() {
        if (artworkListInstance == null) {
            artworkListInstance = new ArtworkController();
        }
        return artworkListInstance;
    }
}
