package org.uade.blockbuster.view;

import javax.swing.*;

public class MenuPrincipal extends JFrame {


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

    }

    private void initializeComponents() {

    }
}
