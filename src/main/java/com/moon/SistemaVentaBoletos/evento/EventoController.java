package com.moon.SistemaVentaBoletos.evento;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/eventos")
public class EventoController {
    private final EventoService eventoService;

    public EventoController(EventoService eventoService){
        this.eventoService = eventoService;
    }

    @GetMapping
    public ResponseEntity<List<Evento>> obtenerEventos(){
        List<Evento> eventos = eventoService.obtenerTodosLosEventos();
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> obtenerEventoPorId(@PathVariable Long id){
        Optional<Evento> evento = eventoService.obtenerEventoPorId(id);
        return evento.map(value -> new ResponseEntity<>(value,HttpStatus.OK)).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Evento> crearEvento(@RequestBody Evento evento){
        try {
            Evento nuevoEvento = eventoService.crearEvento(evento);
            return new ResponseEntity<>(nuevoEvento, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> actualizarEvento(@PathVariable Long id, @RequestBody Evento eventoActualizado){
        Evento evento = eventoService.actualizarEvento(id, eventoActualizado);
        if(evento != null ){
            return new ResponseEntity<>(evento,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Evento> eliminarEvento(@PathVariable Long id){
        eventoService.eliminarEvento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
