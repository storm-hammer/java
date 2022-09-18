package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;

import java.text.DecimalFormat;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.stack.ObjectStack;
import hr.fer.zemris.java.webserver.RequestContext;

public class SmartScriptEngine {

	private DocumentNode documentNode;
	private RequestContext requestContext;
	
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.requestContext = requestContext;
		this.documentNode = documentNode;
	}
	
	public void execute() {
		documentNode.accept(visitor);
	}
	
	private INodeVisitor visitor = new INodeVisitor() {
		
		private ObjectMultistack stack = new ObjectMultistack();
		
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String var = node.getVariable().asText();
			stack.push(var, new ValueWrapper(node.getStartExpression().asText()));
			int increment = 1;
			if(node.getStepExpression() != null) {
				increment = Integer.parseInt(node.getStepExpression().asText());
			}
			do {
				for(int j = 0; j < node.numberOfChildren(); j++) {
					node.getChild(j).accept(this);
				}
				ValueWrapper temp = stack.pop(var);
				temp.add(increment);
				stack.push(var, temp);
			}while(stack.peek(var).numCompare(node.getEndExpression().asText()) <= 0);
			stack.pop(var);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			ObjectStack tempStack = new ObjectStack();
			Element[] elems = node.getElements();
			
			for(int i = 0; i < elems.length; i++) {
				if(elems[i] instanceof ElementConstantInteger) {
					ElementConstantInteger temp = (ElementConstantInteger)elems[i];
					tempStack.push(temp.getValue());
				} else if(elems[i] instanceof ElementConstantDouble) {
					ElementConstantDouble temp = (ElementConstantDouble)elems[i];
					tempStack.push(temp.getValue());
				} else if(elems[i] instanceof ElementString) {
					ElementString temp = (ElementString)elems[i];
					tempStack.push(temp.getValue());
				} else if(elems[i] instanceof ElementOperator) {
					ElementOperator oper = (ElementOperator)elems[i];
					Object a = tempStack.pop();
					Object b = tempStack.pop();
					tempStack.push(calculateOperation(a, b, oper));
				} else if(elems[i] instanceof ElementFunction) {
					ElementFunction temp = (ElementFunction)elems[i];
					applyFunction(temp, tempStack);
				} else if(elems[i] instanceof ElementVariable) {
					ElementVariable temp = (ElementVariable)elems[i];
					tempStack.push(stack.peek(temp.getName()));
				}
			}
			
			if(tempStack.isEmpty()) {
				return;
			}
			
			ObjectStack swapped = new ObjectStack();
			
			while(!tempStack.isEmpty()) {
				swapped.push(tempStack.pop());
			}
			try {
				while(!swapped.isEmpty()) {
					Object a = swapped.pop();
					if(a instanceof ValueWrapper) {
						ValueWrapper v = (ValueWrapper)a;
						requestContext.write(v.getValue().toString());
					} else {
						requestContext.write(a.toString());
					}
				}
			} catch (IOException e) {
			}
		}

		private void applyFunction(ElementFunction temp, ObjectStack tempStack) {
			switch(temp.getName()) {
				case "@sin" -> {
					Number result = null;
					Object a = tempStack.peek();
					if(a instanceof Double) {
						result = Math.sin(Math.toRadians((Double)tempStack.pop()));
					} else if(a instanceof Integer) {
						result = Math.sin(Math.toRadians((Integer)tempStack.pop()));
					} else if(a instanceof String) {
						result = Math.sin(Math.toRadians(Double.parseDouble((String)tempStack.pop())));
					}
					tempStack.push(result);
				}
				case "@decfmt" -> {
					DecimalFormat f = new DecimalFormat(tempStack.pop().toString());
					Object a = tempStack.pop();
					if(a instanceof Double) {
						tempStack.push(f.format((Double)a));
					} else if(a instanceof Integer) {
						tempStack.push(f.format((Integer)a));
					} else if(a instanceof String) {
						tempStack.push(f.format(Double.parseDouble((String)a)));
					}//inace ex??
				}
				case "@dup" -> {
					tempStack.push(tempStack.peek());
				}
				case "@swap" -> {
					Object a = tempStack.pop(), b = tempStack.pop();
					tempStack.push(a);
					tempStack.push(b);
				}
				case "@setMimeType" -> {
					requestContext.setMimeType(tempStack.pop().toString());
				}
				case "@paramGet" -> {
					Object dv = tempStack.pop();
					String name = tempStack.pop().toString();
					Object value = requestContext.getParameter(name);
					tempStack.push(value == null ? dv : value);
				}
				case "@pparamGet" -> {
					Object dv = tempStack.pop();
					String name = tempStack.pop().toString();
					Object value = requestContext.getPersistentParameter(name);
					tempStack.push(value == null ? dv : value);
				}
				case "@pparamSet" -> {
					String name = tempStack.pop().toString();
					String value = tempStack.pop().toString();
					requestContext.setPersistentParameter(name, value);
				}
				case "@pparamDel" -> {
					requestContext.removePersistentParameter(tempStack.pop().toString());
				}
				case "@tparamGet" -> {
					Object dv = tempStack.pop();
					String name = tempStack.pop().toString();
					Object value = requestContext.getTemporaryParameter(name);
					tempStack.push(value == null ? dv : value);
				}
				case "@tparamSet" -> {
					String name = tempStack.pop().toString();
					String value = tempStack.pop().toString();
					requestContext.setTemporaryParameter(name, value);
				}
				case "@tparamDel" -> {
					requestContext.removeTemporaryParameter(tempStack.pop().toString());
				}
				default -> throw new UnsupportedOperationException("Nepoznata funkcija");
			}
		}

		private Object calculateOperation(Object a, Object b, ElementOperator oper) {
			ValueWrapper first;
			if(a instanceof ValueWrapper) {
				first = (ValueWrapper)a;
			} else {
				first = new ValueWrapper(a);
			}
			switch(oper.getSymbol()) {
				case "+" -> first.add(b);
				case "-" -> first.subtract(b);
				case "*" -> first.multiply(b);
				case "/" -> first.divide(b);
				default -> throw new UnsupportedOperationException("Nedozvoljen operator");
			};
			return first.getValue();
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}};
}
