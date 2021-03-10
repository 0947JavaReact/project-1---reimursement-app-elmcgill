/**
 * 
 */

document.getElementById("submit").addEventListener("click", (event) => {
	event.preventDefault();
	let uname = document.getElementById("username").value;
	let email = document.getElementById("email").value;
	let pass1 = document.getElementById("password1").value;
	let pass2 = document.getElementById("password2").value;
	
	if(validateEmail(email)){
		console.log("horay!");
	}
	else{
		document.getElementById("email").value = '';
	}
	
	if(validatePassword(pass1, pass2)){
		console.log("Condratulations");
	}
	else{
		document.getElementById("password1").value = '';
		document.getElementById("password2").value = '';
	}

});

document.getElementById("login-redir").addEventListener("click", (event) => {
	location.href = "../html/index.html";
});

function validateEmail(mail) {

	if (/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(mail)) {
		return (true)
	}
	alert("You have entered an invalid email address!")
	return (false)

}

function validatePassword(pass1, pass2){
	if(pass1 === pass2){
		return true;
	}
	else{
		alert("Your passwords must match!");
	return false;
	}
}