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

public class DlgCircle extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtRadius;
	private boolean confirm;
	

	private Color innerColor = Color.WHITE;
	private Color borderColor = Color.BLACK;
	private boolean innerColorConfirmation;
	private boolean borderColorConfirmation;
	private JTextField txtCoordinateXcir;
	private JTextField txtCoordinateYcir;
	
	private JButton btnInnerColor;
	private JButton btnColor;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgCircle dialog = new DlgCircle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgCircle() {
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblCoordinateXcir = new JLabel("Coordinate X:");
			GridBagConstraints gbc_lblCoordinateXcir = new GridBagConstraints();
			gbc_lblCoordinateXcir.insets = new Insets(0, 0, 5, 5);
			gbc_lblCoordinateXcir.gridx = 2;
			gbc_lblCoordinateXcir.gridy = 0;
			contentPanel.add(lblCoordinateXcir, gbc_lblCoordinateXcir);
		}
		{
			txtCoordinateXcir = new JTextField();
			GridBagConstraints gbc_txtCoordinateXcir = new GridBagConstraints();
			gbc_txtCoordinateXcir.insets = new Insets(0, 0, 5, 0);
			gbc_txtCoordinateXcir.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtCoordinateXcir.gridx = 8;
			gbc_txtCoordinateXcir.gridy = 0;
			contentPanel.add(txtCoordinateXcir, gbc_txtCoordinateXcir);
			txtCoordinateXcir.setColumns(10);
		}
		{
			JLabel lblCoordinateYcir = new JLabel("Coordinate Y:");
			GridBagConstraints gbc_lblCoordinateYcir = new GridBagConstraints();
			gbc_lblCoordinateYcir.insets = new Insets(0, 0, 5, 5);
			gbc_lblCoordinateYcir.gridx = 2;
			gbc_lblCoordinateYcir.gridy = 1;
			contentPanel.add(lblCoordinateYcir, gbc_lblCoordinateYcir);
		}
		{
			txtCoordinateYcir = new JTextField();
			GridBagConstraints gbc_txtCoordinateYcir = new GridBagConstraints();
			gbc_txtCoordinateYcir.insets = new Insets(0, 0, 5, 0);
			gbc_txtCoordinateYcir.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtCoordinateYcir.gridx = 8;
			gbc_txtCoordinateYcir.gridy = 1;
			contentPanel.add(txtCoordinateYcir, gbc_txtCoordinateYcir);
			txtCoordinateYcir.setColumns(10);
		}
		{
			JLabel lblRadius = new JLabel("Radius:");
			GridBagConstraints gbc_lblRadius = new GridBagConstraints();
			gbc_lblRadius.insets = new Insets(0, 0, 5, 5);
			gbc_lblRadius.gridx = 2;
			gbc_lblRadius.gridy = 2;
			contentPanel.add(lblRadius, gbc_lblRadius);
		}
		{
			txtRadius = new JTextField();
			GridBagConstraints gbc_txtRadius = new GridBagConstraints();
			gbc_txtRadius.insets = new Insets(0, 0, 5, 0);
			gbc_txtRadius.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtRadius.gridx = 8;
			gbc_txtRadius.gridy = 2;
			contentPanel.add(txtRadius, gbc_txtRadius);
			txtRadius.setColumns(10);
		}
		{
			btnColor = new JButton("Border Color");
			btnColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					borderColor = JColorChooser.showDialog(null,"Chose a color.", borderColor);
					btnColor.setBackground(borderColor);
					borderColorConfirmation = true;
				}
			});
			GridBagConstraints gbc_btnColor = new GridBagConstraints();
			gbc_btnColor.insets = new Insets(0, 0, 0, 5);
			gbc_btnColor.gridx = 2;
			gbc_btnColor.gridy = 4;
			contentPanel.add(btnColor, gbc_btnColor);
		}
		{
		    btnInnerColor = new JButton("Inner Color");
			btnInnerColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					innerColor = JColorChooser.showDialog(null,"Chose a color.", innerColor);
					btnInnerColor.setBackground(innerColor);
					innerColorConfirmation = true;
				}
			});
			GridBagConstraints gbc_btnInnerColor = new GridBagConstraints();
			gbc_btnInnerColor.insets = new Insets(0, 0, 0, 5);
			gbc_btnInnerColor.gridx = 4;
			gbc_btnInnerColor.gridy = 4;
			contentPanel.add(btnInnerColor, gbc_btnInnerColor);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
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

	
	

	public JTextField getTxtCoordinateXcir() {
		return txtCoordinateXcir;
	}

	public void setTxtCoordinateXcir(JTextField txtCoordinateXcir) {
		this.txtCoordinateXcir = txtCoordinateXcir;
	}

	public JTextField getTxtCoordinateYcir() {
		return txtCoordinateYcir;
	}

	public void setTxtCoordinateYcir(JTextField txtCoordinateYcir) {
		this.txtCoordinateYcir = txtCoordinateYcir;
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

	public JTextField getTxtRadius() {
		return txtRadius;
	}

	public void setTxtRadius(JTextField txtRadius) {
		this.txtRadius = txtRadius;
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

	
	public JButton getBtnColor() {
		return btnColor;
	}

	public void setBtnColor(JButton btnColor) {
		this.btnColor = btnColor;
	}

	
	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public void setBtnInnerColor(JButton btnInnerColor) {
		this.btnInnerColor = btnInnerColor;
	}
	
	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}
	

}
