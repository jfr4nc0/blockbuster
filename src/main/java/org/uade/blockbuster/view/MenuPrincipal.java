package org.uade.blockbuster.view;

import org.uade.blockbuster.controller.FuncionController;
import org.uade.blockbuster.controller.PeliculasController;
import org.uade.blockbuster.controller.SucursalController;
import org.uade.blockbuster.controller.dto.FuncionDto;
import org.uade.blockbuster.controller.dto.PeliculaDto;
import org.uade.blockbuster.controller.dto.SucursalDto;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MenuPrincipal extends JFrame {
    private JLabel peliculaIdLbl, peliculaNombreLbl, generoPeliculaLbl, duracionPeliculaLbl, directorPeliculaLbl, actoresPeliculaLbl, tipoProyeccionLbl;
    private JLabel fechaFuncionLbl, horarioFuncionLbl, sucursalIdLbl, salaIdLbl, precioEntradaLbl;

    private JTextField peliculaIdTxt, peliculaNombreTxt, generoPeliculaTxt, duracionPeliculaTxt, directorPeliculaTxt, actoresPeliculaTxt, tipoProyeccionTxt;
    private JTextField fechaFuncionTxt, horarioFuncionTxt, sucursalIdTxt, salaIdTxt, precioEntradaTxt;

    private JButton registrarNuevaFuncion, registrarPelicula, consultarPeliculas, emitirReportePeliculasConMayorRecaudacion;
    private JButton confirmarNuevaPelicula, confirmarNuevaFuncion, confirmarConsultarPeliculas, flushBtn;

    JComboBox<PeliculaDto> peliculasDisponiblesComboBox;
    JComboBox<SucursalDto> sucursalesDisponiblesComboBox;

    private static final long serialVersionUUID = 1L;
    private JPanel contentPane;

    public MenuPrincipal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        initializeComponents();

        initializeRegistrarNuevaFuncionPorGeneroComponent();

        initializeRegistrarPeliculaPorGeneroComponent();

        initializeConsultarPeliculasPorGeneroComponent();

        initializeEmitirReportePeliculasConMayorRecaudacion();

        JScrollPane scrollPane = new JScrollPane(contentPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setContentPane(scrollPane);
    }

    private void initializeEmitirReportePeliculasConMayorRecaudacion() {
    }

    private void initializeConsultarPeliculasPorGeneroComponent() {

    }

    private void initializeRegistrarPeliculaPorGeneroComponent() {

    }

    private void initializeRegistrarNuevaFuncionPorGeneroComponent() {
        registrarNuevaFuncion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayRegistrarNuevaFuncionPorGeneroForm();
            }
        });
        contentPane.add(registrarNuevaFuncion);

        confirmarNuevaFuncion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int sucursalId = Integer.parseInt(peliculaIdTxt.getText());
                    int peliculaId = Integer.parseInt(peliculaIdTxt.getText());
                    LocalTime horario = LocalTime.parse(generoPeliculaTxt.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                    LocalDate fecha = LocalDate.parse(fechaFuncionTxt.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    double precioEntrada = Double.parseDouble(precioEntradaTxt.getText());
                    int salaId = Integer.parseInt(salaIdTxt.getText());

                    FuncionDto nuevaFuncion = new FuncionDto(peliculaId, sucursalId, salaId, precioEntrada, horario, fecha);
                    int nuevaFuncionId = FuncionController.getInstance().agregarFuncion(nuevaFuncion);
                    JOptionPane.showMessageDialog(null, "Se agrego la funcion con el id: " + nuevaFuncionId, "Carga con Exito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void displayRegistrarNuevaFuncionPorGeneroForm() {
        hideAllForms();

        JPanel nuevaFuncionPanel = new JPanel();
        nuevaFuncionPanel.setLayout(new BoxLayout(nuevaFuncionPanel, BoxLayout.Y_AXIS));

        nuevaFuncionPanel.add(sucursalIdLbl);
        nuevaFuncionPanel.add(sucursalesDisponiblesComboBox);
        nuevaFuncionPanel.add(peliculaIdLbl);
        nuevaFuncionPanel.add(peliculasDisponiblesComboBox);
        nuevaFuncionPanel.add(horarioFuncionLbl);
        nuevaFuncionPanel.add(horarioFuncionTxt);
        nuevaFuncionPanel.add(fechaFuncionLbl);
        nuevaFuncionPanel.add(fechaFuncionTxt);
        nuevaFuncionPanel.add(precioEntradaLbl);
        nuevaFuncionPanel.add(precioEntradaTxt);
        nuevaFuncionPanel.add(salaIdLbl);
        nuevaFuncionPanel.add(salaIdTxt);
        nuevaFuncionPanel.add(confirmarNuevaFuncion);

        contentPane.add(nuevaFuncionPanel);  // Add this new form panel to contentPane

        contentPane.revalidate();
        contentPane.repaint();
    }

    private void hideAllForms() {
        contentPane.removeAll();
        contentPane.add(registrarNuevaFuncion);
        contentPane.add(registrarPelicula);
        contentPane.add(consultarPeliculas);
        contentPane.add(emitirReportePeliculasConMayorRecaudacion);
        contentPane.revalidate();
        contentPane.repaint();
    }

    private void initializeComponents() {
        peliculaIdLbl = new JLabel("Pelicula: ");
        peliculaNombreLbl = new JLabel("Pelicula Nombre: ");
        generoPeliculaLbl = new JLabel("Genero: ");
        duracionPeliculaLbl = new JLabel("Duracion: ");
        directorPeliculaLbl = new JLabel("Director: ");
        actoresPeliculaLbl = new JLabel("Actores: ");
        tipoProyeccionLbl = new JLabel("Tipo de Proyecto: ");
        fechaFuncionLbl = new JLabel("Fecha (Format: dd/MM/yyyy): ");
        horarioFuncionLbl = new JLabel("Hora (Format: HH:mm): ");
        sucursalIdLbl = new JLabel("Sucursal: ");
        salaIdLbl = new JLabel("Sala: ");
        precioEntradaLbl = new JLabel("Precio Entrada: ");

        peliculaIdTxt = new JTextField();
        peliculaNombreTxt = new JTextField();
        generoPeliculaTxt = new JTextField();
        duracionPeliculaTxt = new JTextField();
        directorPeliculaTxt = new JTextField();
        actoresPeliculaTxt = new JTextField();
        tipoProyeccionTxt = new JTextField();
        fechaFuncionTxt = new JTextField();
        horarioFuncionTxt = new JTextField();
        sucursalIdTxt = new JTextField();
        salaIdTxt = new JTextField();
        precioEntradaTxt = new JTextField();

        registrarNuevaFuncion = new JButton("Registrar Nueva Funcion");
        registrarPelicula = new JButton("Registrar Pelicula");
        consultarPeliculas = new JButton("Consultar Peliculas");
        emitirReportePeliculasConMayorRecaudacion = new JButton("Emitir Reporte Peliculas");
        confirmarNuevaFuncion = new JButton("Confirmar: ");
        confirmarNuevaPelicula = new JButton("Confirmar: ");
        confirmarConsultarPeliculas = new JButton("Confirmar: ");

        List<PeliculaDto> peliculasDisponibles = PeliculasController.getInstance().getPeliculasDisponibles();
        peliculasDisponiblesComboBox = new JComboBox<>(peliculasDisponibles.toArray(new PeliculaDto[0]));

        List<SucursalDto> sucursalesDisponibles = SucursalController.getInstance().getSucursalesDto();
        sucursalesDisponiblesComboBox = new JComboBox<>(sucursalesDisponibles.toArray(new SucursalDto[0]));
    }
}
