package org.example;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleEmailGUI extends JFrame {
    private JTextField toField;
    private JTextField subjectField;
    private JTextArea bodyArea;
    private JButton sendButton;

    public SimpleEmailGUI(){
        setTitle("GMAIL");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear input fields
        toField = new JTextField();
        subjectField=new JTextField();
        bodyArea = new JTextArea();
        sendButton=new JButton("ENVIAR");

        // Crear panel para los input fields
        JPanel inputPanel= new JPanel(new GridLayout(4,1));
        inputPanel.add(new JLabel("Para:"));
        inputPanel.add(toField);
        inputPanel.add(new JLabel("Asunto:"));
        inputPanel.add(subjectField);

        // Añadir componentes al frame
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(bodyArea), BorderLayout.CENTER);
        add(sendButton, BorderLayout.SOUTH);

        // Action listener del Bóton de Enviar
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendEmail();
            }
        });
        setAppIcon();
        setVisible(true);
    }
    private void setAppIcon(){
        ImageIcon icon = new ImageIcon("C:/Users/CFGS/Desktop/gmail.png");
        Image image = icon.getImage();
        setIconImage(image);
    }
        private void sendEmail() {
        final String fromEmail ="mumuffxiv@gmail.com";
        final String password ="rtdx zqyp dpqn kobi";
        final String toEmail=toField.getText();
        final String subject=subjectField.getText();
        final String body=bodyArea.getText();

        // Propiedades del servidor SMTP
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP DE GMAIL EN ESTE CASO
            props.put("mail.smtp.socketFactory.port", "465"); // PUERTO SSL
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL Factory Class
            props.put("mail.smtp.auth", "true"); // ACTIVAR SMTP AUTHENTICATION
            props.put("mail.smtp.port", "465"); // SMTP PORT

            // Crear sesión con autenticación
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });


            try {
                MimeMessage msg = new MimeMessage(session);
                // Configurar cabeceras
                msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
                msg.addHeader("format", "flowed");
                msg.addHeader("Content-Transfer-Encoding", "8-bit");
                msg.setFrom(new InternetAddress("no_reply_DOSA@DAM.com", "NO BIZUM NO RESPONDER")); // Ejemplo de dato
                msg.setReplyTo(InternetAddress.parse("no_reply_DOSA@DAM.com", false));
                msg.setSubject(subject, "UTF-8");
                msg.setText(body, "UTF-8");
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

                System.out.println("MENSAJE CREADO");
                Transport.send(msg);
                JOptionPane.showMessageDialog(this, "El email ha sido enviado");
            } catch (MessagingException e) {
                JOptionPane.showMessageDialog(this, "Error al enviar el email " + e.getMessage());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ha ocurrido un ERROR INESPERADO: " + e.getMessage());
            }
        }
    /* Outgoing Mail (SMTP) Server
     * requires TLS or SSL: smtp.gmail.com(SSL)
     * Use Authentication: Yes
     * Port for SSL: 465 */

    public static void main(String[] args) {
        new SimpleEmailGUI();
    }
}