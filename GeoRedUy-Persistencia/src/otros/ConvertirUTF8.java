package otros;

 public class ConvertirUTF8 {

	public String convertir (String dato){
		
		
		char[] chars =dato.toCharArray();
		
		for(int i=0; i<chars.length;i++){
			
			//if(chars.equals('á')||chars.equals('é')||chars.equals('í')||chars.equals('ó')||chars.equals('ú')||chars.equals('a'))
			
			switch(chars[i]) { // Eleige la opcion acorde al numero de mes
			case 'á':
						chars[i]='a';
			case 'é':
				chars[i]='e';
			case 'í':
				chars[i]='i';
			case 'ó':
				chars[i]='o';
			case 'ú':
				chars[i]='u';
			case 'ñ':
				chars[i]='n';
			
			}
			
			
			
		}
			return dato.toString();
	}
	
}
