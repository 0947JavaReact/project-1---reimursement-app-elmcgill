/**
 * 
 */
window.onbeforeunload = () => {
	console.log('before unload');
	fetch('http://localhost:8080/project1/getSession');
}

document.getElementById("new-ticket").addEventListener('click', showForm);

document.getElementById("logout").addEventListener('click', logout);

let userId;

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


var retreiveAllReimbursements = async () => {
	let res = await fetch(`http://localhost:8080/project1/getAllReimbursementsById?id=${userId}`);
	let obj = await res.json();
	console.log(obj);
	
	
	
	let table = document.getElementById("re-table");
	
	table.innerHTML = '<tr><th>STATUS</th><th>TYPE</th><th>AMOUNT</th><th>SUBMITTED DATE</th><th>RESOLVED DATE</th><th>RESOLVED BY</th></tr>';

	for(let i=0; i<obj.length; i++){
		console.log(i);
		let row = table.insertRow(i+1);
		let status = row.insertCell(0);
		status.innerHTML = obj[i].statusString;
		let type = row.insertCell(1);
		type.innerHTML = obj[i].typeString;
		let amount = row.insertCell(2);
		amount.innerHTML = obj[i].reAmount;
		let subDate = row.insertCell(3);
		subDate.innerHTML = new Date(obj[i].reSubmitted).toDateString();
		let resDate = row.insertCell(4);
		
		if(obj[i].reResolved !== null){
			resDate.innerHTML = new Date(obj[i].reResolved).toDateString();
		}
		else{
			resDate.innerHTML = 'N/A';
		}
		
		let resolver = row.insertCell(5);
		if(obj[i].resolverString !== null){
			resolver.innerHTML = obj[i].resolverString;
		}
		else{
			resolver.innerHTML = 'N/A';
		}
	}
}

async function verifyLoggedIn(){
	let res = await fetch('http://localhost:8080/project1/getSession');
	let obj = await res.json();
	
	if(obj.userid < 0){
		location.href = "../html/index.html";
	}
	else{
		userId = obj.userid;
	}
}


document.getElementById("send").addEventListener('click', async (event) => {
	
	event.preventDefault();
	
	let amount = document.getElementById("amount").value;
	let date = document.getElementById("date").value;
	let type = document.getElementById("types").value;
	let desc = document.getElementById("desc").value;
	
	console.log(amount, Date.parse(date), type, desc, userId);
	
	let obj = {
		amount: amount,
		date: Date.parse(date),
		type: type,
		desc: desc,
		author: userId
	};
	
	
	let res =  await fetch('http://localhost:8080/project1/newReimbursement', {
		method: 'POST',
		headers: {
      		'Content-Type': 'application/json'
    	},
    	body: JSON.stringify(obj)
	});
	
	let retObj = await res.json();
	console.log(retObj);
	
	
	document.getElementById("re-form").reset();
	document.getElementById("new-reimbursement").style.display = "none";
	
	await retreiveAllReimbursements();
});

let init = async () => {
	await verifyLoggedIn();
	console.log(userId);
	await retreiveAllReimbursements();
}

init();