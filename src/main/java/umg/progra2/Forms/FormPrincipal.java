package umg.progra2.Forms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormPrincipal {
    private JPanel jPanelPrincipal;
    public static void main(String[] args) {
        JFrame frame = new JFrame("frmPanelPrincipal");
        frame.setContentPane(new FormPrincipal().jPanelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    private JButton buttonAbrirDatos;
    private JButton buttonAbrirUsuario;
    private JButton buttonAbrirChampions;

    public FormPrincipal() {
        buttonAbrirDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuarios usuarios = new Usuarios();
                usuarios.setVisible(true);
//                Datos datos = new Datos();
//                datos.setVisible(true);
            }
        });
    }
}
