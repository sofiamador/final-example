package finalExample;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JTextPane;

import java.awt.Color;

public class MyWindows {

	private JFrame frame;
	private String url;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyWindows window = new MyWindows();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MyWindows() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.CYAN);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
		final JTextPane text1 = new JTextPane();
		text1.setBounds(53, 79, 117, 20);
		frame.getContentPane().add(text1);
		
		
		
		JLabel lblEnterPicturePath = new JLabel("Enter picture path");
		lblEnterPicturePath.setBounds(53, 53, 117, 26);
		frame.getContentPane().add(lblEnterPicturePath);
		
		JLabel lbl1 = new JLabel("");
		lbl1.setBounds(63, 146, 46, 14);
		frame.getContentPane().add(lbl1);
		
		final JTextPane text3 = new JTextPane();
		text3.setBounds(53, 168, 117, 20);
		frame.getContentPane().add(text3);
		
		final JTextPane text2 = new JTextPane();
		text2.setBounds(53, 122, 117, 20);
		frame.getContentPane().add(text2);
		
		JLabel lblEnterBucketName = new JLabel("Enter bucket name");
		lblEnterBucketName.setBounds(53, 98, 117, 26);
		frame.getContentPane().add(lblEnterBucketName);
		
		JLabel lblEnterKeyName = new JLabel("Enter key name");
		lblEnterKeyName.setBounds(53, 146, 117, 26);
		frame.getContentPane().add(lblEnterKeyName);
		
		JButton btnNewButton = new JButton("Upload picture");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f = Example.createFile(text1.getText());
				String url= Example.uploadToS3(f, text2.getText(),text3.getText());
			}
		});
		btnNewButton.setBounds(53, 199, 117, 23);
		frame.getContentPane().add(btnNewButton);
		
		final JTextPane textPhone = new JTextPane();
		textPhone.setBounds(195, 79, 117, 20);
		frame.getContentPane().add(textPhone);
		
		JLabel lblEnterPhineNumber = new JLabel("Enter phine number");
		lblEnterPhineNumber.setBounds(195, 53, 117, 26);
		frame.getContentPane().add(lblEnterPhineNumber);
		
		JButton btnSendMessage = new JButton("Send message");
		btnSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] s = null;
				try {
					s = LocationService.myLocation();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String temperature = null;
				try {
					temperature = WeatherService.getWeather(s[0]);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Example.SendSMS(url + "\nThe temperature is: " + temperature,textPhone.getText());	
			}
		});
		btnSendMessage.setBounds(195, 199, 117, 23);
		frame.getContentPane().add(btnSendMessage);

	}

}
