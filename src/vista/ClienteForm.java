package vista;

import dao.ClienteDAO;
import modelo.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClienteForm extends JPanel {

    private JTextField txtNombre, txtEmail, txtTelefono, txtIdCliente;
    private JTable tabla;
    private DefaultTableModel modelo;
    private ClienteDAO dao = new ClienteDAO();

    public ClienteForm() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));

        txtIdCliente = new JTextField();
        txtNombre    = new JTextField();
        txtEmail     = new JTextField();
        txtTelefono  = new JTextField();

        panelForm.add(new JLabel("ID (editar/eliminar):")); panelForm.add(txtIdCliente);
        panelForm.add(new JLabel("Nombre:"));               panelForm.add(txtNombre);
        panelForm.add(new JLabel("Email:"));                panelForm.add(txtEmail);
        panelForm.add(new JLabel("Telefono:"));             panelForm.add(txtTelefono);

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnGuardar   = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar  = new JButton("Eliminar");
        JButton btnLimpiar   = new JButton("Limpiar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        modelo = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Email", "Telefono"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelForm,    BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll,        BorderLayout.CENTER);

        cargarTabla();

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtIdCliente.setText(modelo.getValueAt(fila, 0).toString());
                txtNombre.setText(modelo.getValueAt(fila, 1).toString());
                txtEmail.setText(modelo.getValueAt(fila, 2).toString());
                txtTelefono.setText(modelo.getValueAt(fila, 3).toString());
            }
        });

        btnGuardar.addActionListener(e -> {
            Cliente c = new Cliente(0, txtNombre.getText(),
                                    txtEmail.getText(), txtTelefono.getText());
            if (dao.insertar(c)) {
                JOptionPane.showMessageDialog(this, "Cliente guardado.");
                limpiar(); cargarTabla();
            }
        });

        btnActualizar.addActionListener(e -> {
            if (txtIdCliente.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecciona un cliente.");
                return;
            }
            Cliente c = new Cliente(Integer.parseInt(txtIdCliente.getText()),
                                    txtNombre.getText(), txtEmail.getText(),
                                    txtTelefono.getText());
            if (dao.actualizar(c)) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado.");
                limpiar(); cargarTabla();
            }
        });

        btnEliminar.addActionListener(e -> {
            if (txtIdCliente.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecciona un cliente.");
                return;
            }
            int id = Integer.parseInt(txtIdCliente.getText());
            int ok = JOptionPane.showConfirmDialog(this,
                "Eliminar cliente ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) {
                if (dao.eliminar(id)) {
                    JOptionPane.showMessageDialog(this, "Cliente eliminado.");
                    limpiar(); cargarTabla();
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiar());
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Cliente c : dao.listar())
            modelo.addRow(new Object[]{
                c.getIdCliente(), c.getNombre(), c.getEmail(), c.getTelefono()
            });
    }

    private void limpiar() {
        txtIdCliente.setText("");
        txtNombre.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        tabla.clearSelection();
    }
}