package com.example.simularemap.repository.dbRepo;

import com.example.simularemap.domain.Hotel;
import com.example.simularemap.domain.enums.HotelType;
import com.example.simularemap.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelsDbRepository implements Repository<Double, Hotel> {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public HotelsDbRepository(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    @Override
    public Hotel findOne(Double aDouble) {
        return null;
    }

    @Override
    public List<Hotel> findAll() {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM hotels";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Double hotelId = resultSet.getDouble("hotelId");
                Double locationId = resultSet.getDouble("locationId");
                String hotelName = resultSet.getString("hotelName");
                Integer noRooms = resultSet.getInt("noRooms");
                Double price = (double) resultSet.getFloat("pricePerNight");
                String type = resultSet.getString("type");
                HotelType hotelType;
                if (type.equals("FAMILY")) {
                    hotelType = HotelType.FAMILY;
                } else if (type.equals("TEENAGERS")) {
                    hotelType = HotelType.TEENAGERS;
                } else {
                    hotelType = HotelType.OLD_PEOPLE;
                }
                Hotel hotel = new Hotel(locationId, hotelName, noRooms, price, hotelType);
                hotel.setId(hotelId);
                hotels.add(hotel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    public List<Hotel> findByLocationId(Double id) {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM hotels WHERE \"locationId\" = ?";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setDouble(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Double hotelId = resultSet.getDouble("hotelId");
                Double locationId = resultSet.getDouble("locationId");
                String hotelName = resultSet.getString("hotelName");
                Integer noRooms = resultSet.getInt("noRooms");
                Double price = (double) resultSet.getFloat("pricePerNight");
                String type = resultSet.getString("type");
                HotelType hotelType;
                if (type.equals("FAMILY")) {
                    hotelType = HotelType.FAMILY;
                } else if (type.equals("TEENAGERS")) {
                    hotelType = HotelType.TEENAGERS;
                } else {
                    hotelType = HotelType.OLD_PEOPLE;
                }
                Hotel hotel = new Hotel(locationId, hotelName, noRooms, price, hotelType);
                hotel.setId(hotelId);
                hotels.add(hotel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    @Override
    public Hotel save(Hotel entity) {
        return null;
    }

    @Override
    public Hotel delete(Double aDouble) {
        return null;
    }

    @Override
    public Hotel update(Hotel entity) {
        return null;
    }
}
