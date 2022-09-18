package hr.fer.oprpp1.custom.scripting.nodes;

public class DocumentNode extends Node {
	
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < this.numberOfChildren(); i++) {
			sb.append(this.getChild(i).toString());
		}
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof DocumentNode)) {
			return false;
		}
		DocumentNode other = (DocumentNode) obj;
		if(this.numberOfChildren() != other.numberOfChildren()) {
			return false;
		}
		for(int i = 0, limit = other.numberOfChildren(); i < limit; i++) {
			if(!this.getChild(i).equals(other.getChild(i))) {
				return false;
			}
		}
		return true;
	}
}
