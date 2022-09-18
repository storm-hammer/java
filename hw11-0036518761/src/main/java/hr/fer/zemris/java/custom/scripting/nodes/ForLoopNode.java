package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

public class ForLoopNode extends Node {
	
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
		if(variable == null || startExpression == null || endExpression == null) {
			throw new NullPointerException("Only the step expression can be null!");
		}
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	public ElementVariable getVariable() {
		return variable;
	}

	public Element getStartExpression() {
		return startExpression;
	}

	public Element getEndExpression() {
		return endExpression;
	}

	public Element getStepExpression() {
		return stepExpression;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{$FOR ");
		
		sb.append(variable.asText()+" ");
		sb.append(startExpression.asText()+" ");
		sb.append(endExpression.asText()+" ");
		if(stepExpression != null) {
			sb.append(stepExpression.asText());
		}
		sb.append("$}");
		
		for(int i = 0; i < this.numberOfChildren(); i++) {
			sb.append(this.getChild(i).toString());
		}
		sb.append("{$END$}\n");
		
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ForLoopNode)) {
			return false;
		}
		ForLoopNode other = (ForLoopNode)obj;
		if(this.numberOfChildren() != other.numberOfChildren()) {
			return false;
		}
		if(!other.startExpression.asText().equals(this.startExpression.asText()) || !other.variable.asText().equals(this.variable.asText())
				|| !other.endExpression.asText().equals(this.endExpression.asText()) || !other.stepExpression.asText().equals(this.stepExpression.asText())) {
			return false;//asText ispitiva jedino polje po kojem bi se mogli razlikovati objekti tog razreda, u ovom slucaju funkcionira kao equals
		}
		for(int i = 0, limit = this.numberOfChildren(); i < limit; i++) {
			if(!this.getChild(i).equals(other.getChild(i))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
	
}
