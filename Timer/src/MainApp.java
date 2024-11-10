import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainApp extends JFrame {

    private JLabel timeLabel;
    private JLabel countdownLabel;
    private JButton chooseColorButton;
    private JButton speedButton;
    private JButton startButton;
    private JButton stopButton;
    private JComboBox<String> speedComboBox;
    private Color currentColor;
    private Timer timer;
    private int countdownSeconds;
    private JLabel lblTime;
    private JTextField countLabel;
    private Timer blinkingTimer;
    public JRadioButton radioButton;
    public JRadioButton radioButton1;
    public JButton resetButton;


    public MainApp() {
        setTitle("Option Dialog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 100);
        setLayout(new FlowLayout());
        //countLabel = new JTextField("20");

        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSettingsDialog();
            }
        });
        add(settingsButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(closeButton);
    }

    private void showSettingsDialog() {
        JDialog settingsDialog = new JDialog(this, "Settings", true);
        settingsDialog.setSize(500,500);
        settingsDialog.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        radioButton = new JRadioButton("on time: ");
        c.gridx = 0;
        c.gridy = 0;
        settingsDialog.add(radioButton,c);

        c.gridx = 1;
        c.gridy = 0;
        lblTime = new JLabel();
        lblTime.setVisible(true);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date d = new Date();
        lblTime.setText(simpleDateFormat.format(d));
        settingsDialog.add(lblTime,c);

        Date d2 = new Date();
        timeLabel = new JLabel(simpleDateFormat.format(d2));

        radioButton1 = new JRadioButton("countdown: ");
        c.gridx = 0;
        c.gridy = 1;
        settingsDialog.add(radioButton1,c);

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(radioButton);
        radioGroup.add(radioButton1);


        c.gridx = 1;
        c.gridy = 1;
        countLabel = new JTextField("    ");
        countLabel.setSize(20,10);
        settingsDialog.add(countLabel,c);


        c.gridx = 0;
        c.gridy = 3;
        chooseColorButton = new JButton("Choose Color");
        settingsDialog.add(chooseColorButton,c);

        c.gridx = 1;
        c.gridy = 3;
        JLabel lblTime1 = new JLabel();
        lblTime1.setVisible(true);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm:ss");
        Date d1 = new Date();
        lblTime1.setText(simpleDateFormat1.format(d1));
        settingsDialog.add(lblTime1,c);

        c.gridx = 0;
        c.gridy = 4;
        speedButton = new JButton("Speed");
        settingsDialog.add(speedButton,c);

        c.gridx = 1;
        c.gridy = 4;
        String[] speeds = {"1 second", "2 seconds", "3 seconds", "4 seconds", "5 seconds"};
        speedComboBox = new JComboBox<>(speeds);
        settingsDialog.add(speedComboBox,c);

        c.gridx = 0;
        c.gridy = 5;
        startButton = new JButton("Start Countdown");
        settingsDialog.add(startButton,c);

        c.gridx = 1;
        c.gridy = 5;
        stopButton = new JButton("Stop");
        settingsDialog.add(stopButton,c);


       /* if(radioGroup.getSelection() == radioButton.getModel()) {

        }else if(radioGroup.getSelection() == radioButton1.getModel()) {

        }*/
        radioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radioButton1.setSelected(false);
                radioButton1.setEnabled(false);
                countLabel.setEnabled(false);
            }
        });

        radioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radioButton.setSelected(false);
                radioButton.setEnabled(false);
                countLabel.setEnabled(true);
            }
        });

        chooseColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(settingsDialog, "Choose Color", currentColor);
                if (selectedColor != null) {
                    currentColor = selectedColor;
                    lblTime1.setForeground(currentColor);
                }
            }
        });

        speedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speedComboBox.setEnabled(!speedComboBox.isEnabled());
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCountdown();
                disableControls(true);
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopCountdown();
                disableControls(false);
                radioButton.setEnabled(true);
                radioButton1.setEnabled(true);
            }
        });

        settingsDialog.setSize(400, 400);
        settingsDialog.setVisible(true);
        settingsDialog.setBackground(Color.orange);
    }

    private void startCountdown() {
        if (speedComboBox != null && speedComboBox.getSelectedItem() != null) {
            String speedText = (String) speedComboBox.getSelectedItem();
            int speed = Integer.parseInt(speedText.split(" ")[0]) * 1000;

            if (radioButton.isSelected()) {
                try {
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                    Date currentTime = new Date();
                    Date selectedTime = dateFormat.parse(timeLabel.getText());
                    long delay =  currentTime.getTime() - selectedTime.getTime();
                    //System.out.println(delay);

                    if (delay > 0) {
                        Timer onTimeTimer = new Timer((int) delay, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                showSecondWindow();
                            }
                        });
                        onTimeTimer.setRepeats(false);
                        onTimeTimer.start();
                    }
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            } else if (radioButton1.isSelected()) {
                try {
                    countdownSeconds = Integer.parseInt(countLabel.getText());
                    if (countdownSeconds <= 0) {
                        JOptionPane.showMessageDialog(this, "Introduceti o valoare numerica pozitiva.", "Eroare", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Introduceti o valoare numerica valida pentru Countdown.", "Eroare", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ActionListener timerAction = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        countdownSeconds--;
                        if (countdownSeconds <= 0) {
                            timer.stop();
                            showSecondWindow();
                        }
                    }
                };

                timer = new Timer(speed, timerAction);
                timer.setInitialDelay(0);
                timer.start();
            }
        }
    }

    private void stopCountdown() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
            //countLabel.getText();
        }
        if (blinkingTimer != null && blinkingTimer.isRunning()) {
            blinkingTimer.stop();
        }
    }

    private void showSecondWindow() {
        JFrame secondWindow = new JFrame("Second Window");
        secondWindow.setSize(300, 300);
        secondWindow.setLocation(400,300);
        secondWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        secondWindow.setVisible(true);

        blinkingTimer = new Timer(1000, new ActionListener() {
            boolean isBlinking = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isBlinking) {
                    secondWindow.getContentPane().setBackground(Color.BLUE);
                } else {
                    secondWindow.getContentPane().setBackground(currentColor);
                }
                isBlinking = !isBlinking;
            }
        });
        blinkingTimer.start();
    }

    private void disableControls(boolean disable) {
        //radioGroup.setSelected(radioGroup.getSelection(), false);
        chooseColorButton.setEnabled(!disable);
        speedButton.setEnabled(!disable);
        startButton.setEnabled(!disable);
        stopButton.setEnabled(disable);
        speedComboBox.setEnabled(!disable);
    }

    private void enableRadioButtons() {
        radioButton.setEnabled(true);
        radioButton1.setEnabled(true);
        radioButton.setSelected(false);
        radioButton1.setSelected(false);
        countLabel.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainApp app = new MainApp();
                app.setVisible(true);
            }
        });
    }
}
