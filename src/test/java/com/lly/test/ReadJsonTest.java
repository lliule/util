package com.lly.test;

import com.lly.util.DownLoad;
import com.lly.read.ReadFileToJson;
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
        List<JSONObject> list = ReadFileToJson.readJsonObjectList("C:\\Users\\Admin\\Desktop\\paper.json");
        for(JSONObject object : list){
            System.out.println(object.get("exams"));
//            DownLoad.download((String)object.get("url"),(String)object.get("folder"),(String)object.get("fileName"));
        }
    }
}
