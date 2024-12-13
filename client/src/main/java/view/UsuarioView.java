package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.Usuario;
import service.UsuarioService;

public class UsuarioView extends JFrame {
    private JTextField idField;
    private JTextField nombreField;
    private JTextField telefonoField;
    private JTextField emailField;
    private JTextArea usuariosArea;
    private UsuarioService usuarioService;

    public UsuarioView() {
        usuarioService = new UsuarioService();

        setTitle("Gestión de Usuarios - UsuarioView");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        // Panel Superior: Formulario de Entrada
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(220, 230, 240));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Etiquetas y Campos de Entrada
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("ID:"), gbc);
        
        gbc.gridx = 1;
        idField = new JTextField();
        formPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Nombre:"), gbc);
        
        gbc.gridx = 1;
        nombreField = new JTextField();
        formPanel.add(nombreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Teléfono:"), gbc);
        
        gbc.gridx = 1;
        telefonoField = new JTextField();
        formPanel.add(telefonoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        emailField = new JTextField();
        formPanel.add(emailField, gbc);

        // Botones
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        JButton registrarButton = new JButton("Registrar Usuario");
        registrarButton.setBackground(new Color(100, 200, 150));
        registrarButton.setForeground(Color.WHITE);
        registrarButton.setFocusPainted(false);
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
        formPanel.add(registrarButton, gbc);

        gbc.gridx = 1;
        JButton obtenerButton = new JButton("Obtener Usuarios");
        obtenerButton.setBackground(new Color(100, 150, 200));
        obtenerButton.setForeground(Color.WHITE);
        obtenerButton.setFocusPainted(false);
        obtenerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obtenerUsuarios();
            }
        });
        formPanel.add(obtenerButton, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Panel Central: Área de Texto para Mostrar Usuarios
        usuariosArea = new JTextArea();
        usuariosArea.setEditable(false);
        usuariosArea.setBackground(new Color(245, 245, 245));
        usuariosArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(usuariosArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Usuarios Registrados"));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void registrarUsuario() {
        try {
            String id = idField.getText();
            String nombre = nombreField.getText();
            String telefono = telefonoField.getText();
            String email = emailField.getText();

            Usuario usuario = new Usuario();
            usuario.setId(id);
            usuario.setNombre(nombre);
            usuario.setTelefono(telefono);
            usuario.setEmail(email);

            String mensaje = usuarioService.crearUsuario(usuario);
            JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void obtenerUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.getUsuarios();
            usuariosArea.setText("");
            for (Usuario usuario : usuarios) {
                usuariosArea.append(usuario.toString() + "\n");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        idField.setText("");
        nombreField.setText("");
        telefonoField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UsuarioView().setVisible(true);
            }
        });
    }
}