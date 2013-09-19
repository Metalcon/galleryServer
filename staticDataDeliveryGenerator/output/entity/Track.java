package de.metalcon.sdd.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONValue;

import de.metalcon.common.Muid;
import de.metalcon.sdd.Detail;
import de.metalcon.sdd.server.Server;

public class Track extends Entity {


    public Track(Server server) {
        super(server);
    }

    @Override
    public void loadFromJson(String json) {
        Map<String, String> entity = parseJson(json);

        setId(new Muid(entity.get("id")));

    }

    @Override
    public void loadFromCreateParams(Map<String, String[]> params) {
        setId(new Muid(getParam(params, "id")));

    }

    @Override
    public void loadFromUpdateParams(Map<String, String[]> params) {
        Muid id = new Muid(getParam(params, "id"));
        loadFromId(id);

        //<LOAD_FROM_UPDATE_PARAMS>
    }

    @Override
    protected void generateJson() {
        Map<String, Object> j;
        // FULL
        j = new LinkedHashMap<String, Object>();
        j.put("id", getId().toString());
        json.put(Detail.FULL, JSONValue.toJSONString(j));

        // SYMBOL
        j = new LinkedHashMap<String, Object>();
        j.put("id", getId().toString());
        json.put(Detail.SYMBOL, JSONValue.toJSONString(j));

        // LINE
        j = new LinkedHashMap<String, Object>();
        j.put("id", getId().toString());
        json.put(Detail.LINE, JSONValue.toJSONString(j));

        // PARAGRAPH
        j = new LinkedHashMap<String, Object>();
        j.put("id", getId().toString());
        json.put(Detail.PARAGRAPH, JSONValue.toJSONString(j));

        // PROFILE
        j = new LinkedHashMap<String, Object>();
        j.put("id", getId().toString());
        json.put(Detail.PROFILE, JSONValue.toJSONString(j));

        // TOOLTIP
        j = new LinkedHashMap<String, Object>();
        j.put("id", getId().toString());
        json.put(Detail.TOOLTIP, JSONValue.toJSONString(j));

        // SEARCH_ENTRY
        j = new LinkedHashMap<String, Object>();
        j.put("id", getId().toString());
        json.put(Detail.SEARCH_ENTRY, JSONValue.toJSONString(j));

        // SEARCH_DETAILED
        j = new LinkedHashMap<String, Object>();
        j.put("id", getId().toString());
        json.put(Detail.SEARCH_DETAILED, JSONValue.toJSONString(j));
    }

}