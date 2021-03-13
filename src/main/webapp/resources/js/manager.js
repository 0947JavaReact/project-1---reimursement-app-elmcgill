/**
 * 
 */

window.onbeforeunload = () => {
	console.log('before unload');
	fetch('http://localhost:8080/project1/getSession');
}

document.getElementById("logout").addEventListener('click', logout);

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
	
}

async function deny(id){
	
	console.log(`Deny this reimbursement ${id}`);
	
}

function desc(id){
	
	console.log(`Show the description for ${id}`);
	
}

async function getPendingReimbursements(){
	
	let res = await fetch(`http://localhost:8080/project1/getPendingReimbursements`);
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
	
	/*
	for(let i=0; i<obj.length; i++){
		
		let row=table.insertRow(i+1);
		row.id = obj[i].reId;
		let status = row.insertCell(0);
		status.innerHTML = obj[i].statusString;
		let type = row.insertCell(1);
		type.innerHTML = obj[i].typeString;
		let amount = row.insertCell(2);
		amount.innerHTML = obj[i].reAmount;
		let employee = row.insertCell(3);
		employee.innerHTML = obj[i].authorString;
		let subDate = row.insertCell(4);
		subDate.innerHTML = new Date(obj[i].reSubmitted).toDateString();
		let appDen = row.insertCell(5);
		appDen.innerHTML = `<button onclick="approve(${obj[i].reId})">Approve</button> <button onclick="deny(${obj[i].reId})">Deny</button>`;
		let descRow = table.insertRow(i+1);
		let desc = descRow.insertCell(0);
		desc.setAttribute("colspan", "6");
		desc.innerHTML = obj[i].reDesc;
	}
	*/
	
}

let init = async () => {
	await verifyLoggedIn();
	console.log(userId);
	await getPendingReimbursements();
}

init();