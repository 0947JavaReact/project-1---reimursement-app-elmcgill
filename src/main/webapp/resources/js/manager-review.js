/**
 * 
 */

/**
 * 
 */

window.onbeforeunload = () => {
	console.log('before unload');
	fetch('http://localhost:8080/project1/getSession');
}

document.getElementById("logout").addEventListener('click', logout);

document.getElementById("home").addEventListener('click', () => {
	location.href = "../html/manager-dashboard.html";
});

let userId;

function logout(){
	fetch('http://localhost:8080/project1/logout')
	.then(() => {
		location.href = "../html/index.html";
	});
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

async function approve(id) {
	
	console.log(`Approved this reimbursement ${id}`);
	
	let date = Date.now();
	
	obj = {
		userid : userId,
		reid : id,
		date: date
	}
	
	console.log(date);
	
	let res = await fetch(`http://localhost:8080/project1/approveReimbursement`,
	{
		method: 'POST',
		headers: {
      		'Content-Type': 'application/json'
    	},
    	body: JSON.stringify(obj)
	});
	
	await getPendingReimbursements();
	
}

async function deny(id){
	
	console.log(`Deny this reimbursement ${id}`);
	
	let date = Date.now();
	
	obj = {
		userid : userId,
		reid : id,
		date: date
	}
	
	console.log(date);
	
	let res = await fetch(`http://localhost:8080/project1/denyReimbursement`,
	{
		method: 'POST',
		headers: {
      		'Content-Type': 'application/json'
    	},
    	body: JSON.stringify(obj)
	});
	
	await getPendingReimbursements();
	
}

async function getPendingReimbursements(){
	
	let res = await fetch(`http://localhost:8080/project1/filterReimbursements?status=${0}`);
	let obj = await res.json();
	console.log(obj);
	
	let table = document.getElementById("re-table");
	
	table.innerHTML = '<tr><th>STATUS</th><th>TYPE</th><th>AMOUNT</th><th>EMPLOYEE</th><th>SUBMITTED DATE</th><th>APPROVE/DENY</th></tr>';
	
	obj.forEach((obj)=> {
		let index = 1;
		
		let row=table.insertRow(index++);
		row.id = obj.reId;
		let status = row.insertCell(0);
		status.innerHTML = obj.statusString;
		let type = row.insertCell(1);
		type.innerHTML = obj.typeString;
		let amount = row.insertCell(2);
		amount.innerHTML = obj.reAmount;
		let employee = row.insertCell(3);
		employee.innerHTML = obj.authorString;
		let subDate = row.insertCell(4);
		subDate.innerHTML = new Date(obj.reSubmitted).toDateString();
		let appDen = row.insertCell(5);
		appDen.innerHTML = `<button onclick="approve(${obj.reId})">Approve</button> <button onclick="deny(${obj.reId})">Deny</button>`;
		let descRow = table.insertRow(index++);
		let desc = descRow.insertCell(0);
		desc.setAttribute("colspan", "6");
		desc.className = "desc";
		desc.innerHTML = `<h3>Description:</h3><p>${obj.reDesc}</p>`;
		
	});
	
}

let init = async () => {
	await verifyLoggedIn();
	console.log(userId);
	await getPendingReimbursements();
}

init();