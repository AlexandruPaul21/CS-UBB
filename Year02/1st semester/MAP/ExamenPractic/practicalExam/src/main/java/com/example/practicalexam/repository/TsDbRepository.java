package com.example.practicalexam.repository;

import com.example.practicalexam.domain.TrainStation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TsDbRepository {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public TsDbRepository(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    public List<TrainStation> getDirectRoutes(Integer idDep, Integer idDest) {
        List<TrainStation> trainStations = new ArrayList<>();
        String sql = "SELECT * FROM train_stations WHERE departure_city = ? AND destination_city = ?";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setInt(1, idDep);
            preparedStatement.setInt(2, idDest);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int trainId = resultSet.getInt("train_id");
                int departureCity = resultSet.getInt("departure_city");
                int destinationCity = resultSet.getInt("destination_city");

                TrainStation trainStation = new TrainStation(trainId, departureCity, destinationCity);
                trainStation.setId(id);
                trainStations.add(trainStation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainStations;
    }

    public List<List<TrainStation>> getRoutes(Integer id1, Integer id2) {
        List<TrainStation> trainStations = new ArrayList<>();
        String sql = "SELECT * FROM train_stations";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int trainId = resultSet.getInt("train_id");
                int departureCity = resultSet.getInt("departure_city");
                int destinationCity = resultSet.getInt("destination_city");

                TrainStation trainStation = new TrainStation(trainId, departureCity, destinationCity);
                trainStation.setId(id);
                trainStations.add(trainStation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<List<TrainStation>> finalResult = new ArrayList<>();
        List<TrainStation> result = new ArrayList<>();
        TrainStation ts = null;
        bk(trainStations, ts, finalResult, result, id1, id2);

        return finalResult;
    }

    private void bk(List<TrainStation> trainStations, TrainStation ts, List<List<TrainStation>> fResult, List<TrainStation> partialResult, Integer from, Integer destination) {
        if (ts != null && ts.getDestinationCityId().equals(destination)) {
            if (partialResult.size() > 1) {
                List<TrainStation> trainStations1 = new ArrayList<>(partialResult);
                fResult.add(trainStations1);
            }
            return;
        }
        for (TrainStation ts1 : trainStations) {
            if (partialResult.contains(ts1)) {
                continue;
            }
            if (ts == null) {
                if (ts1.getDepartureCityId().equals(from)) {
                    partialResult.add(ts1);
                    bk(trainStations, ts1, fResult, partialResult, from, destination);
                    partialResult.remove(ts1);
                }
                continue;
            }
            if (ts1.getDepartureCityId().equals(ts.getDestinationCityId())) {
                partialResult.add(ts1);
                bk(trainStations, ts1, fResult, partialResult, from, destination);
                partialResult.remove(ts1);
            }
        }
    }
}
