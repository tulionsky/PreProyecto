package umg.progra2.Forms;

import umg.progra2.DataBase.Model.EquipoChampions;
import umg.progra2.DataBase.Service.EquipoChampionsService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Champions extends JFrame{
    private JPanel jPanelChampions;
    public static void main(String[] args) {
        JFrame frame = new JFrame("frmChampions");
        frame.setContentPane(new Champions().jPanelChampions);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    private JLabel lblNombreEquipo;
    private JLabel lblPais;
    private JLabel lblCiudad;
    private JLabel lblEstadio;
    private JLabel lblFundacion;
    private JLabel lblEntrenador;
    private JLabel lblWeb;
    private JLabel lblfacebook;
    private JLabel lblTwitter;
    private JLabel lblInstagram;
    private JLabel lblPatrocinador;
    private JTextField textFieldNombreEquipo;
    private JTextField textFieldPais;
    private JTextField textFieldCiudad;
    private JTextField textFieldEstadio;
    private JTextField textFieldfundacion;
    private JTextField textFieldEntrenador;
    private JTextField textFieldWeb;
    private JTextField textFieldfacebook;
    private JTextField textFieldtwitter;
    private JTextField textFieldinstagram;
    private JTextField textFieldpatrocinador;
    private JButton buttonAgregarEquipo;
    private JButton buttonEliminarEquipo;
    private JButton buttonBuscarEquipo;
    private JButton buttonActualizarEquipo;
    private JLabel lblIdEquipo;
    private JTextField textFieldIdEquipo;


    private EquipoChampionsService service = new EquipoChampionsService();
    EquipoChampions equipo = new EquipoChampions();


    public Champions() {
        setContentPane(jPanelChampions);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buttonAgregarEquipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                equipo.setNombre(textFieldNombreEquipo.getText());
                equipo.setPais(textFieldPais.getText());
                equipo.setCiudad(textFieldCiudad.getText());
                equipo.setEstadio(textFieldEstadio.getText());
                equipo.setFundacion(Integer.parseInt(textFieldfundacion.getText()));
                equipo.setEntrenador(textFieldEntrenador.getText());
                equipo.setWebOficial(textFieldWeb.getText());
                equipo.setFacebook(textFieldfacebook.getText());
                equipo.setTwitter(textFieldtwitter.getText());
                equipo.setInstagram(textFieldinstagram.getText());
                equipo.setPatrocinadorPrincipal(textFieldpatrocinador.getText());
                try {
                    service.insertarEquipo(equipo);
                    JOptionPane.showMessageDialog(null, "Equipo agregado exitosamente");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al agregar el equipo: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        buttonActualizarEquipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                equipo.setIdEquipo(Integer.parseInt(textFieldIdEquipo.getText()));
                equipo.setNombre(textFieldNombreEquipo.getText());
                equipo.setPais(textFieldPais.getText());
                equipo.setCiudad(textFieldCiudad.getText());
                equipo.setEstadio(textFieldEstadio.getText());
                equipo.setFundacion(Integer.parseInt(textFieldfundacion.getText()));
                equipo.setEntrenador(textFieldEntrenador.getText());
                equipo.setWebOficial(textFieldWeb.getText());
                equipo.setFacebook(textFieldfacebook.getText());
                equipo.setTwitter(textFieldtwitter.getText());
                equipo.setInstagram(textFieldinstagram.getText());
                equipo.setPatrocinadorPrincipal(textFieldpatrocinador.getText());

                try {
                    service.actualizarEquipo(equipo);
                    JOptionPane.showMessageDialog(null, "Equipo actualizado exitosamente");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el equipo: " + ex.getMessage());
                    ex.printStackTrace();
                }

            }
        });
        buttonEliminarEquipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idEquipo = Integer.parseInt(textFieldIdEquipo.getText());

                try {
                    service.eliminarEquipo(idEquipo);
                    JOptionPane.showMessageDialog(null, "Equipo eliminado exitosamente");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el equipo: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        buttonBuscarEquipo.addActionListener(new ActionListener() {
            private void mostrarEquipoEnFormulario(EquipoChampions equipo) {
                textFieldIdEquipo.setText(String.valueOf(equipo.getIdEquipo()));
                textFieldNombreEquipo.setText(equipo.getNombre());
                textFieldPais.setText(equipo.getPais());
                textFieldCiudad.setText(equipo.getCiudad());
                textFieldEstadio.setText(equipo.getEstadio());
                textFieldfundacion.setText(String.valueOf(equipo.getFundacion()));
                textFieldEntrenador.setText(equipo.getEntrenador());
                textFieldWeb.setText(equipo.getWebOficial());
                textFieldfacebook.setText(equipo.getFacebook());
                textFieldtwitter.setText(equipo.getTwitter());
                textFieldinstagram.setText(equipo.getInstagram());
                textFieldpatrocinador.setText(equipo.getPatrocinadorPrincipal());
            }
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreBusqueda = JOptionPane.showInputDialog("Ingrese el nombre del equipo a buscar:");
                if (nombreBusqueda != null && !nombreBusqueda.isEmpty()) {
                    try {
                        List<EquipoChampions> equipos = service.obtenerTodosLosEquipos();

                        EquipoChampions equipoEncontrado = equipos.stream()
                                .filter(eq -> eq.getNombre().toLowerCase().contains(nombreBusqueda.toLowerCase()))
                                .findFirst()
                                .orElse(null);

                        if (equipoEncontrado != null) {
                            mostrarEquipoEnFormulario(equipoEncontrado);
                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontró ningún equipo con ese nombre", "Equipo no encontrado", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al buscar el equipo: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }

            }
        });

    }
}
