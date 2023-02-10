package com.example.simulation5.service;

import com.example.simulation5.domain.Consultatie;
import com.example.simulation5.domain.Medic;
import com.example.simulation5.domain.Sectie;
import com.example.simulation5.repository.ConsultatiiDbRepository;
import com.example.simulation5.repository.MediciDbRepository;
import com.example.simulation5.repository.SectieDbRepository;
import com.example.simulation5.utils.Observable;
import com.example.simulation5.utils.Observer;

import java.util.Date;
import java.util.List;

public class Service extends Observable {
    private SectieDbRepository sectieDbRepository;
    private MediciDbRepository mediciDbRepository;
    private ConsultatiiDbRepository consultatiiDbRepository;

    public Service(SectieDbRepository sectieDbRepository, MediciDbRepository mediciDbRepository, ConsultatiiDbRepository consultatiiDbRepository) {
        this.sectieDbRepository = sectieDbRepository;
        this.mediciDbRepository = mediciDbRepository;
        this.consultatiiDbRepository = consultatiiDbRepository;
    }

    public List<Sectie> findAllSectii() {
        return sectieDbRepository.findAll();
    }

    public Sectie getSectieForId(Long id) {
        return sectieDbRepository.findOne(id);
    }

    public List<Medic> findAllMedici() {
        return mediciDbRepository.findAll();
    }

    public List<Consultatie> findConsultatiiForMedic(Long id) {
        return consultatiiDbRepository.findAllForMed(id);
    }

    public List<Medic> getMediciForSectie(Long idSectie) {
        return mediciDbRepository.getMedicForSectie(idSectie);
    }

    public boolean addProgramare(Long medId, Long cnp, String nume, Date data, int ora) {
        Consultatie consultatie = new Consultatie(medId, cnp, nume, data, ora);
        return consultatiiDbRepository.save(consultatie);
    }
}
