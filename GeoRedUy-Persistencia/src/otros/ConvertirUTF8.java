package otros;

 public class ConvertirUTF8 {

	public String convertir (String dato){
		
		
		char[] chars =dato.toCharArray();
		
		for(int i=0; i<chars.length;i++){
			
			//if(chars.equals('�')||chars.equals('�')||chars.equals('�')||chars.equals('�')||chars.equals('�')||chars.equals('a'))
			
			switch(chars[i]) { // Eleige la opcion acorde al numero de mes
			case '�':
						chars[i]='a';
			case '�':
				chars[i]='e';
			case '�':
				chars[i]='i';
			case '�':
				chars[i]='o';
			case '�':
				chars[i]='u';
			case '�':
				chars[i]='n';
			
			}
			
			
			
		}
			return dato.toString();
	}
	
}
