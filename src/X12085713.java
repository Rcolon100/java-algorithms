import java.util.Arrays;
import java.util.Scanner;

public class X12085713 extends Polynomial {
	public static void main(String args[]) throws Exception {
		Polynomial p = new X12085713(" X^12"), 
				q = new X12085713("X^2 - X + 1");
		Utility.run(p, q);
	}

	public X12085713(String s) { // complete this code
		
		if(s.length() == 1 && s.charAt(0) == '0'){
			data.addLast(new Term(0.0,0));
		}	
		
		s = s.replaceAll(" ", "");
		s = s.replaceAll("-", "+-");

		Scanner myScanner = new Scanner(s);
		myScanner.useDelimiter("\\+");

		while (myScanner.hasNext()) {
			String term = myScanner.next();
			Term newTerm = new Term();
			Double coefficient = newTerm.coefficient;
			int degree = newTerm.degree;

			if(term.contains("X")){

				String termSplit[] = term.split("X");
				
				if(termSplit.length == 0){ //X by itself
					coefficient = 1.0;
					degree = 1;
				}
				if(termSplit.length == 1){ //all X degree 1
					if(termSplit[0].equals("-")){ //check for -X
						coefficient = -1.0;
						degree = 1; 
					}
					else { //if there's a coefficient in front of X
						coefficient = Double.parseDouble(termSplit[0]); 	
						degree = 1;
					}
				}
				if(termSplit.length == 2){
					if(termSplit[0].equals("")) { //checking for ^
						coefficient = 1.0;
						degree = Integer.parseInt(termSplit[1].substring(1,termSplit[1].length()));
					}
					else if(termSplit[0].equals("-")){
						coefficient = -1.0;
						degree = Integer.parseInt(termSplit[1].substring(1,termSplit[1].length()));
					}
					else {
						coefficient = Double.parseDouble(termSplit[0]);
						degree = Integer.parseInt(termSplit[1].substring(1, termSplit[1].length()));
					}
				}
			}
			else {
				coefficient = Double.parseDouble(term.toString());
				degree = 0;
			}   
			Term finalTerm = new Term(coefficient,degree);
			data.addLast(finalTerm);	   
		}		   
	}	   

	public X12085713() {
		super();
	}

