package hr.fer.zemris.java.custom.scripting.nodes;

public class TextNode extends Node {
	
	private String text;
	
	public TextNode(String text) {
		
		if(text == null) {
			throw new NullPointerException("Text cannot be null!");
		}
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	
	@Override
	public String toString() {
		return getText();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TextNode)) {
			return false;
		}
		TextNode other = (TextNode) obj;
		if(this.getText().equals(other.getText())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
