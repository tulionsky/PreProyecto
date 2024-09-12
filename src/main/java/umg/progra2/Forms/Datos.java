package umg.progra2.Forms;

import umg.progra2.DataBase.Model.TbDatos;
import umg.progra2.DataBase.Service.TbDatosService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Datos extends JFrame{
    private JPanel jPanelDatos;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblDepartamento;
    private JLabel lblNacimiento;
    private JTextField textFieldNombre;
    private JTextField textFieldApellido;
    private JTextField textFieldDepartamento;
    private JTextField textFieldNacimiento;
    private JButton buttonCrear;
    private JButton buttonBuscar;
    private JLabel lblid;
    private JTextField textFieldiD;
    private JButton buttonActualizar;
    private JButton buttonEliminar;

    public Datos() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Permite cerrar este formulario sin cerrar el principal
        buttonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TbDatos tbDatos = new TbDatos();
                tbDatos.setNombre(textFieldNombre.getText());
                tbDatos.setApellido(textFieldApellido.getText());
                tbDatos.setDepartamento(textFieldDepartamento.getText());
                String fechaTexto = textFieldNacimiento.getText();
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); // Ajusta el formato según el input
                try {
                    try {
                        Date fechaUtil = formato.parse(fechaTexto);
                        java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime());
                        tbDatos.setFechaNacimiento(fechaSql);
                        System.out.println("Fecha convertida: " + fechaUtil);
                    } catch (ParseException ex) {
                        System.out.println("Error: Formato de fecha incorrecto.");
                        JOptionPane.showMessageDialog(null, "Error: Formato de fecha incorrecto.");
                        return; // Salir del método si hay un error de fecha
                    }

                    // Bloque para la inserción en la base de datos
                    new TbDatosService().insertarDato(tbDatos);
                    JOptionPane.showMessageDialog(null, "Datos ingresado");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al guardar datos: " + ex.getMessage());
                    ex.printStackTrace();
                }


            }
        });
        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idDato = textFieldiD.getText().isEmpty() ? 0 : Integer.parseInt(textFieldiD.getText());
                try{
                    TbDatos UsuarioEncontrado = new TbDatosService().obtenerPorId(idDato);
                    if (UsuarioEncontrado != null) {
                        textFieldiD.setText(Integer.toString(UsuarioEncontrado.getCodigo()));
                        textFieldNombre.setText(UsuarioEncontrado.getNombre());
                        textFieldApellido.setText(UsuarioEncontrado.getApellido());
                        textFieldDepartamento.setText(UsuarioEncontrado.getDepartamento());
                        Date date = UsuarioEncontrado.getFechaNacimiento();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String fechaTexto = sdf.format(date);
                        textFieldNacimiento.setText(fechaTexto);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontro el Usuario");
                    }
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(null,"Error al obtener el usuario"+ ex.getMessage());
                }

            }
        });
        buttonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(textFieldiD.getText());
                    String nombre = textFieldNombre.getText();
                    String apellido = textFieldApellido.getText();
                    String departamento = textFieldDepartamento.getText();

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date utilDate = sdf.parse(textFieldNacimiento.getText());
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                    TbDatos dato = new TbDatos(codigo, nombre, apellido, departamento, sqlDate);
                    TbDatosService tbDatosService = new TbDatosService();

                    boolean actualizado = tbDatosService.actualizarDato(dato);

                    if (actualizado) {
                        JOptionPane.showMessageDialog(null, "Registro actualizado con éxito");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo actualizar el registro");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: El ID debe ser un número válido");
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Formato de fecha inválido. Use dd/MM/yyyy");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar: " + ex.getMessage());
                }

            }
        });
        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    // Asumimos que hay un campo de texto para el ID
                    String idText = textFieldiD.getText();

                    if (idText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID para eliminar.");
                        return;
                    }

                    int codigo = Integer.parseInt(idText);

                    // Confirmación antes de eliminar
                    int confirm = JOptionPane.showConfirmDialog(
                            null,
                            "¿Está seguro de que desea eliminar el registro con ID " + codigo + "?",
                            "Confirmar Eliminación",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        TbDatosService tbDatosService = new TbDatosService();
                        boolean eliminado = tbDatosService.eliminarDato(codigo);

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
                textFieldiD.setText("");
                textFieldNombre.setText("");
                textFieldApellido.setText("");
                textFieldDepartamento.setText("");
                textFieldNacimiento.setText("");
            }


        });
    }
}
