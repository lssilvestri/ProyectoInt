package com.dh.clinica.service.impl;

import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.repository.IOdontologoRepository;
import com.dh.clinica.service.IOdontologoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {

    private IOdontologoRepository iOdontologoRepository;

    public OdontologoService(IOdontologoRepository iOdontologoRepository) {
        this.iOdontologoRepository = iOdontologoRepository;
    }

    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return iOdontologoRepository.save(odontologo);
    }

    @Override
    public Optional<Odontologo> buscarPorId(Integer id) {
        return iOdontologoRepository.findById(id);
    }

    public List<Odontologo> buscarTodos(){
        return iOdontologoRepository.findAll();
    }

    public void modificarOdontologo(Odontologo odontologo){
        iOdontologoRepository.save(odontologo);
    }

    public void eliminarOdontologo(Integer id){
        iOdontologoRepository.deleteById(id);
    }
}