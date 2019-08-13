package com.ahyoxsoft.remoteresourceloader.util;

import android.util.JsonReader;

import com.ahyoxsoft.remoteresourceloader.data.pojo.ResourceInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Utility {

    public String getStringFromResponse(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
            String line;

            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
    public List<ResourceInfo> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readResourceArray(reader);
        } finally {
            reader.close();
        }
    }

    public List<ResourceInfo> readResourceArray(JsonReader reader) throws IOException {
        List<ResourceInfo> resourceInfos = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            resourceInfos.add(readMessage(reader));
        }
        reader.endArray();
        return resourceInfos;
    }

    public ResourceInfo readMessage(JsonReader reader) throws IOException {
        int id = -1;
        String author = null;
        String url = null;
        String download_url = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextInt();
            } else if (name.equals("author")) {
                author = reader.nextString();
            } else if (name.equals("url")) {
                url = reader.nextString();
            } else if (name.equals("download_url")) {
                download_url = reader.nextString();
            }  else {
                reader.skipValue();
            }

        }
        reader.endObject();
        int index = download_url.lastIndexOf("/");
        return new ResourceInfo(download_url.substring(0, index+1), download_url.substring(index+1), String.valueOf(id));
    }

}
