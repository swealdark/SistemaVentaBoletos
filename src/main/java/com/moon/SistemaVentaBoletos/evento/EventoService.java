package com.moon.SistemaVentaBoletos.evento;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository){
        this.eventoRepository = eventoRepository;
    }
    public List<Evento> obtenerTodosLosEventos(){
        return eventoRepository.findAll();
    }
    public Optional<Evento> obtenerEventoPorId(Long id){
        return eventoRepository.findById(id);
    }
    public Evento crearEvento(Evento evento){
        return eventoRepository.save(evento);
    }
    public Evento actualizarEvento(Long id, Evento eventoActualizado){
        Optional<Evento> eventoOptional = eventoRepository.findById(id);
        if(eventoOptional.isPresent()){
            Evento evento = eventoOptional.get();
            evento.setNombre(eventoActualizado.getNombre());
            evento.setDescripcion(eventoActualizado.getDescripcion());
            evento.setFecha(eventoActualizado.getFecha());
            evento.setLugar(eventoActualizado.getLugar());
            evento.setCapacidad(eventoActualizado.getCapacidad());
            evento.setBoletosDisponibles(eventoActualizado.getBoletosDisponibles());
            evento.setPrecio(eventoActualizado.getPrecio());
            return eventoRepository.save(evento);
        }return null;
    }
    public void eliminarEvento(Long id){
        eventoRepository.deleteById(id);
    }
}
