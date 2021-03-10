/**
 * 
 */

window.onload = ()=> {
	
	verifyLoggedIn();
	retreiveAllReimbursements();
	
}

document.getElementById("new-ticket").addEventListener('click', showForm);

function showForm(){
	
	console.log("Clicked");
	document.getElementById("new-reimbursement").style.display = "flex";
	
}

document.getElementById("send").addEventListener('click', (event) => {
	
	event.preventDefault();
	
	let amount = document.getElementById("amount").value;
	let date = document.getElementById("date").value;
	let type = document.getElementById("types").value;
	let desc = document.getElementById("desc").value;
	
	console.log(amount, date, type, desc);
	
	document.getElementById("re-form").reset();
	document.getElementById("new-reimbursement").style.display = "none";
	
	retrieveAllReimbursements();
});

function retreiveAllReimbursements(){
	
	//Get the list of reimbursements to append to the table
	
}

function verifyLoggedIn(){
	
	//Check if the user is logged in
	
}