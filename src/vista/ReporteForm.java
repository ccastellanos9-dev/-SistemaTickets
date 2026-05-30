package vista;

import conexion.Conexion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class ReporteForm extends JPanel {

    private JTable tabla;
    private DefaultTableModel modelo;
    private String tituloReporte = "";

    public ReporteForm() {
        setLayout(new BorderLayout(10, 10));

        // Panel botones de reportes
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnAbiertos = new JButton("Tickets Abiertos");
        JButton btnUrgentes = new JButton("Tickets Urgentes");
        JButton btnResumen  = new JButton("Resumen por Agente");
        panelBotones.add(btnAbiertos);
        panelBotones.add(btnUrgentes);
        panelBotones.add(btnResumen);

        // Panel botón descargar
        JPanel panelDescargar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnDescargar = new JButton("Descargar Reporte CSV");
        btnDescargar.setBackground(new Color(34, 139, 34));
        btnDescargar.setForeground(Color.WHITE);
        btnDescargar.setFocusPainted(false);
        panelDescargar.add(btnDescargar);

        // Panel norte con ambas filas
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelBotones,   BorderLayout.NORTH);
        panelNorte.add(panelDescargar, BorderLayout.SOUTH);

        modelo = new DefaultTableModel();
        tabla  = new JTable(modelo);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scroll = new JScrollPane(tabla);

        add(panelNorte, BorderLayout.NORTH);
        add(scroll,     BorderLayout.CENTER);

        // Cargar vistas
        btnAbiertos.addActionListener(e -> {
            tituloReporte = "Tickets_Abiertos";
            cargarVista("SELECT * FROM VW_TICKETS_ABIERTOS");
        });
        btnUrgentes.addActionListener(e -> {
            tituloReporte = "Tickets_Urgentes";
            cargarVista("SELECT * FROM VW_TICKETS_URGENTES");
        });
        btnResumen.addActionListener(e -> {
            tituloReporte = "Resumen_por_Agente";
            cargarVista("SELECT * FROM VW_RESUMEN_AGENTE");
        });

        // Descargar CSV
        btnDescargar.addActionListener(e -> descargarCSV());
    }

    private void cargarVista(String sql) {
        modelo.setRowCount(0);
        modelo.setColumnCount(0);
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();
            for (int i = 1; i <= cols; i++)
                modelo.addColumn(meta.getColumnName(i));
            while (rs.next()) {
                Object[] fila = new Object[cols];
                for (int i = 0; i < cols; i++)
                    fila[i] = rs.getObject(i + 1);
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void descargarCSV() {
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                "Primero carga un reporte antes de descargar.",
                "Sin datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Abrir selector de carpeta/archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte como CSV");
        fileChooser.setSelectedFile(new java.io.File(tituloReporte + ".csv"));

        int opcion = fileChooser.showSaveDialog(this);

        if (opcion == JFileChooser.APPROVE_OPTION) {
            java.io.File archivo = fileChooser.getSelectedFile();

            // Asegurar extensión .csv
            if (!archivo.getName().endsWith(".csv")) {
                archivo = new java.io.File(archivo.getAbsolutePath() + ".csv");
            }

            try (FileWriter fw = new FileWriter(archivo)) {

                // Escribir encabezados
                int cols = modelo.getColumnCount();
                for (int i = 0; i < cols; i++) {
                    fw.write(modelo.getColumnName(i));
                    if (i < cols - 1) fw.write(";");
                }
                fw.write("\n");

                // Escribir filas
                for (int fila = 0; fila < modelo.getRowCount(); fila++) {
                    for (int col = 0; col < cols; col++) {
                        Object valor = modelo.getValueAt(fila, col);
                        fw.write(valor != null ? valor.toString() : "");
                        if (col < cols - 1) fw.write(";");
                    }
                    fw.write("\n");
                }

                JOptionPane.showMessageDialog(this,
                    "Reporte guardado en:\n" + archivo.getAbsolutePath(),
                    "Descarga exitosa", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                    "Error al guardar: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}