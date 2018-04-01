package com.lly.test;

import com.lly.util.DownLoad;
import com.lly.util.ReadFileToJson;
import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import static java.lang.System.out;
public class ReadJsonTest {

    @Test
    public void testReadJson(){
        List<String> list = ReadFileToJson.readJson("G:/new.json");
        out.println(list.toString());
    }

    @Test
    public void testReadJsonObject() throws Exception {
        List<JSONObject> list = ReadFileToJson.readJsonObjectList("G:/new1.json");
        for(JSONObject object : list){
            DownLoad.download((String)object.get("url"),(String)object.get("folder"),(String)object.get("fileName"));
        }
    }
}
