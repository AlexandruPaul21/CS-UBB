package com.example.practicalexam.domain;

public class TrainStation extends Entity<Integer> {
    private Integer trainId;
    private Integer departureCityId;
    private Integer destinationCityId;

    public TrainStation(Integer trainId, Integer departureCityId, Integer destinationCityId) {
        this.trainId = trainId;
        this.departureCityId = departureCityId;
        this.destinationCityId = destinationCityId;
    }

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public Integer getDepartureCityId() {
        return departureCityId;
    }

    public void setDepartureCityId(Integer departureCityId) {
        this.departureCityId = departureCityId;
    }

    public Integer getDestinationCityId() {
        return destinationCityId;
    }

    public void setDestinationCityId(Integer destinationCityId) {
        this.destinationCityId = destinationCityId;
    }

    @Override
    public String toString() {
        return "TrainStation{" +
                "trainId=" + trainId +
                ", departureCityId=" + departureCityId +
                ", destinationCityId=" + destinationCityId +
                '}';
    }
}
