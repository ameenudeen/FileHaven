/* Author	: Tang Jun Hao
 * Function	: validate empty(string input)
 * 			  validate_letter_number(String input)
 * 			  validate_letter(input)
 */

function validate_empty(input){
		if(input==null){
			return false;
		}
		for(var i=0;i<input.length;i++){
			if(input.charAt(i)!=' '){

				return true;
			}
		}
		return false;
}

function validate_letter(input){
	var reg = /^[A-Za-z\s]+$/;  
	if(reg.test(input)){
		return true;
	}
	return false;
}

function validate_letter_number(input){
	var reg = /^[A-Za-z0-9]+$/;  
	if(reg.test(input)){
		return true;
	}
	return false;
}