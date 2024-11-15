package org.uade.blockbuster.controller;

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

public class PeliculasController {
    private static volatile PeliculasController INSTANCE;
    private Collection<Pelicula> peliculas;

    private PeliculasController() {
        this.peliculas = new ArrayList<Pelicula>();

        cargaInicial();
    }

    private void cargaInicial() {
        Pelicula laSustancia = new Pelicula(1, TipoGenero.TERROR, "La sustancia", 150, "Coralie Fargeat",
                List.of("Demi Moore", "Margaret Qualley", "Dennis Quaid", "Gore Abrams", "Hugo Diego Garcia", "Olivier Raynal", "Tiffany Hofstetter", "Tom Morton", "Jiselle Burkhalter", "Axel Baille"),
                TipoProyeccion.DOS_D);
        Pelicula elJoker2 = new Pelicula(2, TipoGenero.DRAMA, "JOKER 2", 120, "Todd Phillips",
                List.of("Joaquin Phoenix", "Lady Gaga", "Brendan Gleeson", "Catherine Keener", "Zazie Beetz", "Jacob Lofland", "Harry Lawtey", "Ken Leung", "Steve Coogan", "Gattlin Griffith", "Bill Smitrovich"),
                TipoProyeccion.DOS_D);
        Pelicula noTeSueltes = new Pelicula(3, TipoGenero.SUSPENSO, "No te sueltes", 130, "Alexandre Aja",
                List.of("Halle Berry", "Matthew Kevin Anderson", "Stephanie Lavigne", "Christin Park", "Percy Daggs", "Anthony B. Jenkins"),
                TipoProyeccion.TRES_D);

        peliculas.add(laSustancia);
        peliculas.add(elJoker2);
        peliculas.add(noTeSueltes);
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

    public void agregarPelicula(PeliculaDto peliculaDto) {
        validarNuevaPelicula(peliculaDto);


    }

    private void validarNuevaPelicula(PeliculaDto peliculaDto) {
        if (Objects.isNull(peliculaDto.getNombrePelicula())) throw new IllegalArgumentException("El nombre pelicula no puede ser nulo");
        if (Objects.isNull(peliculaDto.getDirector())) throw new IllegalArgumentException("El director no puede ser nulo");
        if (Objects.isNull(peliculaDto.getGenero())) throw new IllegalArgumentException("El genero no puede ser nulo");
        if (Objects.isNull(peliculaDto.getTipoProyeccion())) throw new IllegalArgumentException("El tipo de proyeccion no puede ser nulo");
        if (existePelicula(peliculaDto.getNombrePelicula(), peliculaDto.getDirector(), peliculaDto.getGenero(), peliculaDto.getTipoProyeccion())) throw new IllegalArgumentException("El pelicula ya existe");
    }

    public boolean existePelicula(String titulo, String director, String genero, String tipoProyeccion) {
        return peliculas.stream()
                .anyMatch(pelicula -> pelicula.getNombrePelicula().equals(titulo) &&
                        pelicula.getDirector().equals(director) &&
                        pelicula.getGenero().equals(TipoGenero.valueOf(genero)) &&
                        pelicula.getTipoProyeccion().equals(TipoProyeccion.valueOf(tipoProyeccion)));
    }

    public Pelicula buscarPeliculaById(int peliculaId) throws NotFoundException {
        return buscarPelicula(pelicula -> pelicula.getPeliculaId() == peliculaId)
                .orElseThrow(() -> new NotFoundException("No se encontro un pelicula con el id " + peliculaId));
    }

    private Optional<Pelicula> buscarPelicula(Predicate<Pelicula> predicate) {
        return peliculas.stream().filter(predicate).findFirst();
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