	public Polynomial add(Polynomial p) { //complete this code
		Polynomial ans = new X12085713();
		
		if(this.data.size() == 0) return p;
		if(p.data.size()==0) return this;
		
		try{

			DNode<Term> poly1 = this.data.getFirst();
			DNode<Term> poly2 = p.data.getFirst();

			while ( poly1.getData() != null && poly2.getData() != null){

				int exp1 = poly1.getData().getDegree();
				int exp2 = poly2.getData().getDegree();
				double coef1 = poly1.getData().getCoefficient();
				double coef2 = poly2.getData().getCoefficient();
				
				if(exp1 == exp2){ //check if polynomial exponents are the same
					double newCoef = coef1 + coef2;
					Term newTerm = new Term(newCoef, exp1);
					ans.data.addLast(newTerm);
					poly1 = poly1.getNext();
					poly2 = poly2.getNext();
				}
				else if(exp1 < exp2){
					Term newTerm2 = new Term(coef2, exp2);
					ans.data.addLast(newTerm2);
					poly2 = poly2.getNext();
				}
				else{
					Term newTerm3 = new Term(coef1, exp1);
					ans.data.addLast(newTerm3);
					poly1 = poly1.getNext();
				}
			}
			while(poly1.getData() != null){
				Term current1 = new Term(poly1.getData().getCoefficient(), poly1.getData().getDegree());
				ans.data.addLast(current1);
				poly1 = poly1.getNext();
			}
			while(poly2.getData() != null){
				Term current2 = new Term(poly2.getData().getCoefficient(), poly2.getData().getDegree());
				ans.data.addLast(current2);
				poly2 = poly2.getNext();
			}
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		return ans;
	}

	public Polynomial subtract(Polynomial p) {
		Polynomial ans = new X12085713();
		
		try{
			if(this.data.size() == 0) {
					DNode<Term> poly2 = p.data.getFirst();
					while(poly2.getData() != null){
						Term current1 = new Term(poly2.getData().getCoefficient() * -1.0, poly2.getData().getDegree());
						ans.data.addLast(current1);
						poly2 = poly2.getNext();
					}
					return ans;
			}
			if(p.data.size()==0) return this;
			
			DNode<Term> poly1 = this.data.getFirst();
			DNode<Term> poly2 = p.data.getFirst();
			while ( poly1.getData() != null && poly2.getData() != null){

				int exp1 = poly1.getData().getDegree() , exp2 = poly2.getData().getDegree();
				double coef1 = poly1.getData().getCoefficient(), coef2 = poly2.getData().getCoefficient();
				
				if(exp1 == exp2){//check if polynomial exponents are the same
					if(coef1==coef2){
						poly1 = poly1.getNext();
						poly2 = poly2.getNext();
					}else {
						double newCoef = coef1 - coef2;
						Term newTerm = new Term(newCoef, exp1);
						ans.data.addLast(newTerm);
						poly1 = poly1.getNext();
						poly2 = poly2.getNext();
					}
				}
				else if(exp1 < exp2){
					Term newTerm2 = new Term(coef2, exp2);
					ans.data.addLast(newTerm2);
					poly2 = poly2.getNext();
				}
				else{
					Term newTerm3 = new Term(coef1, exp1);
					ans.data.addLast(newTerm3);
					poly1 = poly1.getNext();
				}
			}
			while(poly1.getData() != null){
				Term current1 = new Term(poly1.getData().getCoefficient() * -1.0, poly1.getData().getDegree());
				ans.data.addLast(current1);
				poly1 = poly1.getNext();
			}
			while(poly2.getData() != null){
				Term current2 = new Term(poly2.getData().getCoefficient() * -1.0, poly2.getData().getDegree());
				ans.data.addLast(current2);
				poly2 = poly2.getNext();
			}
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		return ans;
	}

	public Polynomial multiply(Polynomial p) {
		Polynomial ans = new X12085713();
		
		try{
			DNode<Term> poly1 = this.data.getFirst();
			DNode<Term> poly2 = p.data.getFirst();
				for(DNode <Term> i = poly1; i != null && i.getNext() != null; i = i.getNext()){
					Polynomial partialProduct = new X12085713();
					for(DNode <Term> j = poly2; j != null && j.getNext() != null; j = j.getNext()){
						Double newCoefficient = i.getData().getCoefficient() * j.getData().getCoefficient();
						int newExponent = i.getData().getDegree() + j.getData().getDegree();
						Term newTerm = new Term(newCoefficient, newExponent);
						partialProduct.data.addLast(newTerm);
					}
					ans = ans.add(partialProduct);
				}
				return ans;
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		return ans;
	}

	public Polynomial divide(Polynomial p) throws Exception {
		Polynomial ans = new X12085713();
		
		try{
			
			DNode<Term> poly1 = this.data.getFirst();
			DNode<Term> poly2 = p.data.getFirst();

			if(poly1.getData().getDegree() < poly2.getData().getDegree()) return ans; //base case
			
			else { 
				double newCoefficient = poly1.getData().getCoefficient()/poly2.getData().getCoefficient();
				int newExponent = poly1.getData().getDegree() - poly2.getData().getDegree();
				Term Factor = new Term(newCoefficient, newExponent);
				Polynomial Quotient = new X12085713();
				Polynomial Remainder = new X12085713();
				Quotient.data.addLast(Factor); 
				Remainder = this.subtract(p.multiply(Quotient));
				ans = ans.add(Remainder.divide(p)); //recursive step
				ans = ans.add(Quotient);
			}
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		return ans;
	}

	public Polynomial remainder(Polynomial p) throws Exception {
		Polynomial ans = new X12085713();
		
		try{
			
			DNode<Term> poly1 = this.data.getFirst();
			DNode<Term> poly2 = p.data.getFirst();

			if(poly1.getData().getDegree() < poly2.getData().getDegree()) return this; //base case
			
			else {
				double newCoefficient = poly1.getData().getCoefficient()/poly2.getData().getCoefficient();
				int newExponent = poly1.getData().getDegree() - poly2.getData().getDegree();
				Term Factor = new Term(newCoefficient, newExponent);
				Polynomial Quotient = new X12085713();
				Polynomial Remainder = new X12085713();
				Quotient.data.addLast(Factor); 
				Remainder = this.subtract(p.multiply(Quotient)); //recursive step
				ans = Remainder.remainder(p); //get remainder
			}
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		return ans;
	}
}