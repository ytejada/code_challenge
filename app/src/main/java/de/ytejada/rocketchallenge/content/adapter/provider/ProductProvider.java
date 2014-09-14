package de.ytejada.rocketchallenge.content.adapter.provider;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.octo.android.robospice.GsonGoogleHttpClientSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.ytejada.rocketchallenge.content.adapter.ClothesItem;

import static de.ytejada.rocketchallenge.fragments.SearchFragment.SortingType;

/**
 * Abstraction for the Rest api located at "{@code https://www.zalora.com.my/mobile-api/women/clothing/}".
 * This class uses a service for retrieving results of searches performed against the api.
 * Searches can be parametrized with the desired sorting type.
 */
public class ProductProvider implements RequestListener<Data[]> {


    private SpiceManager mSpiceManager = new SpiceManager(GsonGoogleHttpClientSpiceService.class);

    private final Set<ContentProviderListener> mListeners = new HashSet<ContentProviderListener>();


    public void setup(Activity a) {
        mSpiceManager.start(a);
    }

    public void onDestroy() {
        mSpiceManager.shouldStop();
    }

    public void addContentProviderListener(ContentProviderListener l) {
        mListeners.add(l);
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        Log.w("RoboProvider", "onRequestFailure: ");
        for (ContentProviderListener curListener : mListeners) {
            curListener.onError("", spiceException.getMessage());
        }
    }


    @Override
    public void onRequestSuccess(Data[] response) {

        Log.d("RoboProvider", "onRequestSuccess: got " + response.length + " data");
        for (Data film : response) {
            Log.d("RoboProvider", "onRequestSuccess: data: " + film);
        }
        for (ContentProviderListener curListener : mListeners) {
            curListener.onNewContent("", Arrays.asList((ClothesItem[]) response));
        }
    }

    public void sendRequest(final SortingType sortingType) {
        mSpiceManager.execute(new DataRequest(sortingType), null, DurationInMillis.ALWAYS_EXPIRED, this);
    }


    /**
     * Spice Request, for handling http requests.
     */
    final class DataRequest extends GoogleHttpClientSpiceRequest<Data[]> {


        private SortingType sortingType;

        public DataRequest(Class<Data[]> clazz) {
            super(clazz);
            sortingType = null;
        }

        public DataRequest(final SortingType sortingType) {
            this(Data[].class);
            this.sortingType = sortingType;
        }

        @Override
        public Data[] loadDataFromNetwork() throws Exception {
            Log.d("DataRequest", "loadDataFromNetwork: ");

            final Uri.Builder uribuilder = Uri.parse("https://www.zalora.com.my/mobile-api/women/clothing/").buildUpon();
            uribuilder.appendQueryParameter("format", "JSON");
            if (sortingType != SortingType.NONE) {
                uribuilder.appendQueryParameter("sort", getSearchTokenFor(sortingType));
            }

            HttpRequest request = getHttpRequestFactory()//
                    .buildGetRequest(new GenericUrl(uribuilder.build().toString()));
            request.setParser(GsonFactory.getDefaultInstance().createJsonObjectParser());
            final HttpResponse response = request.execute();

            return processDebug(response);
        }

        private Data[] processDebug(HttpResponse response) throws IOException {

            final String responseStr = response.parseAsString();

            JsonParser parser2 = new JsonParser();
            JsonElement element = parser2.parse(responseStr);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Log.d("ProductRequest", "loadDataFromNetwork array :" + element.isJsonArray() + " " + element);

            //Parse manually, while direct data parsing does not work as expected.
            final JsonArray arr = element.getAsJsonObject().getAsJsonObject("metadata").getAsJsonArray("results");
            final int size = arr.size();
            final Data[] cleanData = new Data[size];
            for (int i = 0; i < size; i++) {
                final JsonObject curDataJson = arr.get(i).getAsJsonObject().getAsJsonObject("data");
                final Data curData = gson.fromJson(curDataJson, Data.class);

                final JsonArray curImagesJson = arr.get(i).getAsJsonObject().getAsJsonArray("images");
                final Images[] curImages = gson.fromJson(curImagesJson, Images[].class);
                curData.setImages(Arrays.asList(curImages));
                cleanData[i] = curData;
            }

            return cleanData;
        }

    }


    /**
     * Returns the parameter token to be used in the Rest api call for the given {@link SortingType}.
     */
    private static String getSearchTokenFor(SortingType type) {
        switch (type) {

            case BRAND:
                return "brand";
            case POPULARITY:
                return "popularity";
            case NAME:
                return "name";
            case PRICE:
                return "price";
            default:
                return "";

        }
    }

}
