package org.uade.blockbuster.controller;

import lombok.extern.slf4j.Slf4j;
import org.uade.blockbuster.controller.dto.PeliculaDto;
import org.uade.blockbuster.exceptions.NotFoundException;
import org.uade.blockbuster.model.Pelicula;
import org.uade.blockbuster.model.enums.TipoGenero;
import org.uade.blockbuster.model.enums.TipoProyeccion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
public class PeliculasController {
    private static volatile PeliculasController INSTANCE;
    private Collection<Pelicula> peliculas;

    private PeliculasController() {
        this.peliculas = new ArrayList<Pelicula>();

        cargaInicial();
    }

    private void cargaInicial() {
        agregarPelicula("La sustancia", "Coralie Fargeat", TipoGenero.TERROR, 150,
                List.of("Demi Moore", "Margaret Qualley", "Dennis Quaid", "Gore Abrams", "Hugo Diego Garcia", "Olivier Raynal", "Tiffany Hofstetter", "Tom Morton", "Jiselle Burkhalter", "Axel Baille"),
                TipoProyeccion.DOS_D);
        agregarPelicula("JOKER 2", "Todd Phillips", TipoGenero.DRAMA, 120,
                List.of("Joaquin Phoenix", "Lady Gaga", "Brendan Gleeson", "Catherine Keener", "Zazie Beetz", "Jacob Lofland", "Harry Lawtey", "Ken Leung", "Steve Coogan", "Gattlin Griffith", "Bill Smitrovich"),
                TipoProyeccion.DOS_D);
        agregarPelicula("No te sueltes", "Alexandre Aja", TipoGenero.SUSPENSO, 130,
                List.of("Halle Berry", "Matthew Kevin Anderson", "Stephanie Lavigne", "Christin Park", "Percy Daggs", "Anthony B. Jenkins"),
                TipoProyeccion.TRES_D);
    }

    public static PeliculasController getInstance() {
        PeliculasController result = INSTANCE;
        if (result != null) {
            return result;
        }
        synchronized (PeliculasController.class) {
            if (INSTANCE == null) {
                INSTANCE = new PeliculasController();
            }
            return INSTANCE;
        }
    }

    public List<PeliculaDto> getPeliculasDisponibles() {
        return peliculas.stream()
                .map(this::toDto)
                .toList();
    }

    public List<PeliculaDto> getPeliculasDisponiblesByGenero(String genero) {
        return peliculas.stream()
                .filter(pelicula -> pelicula.getGenero().equals(TipoGenero.valueOf(genero)))
                .map(this::toDto)
                .toList();
    }

    public int agregarPelicula(PeliculaDto peliculaDto) {
        return agregarPelicula(
                peliculaDto.getNombrePelicula(),
                peliculaDto.getDirector(),
                TipoGenero.valueOf(peliculaDto.getGenero()),
                peliculaDto.getDuracionEnMinutos(),
                peliculaDto.getActores(),
                TipoProyeccion.valueOf(peliculaDto.getTipoProyeccion())
        );
    }

    public int agregarPelicula(String nombrePelicula, String director, TipoGenero genero, int duracionEnMin, Collection<String> actores, TipoProyeccion tipoProyeccion) {
        validarNuevaPelicula(nombrePelicula, director, genero, tipoProyeccion);

        int peliculaId = peliculas.size() + 1;
        Pelicula pelicula = new Pelicula(peliculaId, genero, nombrePelicula, duracionEnMin, director, actores, tipoProyeccion);

        peliculas.add(pelicula);
        log.info("Se agrego la pelicula id: " + peliculaId);

        return peliculaId;
    }

    public List<String> getTiposGeneros() {
        return TipoGenero.getAllGeneros();
    }

    public List<String> getTiposProyeccion() {
        return TipoProyeccion.getAllProyecciones();
    }

    private void validarNuevaPelicula(String nombrePelicula, String director, TipoGenero genero, TipoProyeccion tipoProyeccion) {
        if (Objects.isNull(nombrePelicula)) throw new IllegalArgumentException("El nombre pelicula no puede ser nulo");
        if (Objects.isNull(director)) throw new IllegalArgumentException("El director no puede ser nulo");
        if (Objects.isNull(genero)) throw new IllegalArgumentException("El genero no puede ser nulo");
        if (Objects.isNull(tipoProyeccion)) throw new IllegalArgumentException("El tipo de proyeccion no puede ser nulo");
        if (existePelicula(nombrePelicula, director, genero, tipoProyeccion)) throw new IllegalArgumentException("El pelicula ya existe");
    }

    public boolean existePelicula(String titulo, String director, TipoGenero genero, TipoProyeccion tipoProyeccion) {
        return peliculas.stream()
                .anyMatch(pelicula -> pelicula.getNombrePelicula().equals(titulo) &&
                        pelicula.getDirector().equals(director) &&
                        pelicula.getGenero().equals(genero) &&
                        pelicula.getTipoProyeccion().equals(tipoProyeccion));
    }

    public Pelicula buscarPeliculaById(int peliculaId) throws NotFoundException {
        return buscarPelicula(pelicula -> pelicula.getPeliculaId() == peliculaId)
                .orElseThrow(() -> new NotFoundException("No se encontro un pelicula con el id " + peliculaId));
    }

    private Optional<Pelicula> buscarPelicula(Predicate<Pelicula> predicate) {
        return peliculas.stream().filter(predicate).findFirst();
    }

    public PeliculaDto buscarPeliculaDtoById(int peliculaId) throws NotFoundException {
        return this.toDto(this.buscarPeliculaById(peliculaId));
    }

    private PeliculaDto toDto(Pelicula pelicula) {
        return new PeliculaDto(
                pelicula.getPeliculaId(),
                pelicula.getGenero().name(),
                pelicula.getNombrePelicula(),
                pelicula.getDuracionEnMinutos(),
                pelicula.getDirector(),
                pelicula.getActores(),
                pelicula.getTipoProyeccion().getTipo());
    }
}
