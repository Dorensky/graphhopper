package com.graphhopper.integration;

import com.graphhopper.GraphHopper;
import com.graphhopper.routing.TestProfiles;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;

import static org.junit.Assert.*;

public class SimpleGraphHopperIT {

    @Test
    public void method() throws Exception {

        String resourcePath = "/com/graphhopper/reader/osm/test-osm.xml";

        URL resource = getClass().getResource(resourcePath);
        assertNotNull("Fichier OSM introuvable: " + resourcePath, resource);

        File osmFile = new File(resource.getFile());
        File graphDir = Files.createTempDirectory("gh-it").toFile();

        GraphHopper hopper = new GraphHopper()
                .setOSMFile(osmFile.getAbsolutePath())
                .setGraphHopperLocation(graphDir.getAbsolutePath())
                .setEncodedValuesString("car_access, car_average_speed")
                .setProfiles(TestProfiles.accessAndSpeed("car", "car"));

        hopper.importOrLoad();

        assertTrue("Le graphe doit contenir des noeuds",
                hopper.getBaseGraph().getNodes() > 0);

        assertTrue("Le graphe doit contenir des arêtes",
                hopper.getBaseGraph().getEdges() > 0);

        hopper.close();
    }
}
