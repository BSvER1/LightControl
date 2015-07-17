package lightcontrol.gui.sequence.constructs;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class SequenceChannelBarButton extends JButton {

	private int position; //position in the layout
	private int btnlength; // length of this button

	public SequenceChannelBarButton() {
		super("");
		position = 1;
		btnlength = SequenceChannelBar.MAX_SEGMENTS;
	}
	
	public SequenceChannelBarButton(int position, int length) {
		this.position = position;
		this.btnlength = length;
	}
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getLength() {
		return btnlength;
	}

	public void setLength(int length) {
		this.btnlength = length;
	}

}
