package ar.edu.unlu.escobade15.Vista;

import javax.swing.*;
import java.awt.event.*;

public class DialogoNombre extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtNombreJugador;
    private JLabel escribeTuNombreLabel;
    private String nombreJugador;


    private void setNombreJugador(String nombre){
        nombreJugador = nombre;
    }

    public String getNombreJugador(){
        return nombreJugador;
    }

    public DialogoNombre() {
        setTitle("Ingresar nombre de jugador");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if (txtNombreJugador != null && !txtNombreJugador.getText().trim().isEmpty()) {
            setNombreJugador(txtNombreJugador.getText());
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Se debe especificar el nombre del jugador.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }


    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        DialogoNombre dialog = new DialogoNombre();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
