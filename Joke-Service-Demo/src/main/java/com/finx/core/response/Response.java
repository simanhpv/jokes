package com.finx.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finx.core.dto.JokeDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Response implements Serializable {
    @JsonProperty("total")
    private int total;
    @JsonProperty("result")
    List<JokeDTO> result = new ArrayList< JokeDTO >();

    public Response(){

    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }

    public List<JokeDTO> getResult() {
        return result;
    }

    public void setResult(ArrayList<JokeDTO> result) {
        this.result = result;
    }
}
