/**
 * 
 */

window.onload = ()=> {
	verifyLoggedIn();
	console.log('beforeGetAllRe');
	retreiveAllReimbursements();
	
}

window.onbeforeunload = () => {
	console.log('before unload');
	fetch('http://localhost:8080/project1/getSession');
}

document.getElementById("new-ticket").addEventListener('click', showForm);

document.getElementById("logout").addEventListener('click', logout);

function logout(){
	fetch('http://localhost:8080/project1/logout')
	.then(() => {
		location.href = "../html/index.html";
	});
}

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
	fetch('http://localhost:8080/project1/getSession')
	.then((res)=>{
		console.log('fetch complete');
		return res.json();
	}).then((res)=>{
		if(res.userid < 0){
			location.href = "../html/index.html";
		}
		else{
			console.log('you may proceed');
		}
		console.log(res);
	});
}