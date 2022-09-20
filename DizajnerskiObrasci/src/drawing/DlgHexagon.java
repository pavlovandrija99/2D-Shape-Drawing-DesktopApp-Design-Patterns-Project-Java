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

public class DlgHexagon extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private JTextField txtHexagonRadius;
	private boolean confirm;

	private Color innerColor = Color.WHITE;
	private Color borderColor = Color.BLACK;
	private boolean innerColorConfirmation;
	private boolean borderColorConfirmation;
	private JTextField txtCoordinateX;
	private JTextField txtCoordinateY;
	
	private JButton btnHexagonInnerColor;
	private JButton btnHexagonBorderColor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgHexagon dialog = new DlgHexagon();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgHexagon() {
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblCoordinateX = new JLabel("Coordinate X:");
			GridBagConstraints gbc_lblCoordinateX = new GridBagConstraints();
			gbc_lblCoordinateX.insets = new Insets(0, 0, 5, 5);
			gbc_lblCoordinateX.gridx = 2;
			gbc_lblCoordinateX.gridy = 0;
			contentPanel.add(lblCoordinateX, gbc_lblCoordinateX);
		}
		{
			txtCoordinateX = new JTextField();
			GridBagConstraints gbc_txtCoordinateX = new GridBagConstraints();
			gbc_txtCoordinateX.gridwidth = 3;
			gbc_txtCoordinateX.insets = new Insets(0, 0, 5, 0);
			gbc_txtCoordinateX.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtCoordinateX.gridx = 7;
			gbc_txtCoordinateX.gridy = 0;
			contentPanel.add(txtCoordinateX, gbc_txtCoordinateX);
			txtCoordinateX.setColumns(10);
		}
		{
			JLabel lblCoordinateY = new JLabel("Coordinate Y:");
			GridBagConstraints gbc_lblCoordinateY = new GridBagConstraints();
			gbc_lblCoordinateY.insets = new Insets(0, 0, 5, 5);
			gbc_lblCoordinateY.gridx = 2;
			gbc_lblCoordinateY.gridy = 1;
			contentPanel.add(lblCoordinateY, gbc_lblCoordinateY);
		}
		{
			txtCoordinateY = new JTextField();
			GridBagConstraints gbc_txtCoordinateY = new GridBagConstraints();
			gbc_txtCoordinateY.gridwidth = 3;
			gbc_txtCoordinateY.insets = new Insets(0, 0, 5, 0);
			gbc_txtCoordinateY.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtCoordinateY.gridx = 7;
			gbc_txtCoordinateY.gridy = 1;
			contentPanel.add(txtCoordinateY, gbc_txtCoordinateY);
			txtCoordinateY.setColumns(10);
		}
		{
			JLabel lblHexagonRadius = new JLabel("Radius:");
			GridBagConstraints gbc_lblHexagonRadius = new GridBagConstraints();
			gbc_lblHexagonRadius.insets = new Insets(0, 0, 5, 5);
			gbc_lblHexagonRadius.gridx = 2;
			gbc_lblHexagonRadius.gridy = 2;
			contentPanel.add(lblHexagonRadius, gbc_lblHexagonRadius);
		}
		{
			txtHexagonRadius = new JTextField();
			txtHexagonRadius.setText("");
			GridBagConstraints gbc_txtHexagonRadius = new GridBagConstraints();
			gbc_txtHexagonRadius.insets = new Insets(0, 0, 5, 0);
			gbc_txtHexagonRadius.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtHexagonRadius.gridx = 9;
			gbc_txtHexagonRadius.gridy = 2;
			contentPanel.add(txtHexagonRadius, gbc_txtHexagonRadius);
			txtHexagonRadius.setColumns(10);
		}
		{
			btnHexagonInnerColor = new JButton("Inner Color");
			btnHexagonInnerColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					innerColor = JColorChooser.showDialog(null, "Chose a color.", innerColor);
					btnHexagonInnerColor.setBackground(innerColor);
					innerColorConfirmation = true;
				}
			});
			GridBagConstraints gbc_btnHexagonInnerColor = new GridBagConstraints();
			gbc_btnHexagonInnerColor.insets = new Insets(0, 0, 0, 5);
			gbc_btnHexagonInnerColor.gridx = 2;
			gbc_btnHexagonInnerColor.gridy = 5;
			contentPanel.add(btnHexagonInnerColor, gbc_btnHexagonInnerColor);
		}
		{
			btnHexagonBorderColor = new JButton("Border Color");
			btnHexagonBorderColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					borderColor = JColorChooser.showDialog(null, "Chose a color.", borderColor);
					btnHexagonBorderColor.setBackground(borderColor);
					borderColorConfirmation = true;
				}
			});
			GridBagConstraints gbc_btnHexagonBorderColor = new GridBagConstraints();
			gbc_btnHexagonBorderColor.insets = new Insets(0, 0, 0, 5);
			gbc_btnHexagonBorderColor.gridx = 4;
			gbc_btnHexagonBorderColor.gridy = 5;
			contentPanel.add(btnHexagonBorderColor, gbc_btnHexagonBorderColor);
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
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	


	public JTextField getTxtHexagonRadius() {
		return txtHexagonRadius;
	}

	public void setTxtHexagonRadius(JTextField txtHexagonRadius) {
		this.txtHexagonRadius = txtHexagonRadius;
	}

	public JTextField getTxtCoordinateX() {
		return txtCoordinateX;
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

	public JButton getBtnHexagonInnerColor() {
		return btnHexagonInnerColor;
	}

	public void setBtnHexagonInnerColor(JButton btnHexagonInnerColor) {
		this.btnHexagonInnerColor = btnHexagonInnerColor;
	}

	public JButton getBtnHexagonBorderColor() {
		return btnHexagonBorderColor;
	}

	public void setBtnHexagonBorderColor(JButton btnHexagonBorderColor) {
		this.btnHexagonBorderColor = btnHexagonBorderColor;
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}


	public boolean isInnerColorConfirmation() {
		return innerColorConfirmation;
	}

	public void setInnerColorConfirmation(boolean innerColorConfirmation) {
		this.innerColorConfirmation = innerColorConfirmation;
	}

	public boolean isBorderColorConfirmation() {
		return borderColorConfirmation;
	}

	public void setBorderColorConfirmation(boolean borderColorConfirmation) {
		this.borderColorConfirmation = borderColorConfirmation;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}
	
}

