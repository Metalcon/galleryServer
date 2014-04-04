package de.metalcon.sdd.testBandRecordUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.metalcon.domain.Muid;
import de.metalcon.domain.MuidType;
import de.metalcon.exceptions.ServiceOverloadedException;
import de.metalcon.sdd.Sdd;
import de.metalcon.sdd.WriteTransaction;
import de.metalcon.sdd.api.exception.InvalidConfigException;
import de.metalcon.sdd.config.Config;
import de.metalcon.sdd.config.XmlConfig;

public class Parse {

    public static final int TRANSACTION_LENGTH = 5;

    public static final long BAND_ID_PREFIX = 1000000000L;

    public static final long RECORD_ID_PREFIX = 2000000000L;

    public static final long USER_ID_PREFIX = 3000000000L;

    private Sdd sdd;

    @Before
    public void setUp() throws InvalidConfigException, IOException {
        Config config = new XmlConfig("src/test/resources/galleryConfig.xml");
        sdd = new Sdd(config);
    }

    @After
    public void tearDown() throws IOException {
        sdd.close();
    }

    @Test
    public void test() throws ServiceOverloadedException {
        Muid muidEntity = Muid.create(MuidType.USER);
        createEntity(muidEntity.getValue());

        Muid muidGallery = Muid.create(MuidType.GENRE);
        createGallery(muidEntity.getValue(), muidGallery.getValue());
    }

    private void createEntity(long id) {
        System.out.println("entity " + id);

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("uid", String.valueOf(id));

        // create entity
        WriteTransaction tx = sdd.createWriteTransaction();
        tx.setProperties(id, "entity", properties);

        // create generic galleries
        long galleryId = id + 1;
        tx.setRelation(id, "entity", "allImages", galleryId);
        tx.setRelation(id, "entity", "allImages", galleryId);

        galleryId += 1;
        tx.setRelation(id, "entity", "newsFeedImages", galleryId);

        tx.commit();
        sdd.waitUntilQueueEmpty();
    }

    private void createGallery(long entityId, long galleryId) {
        System.out.println("Gallery " + galleryId + " @ " + entityId);

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("uid", String.valueOf(galleryId));

        // create gallery
        WriteTransaction tx = sdd.createWriteTransaction();
        tx.setProperties(galleryId, "userGallery", properties);

        tx.commit();
        sdd.waitUntilQueueEmpty();
    }

}
