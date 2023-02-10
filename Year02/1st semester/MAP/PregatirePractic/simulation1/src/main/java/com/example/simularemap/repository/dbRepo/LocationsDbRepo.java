package com.example.simularemap.repository.dbRepo;

import com.example.simularemap.domain.Location;
import com.example.simularemap.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationsDbRepo implements Repository<Double, Location> {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public LocationsDbRepo(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    @Override
    public Location findOne(Double aDouble) {
        return null;
    }

    @Override
    public List<Location> findAll() {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM locations";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Double locationId = resultSet.getDouble("locationId");
                String hotelName = resultSet.getString("locationName");
                Location location = new Location(hotelName);
                location.setId(locationId);
                locations.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }

    @Override
    public Location save(Location entity) {
        return null;
    }

    @Override
    public Location delete(Double aDouble) {
        return null;
    }

    @Override
    public Location update(Location entity) {
        return null;
    }
}
