package vista;

import dao.AgenteDAO;
import dao.CategoriaDAO;
import dao.ClienteDAO;
import dao.TicketDAO;
import modelo.Agente;
import modelo.Categoria;
import modelo.Cliente;
import modelo.Ticket;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TicketForm extends JPanel {

    private JTextField   txtTitulo, txtDescripcion, txtIdTicket;
    private JComboBox<Cliente>   cmbCliente;
    private JComboBox<Agente>    cmbAgente;
    private JComboBox<Categoria> cmbCategoria;
    private JComboBox<String>    cmbPrioridad, cmbEstado;
    private JTable tabla;
    private DefaultTableModel modelo;

    private TicketDAO    ticketDAO    = new TicketDAO();
    private AgenteDAO    agenteDAO    = new AgenteDAO();
    private CategoriaDAO categoriaDAO = new CategoriaDAO();
    private ClienteDAO   clienteDAO   = new ClienteDAO();

    public TicketForm() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(8, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Ticket"));

        txtIdTicket    = new JTextField();
        txtTitulo      = new JTextField();
        txtDescripcion = new JTextField();
        cmbCliente     = new JComboBox<>();
        cmbAgente      = new JComboBox<>();
        cmbCategoria   = new JComboBox<>();
        cmbPrioridad   = new JComboBox<>(new String[]{"ALTA", "MEDIA", "BAJA"});
        cmbEstado      = new JComboBox<>(new String[]{"ABIERTO", "EN_PROCESO", "CERRADO"});

        panelForm.add(new JLabel("ID Ticket (editar/eliminar):")); panelForm.add(txtIdTicket);
        panelForm.add(new JLabel("Titulo:"));                      panelForm.add(txtTitulo);
        panelForm.add(new JLabel("Descripcion:"));                 panelForm.add(txtDescripcion);
        panelForm.add(new JLabel("Cliente:"));                     panelForm.add(cmbCliente);
        panelForm.add(new JLabel("Agente:"));                      panelForm.add(cmbAgente);
        panelForm.add(new JLabel("Categoria:"));                   panelForm.add(cmbCategoria);
        panelForm.add(new JLabel("Prioridad:"));                   panelForm.add(cmbPrioridad);
        panelForm.add(new JLabel("Estado:"));                      panelForm.add(cmbEstado);

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnGuardar    = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar Estado");
        JButton btnEliminar   = new JButton("Eliminar");
        JButton btnLimpiar    = new JButton("Limpiar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        modelo = new DefaultTableModel(
            new String[]{"ID","Titulo","Cliente","Agente","Categoria","Estado","Prioridad","Fecha"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelForm,    BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll,        BorderLayout.CENTER);

        cargarCombos();
        cargarTabla();

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtIdTicket.setText(modelo.getValueAt(fila, 0).toString());
                txtTitulo.setText(modelo.getValueAt(fila, 1).toString());
                cmbEstado.setSelectedItem(modelo.getValueAt(fila, 5).toString());
                cmbPrioridad.setSelectedItem(modelo.getValueAt(fila, 6).toString());
            }
        });

        btnGuardar.addActionListener(e -> {
            Cliente  cl  = (Cliente)   cmbCliente.getSelectedItem();
            Agente   ag  = (Agente)    cmbAgente.getSelectedItem();
            Categoria cat = (Categoria) cmbCategoria.getSelectedItem();
            if (cl == null || ag == null || cat == null) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos.");
                return;
            }
            Ticket t = new Ticket(0, cl.getIdCliente(), ag.getIdAgente(),
                cat.getIdCategoria(), txtTitulo.getText(),
                txtDescripcion.getText(), "ABIERTO",
                cmbPrioridad.getSelectedItem().toString());
            if (ticketDAO.insertar(t)) {
                JOptionPane.showMessageDialog(this, "Ticket guardado.");
                limpiar(); cargarTabla();
            }
        });

        btnActualizar.addActionListener(e -> {
            if (txtIdTicket.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecciona un ticket.");
                return;
            }
            int id = Integer.parseInt(txtIdTicket.getText());
            if (ticketDAO.cambiarEstado(id, cmbEstado.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(this, "Estado actualizado.");
                limpiar(); cargarTabla();
            }
        });

        btnEliminar.addActionListener(e -> {
            if (txtIdTicket.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecciona un ticket.");
                return;
            }
            int id = Integer.parseInt(txtIdTicket.getText());
            int ok = JOptionPane.showConfirmDialog(this,
                "Eliminar ticket ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) {
                if (ticketDAO.eliminar(id)) {
                    JOptionPane.showMessageDialog(this, "Ticket eliminado.");
                    limpiar(); cargarTabla();
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiar());
    }

    private void cargarCombos() {
        cmbCliente.removeAllItems();
        for (Cliente c : clienteDAO.listar())   cmbCliente.addItem(c);
        cmbAgente.removeAllItems();
        for (Agente a : agenteDAO.listar())      cmbAgente.addItem(a);
        cmbCategoria.removeAllItems();
        for (Categoria c : categoriaDAO.listar()) cmbCategoria.addItem(c);
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Object[] fila : ticketDAO.listarDetalle())
            modelo.addRow(fila);
    }

    private void limpiar() {
        txtIdTicket.setText("");
        txtTitulo.setText("");
        txtDescripcion.setText("");
        tabla.clearSelection();
        cargarCombos();
    }
}