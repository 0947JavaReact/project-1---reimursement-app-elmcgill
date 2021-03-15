/**
 * 
 */

document.getElementById("submit").addEventListener("click", function(event){
	event.preventDefault();
	let uname = document.getElementById("username").value;
	let pass = document.getElementById("password").value;
	
	console.log(`Username: ${uname}, Password: ${pass}`);
	
	let uObj = {
		username: uname,
		password: pass	
	}
	
	//Do the request here
	fetch('http://localhost:8080/project1/login', {
		method: 'POST',
		headers: {
      		'Content-Type': 'application/json'
    	},
    	body: JSON.stringify(uObj)
	})
	.then((res) => {
		if(res.status == 200){
			return res.json();
		}
		else{
			alert("Username or password is incorrect!");
		}
	})
	.then((res)=>{
		
		//Reset the form
		document.getElementById("login-form").reset();
		if(res.userType == 0){
			location.href = "../html/employee-dashboard.html";
		}
		else{
			location.href = "../html/manager-dashboard.html";
		}
		
		console.log(res);
	});
	
	
});

document.getElementById("register-redir").addEventListener("click", (event) => {
	location.href = "../html/registration.html";
});