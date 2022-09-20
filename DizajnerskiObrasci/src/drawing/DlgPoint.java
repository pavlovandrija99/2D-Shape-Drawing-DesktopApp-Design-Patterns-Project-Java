package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgPoint extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCoordinateX;
	private JTextField txtCoordinateY;
	private boolean confirm;
	
	private boolean colorConfirmation;
	private Color color = Color.BLACK;
	
	JButton btnPointColor;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgPoint dialog = new DlgPoint();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgPoint() {
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblCoordinateX = new JLabel("Coordinate X:");
			GridBagConstraints gbc_lblCoordinateX = new GridBagConstraints();
			gbc_lblCoordinateX.gridwidth = 3;
			gbc_lblCoordinateX.insets = new Insets(0, 0, 5, 5);
			gbc_lblCoordinateX.gridx = 2;
			gbc_lblCoordinateX.gridy = 2;
			contentPanel.add(lblCoordinateX, gbc_lblCoordinateX);
		}
		{
			txtCoordinateX = new JTextField();
			GridBagConstraints gbc_txtCoordinateX = new GridBagConstraints();
			gbc_txtCoordinateX.insets = new Insets(0, 0, 5, 0);
			gbc_txtCoordinateX.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtCoordinateX.gridx = 7;
			gbc_txtCoordinateX.gridy = 2;
			contentPanel.add(txtCoordinateX, gbc_txtCoordinateX);
			txtCoordinateX.setColumns(10);
		}
		{
			JLabel lblCoordinateY = new JLabel("Coordinate Y:");
			GridBagConstraints gbc_lblCoordinateY = new GridBagConstraints();
			gbc_lblCoordinateY.gridwidth = 3;
			gbc_lblCoordinateY.insets = new Insets(0, 0, 5, 5);
			gbc_lblCoordinateY.gridx = 2;
			gbc_lblCoordinateY.gridy = 3;
			contentPanel.add(lblCoordinateY, gbc_lblCoordinateY);
		}
		{
			txtCoordinateY = new JTextField();
			GridBagConstraints gbc_txtCoordinateY = new GridBagConstraints();
			gbc_txtCoordinateY.insets = new Insets(0, 0, 5, 0);
			gbc_txtCoordinateY.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtCoordinateY.gridx = 7;
			gbc_txtCoordinateY.gridy = 3;
			contentPanel.add(txtCoordinateY, gbc_txtCoordinateY);
			txtCoordinateY.setColumns(10);
		}
		{
			btnPointColor = new JButton("Color");
			btnPointColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					color=JColorChooser.showDialog(null,"Choose a color.",color);
					btnPointColor.setBackground(color);
					colorConfirmation = true;
					
				}
			});
			GridBagConstraints gbc_btnPointColor = new GridBagConstraints();
			gbc_btnPointColor.insets = new Insets(0, 0, 0, 5);
			gbc_btnPointColor.gridx = 3;
			gbc_btnPointColor.gridy = 5;
			contentPanel.add(btnPointColor, gbc_btnPointColor);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						confirm = true;
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	

	public JTextField getTxtCoordinateX() {
		return txtCoordinateX;
	}

	public boolean isColorConfirmation() {
		return colorConfirmation;
	}

	public void setColorConfirmation(boolean colorConfirmation) {
		this.colorConfirmation = colorConfirmation;
	}

	public void setTxtCoordinateX(JTextField txtCoordinateX) {
		this.txtCoordinateX = txtCoordinateX;
	}

	public JTextField getTxtCoordinateY() {
		return txtCoordinateY;
	}

	public void setTxtCoordinateY(JTextField txtCoordinateY) {
		this.txtCoordinateY = txtCoordinateY;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	
	public JButton getBtnPointColor() {
		return btnPointColor;
	}

	public void setBtnPointColor(JButton btnPointColor) {
		this.btnPointColor = btnPointColor;
	}
	
	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}
	
	
}
