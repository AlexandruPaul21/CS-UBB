package com.example.practicalexam.service;

import com.example.practicalexam.domain.City;
import com.example.practicalexam.domain.TrainStation;
import com.example.practicalexam.repository.CitiesDbRepository;
import com.example.practicalexam.repository.TsDbRepository;
import com.example.practicalexam.utils.Observable;
import javafx.scene.effect.ImageInput;

import java.util.ArrayList;
import java.util.List;

public class Service extends Observable {
    private CitiesDbRepository citiesDbRepository;
    private TsDbRepository tsDbRepository;

    public Service(CitiesDbRepository citiesDbRepository, TsDbRepository tsDbRepository) {
        this.citiesDbRepository = citiesDbRepository;
        this.tsDbRepository = tsDbRepository;
    }

    public List<City> getAllCities() {
        return citiesDbRepository.findAll();
    }

    public List<List<TrainStation>> getAllDirectRoutes(Integer idDep, Integer idDest) {
        List<List<TrainStation>> trains = tsDbRepository.getRoutes(idDep, idDest);
        List<TrainStation> ts1 = tsDbRepository.getDirectRoutes(idDep, idDest);
        trains.add(ts1);
        List<List<TrainStation>> finalRoutes = new ArrayList<>();
        for (List<TrainStation> route : trains) {
            boolean multiTrain = false;
            if (route.isEmpty()) {
                continue;
            }
            Integer id = route.get(0).getTrainId();
            for (TrainStation ts : route) {
                if (!id.equals(ts.getTrainId())) {
                    multiTrain = true;
                    break;
                }
            }
            if (!multiTrain) {
                finalRoutes.add(route);
            }
        }
        notifyObs(getCityById(idDep), getCityById(idDest));
        return finalRoutes;
    }

    public City getCityById(Integer id) {
        return citiesDbRepository.findOne(id);
    }

    public List<List<TrainStation>> getNotDirectedRoutes(Integer id1, Integer id2) {
        notifyObs(getCityById(id1), getCityById(id2));
        List<List<TrainStation>> trains = tsDbRepository.getRoutes(id1, id2);
        List<TrainStation> ts1 = tsDbRepository.getDirectRoutes(id1, id2);
        trains.add(ts1);
        return trains;
    }
}
