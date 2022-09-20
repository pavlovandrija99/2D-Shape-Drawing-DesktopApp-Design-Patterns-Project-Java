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

public class DlgDonut extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtRadius;
	private JTextField txtInnerRadius;
	public boolean confirm;
	private Color innerColor = Color.WHITE;
	private Color borderColor = Color.BLACK;
	private  boolean innerColorConfirmation;
	private boolean borderColorConfirmation;
	private JTextField txtCoordinateXdon;
	private JTextField txtCoordinateYdon;
	
	private JButton btnInnerColor;
	private JButton btnBorderColor;

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgDonut dialog = new DlgDonut();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgDonut() {
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblCoordinateXdon = new JLabel("Coordinate X:");
			GridBagConstraints gbc_lblCoordinateXdon = new GridBagConstraints();
			gbc_lblCoordinateXdon.insets = new Insets(0, 0, 5, 5);
			gbc_lblCoordinateXdon.gridx = 6;
			gbc_lblCoordinateXdon.gridy = 0;
			contentPanel.add(lblCoordinateXdon, gbc_lblCoordinateXdon);
		}
		{
			txtCoordinateXdon = new JTextField();
			GridBagConstraints gbc_txtCoordinateXdon = new GridBagConstraints();
			gbc_txtCoordinateXdon.insets = new Insets(0, 0, 5, 5);
			gbc_txtCoordinateXdon.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtCoordinateXdon.gridx = 8;
			gbc_txtCoordinateXdon.gridy = 0;
			contentPanel.add(txtCoordinateXdon, gbc_txtCoordinateXdon);
			txtCoordinateXdon.setColumns(10);
		}
		{
			JLabel lblCoordinateYdon = new JLabel("CoordinateY:");
			GridBagConstraints gbc_lblCoordinateYdon = new GridBagConstraints();
			gbc_lblCoordinateYdon.insets = new Insets(0, 0, 5, 5);
			gbc_lblCoordinateYdon.gridx = 6;
			gbc_lblCoordinateYdon.gridy = 1;
			contentPanel.add(lblCoordinateYdon, gbc_lblCoordinateYdon);
		}
		{
			txtCoordinateYdon = new JTextField();
			GridBagConstraints gbc_txtCoordinateYdon = new GridBagConstraints();
			gbc_txtCoordinateYdon.insets = new Insets(0, 0, 5, 5);
			gbc_txtCoordinateYdon.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtCoordinateYdon.gridx = 8;
			gbc_txtCoordinateYdon.gridy = 1;
			contentPanel.add(txtCoordinateYdon, gbc_txtCoordinateYdon);
			txtCoordinateYdon.setColumns(10);
		}
		{
			JLabel lblRadius = new JLabel("Radius:");
			GridBagConstraints gbc_lblRadius = new GridBagConstraints();
			gbc_lblRadius.insets = new Insets(0, 0, 5, 5);
			gbc_lblRadius.gridx = 6;
			gbc_lblRadius.gridy = 2;
			contentPanel.add(lblRadius, gbc_lblRadius);
		}
		{
			txtRadius = new JTextField();
			GridBagConstraints gbc_txtRadius = new GridBagConstraints();
			gbc_txtRadius.gridwidth = 2;
			gbc_txtRadius.insets = new Insets(0, 0, 5, 0);
			gbc_txtRadius.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtRadius.gridx = 8;
			gbc_txtRadius.gridy = 2;
			contentPanel.add(txtRadius, gbc_txtRadius);
			txtRadius.setColumns(10);
		}
		{
			JLabel lblInnerRadius = new JLabel("Inner radius:");
			GridBagConstraints gbc_lblInnerRadius = new GridBagConstraints();
			gbc_lblInnerRadius.insets = new Insets(0, 0, 5, 5);
			gbc_lblInnerRadius.gridx = 6;
			gbc_lblInnerRadius.gridy = 3;
			contentPanel.add(lblInnerRadius, gbc_lblInnerRadius);
		}
		{
			txtInnerRadius = new JTextField();
			GridBagConstraints gbc_txtInnerRadius = new GridBagConstraints();
			gbc_txtInnerRadius.gridwidth = 2;
			gbc_txtInnerRadius.insets = new Insets(0, 0, 5, 0);
			gbc_txtInnerRadius.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtInnerRadius.gridx = 8;
			gbc_txtInnerRadius.gridy = 3;
			contentPanel.add(txtInnerRadius, gbc_txtInnerRadius);
			txtInnerRadius.setColumns(10);
		}
		{
			btnInnerColor = new JButton("Inner Color");
			btnInnerColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					innerColor = JColorChooser.showDialog(null, "Chose a color.", innerColor);
					btnInnerColor.setBackground(innerColor);
					innerColorConfirmation = true;
				}
			});
			GridBagConstraints gbc_btnInnerColor = new GridBagConstraints();
			gbc_btnInnerColor.insets = new Insets(0, 0, 0, 5);
			gbc_btnInnerColor.gridx = 6;
			gbc_btnInnerColor.gridy = 5;
			contentPanel.add(btnInnerColor, gbc_btnInnerColor);
		}
		{
			btnBorderColor = new JButton("Border Color");
			btnBorderColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					borderColor = JColorChooser.showDialog(null, "Chose a color.", borderColor);
					btnBorderColor.setBackground(borderColor);
					borderColorConfirmation = true;
				}
			});
			GridBagConstraints gbc_btnBorderColor = new GridBagConstraints();
			gbc_btnBorderColor.insets = new Insets(0, 0, 0, 5);
			gbc_btnBorderColor.gridx = 8;
			gbc_btnBorderColor.gridy = 5;
			contentPanel.add(btnBorderColor, gbc_btnBorderColor);
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

	public JTextField getTxtCoordinateXdon() {
		return txtCoordinateXdon;
	}

	public void setTxtCoordinateXdon(JTextField txtCoordinateXdon) {
		this.txtCoordinateXdon = txtCoordinateXdon;
	}

	public JTextField getTxtCoordinateYdon() {
		return txtCoordinateYdon;
	}

	public void setTxtCoordinateYdon(JTextField txtCoordinateYdon) {
		this.txtCoordinateYdon = txtCoordinateYdon;
	}

	public void setTxtCoordinateYrec(JTextField txtCoordinateYrec) {
		this.txtCoordinateYdon = txtCoordinateYrec;
	}

	public JTextField getTxtRadius() {
		return txtRadius;
	}

	public void setTxtRadius(JTextField txtRadius) {
		this.txtRadius = txtRadius;
	}

	public JTextField getTxtInnerRadius() {
		return txtInnerRadius;
	}

	public void setTxtInnerRadius(JTextField txtInnerRadius) {
		this.txtInnerRadius = txtInnerRadius;
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

	
	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public void setBtnInnerColor(JButton btnInnerColor) {
		this.btnInnerColor = btnInnerColor;
	}

	public JButton getBtnBorderColor() {
		return btnBorderColor;
	}

	public void setBtnBorderColor(JButton btnBorderColor) {
		this.btnBorderColor = btnBorderColor;
	}
	
}
