package umg.progra2.Forms;

import umg.progra2.DataBase.Dao.UserDao;
import umg.progra2.DataBase.Model.User;
import umg.progra2.DataBase.Service.TbDatosService;
import umg.progra2.DataBase.Service.UserService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Usuarios extends JFrame {
    private JPanel jPanelUsuarios;
    public static void main(String[] args) {
        JFrame frame = new JFrame("frmUsuarios");
        frame.setContentPane(new Usuarios().jPanelUsuarios);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    private JLabel lblTitulo;
    private JLabel lblidUsuario;
    private JTextField textFieldId_Usuario;
    private JLabel lblNombreUsuario;
    private JLabel lblCarne;
    private JLabel lblCorreo;
    private JLabel lblSeccion;
    private JTextField textFieldNombre;
    private JTextField textFieldCarne;
    private JTextField textFieldCorreo;
    private JTextField textFieldSeccion;
    private JButton buttonCrearUsuario;
    private JButton buttonActualizarUsuario;
    private JButton buttonBuscarUsuario;
    private JButton buttonEliminarUsuario;


    public Usuarios() {
        setContentPane(jPanelUsuarios);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buttonCrearUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                user.setNombre(textFieldNombre.getText());
                user.setCarne(textFieldCarne.getText());
                user.setCorreo(textFieldCorreo.getText());
                user.setSeccion(textFieldSeccion.getText());
               try {
                   if (new UserService().checkEmailDuplicated(user.getCorreo())) {
                       JOptionPane.showMessageDialog(null, "El correo ya está en uso. Por favor, utiliza otro correo.");
                       return; // Salir si el correo ya existe
                   }
                   else {
                    new UserService().createUser(user);
                    JOptionPane.showMessageDialog(null, "Usuario creado");
                   }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al crear el usuario: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        buttonActualizarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(textFieldId_Usuario.getText());
                    String nombre = textFieldNombre.getText();
                    String carne = textFieldCarne.getText();
                    String correo = textFieldCorreo.getText();
                    String seccion = textFieldSeccion.getText();
                    User user = new User(codigo, nombre, carne, correo, seccion);
                    UserService userService = new UserService();

                    boolean actualizado = userService.actualizarUser(user);

                    if (actualizado) {
                        JOptionPane.showMessageDialog(null, "Registro actualizado con éxito");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo actualizar el registro");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: El ID debe ser un número válido");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar: " + ex.getMessage());
                }

            }
        });
        buttonBuscarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carneUsuario = textFieldCarne.getText().isEmpty() ? null : (textFieldCarne.getText());
                try{
                    User UsuarioEncontrado = new UserService().getUserByCarne(carneUsuario);
                    if (UsuarioEncontrado != null) {
                        textFieldId_Usuario.setText(Integer.toString(UsuarioEncontrado.getId()));
                        textFieldNombre.setText(UsuarioEncontrado.getNombre());
                        textFieldCarne.setText(UsuarioEncontrado.getCarne());
                        textFieldCorreo.setText(UsuarioEncontrado.getCorreo());
                        textFieldSeccion.setText(UsuarioEncontrado.getSeccion());
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontro el Usuario");
                    }
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(null,"Error al obtener el usuario"+ ex.getMessage());
                }

            }
        });
        buttonEliminarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Asumimos que hay un campo de texto para el ID
                    String idText = textFieldId_Usuario.getText();

                    if (idText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID para eliminar.");
                        return;
                    }
                    // Confirmación antes de eliminar
                    int confirm = JOptionPane.showConfirmDialog(
                            null,
                            "¿Está seguro de que desea eliminar el registro con ID " + idText + "?",
                            "Confirmar Eliminación",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        UserService userService = new UserService();
                        boolean eliminado = userService.eliminarUser(idText);

                        if (eliminado) {
                            JOptionPane.showMessageDialog(null, "Registro eliminado con éxito");
                            // Opcionalmente, limpiar los campos después de eliminar
                            limpiarCampos();
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo eliminar el registro");
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: El ID debe ser un número válido");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar: " + ex.getMessage());
                }
            }

            // Método para limpiar los campos después de eliminar
            private void limpiarCampos() {
                textFieldId_Usuario.setText("");
                textFieldNombre.setText("");
                textFieldCarne.setText("");
                textFieldCorreo.setText("");
                textFieldSeccion.setText("");
            }

        });
    }
}
