package org.example.start;

import org.example.model.Flight;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class RestTest {
    public static final String URL = "http://localhost:8080/api/flight";

    private final RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) throws ServiceException {
        try {
            return callable.call();
        } catch (ResourceAccessException | HttpClientErrorException e) { // server down, resource exception
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Flight[] getAll() throws ServiceException {
        return execute(() -> restTemplate.getForObject(URL, Flight[].class));
    }

    public Flight getById(String id) throws ServiceException {
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), Flight.class));
    }

    public Flight create(Flight user) throws ServiceException {
        return execute(() -> restTemplate.postForObject(URL, user, Flight.class));
    }

    public void update(Flight user) throws ServiceException {
        execute(() -> {
            restTemplate.put(URL, user);
            return null;
        });
    }

    public void delete(String id) throws ServiceException {
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id));
            return null;
        });
    }

}