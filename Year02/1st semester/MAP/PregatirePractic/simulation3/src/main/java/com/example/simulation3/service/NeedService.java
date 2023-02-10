package com.example.simulation3.service;

import com.example.simulation3.domain.Need;
import com.example.simulation3.domain.Person;
import com.example.simulation3.repository.NeedDbRepository;
import com.example.simulation3.utils.Observable;

import java.util.List;

public class NeedService extends Observable {
    private NeedDbRepository needDbRepository;

    public NeedService(NeedDbRepository needDbRepository) {
        this.needDbRepository = needDbRepository;
    }

    public List<Need> getNeedsAssigned(Person hero) {
        List<Need> lst = needDbRepository.getNeedsAssigned(hero);
        return lst;
    }

    public boolean markNeedAsSolved(Long id, Person hero) {
        boolean bo = needDbRepository.markNeedAsSolved(id, hero);
        notifyObs();
        return bo;
    }

    public List<Need> getNeedForHero(Person hero) {
        return needDbRepository.getNeedAvbForHero(hero);
    }

    public Need save(Need need) {
        Need need1 = needDbRepository.save(need);
        notifyObs();
        return need1;
    }
}
