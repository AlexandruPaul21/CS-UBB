package org.example.response;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    List<Country> countryResults;
    ResponseType responseType;

    public Response(ResponseType responseType, List<Country> countryResults) {
        this.responseType = responseType;
        this.countryResults = countryResults;
    }

    public List<Country> getCountryResults() {
        return countryResults;
    }

    public ResponseType getResponseType() {
        return responseType;
    }
}
