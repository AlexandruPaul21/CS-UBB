package com.example.simularemap.repository.dbRepo;

import com.example.simularemap.domain.SpecialOffer;
import com.example.simularemap.repository.Repository;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpecialOfferDbRepo implements Repository<Double, SpecialOffer> {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public SpecialOfferDbRepo(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    public List<SpecialOffer> findBetweenDates(Double hotelId, Date startDate, Date endDate) {
        List<SpecialOffer> sps = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = formatter.format(startDate);
        String date2 = formatter.format(endDate);
        String sql = "SELECT * FROM special_offers WHERE \'" + date1 + "\' BETWEEN start_date AND end_date AND \'" + date2 + "\' BETWEEN start_date AND end_date " +
                "AND hotel_id = ?";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setDouble(1, hotelId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Double id = resultSet.getDouble("id");
                Date start = resultSet.getDate("start_date");
                Date end = resultSet.getDate("end_date");
                Integer percent = resultSet.getInt("percent");
                Double hotel_id = resultSet.getDouble("hotel_id");
                SpecialOffer sp = new SpecialOffer(hotel_id, start, end, percent);
                sp.setId(id);
                sps.add(sp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sps;
    }

    @Override
    public SpecialOffer findOne(Double aDouble) {
        return null;
    }

    @Override
    public List<SpecialOffer> findAll() {
        return null;
    }

    @Override
    public SpecialOffer save(SpecialOffer entity) {
        return null;
    }

    @Override
    public SpecialOffer delete(Double aDouble) {
        return null;
    }

    @Override
    public SpecialOffer update(SpecialOffer entity) {
        return null;
    }
}
