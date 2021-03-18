/**
 * 
 */

window.onload = () => {
	document.getElementById("emp-radio").checked = true;
}

document.getElementById("submit").addEventListener("click", async (event) => {
	event.preventDefault();
	let uname = document.getElementById("username").value;
	let email = document.getElementById("email").value;
	let pass1 = document.getElementById("password1").value;
	let pass2 = document.getElementById("password2").value;
	let getSelectedValue = document.querySelector('input[name="rad"]:checked').value;
	if (!validateEmail(email)) {
		document.getElementById("email").value = '';
	}
	else if (!validatePassword(pass1, pass2)) {
		document.getElementById("password1").value = '';
		document.getElementById("password2").value = '';
	}
	else if(getSelectedValue === null){
		alert('You must include your job title');
	}
	else {
		let registerObj = {
			username: uname,
			email: email,
			password: pass1,
			role: getSelectedValue
		};

		let res = await fetch('http://localhost:8080/project1/register', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(registerObj)
		});

		if (res.status !== 200) {
			let message = await res.json();
			alert(message.message);
			document.getElementById("reg-form").reset();
		}
		else {
			let message = await res.json();
			document.getElementById("reg-form").reset();
			location.href = "../html/index.html";
		}
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

function validatePassword(pass1, pass2) {
	if (pass1 === pass2) {
		return true;
	}
	else {
		alert("Your passwords must match!");
		return false;
	}
}