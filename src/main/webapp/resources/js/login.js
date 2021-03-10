/**
 * 
 */

document.getElementById("submit").addEventListener("click", function(event){
	event.preventDefault();
	let uname = document.getElementById("username").value;
	let pass = document.getElementById("password").value;
	
	console.log(`Username: ${uname}, Password: ${pass}`);
	
	//Do the request here
	fetch(``)
	.then((res) => {
		if(res.status == 200){
			return res.json();
		}
	});
	
	
});

document.getElementById("register-redir").addEventListener("click", (event) => {
	location.href = "../html/registration.html";
});